package homework104;

public class Manager extends Employee {

    public Manager(String name, double permSalary, Company company) {
        super(name, permSalary, company);
    }

    @Override
    public double getMonthSalary() {
        return Math.round((115000 + Math.random()*25000)*0.05*10)/10d + permSalary;
    }
}
