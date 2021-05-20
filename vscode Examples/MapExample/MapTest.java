package MapExample;

import java.util.HashMap;

public class MapTest {
    public static void main(String[] args) {
        var map = new HashMap<String, String>();
        map.put("11", "name");
        map.put("22", "id");
        map.put("33", "value");
        map.forEach((k, v) -> System.out.println("key=" + k + " value=" + v));
    }
}
