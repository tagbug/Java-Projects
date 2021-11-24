package DataStruct;

import java.util.Scanner;

public class BST<T extends Comparable<T>> {
    private class Node {
        T data; // 节点数据
        Node leftChild, rightChild; // leftChild.data < data < rightChild.data

        public Node(T data) {
            this.data = data;
        }
    }

    private Node head; // 头节点

    /**
     * 用给定的数据创建一个二叉排序树
     * 
     * @param data 数据数组
     */
    public BST(T[] data) {
        if (data.length == 0) {
            throw new IllegalArgumentException("数据不能为空！");
        }
        for (T elem : data) {
            insert(elem);
        }
    }

    /**
     * 向BST中插入新数据
     * 
     * @param data 数据
     */
    public void insert(T data) {
        // 头节点为空的话，直接创建
        if (head == null) {
            head = new Node(data);
            return;
        }
        insert(data, head);
    }

    /**
     * 插入递归子函数
     * 
     * @param data 数据
     * @param node 节点
     */
    private void insert(T data, Node node) {
        // 如果要插入的数据已存在，则忽略
        if (data.equals(node.data)) {
            return;
        }
        // 否则递归查找合适位置
        if (data.compareTo(node.data) < 0) {
            // 小于node.data，则进入左子树
            // 如果左子树不存在，则已找到位置
            if (node.leftChild == null) {
                node.leftChild = new Node(data);
                return;
            }
            // 否则递归
            insert(data, node.leftChild);
        } else {
            // 大于node.data，则进入右子树
            // 如果右子树不存在，则已找到位置
            if (node.rightChild == null) {
                node.rightChild = new Node(data);
                return;
            }
            // 否则递归
            insert(data, node.rightChild);
        }
    }

    /**
     * 中序遍历
     * 
     * @return 遍历结果
     */
    public String inorderTraversal() {
        StringBuilder sb = new StringBuilder();
        inorderTraversal(head, sb);
        return sb.toString();
    }

    /**
     * 中序遍历递归子函数
     * 
     * @param node 节点
     * @param sb   字符串构造器
     * @return 节点数据
     */
    private void inorderTraversal(Node node, StringBuilder sb) {
        if (node.leftChild != null) {
            inorderTraversal(node.leftChild, sb);
        }
        sb.append(node.data + " ");
        if (node.rightChild != null) {
            inorderTraversal(node.rightChild, sb);
        }
    }

    /**
     * 删除BST中的最小值
     */
    public T deleteMin() {
        return deleteMin(head, null);
    }

    /**
     * delMin递归子函数
     */
    private T deleteMin(Node node, Node preNode) {
        // 找到最小节点
        // 如果存在右子树，则把右子树接到最小节点的上一个节点上
        // 如果不存在，则直接将上一个节点的leftChild置空
        if (node.leftChild == null) {
            // 这时node即为最小节点
            if (preNode == null) {
                // 上一节点不存在，说明此节点为根节点
                head = head.rightChild == null ? null : head.rightChild;
            } else {
                // 上一节点存在
                if (node.rightChild != null) {
                    preNode.leftChild = node.rightChild;
                } else {
                    preNode.leftChild = null;
                }
            }
            return node.data;
        }
        return deleteMin(node.leftChild, node);
    }

    /**
     * 删除BST中的最大值
     */
    public T deleteMax() {
        return deleteMax(head, null);
    }

    /**
     * delMax递归子函数
     */
    private T deleteMax(Node node, Node preNode) {
        // 找到最大节点
        // 如果存在左子树，则把左子树接到最大节点的上一个节点上
        // 如果不存在，则直接将上一个节点的rightChild置空
        if (node.rightChild == null) {
            // 这时node即为最大节点
            if (preNode == null) {
                // 上一节点不存在，说明此节点为根节点
                head = head.leftChild == null ? null : head.leftChild;
            } else {
                // 上一节点存在
                if (node.leftChild != null) {
                    preNode.rightChild = node.leftChild;
                } else {
                    preNode.rightChild = null;
                }
            }
            return node.data;
        }
        return deleteMin(node.rightChild, node);
    }

    /**
     * 删除指定数据
     * 
     * @param data 数据
     * @return 删除结果（成功/失败）
     */
    public boolean delete(T data) {
        return delete(data, head, null, false);
    }

