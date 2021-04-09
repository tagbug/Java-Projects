package 链表排序查找;

public class StudentInfo {
    private String name;
    private long id;
    private int score;

    public StudentInfo() {
        name = "";
        id = 0;
        score = 0;
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
