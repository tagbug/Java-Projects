package 链表排序查找;

import java.util.LinkedList;

/**
 * <p>以链表形式存储学生个人信息表
 * <p>依赖{@link StudentInfo}、{@link LinkedList} 
 * @author TagBug
 * @version 0.1
 * @since 1.10
 */
public class StudentList {
    private LinkedList<StudentInfo> studentInfos;

    public StudentList() {
        studentInfos = new LinkedList<StudentInfo>();
    }
    
    public StudentList(StudentInfo studentInfo) {
        studentInfos = new LinkedList<StudentInfo>();
        studentInfos.add(studentInfo);
    }

    public boolean add(StudentInfo studentInfo) {
        return studentInfos.add(studentInfo);
    }
    
    public boolean remove(StudentInfo studentInfo) {
        return studentInfos.remove(studentInfo);
    }

    public StudentInfo remove(int index) {
        return studentInfos.remove(index);
    }

    /**
     * @param score 学生总分
     * @return 学生个人信息
     */
    public StudentInfo searchByScore(int score) {
        for (var info : studentInfos) {
            if (info.getScore()==score)
                return info;
        }
        return null;
    }
    
    /**
     * @param score 学生姓名
     * @return 学生个人信息
     */
    public StudentInfo searchByName(String name) {
        for (var info : studentInfos) {
            if(info.getName().equals(name))
                return info;
        }
        return null;
    }
    
    public void sortByScore() {
        studentInfos.sort((x, y) -> x.compareTo(y));// 调用StudentInfo的默认比较方法，实现按分数排序
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (StudentInfo info : studentInfos) {
            sb.append(info.toString());
        }
        return sb.toString();
    }
}
