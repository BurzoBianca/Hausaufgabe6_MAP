package controller;

import classes.Course;
import classes.Pair;
import classes.Student;
import classes.Teacher;
import repository.CourseRepo;
import repository.StudentRepo;
import repository.TeacherRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RegistrationSystem {
    private CourseRepo courses;
    private StudentRepo students;
    private TeacherRepo teachers;

    public RegistrationSystem(CourseRepo courses, StudentRepo students, TeacherRepo teachers) {
        this.courses = courses;
        this.students = students;
        this.teachers = teachers;
    }


    public RegistrationSystem(){}

  
    public boolean register(Course course, Student student) throws IllegalArgumentException, Exception_Input, Exception_AlreadyExists {
        if(students.getAll().stream().noneMatch(elem -> elem.getId() == student.getId())){
            throw new Exception_Input("No such student");
        }
        else
        if(courses.getAll().stream().noneMatch(elem -> elem.getId() == course.getId())){
            throw new Exception_Input("No such course");
        }
        else {

            //update REPO studenti
            student.addCourse(course);
            students.update(student);


            //update REPO cursuri
            course.addStudent(student.getId());
            courses.update(course);    

            return true;
        }
    }

    
    public List<Course> retriveCoursesWithFreePlaces(){
        return courses.getAll().stream()
                .filter(elem -> elem.getMaxEnrollement()-elem.getStudentsEnrolledId().size() >0)
                .collect(Collectors.toList());
    }

    
    public List<Integer> retrieveStudentsEnrolledForACourse(Course course) throws IllegalArgumentException, Exception_Input {
        if(courses.getAll().stream().anyMatch(elem -> elem.getId() == course.getId())){
            return course.getStudentsEnrolledId();
        }
        else
            throw new Exception_Input("No such course");
    }


    public List<Course> getAllCourses(){
        return courses.getAll();
    }

    
    public List<Course> deleteCourse(Course course) throws IllegalArgumentException, Exception_Input {
        if(courses.getAll().contains(course)){
            for (Student student: students.getAll()){ 
                List<Integer> enrolledCoursesId = student.getEnrolledCourses().stream()      
                        .map(Pair::getCourseId)
                        .collect(Collectors.toList());

                if(enrolledCoursesId.contains(course.getId())){
                    student.removeCourse(course);
                    
                    students.update(student); 
                }
            }
            
            int teacherId = course.getIdTeacher();

            Teacher teacher = teachers.getAll().stream()
                    .filter(elem -> elem.getId() == teacherId)
                    .findAny()
                    .orElse(null);
            assert teacher != null;
            teacher.removeCourse(course.getId());
            teachers.update(teacher);
            
            courses.delete(course);


            return courses.getAll();
        }
        else {
            throw new Exception_Input("No such course");
        }

    }

    public List<Course> updateCreditsCourse(Course course, int newCredits) throws Exception_Input {
        List<Integer> toUnenrollStudents = new ArrayList<>();

        if(courses.getAll().contains(course)) {
            //update student REPO
            for (int studentId : course.getStudentsEnrolledId()) {
                Student student = students.getAll().stream()
                        .filter(elem -> elem.getId() == studentId)
                        .findAny()
                        .orElse(null);
                try{

                    assert student != null;
                    if(student.getEnrolledCourses().stream().anyMatch(elem -> elem.getCourseId() == course.getId())) {
                        student.updateCredits(course, newCredits);
                        students.update(student);
                    }
                }
                catch (Exception_LimitECTS e){
                    System.out.println("Credit limit exceded for a student:" + e);
                    int problemStudentId = e.getId();
                    toUnenrollStudents.add(problemStudentId);
                }
            }
            if(toUnenrollStudents.size()>0){
                course.getStudentsEnrolledId().removeAll(toUnenrollStudents);
                courses.update(course);
            }

            course.setCredits(newCredits);
            courses.updateCredits(course);

            return courses.getAll();
        }
        else{
            throw new Exception_Input("No such course");
        }
    }

}