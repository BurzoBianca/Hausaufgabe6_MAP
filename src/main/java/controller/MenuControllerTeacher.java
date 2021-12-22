package controller;
import repository.*;
import classes.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MenuControllerTeacher implements Initializable {

    final StudentRepo studentRepo = new StudentRepo();
    final CourseRepo courseRepo = new CourseRepo();
    final TeacherRepo teacherRepo = new TeacherRepo();
    RegistrationSystem registrationSystem = new RegistrationSystem(courseRepo,studentRepo,teacherRepo);

    private Course teacherCourse;

    private Teacher loggedInTeacher;

    private List<Student> enrolledStudents;


    @FXML
    private ImageView teacherImageView;

    @FXML
    private ImageView checkImageView;

    @FXML
    private TextArea studentsTextArea;

    @FXML
    private TextField courseNameTextField;

    @FXML
    private Label courseMessageLabel;

    @FXML
    private Label teacherNameLabel;


    public void goButtonOnAction(){
        if(!courseNameTextField.getText().isBlank()){
            validateCourse(courseNameTextField.getText());
        }
        else{
            courseMessageLabel.setText("Please enter a course name");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File teacherFile = new File("img/Teachers-icon.png");
        Image teacherImage = new Image(teacherFile.toURI().toString());
        teacherImageView.setImage(teacherImage);

        File checkFile = new File("img/checkmark.png");
        Image checkImage = new Image(checkFile.toURI().toString());
        checkImageView.setImage(checkImage);

        studentsTextArea.setEditable(false);

    }

    public void validateCourse(String courseName){
        Course course = courseRepo.getAll().stream()
                .filter(elem -> Objects.equals(elem.getName(), courseName))
                .findAny()
                .orElse(null);

        if(course == null){
            courseMessageLabel.setText("No such course. Please try again.");
            studentsTextArea.setText("");
        }
        else {
            if(loggedInTeacher.getCourses().stream().anyMatch(elem -> elem == course.getId())){
                teacherCourse = course;
                courseMessageLabel.setText("");
                enrolledStudents = new ArrayList<>();

                for(int id : teacherCourse.getStudentsEnrolledId()){
                    Student enrolledStudent= studentRepo.getAll().stream()
                            .filter(student -> student.getStudentId()==id)
                            .findFirst()
                            .orElse(null);
                    enrolledStudents.add(enrolledStudent);
                }
                if(enrolledStudents.size()!=0){
                    StringJoiner joiner = new StringJoiner("\n");
                    enrolledStudents.stream().map(String::valueOf).forEach(joiner::add);
                    studentsTextArea.setText(joiner.toString());
                }
                else{
                    studentsTextArea.setText("No students enrolled yet");
                }

            }
            else{
                courseMessageLabel.setText("Course not taught by you. Please try again");
                studentsTextArea.setText("");
            }
        }
    }

    public void initData(String teacherName) {
        loggedInTeacher = teacherRepo.getAll().stream()
                .filter(elem -> Objects.equals(elem.getFirstName(), teacherName))
                .findAny()
                .orElse(null);
        assert loggedInTeacher != null;
        teacherNameLabel.setText("Hello " + teacherName + "!");
    }


}