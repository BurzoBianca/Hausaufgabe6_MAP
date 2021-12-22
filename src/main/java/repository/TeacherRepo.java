package repository;

import classes.Teacher;


public class TeacherRepo extends InMemoryRepo<Teacher> {
    private TeacherDAO teacherDAO= new TeacherDAO();

    public TeacherRepo() {
        super();
        repoList = teacherDAO.getTeachers();
    }


    @Override
    public Teacher update(Teacher obj) {
        Teacher teacherToUpdate = this.repoList.stream()
                .filter(teacher -> teacher.getId() == obj.getId())
                .findFirst()
                .orElseThrow();

        teacherToUpdate.setCourses(obj.getCourses());

        return teacherToUpdate;
    }
}
