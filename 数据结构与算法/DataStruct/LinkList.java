package DataStruct;

/**
 * 数据结构：
 * <p>
 * 线性表链式存储结构定义和其基本方法
 * </p>
 * 
 * @author TagBug
 * @date 2021.09.18 21:09:09
 */
public class LinkList<T> {
    private class Node {
        T data;
        Node next;
    }

    private int length;
    private Node head;

    /**
     * 创建一个新的空链表
     */
    public LinkList() {
        length = 0;
        head = null;
    }

    /**
     * 用给定数据创建一个新的链表
     * 
     * @param arr 数据数组
     */
    public LinkList(T[] arr) {
        this();
        // 创建数据节点
        for (int i = 1; i <= arr.length; i++) {
            insert(i, arr[i - 1]);
        }
    }

    /**
     * 获取链表中指定位序数据
     * 
     * @param index 位序
     * @return 查找结果
     * @throws IndexOutOfBoundsException 当位序越界时抛出
     */
    public T getElem(int index) throws IndexOutOfBoundsException {
        if (index < 1 || index > length) {
            throw new IndexOutOfBoundsException(String.format("位序越界：%d 不在[1,%d]的范围内！\n", index, length));
        }
        Node node = head;
        for (int i = 1; i < index; i++) {
            if (node == null) {
                throw new NullPointerException(String.format("链表内部出错！链表长度与实际不符：(%d < %d)\n", i, length));
            }
            node = node.next;
        }
        return node.data;
    }

    /**
     * 在链表中定位给定的数据，并在找不到时返回0
     * 
     * @param elem 要查找的元素
     * @return 元素位序或0
     */
    public int locate(T elem) {
        Node node = head;
        int index = 1;
        while (node != null) {
            if (node.data == elem) {
                return index;
            }
            node = node.next;
            index++;
        }
        return 0;
    }

    /**
     * 向链表中插入新元素
     * 
     * @param index 位序
     * @param elem  要插入的新元素
     */
    public void insert(int index, T elem) {
        if (index < 1 || index > length + 1) {
            throw new IndexOutOfBoundsException(String.format("位序越界：%d 不在[1,%d]的范围内！\n", index, length));
        }
        // 单独判断如果是插入第一个元素的情况
        if (index == 1) {
            head = new Node();
            head.data = elem;
            length += 1;
            return;
        }
        // index >= 2
        Node node = head;
        for (int i = 2; i < index; i++) {
            if (node == null) {
                throw new NullPointerException(String.format("链表内部出错！链表长度与实际不符：(%d < %d)\n", i, length));
            }
            node = node.next;
        }
        Node newNode = new Node();
        newNode.data = elem;
        newNode.next = node.next;
        node.next = newNode;
        length += 1;
    }

    /**
     * 从链表中删除并返回指定位序的元素
     * 
     * @param head  链表头
     * @param index 位序
     */
    public T delete(int index) {
        if (index < 1 || index > length) {
            throw new IndexOutOfBoundsException(String.format("位序越界：%d 不在[1,%d]的范围内！\n", index, length));
        }
        // 单独判断如果是删除第一个元素的情况
        if (index == 1) {
            Node node = head.next;
            // 当指针有效时才赋值
            T deletedElem = node.data;
            head.next = node.next;
            length -= 1;
            return deletedElem;
        }
        // index >= 2
        Node node = head;
        for (int i = 2; i < index; i++) {
            if (node == null) {
                throw new NullPointerException(String.format("链表内部出错！链表长度与实际不符：(%d < %d)\n", i, length));
            }
            node = node.next;
        }
        Node deletedNode = node.next;
        T deletedElem = deletedNode.data;
        node.next = deletedNode.next;
        length -= 1;
        return deletedElem;
    }

    /**
     * 获取链表长度
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
        Node node = head;
        if (node == null) {
            sb.append("[-空表-]");
            return sb.toString();
        }
        sb.append(node.data);
        sb.append(' ');
        node = node.next;
        while (node != null) {
            sb.append("-> ");
            sb.append(node.data);
            sb.append(' ');
            node = node.next;
        }
        return sb.toString();
    }

    /**
     * 单元测试
     */
    public static void main(String[] args) {
        Integer[] data = { 5, 12, 45, 32, 1111, 33, 23, 455, 9, 0 };
        LinkList<Integer> list = new LinkList<>(data);
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
