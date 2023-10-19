package model;

import java.util.List;

public class Student {
    private int id;
    private String name;
    private List<Subject> subjects;

    public Student(int id, String name, List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.subjects = subjects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public String toString(){
        return "[%d, %s, %s]".formatted(id, name, subjects);
    }
}
