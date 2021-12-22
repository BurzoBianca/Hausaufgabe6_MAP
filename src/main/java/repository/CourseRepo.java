package repository;

import classes.Course;


public class CourseRepo extends InMemoryRepo<Course> {
    private CourseDAO courseDAO = new CourseDAO();

    public CourseRepo() {
        super();
        repoList = courseDAO.getCourses();
    }


    @Override
    public Course update(Course obj) {
        Course courseToUpdate = this.repoList.stream()
                .filter(course -> course.getId() == obj.getId())
                .findFirst()
                .orElseThrow();

        courseToUpdate.setStudentsEnrolledId(obj.getStudentsEnrolledId());

        return courseToUpdate;
    }


    public Course updateCredits(Course obj) {
        Course courseToUpdate = this.repoList.stream()
                .filter(course -> course.getId() == obj.getId())
                .findFirst()
                .orElseThrow();

        courseToUpdate.setCredits(obj.getCredits());
        return courseToUpdate;
    }


    @Override
    public void delete(Course obj) {
        super.delete(obj);
    }
}