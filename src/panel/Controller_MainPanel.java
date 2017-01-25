package panel;


import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Created by root on 25.01.17.
 */
public class Controller_MainPanel {


    public Label clientNameLabel;
    public Label clientSurnameLabel;
    public Label clientEmailLabel;
    public Label clientTelephoneLabel;
    public Label clientAgeLabel;


    public void OnClickLogout(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void OnClickSendMoney(ActionEvent actionEvent) {
    }

    public void OnClickChangeFirstName(ActionEvent actionEvent) {

        //TODO:Upakować w funkcje:
        TextInputDialog dialog = new TextInputDialog("Imie");
        dialog.setTitle("Zmiana danych osobowych");
        dialog.setHeaderText("Zmiana danych wymaga późniejszej weryfikacji.");
        dialog.setContentText("Prosimy o wprowadzenie nowego imienia");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name->System.out.println("Your name: " + name));


        }



    public void OnClickChangeSecondName(ActionEvent actionEvent) {

        TextInputDialog dialog = new TextInputDialog(" Nazwisko");
        dialog.setTitle("Zmiana danych osobowych");
        dialog.setHeaderText("Zmiana danych wymaga późniejszej weryfikacji.");
        dialog.setContentText("Prosimy o wprowadzenie nowego nazwiska:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(surname->System.out.println("Your surname: " + surname));

    }

    public void OnClickChangeEmail(ActionEvent actionEvent) {
    }

    public void OnClickChangeTelephoneNum(ActionEvent actionEvent) {


        TextInputDialog dialog = new TextInputDialog("123456789");
        dialog.setTitle("Zmiana danych osobowych");
        dialog.setHeaderText("Zmiana danych wymaga późniejszej weryfikacji.");
        dialog.setContentText("Prosimy o wprowadzenie nowego numeru telefonu:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name->System.out.println("Your tel. is: " + name));
    }

    public void OnClickChangeAge(ActionEvent actionEvent) {


        TextInputDialog dialog = new TextInputDialog("sweet sixteen");
        dialog.setTitle("Zmiana danych osobowych");
        dialog.setHeaderText("Zmiana danych wymaga późniejszej weryfikacji.");
        dialog.setContentText("Prosimy o wprowadzenie swojego wieku");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name->System.out.println("Your age is " + name));
    }
}
