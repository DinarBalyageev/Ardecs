import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Drivers driversList = new Drivers();
        Boolean exit = false;
        String menu = "Menu:\n" +
                "1 Загрузить файл\n" +
                "2 Сохранить файл\n" +
                "3 Добавить водителя\n" +
                "4 Удалить водителя\n" +
                "5 Напечатать всех\n" +
                "6 Напечатать N первых элементов\n" +
                "7 Сортировать по возрастанию среднемесячного заработка\n" +
                "8 Сортировать по убыванию среднемесячного заработка\n" +
                "9 Выход";
        Scanner scanner = new Scanner(System.in);
        System.out.println(menu);
        while (!exit) {
            String next = scanner.nextLine();
            switch (next) {
                case "1":
                    driversList.outXML();
                    break;
                case "2":
                    driversList.inXML();
                    break;
                case "3":
                    driversList.add();
                    break;
                case "4":
                    driversList.delete();
                    break;
                case "5":
                    driversList.printAll();
                    break;
                case "6":
                    driversList.print();
                    break;
                case "7":
                    driversList.sortByInc();
                    break;
                case "8":
                    driversList.sortByDec();
                    break;
                case "9":
                    System.out.println("Вы уверенно что хотите выйти? Y-да, N-нет.");
                    while (true) {
                        String str = scanner.nextLine();
                        if (str.equals("Y")) {
                            exit = true;
                            break;
                        } else if (str.equals("N")) break;
                        else System.out.println("Введите Y-для выхода, N-для выхода в основное меню");
                    }
                    break;
                case "":
                    System.out.println(menu);
                    break;
                default:
                    System.out.println("Неизвестная команда");
            }
        }
    }
}
