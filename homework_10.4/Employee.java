package homework104;

public abstract class Employee implements Salary {
    public String name;
    public double permSalary;
    public Company company;

    public Employee(String name, double permSalary, Company company) {
        this.name =  name;
        this.permSalary = permSalary;
        this.company = company;
    }

    public String getName() {
        return name;
    }
}
