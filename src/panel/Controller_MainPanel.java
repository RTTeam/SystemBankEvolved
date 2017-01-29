package panel;


import basic.AccountEntity;
import basic.Controller_basic;
import basic.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.hibernate.Interceptor;
import org.hibernate.PersistentObjectException;
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
    public Label accTypeLabel;
    public Label expDateLabel;
    public Label creditsLabel;
    public Label creditsValueLabel;

    public void OnClickLogout(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    public void OnClickSendMoney(ActionEvent actionEvent) {
    }

    public String DialogBoxQuestion(){

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Zmiana danych osobowych");
        dialog.setHeaderText("Wprowadzone zmiany wymagają późniejszej autoryzacji.");
        dialog.setContentText("Prosimy o uzupełnienie pola:");
        Optional<String> result = dialog.showAndWait();
        String name1= result.get();
        return name1;
    }

    public void OnClickChangeFirstName(ActionEvent actionEvent) {

        clientNameLabel.setText(DialogBoxQuestion());
        updateData(clientNameLabel.getText(),1);


    }

    public void updateData(String naming, int which){
        Session session = Main.getSession();
        session.beginTransaction();
        String receivedID = session.createQuery("select id from AccountEntity where accountNum="+Controller_basic.logged_account_num+"")
                .getSingleResult().toString();

        Integer newId= Integer.parseInt(receivedID);
        PersonEntity person = (PersonEntity)session.get(PersonEntity.class,newId);
        if(which==1){
            person.setFirstName(naming);
            session.update(person);
        }else if(which==2){
            person.setSecondName(naming);
            session.update(person);
        }
        else if(which==3){
            person.setEmailAdress(naming);
            session.update(person);
        }
        else if(which==4){
            person.setTelNumber(Integer.parseInt(naming));
            session.update(person);
        }
        else if(which==5){
            person.setClientAge(Integer.parseInt(naming));
            session.update(person);
        }

        session.getTransaction().commit();
        session.close();
        }


    public void OnClickChangeSecondName(ActionEvent actionEvent) {

        clientSurnameLabel.setText(DialogBoxQuestion());
        updateData(clientSurnameLabel.getText(),2);
    }

    public void OnClickChangeEmail(ActionEvent actionEvent) {

        clientEmailLabel.setText(DialogBoxQuestion());
        updateData(clientEmailLabel.getText(),3);
    }

    public void OnClickChangeTelephoneNum(ActionEvent actionEvent) {

        clientTelephoneLabel.setText(DialogBoxQuestion());
        updateData(clientTelephoneLabel.getText(),4);


    }

    public void OnClickChangeAge(ActionEvent actionEvent) {

        clientAgeLabel.setText(DialogBoxQuestion());
        updateData(clientAgeLabel.getText(),5);

    }

    public void OnSelectSaldo(Event event) {

        Session session = Main.getSession();
        session.beginTransaction();

        String receivedID = session.createQuery("select id from AccountEntity where accountNum="+Controller_basic.logged_account_num+"")
                .getSingleResult().toString();

        Integer newId= Integer.parseInt(receivedID);
        AccountEntity account = (AccountEntity)session.get(AccountEntity.class,newId);
        saldoValueLabel.setText(account.getMoneyValue()+" zł");
        accTypeLabel.setText(account.getAccountType());
        expDateLabel.setText(account.getAccountExpDate());
        creditsLabel.setText(Integer.toString((int )(Math.random() * 12 + 1)));
        creditsValueLabel.setText(Integer.toString((int )(Math.random() * 12000 + 200)));
        session.getTransaction().commit();
        session.close();

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

        String receivedID = session.createQuery("select id from AccountEntity where accountNum="+Controller_basic.logged_account_num+"")
                .getSingleResult().toString();

        Integer newId= Integer.parseInt(receivedID);
        PersonEntity person = (PersonEntity)session.get(PersonEntity.class,newId);
        clientNameLabel.setText(person.getFirstName());
        clientSurnameLabel.setText(person.getSecondName());
        clientEmailLabel.setText(person.getEmailAdress());
        clientAgeLabel.setText(String.valueOf(person.getClientAge()));
        clientTelephoneLabel.setText(String.valueOf(person.getTelNumber()));
        session.getTransaction().commit();
        session.close();

    }
}