    /**
     * delete递归子函数
     * 
     * @param data   数据
     * @param node   节点
     * @param isLeft node是否为preNode的左子树
     * @return 删除结果（成功/失败）
     */
    private boolean delete(T data, Node node, Node preNode, boolean isLeft) {
        // 先查找是否存在给定数据的节点
        if (node == null) {
            // 不存在该节点
            return false;
        }
        if (data.equals(node.data)) {
            // 找到节点，执行删除逻辑
            // 有三种方法使删除该节点后任保持BST有序性
            // 一：找到右子树中的最小值节点，将其替代该节点
            // 二：找到左子树中的最大值节点，将其替代该节点
            // 三：左右子树都不存在，直接移除
            // 这里优先选择方法一
            if (node.rightChild != null) {
                T min = deleteMin(node.rightChild, node);
                node.data = min;
            } else if (node.leftChild != null) {
                T max = deleteMax(node.leftChild, node);
                node.data = max;
            } else {
                // 没有左右子树，那么直接移除即可
                if (preNode == null) {
                    // 将要移除唯一的头节点
                    head = null;
                } else {
                    if (isLeft) {
                        preNode.leftChild = null;
                    } else {
                        preNode.rightChild = null;
                    }
                }
            }
            return true;
        }
        // 递归查找
        if (data.compareTo(node.data) < 0) {
            // 小于node.data，则进入左子树
            return delete(data, node.leftChild, node, true);
        } else {
            // 大于node.data，则进入右子树
            return delete(data, node.rightChild, node, false);
        }
    }

    /**
     * 通过数据获取BST中对应的节点
     */
    public Node findNode(T data) {
        return findNode(data, head);
    }

    private Node findNode(T data, Node node) {
        if (node == null) {
            return null;
        }
        if (data.equals(node.data)) {
            return node;
        }
        if (data.compareTo(node.data) < 0) {
            return findNode(data, node.leftChild);
        } else {
            return findNode(data, node.rightChild);
        }
    }

    /**
     * 在BST中寻找两个指定结点的最近公共祖先
     */
    public T findParent(T data1, T data2) {
        // 如果data1或data2对应的node不在BST中，那么直接返回null
        if (findNode(data1) == null || findNode(data2) == null) {
            return null;
        }
        return findParent(data1, data2, head);
    }

    private T findParent(T data1, T data2, Node node) {
        // 如果data1/data2中任意一个与当前node的值相等，那么最近公共祖先即为当前node
        if (data1.equals(node.data) || data2.equals(node.data)) {
            return node.data;
        }
        // 如果data1/data2的值均小于node，那么往node的左子树上继续寻找
        if (data1.compareTo(node.data) < 0 && data2.compareTo(node.data) < 0) {
            return findParent(data1, data2, node.leftChild);
        }
        // 如果data1/data2的值均大于node，那么往node的右子树上继续寻找
        if (data1.compareTo(node.data) > 0 && data2.compareTo(node.data) > 0) {
            return findParent(data1, data2, node.rightChild);
        }
        // 只剩下一种情况：data1和data2的值正好位于node的左右两边，那么最近公共祖先只能是当前node
        return node.data;
    }

    /**
     * 单元测试
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("请输入数据：");
            String[] line = scanner.nextLine().split(" ");
            Integer[] data = new Integer[line.length];
            for (int i = 0; i < line.length; i++) {
                data[i] = Integer.valueOf(line[i]);
            }
            BST<Integer> bst = new BST<Integer>(data);
            System.out.println("中序遍历结果：");
            System.out.println(bst.inorderTraversal());
            System.out.print("删除指定数据：");
            Integer delData = scanner.nextInt();
            bst.delete(delData);
            System.out.println("删除后中序遍历结果：");
            System.out.println(bst.inorderTraversal());
            System.out.print("寻找公共祖先：");
            Integer result = bst.findParent(scanner.nextInt(), scanner.nextInt());
            System.out.println("找到的节点：" + result);
        } catch (NumberFormatException e) {
            System.out.println("err：输入数据格式有误！");
        } catch (Exception e) {
            System.out.println("未知异常：" + e);
        }
    }
}
