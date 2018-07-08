/**
 * Класс служит хранения информации о водителе
 *
 * @version 1.0
 */

class Driver {
    private String firstName;
    private String name;
    private String secondName;
    private String rate;
    private double salary;

    Driver() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return  "Фамилия='" + firstName + '\'' +
                ", Имя='" + name + '\'' +
                ", Отчество='" + secondName + '\'' +
                ", стратегия оплаты='" + rate + '\'' +
                ", Среднемесячная заработная плата=" + salary;
    }
}
