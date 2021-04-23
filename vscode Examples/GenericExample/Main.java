package GenericExample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.DoubleFunction;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        Collection<Pair<String>> table = new ArrayList<>();
        Pair<String> pair1 = new Pair<>("1", "2");
        Pair<String> pair2 = new Pair<>("3", "4");
        addAll(table, pair1, pair2);
        Pair<String> p = makePair(String::new);
        String[] arr = makeTArr(String[]::new);
        Pair<String> pp = makeTArr2(Pair::new);
    }

    @SafeVarargs
    public static <T> void addAll(Collection<T> coll, T... ts) {
        for (T t : ts)
            coll.add(t);
    }

    @SafeVarargs
    public static <E> E[] array(E... arr) {
        return arr;
    }

    public static <T> Pair<T> makePair(Supplier<T> constr) {
        return new Pair<>(constr.get(), constr.get());
    }

    public static <T> T[] makeTArr(IntFunction<T[]> constr) {
        return constr.apply(2);
    }

    public static <T> T makeTArr2(DoubleFunction<T> constr) {
        return constr.apply(2);
    }
}
