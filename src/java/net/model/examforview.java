package net.model;

public class examforview {
    private int id;
    private String title;
    private String image;
    private String description;
    private String averageTime;
    private float averageMark;
    private int totalStudents;

    public examforview(int id, String title, String image, String description, String averageTime, float averageMark, int totalStudents) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.averageTime = averageTime;
        this.averageMark = averageMark;
        this.totalStudents = totalStudents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(String averageTime) {
        this.averageTime = averageTime;
    }

    public float getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(float averageMark) {
        this.averageMark = averageMark;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }
    
}
