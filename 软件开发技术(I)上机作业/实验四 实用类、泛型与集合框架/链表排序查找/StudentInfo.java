package 链表排序查找;

/**
 * 用来存储和处理单个学生的个人信息
 * @author TagBug
 * @version 0.1
 * @since 1.10
 */
public class StudentInfo implements Comparable<StudentInfo>{
    /** 默认名字 {@value}"学生" */
    private String name = "学生";
    private long id;
    private int score;

    public StudentInfo() {
    }
    
    public StudentInfo(String name, long id, int score) {
        this.name = name;
        this.id = id;
        this.score = score;
    }

    @Override
    public String toString() {
        return "姓名：" + name + "\t学号：" + id + "\t成绩：" + score + "\n";
    }

    /**
     * 根据 总分、学号、姓名 依次从小到大
     */
    @Override
    public int compareTo(StudentInfo other) {
        if (this == other)
            return 0;
        if (other == null)
            return 1;
        var result = Integer.compare(score, other.score);
        return result != 0 ? result : (result = Long.compare(id, other.id)) != 0 ? result : name.compareTo(other.name);
    }
    
    // Getters&Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
