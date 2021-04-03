package InterfaceExams;

abstract class Table implements Compareable, Sortable {
    protected int size = 0;

    public abstract void show();

    public void clean() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public abstract String toString();
}
