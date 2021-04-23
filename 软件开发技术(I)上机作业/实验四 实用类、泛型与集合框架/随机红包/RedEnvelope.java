package 随机红包;

public abstract class RedEnvelope {
    protected double moneyLeft;
    protected int peopleLeft;

    RedEnvelope(double money, int people) {
        reset(money, people);
    }

    public abstract double giveMoney();

    public boolean hasNext() {
        return peopleLeft != 0;
    }

    public void reset(double money, int people) {
        if (money > 0 && people > 0) {
            moneyLeft = Math.round(money * 100) / 100;
            peopleLeft = people;
        }
    }
}
