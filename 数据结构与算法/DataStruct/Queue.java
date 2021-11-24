package DataStruct;

import java.lang.reflect.Array;

/**
 * 数据结构： 栈的存储结构定义和其基本方法
 * 
 * @param data      数组
 * @param front     队首指针，指向队首元素
 * @param rear      队尾指针，指向队尾元素<br>
 *                  当front==rear时，表示队列为空<br>
 *                  当(rear+1)%queueSize==front时，表示队列为满
 * @param queueSize 目前实际队列大小<br>
 *                  队列可用空间永远比queueSize少1（留下一个空位用于判断队列为满）
 * @author TagBug
 * @date 2021.09.22 08:10:08
 */
public class Queue<T> {
    private static final int QUEUE_INIT_SIZE = 100;
    // private static final int INCREMENT_SIZE = 10;

    private T[] data;
    private int front;
    private int rear;
    private int queueSize;

    /**
     * 创建一个空队列
     * 
     * @param type 队列元素类型
     */
    @SuppressWarnings("unchecked")
    public Queue(Class<T> type) {
        data = (T[]) Array.newInstance(type, QUEUE_INIT_SIZE);
        front = 0;
        rear = -1;
        queueSize = QUEUE_INIT_SIZE;
    }

    /**
     * 将元素添加到队首
     */
    public boolean addFirst(T elem) {
        if ((rear + 1) % queueSize == queueSize) {
            System.out.println("队列已满！");
            return false;
        }
        int idx = front - 1;
        front = idx < 0 ? queueSize - 1 : idx;
        data[front] = elem;
        return true;
    }

    /**
     * 将元素添加到队尾
     */
    public boolean addLast(T elem) {
        if ((rear + 1) % queueSize == queueSize) {
            System.out.println("队列已满！");
            return false;
        }
        int idx = (rear + 1) % queueSize;
        rear = idx;
        data[idx] = elem;
        return true;
    }

    /**
     * 将元素从队首移除
     */
    public T removeFirst() {
        if (getLength() == 0) {
            throw new IllegalStateException("队列为空！");
        }
        T elem = data[front];
        front = (front + 1) % queueSize;
        return elem;
    }

    /**
     * 将元素从队尾移除
     */
    public T removeLast() {
        if (getLength() == 0) {
            throw new IllegalStateException("队列为空！");
        }
        T elem = data[rear];
        int idx = rear - 1;
        rear = idx < 0 ? queueSize - 1 : idx;
        return elem;
    }

    /**
     * 返回队列大小
     */
    int getLength() {
        if ((rear + 1) % queueSize == front) {
            return 0;
        }
        if (front <= rear) {
            return rear - front + 1;
        } else {
            return rear + queueSize - front + 1;
        }
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
        if (getLength() == 0) {
            sb.append("[-空表-]");
            return sb.toString();
        }
        if (front <= rear) {
            for (int i = front; i <= rear; i++) {
                if (i > front) {
                    sb.append("-> ");
                }
                sb.append(data[i]);
                sb.append(' ');
            }
        } else {
            for (int i = front; i < queueSize; i++) {
                if (i > front) {
                    sb.append("-> ");
                }
                sb.append(data[i]);
                sb.append(' ');
            }
            for (int i = 0; i <= rear; i++) {
                sb.append("-> ");
                sb.append(data[i]);
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    /**
     * 测试用例
     * 
     * @param args
     */
    public static void main(String[] args) {
        Integer[] dataFront = { 1111, 32, 45, 12, 5 };
        Integer[] dataRear = { 33, 23, 455, 9, 0 };
        Queue<Integer> queue = new Queue<>(Integer.class);
        for (Integer n : dataFront) {
            queue.addFirst(n);
        }
        for (Integer n : dataRear) {
            queue.addLast(n);
        }
        System.out.println(queue.getLength()); // 10
        System.out.println(queue); // 5 -> 12 -> 45 -> 32 -> 1111 -> 33 -> 23 -> 455 -> 9 -> 0
        queue.removeFirst();
        queue.removeLast();
        System.out.println(queue); // 12 -> 45 -> 1111 -> 33 -> 455 -> 9
        System.out.println(queue.getLength()); // 8
        queue.removeFirst();
        queue.removeFirst();
        queue.removeFirst();
        queue.removeFirst();
        queue.removeFirst();
        queue.removeFirst();
        System.out.println(queue.getLength()); // 2
        queue.removeLast();
        queue.removeLast();
        System.out.println(queue.getLength()); // 0
    }
}
