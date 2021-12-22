package controller;

import classes.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import repository.*;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LogInControllerStudent implements Initializable{
    final StudentRepo studentRepo = new StudentRepo();
    ObservableList<Student> students = FXCollections.observableArrayList(studentRepo.getAll());

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private ImageView studentHeaderImageView;

    @FXML
    private ImageView lockImageView;

    public void loginButtonOnAction(){
        if(!nameTextField.getText().isBlank()){
            validateLogin();
        }
        else{
            loginMessageLabel.setText("Please enter your name");
        }
    }

    public void cancelButtonOnAction(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File studentsHeaderFile = new File("img/studentsmenu.png");
        Image studentsHeaderImage = new Image(studentsHeaderFile.toURI().toString());
        studentHeaderImageView.setImage(studentsHeaderImage);

        File lockFile = new File("img/lock.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }

    public void validateLogin(){
        if(studentRepo.getAll().stream().anyMatch(elem -> Objects.equals(elem.getFirstName(), nameTextField.getText()))){
            loginMessageLabel.setText("Congratulations!");
            createStudentMenuWindow(nameTextField.getText());
        }
        else {
            loginMessageLabel.setText("Invalid login. Please try again.");
        }
    }

    public void createStudentMenuWindow(String studentName){
        try{
            URL fxmlLocation = GUI.class.getResource("MenuStudent.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            Stage teacherLoginStage = new Stage();
            teacherLoginStage.setTitle("Student menu");
            teacherLoginStage.setScene(new Scene(root, 800, 400));

            MenuControllerStudent controller = loader.getController();
            controller.initData(studentName);

            teacherLoginStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}