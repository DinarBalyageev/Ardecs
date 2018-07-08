import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Класс служит хранения списка водителей и производит операции на списком
 *
 * @version 1.0
 */

public class Drivers {
    private ArrayList<Driver> drivers = new ArrayList<>();

    @XmlRootElement(name = "drivers")
    public static class DriversList {
        private ArrayList<Driver> driversList = new ArrayList<>();

        private void add(ArrayList<Driver> drivers) {
            driversList.addAll(drivers);
        }

        @XmlElement(name = "driver")
        public List<Driver> getDriver() {
            return driversList;
        }

        public void setDriver(ArrayList<Driver> driversList) {
            this.driversList = driversList;
        }
    }
    /**
     * Компаратор для сортировки по увеличению заработной платы. В случае при
     * совпадении зарплаты – упорядочивает данные по ФИО в лексикографическом порядке
     */
    private Comparator<Driver> sortInc = new Comparator<Driver>() {
        @Override
        public int compare(Driver o1, Driver o2) {
            int flag = (int) (o1.getSalary() - o2.getSalary());
            if (flag == 0) flag = o1.getFirstName().compareTo(o2.getFirstName());
            if (flag == 0) flag = o1.getName().compareTo(o2.getName());
            if (flag == 0) flag = o1.getSecondName().compareTo(o2.getSecondName());
            return flag;
        }
    };
    /**
     * Компаратор для сортировки по уменьшению заработной платы. В случае при
     * совпадении зарплаты – упорядочивает данные по ФИО в лексикографическом порядке
     */
    private Comparator<Driver> sortDec = new Comparator<Driver>() {
        @Override
        public int compare(Driver o1, Driver o2) {
            int flag = (int) (o2.getSalary() - o1.getSalary());
            if (flag == 0) flag = o1.getFirstName().compareTo(o2.getFirstName());
            if (flag == 0) flag = o1.getName().compareTo(o2.getName());
            if (flag == 0) flag = o1.getSecondName().compareTo(o2.getSecondName());
            return flag;
        }
    };

    /**
     * Метод загружает список из XML файда
     */
    void outXML() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя файла для загрузки");
        String src = scanner.nextLine();
        File file = new File("src/xml/" + src + ".txt");
        if (file.exists()) {
            try {
                JAXBContext context = JAXBContext.newInstance(DriversList.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                DriversList driverList = (DriversList) unmarshaller.unmarshal(file);
                drivers.clear();
                drivers.addAll(driverList.driversList);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            System.out.println("Загрузка произведена успешно");
        } else System.out.println("Файл " + src + " не найден");
    }

    /**
     * Метод сохраняет список в XML файл
     */
    public void inXML() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя файла для сохранения");
        String src = scanner.nextLine();
        File file = new File("src/xml/" + src + ".txt");
        DriversList driverList = new DriversList();
        driverList.add(drivers);
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(DriversList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(driverList, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.println("Сохранение произведено успешно");

    }

    /**
     * Метод для добавления водителя с список
     */
    void add() {
        Scanner scanner = new Scanner(System.in);
        Driver driver = new Driver();
        System.out.println("Введите фамилию нового водителя");
        driver.setFirstName(scanner.nextLine());
        System.out.println("Введите имя нового водителя");
        driver.setName(scanner.nextLine());
        System.out.println("Введите отчество нового водителя");
        driver.setSecondName(scanner.nextLine());
        System.out.println("Введите стратегию оплаты: 1 - почасовая, 2 - фиксированная");
        Boolean exit = false;
        while (!exit) {
            String next = scanner.nextLine();
            double salary;
            switch (next) {
                case "1":
                    System.out.println("Введите почасовую ставку");
                    driver.setRate("Почасовая");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Введено не число");
                        scanner.next();
                    }
                    salary = scanner.nextDouble();
                    driver.setSalary(salary * 20.8 * 8);
                    exit = true;
                    break;
                case "2":
                    System.out.println("Введите фиксированную месячную оплату");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Введено не число");
                        scanner.next();
                    }
                    salary = scanner.nextDouble();
                    driver.setSalary(salary);
                    driver.setRate("Фиксированная");
                    exit = true;
                    break;
                default:
                    System.out.println("Неверный ввод. Введите стратегию оплаты: 1 - почасовая, 2 - фиксированная");
            }
        }
        System.out.println("Водитель успешно добавлен");
        drivers.add(driver);
    }
    /**
     * Метод для удаления водителя из списка по фамилии и имени
     */
    void delete() {
        if (drivers.size() != 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите фамилию");
            String firstName = scanner.nextLine();
            System.out.println("Введите имя");
            String secondName = scanner.nextLine();
            if (drivers.removeIf(driver -> driver.getFirstName().equals(firstName)
                    && driver.getName().equals(secondName))) System.out.println("Водитель успешно удален");
            else System.out.println("Водитель не найден");
        } else System.out.println("Список водителей пуст");
    }

    /**
     * Метод для печати всего списка водителей
     */
    void printAll() {
        if (drivers.size() != 0) {
            drivers.forEach(System.out::println);
        } else System.out.println("Список водителей пуст");
    }

    /**
     * Метод для печати N водителей
     */
    void print() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите число для вывода");
        while (!scanner.hasNextInt()) {
            System.out.println("Введено не число");
            scanner.next();
        }
        int count = scanner.nextInt();
        if (drivers.size() != 0) {
            if (count <= drivers.size()) {
                drivers.stream().limit(count).forEach(System.out::println);
            } else System.out.println("Введенное число больше количества водителей в списке");
        } else System.out.println("Список водителей пуст");
    }

    /**
     * Метод для сортировки водителей по увеличению ЗП
     */
    void sortByInc() {
        if (drivers.size() != 0) {
            drivers.sort(sortInc);
            System.out.println("Произведена сортировка по увеличению");
        } else System.out.println("Список водителей пуст");
    }

    /**
     * Метод для сортировки водителей по уменьшению ЗП
     */
    void sortByDec() {
        if (drivers.size() != 0) {
            drivers.sort(sortDec);
            System.out.println("Произведена сортировка по уменьшению");
        } else System.out.println("Список водителей пуст");
    }
}
