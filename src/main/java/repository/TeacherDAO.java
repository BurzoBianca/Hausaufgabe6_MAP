package repository;

import classes.Course;
import classes.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private List<Teacher> teachers = new ArrayList<>();

    public TeacherDAO(){
        Teacher teacher = new Teacher(11,"Carmen","Matei");
        teachers.add(teacher);
        Course course = new Course(1,"MAP", 11, 2, 5);
        Course course2 = new Course(2,"BD", 11, 2, 6);
        teacher.addCourse(course.getId());
        teacher.addCourse(course2.getId());
        Teacher teacher2 = new Teacher(12, "Cornelia", "Ignat");
        teachers.add(teacher2);
        Course course3 = new Course(3,"LP", 12, 2, 6);
        teacher2.addCourse(course3.getId());
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public boolean addTeacher(Teacher teacher){
        for(Teacher t: teachers){
            if(teacher.getLastName().equals(t.getLastName())){
                return false;
            }
        }
        teachers.add(teacher);
        return true;
    }
}