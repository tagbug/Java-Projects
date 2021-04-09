package 随机红包;

public class RandomRedEnvelope extends RedEnvelope {
    RandomRedEnvelope(double money, int people) {
        super(money, people);// 调用父类构造方法
    }

    public double giveMoney() {
        if (moneyLeft > 0 && peopleLeft > 0) {
            double giveMoney = Math.floor(Math.random() * (moneyLeft * 100 - peopleLeft + 1) + 1);// 算出一个最低 1分 最高能留给 剩余人数每人1分 的数
            giveMoney /= 100;
            moneyLeft -= giveMoney;
            peopleLeft -= 1;
            return giveMoney;
        }
        return 0;
    }
}
