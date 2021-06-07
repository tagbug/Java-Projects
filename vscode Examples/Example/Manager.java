package Example;

public class Manager extends Employee {
    private double bounds;

    public double getSalary() {
        return super.getSalary() + bounds;
    }

    public double getBounds() {
        return bounds;
    }
}
