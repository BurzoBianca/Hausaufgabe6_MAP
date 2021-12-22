package repository;

import classes.Course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseDAO {
    private List<Course> courses = new ArrayList<>();

    public CourseDAO(){
        Course course1 = new Course(1,"MAP", 11, 5, 5);
        Course course2 = new Course(2,"BD", 11, 5, 6);
        Course course3 = new Course(3,"LP", 12, 5, 6);
        course1.setStudentsEnrolledId(new ArrayList<>(List.of(1,2)));
        course2.setStudentsEnrolledId(new ArrayList<>(List.of(3,2)));
        course3.setStudentsEnrolledId(new ArrayList<>(List.of(1,3)));
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public boolean addCourse(Course course){
        for(Course c: courses){
            if(course.getId()==c.getId()){
                return false;
            }
        }
        courses.add(course);
        return true;
    }
}