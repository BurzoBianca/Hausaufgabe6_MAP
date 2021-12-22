package repository;

import classes.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private List<Student> students = new ArrayList<>();

    public StudentDAO(){
        students.add(new Student(1,"Catalina","Vasiu", 1));
        students.add(new Student(2,"Victor", "Santa", 2));
        students.add(new Student(3,"Darius", "Oros", 3));
    }

    public List<Student> getStudents() {
        return students;
    }

    public boolean addStudent(Student student){
        for(Student s: students){
            if(student.getLastName().equals(s.getLastName())){
                return false;
            }
        }
        students.add(student);
        return true;
    }

}