package SortAlgorithms;


public class Sort {
    static enum method {
        insert
    };

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> void insertSort(T[] arr) {
        Comparable<T> max = arr[0];
        T temp;
        int idx;
        for (int i = arr.length - 1; i > 0; i--) {
            max = arr[i];
            idx = i;
            for (int j = i; j >= 0; j--) {
                temp = arr[j];
                if (max.compareTo(temp) < 0) {
                    max = temp;
                    idx = j;
                }
            }
            arr[idx] = arr[i];
            arr[i] = (T) max;
        }
    }

    public static <T extends Comparable<T>> void timing(method type, T[] arr) {
        // System.out.printf("\n原始数据集：");
        // for (T elem : arr) {
        //     System.out.print(elem);
        //     System.out.print(',');
        // }
        long beftime = System.nanoTime();
        switch (type) {
        case insert:
            insertSort(arr);
            break;
        default:
            break;
        }
        long afttime = System.nanoTime();
        long runtime = afttime - beftime;
        // System.out.printf("\n排序后：\n");
        // for (T elem : arr) {
        //     System.out.print(elem);
        //     System.out.print(',');
        // }
        System.out.printf("\n运行时间：" + runtime + "纳秒（" + runtime / 1E6 + "毫秒）");
    }

}
