package panel;


import basic.AccountEntity;
import basic.Controller_basic;
import basic.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.util.List;
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
    public TableView historyTableView;
    public Label saldoValueLabel;

    public void OnClickLogout(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void OnClickSendMoney(ActionEvent actionEvent) {
    }

    public String DialogBoxQuestion(){

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Zmiana danych osobowych");
        dialog.setHeaderText("Wprowadzone zmiany wymagają późniejszej autoryzacji.");
        dialog.setContentText("Prosimy o uzupełnienie pola:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name->System.out.println("Your name: " + name));

        String name1= result.get();

        return name1;
    }

    public void OnClickChangeFirstName(ActionEvent actionEvent) {

        clientNameLabel.setText(DialogBoxQuestion());

        Session session = Main.getSession();
        session.beginTransaction();

        String receivedClientID = session.createQuery("select accountOwnerId from AccountEntity where accountNum='"+Controller_basic.logged_account_num+"'")
                .getSingleResult().toString();
//TODO;
        //session.createQuery("Insert into PersonEntity Open  firstName where personID='"+receivedClientID+"'")
        //        .getSingleResult().toString();




    }


    public void OnClickChangeSecondName(ActionEvent actionEvent) {

        clientSurnameLabel.setText(DialogBoxQuestion());


    }

    public void OnClickChangeEmail(ActionEvent actionEvent) {

        clientEmailLabel.setText(DialogBoxQuestion());
    }

    public void OnClickChangeTelephoneNum(ActionEvent actionEvent) {

        clientTelephoneLabel.setText(DialogBoxQuestion());

    }

    public void OnClickChangeAge(ActionEvent actionEvent) {

        clientAgeLabel.setText(DialogBoxQuestion());

    }

    public void OnSelectSaldo(Event event) {

        Session session = Main.getSession();
        session.beginTransaction();

        String moneyData = session.createQuery("select moneyValue from AccountEntity where accountNum='"+Controller_basic.logged_account_num+"'")
                .getSingleResult().toString();

        saldoValueLabel.setText(moneyData+" zł");


    }

    public void OnSelectHistory(Event event) {

        Session session = Main.getSession();
        session.beginTransaction();

        List<Group> groupList = session.createQuery("FROM PersonEntity ").list();

        historyTableView.setItems(FXCollections.observableList(groupList));
        historyTableView.getSelectionModel().select(0);
        session.getTransaction().commit();
        session.close();
    }

    public void OnSelectTransactions(Event event) {


    }

    public void OnSelectOptions(Event event) {

        Session session = Main.getSession();
        session.beginTransaction();

        String receivedClientID = session.createQuery("select accountOwnerId from AccountEntity where accountNum='"+Controller_basic.logged_account_num+"'")
                .getSingleResult().toString();

        try {
            String storedClientName = session.createQuery("select firstName from PersonEntity where personID='"+receivedClientID+"'")
                    .getSingleResult().toString();
            clientNameLabel.setText(storedClientName);


            String storedClientSurname = session.createQuery("select secondName from PersonEntity where personID='"+receivedClientID+"'")
                    .getSingleResult().toString();
            clientSurnameLabel.setText(storedClientSurname);

            String storedClientEmail = session.createQuery("select emailAdress from PersonEntity where personID='"+receivedClientID+"'")
                    .getSingleResult().toString();
            clientEmailLabel.setText(storedClientEmail);

            String storedClientAge = session.createQuery("select clientAge from PersonEntity where personID='"+receivedClientID+"'")
                    .getSingleResult().toString();
            clientAgeLabel.setText(storedClientAge);

            String storedClientTelephoneNum = session.createQuery("select telNumber from PersonEntity where personID='"+receivedClientID+"'")
                    .getSingleResult().toString();
            clientTelephoneLabel.setText(storedClientTelephoneNum);

        } catch (RuntimeException e)
        {
            e.printStackTrace();
        }
        session.getTransaction().commit();

        session.close();

    }
}
