package 精编p127;

public class Dog {
    DogState state;
    
    public void cry() {
        state.showState();
    }
    public void setState(DogState a) {
        state = a;
    }
}
