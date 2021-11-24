package DataStruct;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 数据结构： 栈的存储结构定义和其基本方法
 * 
 * @param base      数组
 * @param top       栈顶指针，指向栈顶下一位置，当top=0时表示栈空
 * @param stackSize 目前栈大小
 * @author TagBug
 * @date 2021.09.22 08:10:08
 */
public class Stack<T> {
    private static final int STACK_INIT_SIZE = 100;
    private static final int INCREMENT_SIZE = 10;

    private T[] base;
    private int top;
    private int size;

    /**
     * 创建一个空栈
     * 
     * @param type 栈元素类型
     */
    @SuppressWarnings("unchecked")
    public Stack(Class<T> type) {
        base = (T[]) Array.newInstance(type, STACK_INIT_SIZE);
        top = 0;
        size = STACK_INIT_SIZE;
    }

    /**
     * 将元素入栈，并在必要时给其扩容
     * 
     * @param elem 欲推入的元素
     */
    public void push(T elem) {
        // 如果栈满则扩容
        if (top > size) {
            base = Arrays.copyOf(base, size + INCREMENT_SIZE);
        }
        // 推入元素
        base[top] = elem;
        top += 1;
    }

    /**
     * 将元素出栈
     * 
     * @param elem 用于存放获取的元素
     */
    public T pop() {
        // 如果栈空
        if (top <= 0) {
            throw new IllegalStateException("栈为空！");
        }
        top -= 1;
        return base[top];
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
        if (top == 0) {
            sb.append("[-空表-]");
            return sb.toString();
        }
        for (int i = 0; i < top; i++) {
            if (i > 0) {
                sb.append("-> ");
            }
            sb.append(base[i]);
            sb.append(' ');
        }
        return sb.toString();
    }

    /**
     * 测试用例
     * 
     * @param args
     */
    public static void main(String[] args) {
        Integer[] data = { 5, 12, 45, 32, 1111, 33, 23, 455, 9, 0 };
        Stack<Integer> stack = new Stack<>(Integer.class);
        System.out.println(stack); // [空表]
        for (Integer n : data) {
            stack.push(n);
        }
        System.out.println(stack); // 5 -> 12 -> 45 -> 32 -> 1111 -> 33 -> 23 -> 455 -> 9 -> 0
        stack.pop();
        stack.pop();
        System.out.println(stack); // 5 -> 12 -> 45 -> 32 -> 1111 -> 33 -> 23 -> 455
    }
}
