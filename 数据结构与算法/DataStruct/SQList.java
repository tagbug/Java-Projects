package DataStruct;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 数据结构：
 * <p>
 * 线性表顺序存储结构定义和其基本方法
 * </p>
 * 
 * @author TagBug
 * @date 2021.09.18 21:08:48
 */
public class SQList<T> {
    private static final int LIST_INIT_SIZE = 100;
    private static final int LIST_INCREMENT = 10;

    private T[] data;
    private int length;
    private int listSize;

    /**
     * 线性表初始化
     * 
     * @param type 元素类型
     */
    @SuppressWarnings("unchecked")
    public SQList(Class<T> type) {
        data = (T[]) Array.newInstance(type, LIST_INIT_SIZE);
        length = 0;
        listSize = LIST_INIT_SIZE;
    }

    /**
     * 使用给定数据创建新线性表
     * 
     * @param type 元素类型
     * @param arr  数据数组
     */
    public SQList(Class<T> type, T[] arr) {
        this(type);
        for (int i = 0; i < arr.length; i++) {
            insert(i + 1, arr[i]);
        }
    }

    /**
     * 在线性表中定位给定的数据，并在找不到时返回0
     * 
     * @param elem 要定位的数据
     * @return 数据在线性表中的位序或0
     */
    public int locate(T elem) {
        for (int i = 0; i < length; i++) {
            if (data[i] == elem) {
                return i + 1; // 返回数组索引+1（[1,length]）
            }
        }
        return 0;
    }

    /**
     * 获取线性表中指定位置的元素
     * 
     * @param index 位序
     * @return 查找到的元素
     * @throws IndexOutOfBoundsException 当位序越界时抛出
     */
    public T getElem(int index) throws IndexOutOfBoundsException {
        if (index < 1 || index > length) {
            throw new IndexOutOfBoundsException(String.format("位序越界：%d 不在[1,%d]的范围内！\n", index, length));
        }
        return data[index - 1];
    }

    /**
     * 在线性表中指定位置插入新元素
     * 
     * @param index 位序
     * @param elem  新元素
     * @throws IndexOutOfBoundsException 当位序越界时抛出
     */
    public void insert(int index, T elem) throws IndexOutOfBoundsException {
        if (index < 1 || index > length + 1) {
            throw new IndexOutOfBoundsException(String.format("位序越界：%d 不在[1,%d]的范围内！\n", index, length));
        }
        // 当线性表长度≥容量时，给其扩容
        if (length >= listSize) {
            listSize += LIST_INCREMENT;
            data = Arrays.copyOf(data, listSize);
        }
        // 移动元素
        for (int i = length; i >= index; i--) {
            data[i] = data[i - 1];
        }
        // 插入新元素
        data[index - 1] = elem;
        length += 1;
    }

    /**
     * 删除线性表中指定位置的元素并返回它
     * 
     * @param index 位序
     * @return 删除的元素
     * @throws IndexOutOfBoundsException 当位序越界时抛出
     */
    public T delete(int index) throws IndexOutOfBoundsException {
        if (index < 1 || index > length) {
            throw new IndexOutOfBoundsException(String.format("位序越界：%d 不在[1,%d]的范围内！\n", index, length));
        }
        T deletedElem = data[index - 1];
        // 删除（移动）元素
        for (int i = index - 1; i < length - 1; i++) {
            data[i] = data[i + 1];
        }
        length -= 1;
        return deletedElem;
    }

    /**
     * 返回线性表目前长度
     */
    public int getLength() {
        return length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(":\n");
        if (length == 0) {
            sb.append("[-空表-]");
            return sb.toString();
        }
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append("-> ");
            }
            sb.append(data[i]);
            sb.append(' ');
        }
        return sb.toString();
    }

    /**
     * 单元测试
     */
    public static void main(String[] args) {
        Integer[] data = { 5, 12, 45, 32, 1111, 33, 23, 455, 9, 0 };
        SQList<Integer> list = new SQList<>(Integer.class, data);
        System.out.println(list); // 5 -> 12 -> 45 -> 32 -> 1111 -> 33 -> 23 -> 455 -> 9 -> 0
        list.delete(4);
        list.delete(6);
        System.out.println(list); // 5 -> 12 -> 45 -> 1111 -> 33 -> 455 -> 9 -> 0
        System.out.println(list.locate(9)); // 7
        System.out.println(list.getLength()); // 8
        list.insert(3, 101);
        System.out.println(list); // 5 -> 12 -> 101 -> 45 -> 1111 -> 33 -> 455 -> 9 -> 0
    }
}
