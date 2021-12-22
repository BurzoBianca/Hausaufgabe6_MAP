package repository;

import classes.Student;


public class StudentRepo extends InMemoryRepo<Student> {
    StudentDAO studentDAO = new StudentDAO();

    public StudentRepo(){
        super();
        repoList = studentDAO.getStudents();
    }


    @Override
    public Student update(Student obj) {
        Student studentToUpdate = this.repoList.stream()
                .filter(student -> student.getStudentId() == obj.getStudentId())
                .findFirst()
                .orElseThrow();

        studentToUpdate.setEnrolledCourses(obj.getEnrolledCourses());
        studentToUpdate.setTotalCredits(obj.getTotalCredits());

        return studentToUpdate;
    }
}