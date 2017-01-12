package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public LoginModel loginModel = new LoginModel();

    @FXML
    private Label isConnected;


    public TextField login;
    public PasswordField password;


    public void OnClickLogin(ActionEvent actionEvent) {

        if(login.getText().trim().isEmpty() || password.getText().trim().isEmpty() ){
            Alert fail= new Alert(Alert.AlertType.INFORMATION);
            fail.initModality(Modality.WINDOW_MODAL);
            fail.setHeaderText("Coś się nie powiodło.");
            fail.setContentText("Uzupełnij brakujące dane.");
            fail.showAndWait();
        } else {
            //TODO: Przesłanie do bazy danych.
            try{
                Parent root1 = FXMLLoader.load(getClass().getResource("MainPanel.fxml"));
                Stage stage1 = new Stage();
                stage1.initModality(Modality.WINDOW_MODAL);

                final Node source = (Node) actionEvent.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();

                stage1.setTitle("Panel sterowania kontem");
                stage1.setScene(new Scene(root1));
                stage1.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void CloseButton(ActionEvent actionEvent) {

    }

    public void OnClickLogout(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(loginModel.isDbConnected()) {
            isConnected.setText("Connected");
        }
        else{

            isConnected.setText("Not Connected");
        }

    }
}
