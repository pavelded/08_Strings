package homework104;

public class Operator extends Employee {

    public Operator(String name, double permSalary, Company company) {
        super(name, permSalary, company);
    }

    @Override
    public double getMonthSalary() {
        return permSalary;
    }
}
