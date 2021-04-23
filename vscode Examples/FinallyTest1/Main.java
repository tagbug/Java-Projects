package FinallyTest1;

public class Main {
    public static void main(String[] args) {
        Temp result = add();
        System.out.println(result.i);
    }
    
    public static Temp add() {
        Temp temp = new Temp();
        temp.i=100;
        try{
            return temp;
        } finally {
            temp.i++;
        }
    }
}
