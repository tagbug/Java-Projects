package 链表排序查找;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

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

    public StudentInfo searchByScore(int score) {
        ListIterator<StudentInfo> iterator=studentInfos.listIterator();
        StudentInfo studentInfo;
        while (iterator.hasNext()) {
            studentInfo = iterator.next();
            if (studentInfo.getScore() == score) {
                return studentInfo;
            }
        }
        return new StudentInfo();
    }
    
    public StudentInfo searchByName(String name) {
        ListIterator<StudentInfo> iterator = studentInfos.listIterator();
        StudentInfo studentInfo;
        while (iterator.hasNext()) {
            studentInfo = iterator.next();
            if (studentInfo.getName().equals(name)) {
                return studentInfo;
            }
        }
        return new StudentInfo();
    }
    
    public void sortByScore() {
        studentInfos.sort(Comparator.comparingInt(StudentInfo::getScore));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListIterator<StudentInfo> iterator = studentInfos.listIterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().toString());
        }
        return sb.toString();
    }
}
