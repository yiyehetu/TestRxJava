package com.meluo.testrxjava;

import java.util.ArrayList;

/**
 * Created by admin on 2017/3/27.
 */

public class Student {
    private String name;
    private ArrayList<Course> courses;

    public Student() {
    }

    public Student(String name, ArrayList<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }
}
