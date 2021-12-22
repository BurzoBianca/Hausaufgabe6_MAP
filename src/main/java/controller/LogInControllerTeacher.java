package controller;
import repository.*;
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

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LogInControllerTeacher implements Initializable {
    final TeacherRepo teacherRepo = new TeacherRepo();

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private ImageView teacherHeaderImageView;

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

        File teachersHeaderFile = new File("img/teachermenu.png");
        Image teachersHeaderImage = new Image(teachersHeaderFile.toURI().toString());
        teacherHeaderImageView.setImage(teachersHeaderImage);

        File lockFile = new File("img/lock.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }

    public void validateLogin(){
        if(teacherRepo.getAll().stream().anyMatch(elem -> Objects.equals(elem.getFirstName(), nameTextField.getText()))){
            loginMessageLabel.setText("Congratulations!");
            createTeacherMenuWindow(nameTextField.getText());
        }
        else {
            loginMessageLabel.setText("Invalid login. Please try again.");
        }
    }

    public void createTeacherMenuWindow(String teacherName){
        try{
            URL fxmlLocation = GUI.class.getResource("MenuTeacher.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            Stage teacherLoginStage = new Stage();
            teacherLoginStage.setTitle("Teacher menu");
            teacherLoginStage.setScene(new Scene(root, 800, 400));

            MenuControllerTeacher controller = loader.getController();
            controller.initData(teacherName);

            teacherLoginStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}