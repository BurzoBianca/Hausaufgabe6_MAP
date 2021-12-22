package controller;

import classes.Course;
import classes.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import repository.CourseRepo;
import repository.StudentRepo;
import repository.TeacherRepo;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuControllerStudent implements Initializable {
    final StudentRepo studentRepo = new StudentRepo();
    final CourseRepo courseRepo = new CourseRepo();
    final TeacherRepo teacherRepo = new TeacherRepo();
    RegistrationSystem registrationSystem = new RegistrationSystem(courseRepo, studentRepo, teacherRepo);

    private Student loggedInStudent;

    @FXML
    private Label studentNameLabel;

    @FXML
    private ImageView studentImageView;

    @FXML
    private ImageView checkImageView1;

    @FXML
    private ImageView checkImageView2;

    @FXML
    private TextField courseTextField;

    @FXML
    private Label registerMessageLabel;

    @FXML
    private TextArea creditsTextArea;


    public void registerButtonOnAction(){
        if(!courseTextField.getText().isBlank()){
            validateRegister();
        }
        else{
            registerMessageLabel.setText("Please enter a course name");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File studentFile = new File("img/studentslogin.png");
        Image studentImage = new Image(studentFile.toURI().toString());
        studentImageView.setImage(studentImage);

        File checkFile = new File("img/checkmark.png");
        Image checkImage = new Image(checkFile.toURI().toString());
        checkImageView1.setImage(checkImage);
        checkImageView2.setImage(checkImage);

        creditsTextArea.setEditable(false);

    }

    public void validateRegister(){
        Course course = courseRepo.getAll().stream()
                .filter(elem -> Objects.equals(elem.getName(), courseTextField.getText()))
                .findAny()
                .orElse(null);
        if(course == null){
            registerMessageLabel.setText("No such course. Please try again.");
        }
        else {
            try {
                registrationSystem.register(course, loggedInStudent);
                registerMessageLabel.setText("Congratulations you have been enrolled!");
                creditsTextArea.setText(String.valueOf(loggedInStudent.getTotalCredits()));
            } catch (Exception_AlreadyExists e1) {
                registerMessageLabel.setText("You are already enrolled to this course. \n" +
                        "Please try again.");
            }
            catch (Exception_LimitECTS e2){
                registerMessageLabel.setText("Credit limit has been reached. \n" +
                        "Cannot enroll yourself to this course.");
            } catch (Exception_MaxLCurs e3) {
                registerMessageLabel.setText("This course has no free places. \n" +
                        "Cannot enroll yourself to this course.");
            }
        }
    }


    public void initData(String studentName) {
        loggedInStudent = studentRepo.getAll().stream()
                .filter(elem -> Objects.equals(elem.getFirstName(), studentName))
                .findAny()
                .orElse(null);
        assert loggedInStudent != null;
        creditsTextArea.setText(String.valueOf(loggedInStudent.getTotalCredits()));
        studentNameLabel.setText("Hello " + studentName + "!");
    }
}