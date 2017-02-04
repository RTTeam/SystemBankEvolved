package panel;


import basic.AccountEntity;
import basic.Controller_basic;
import basic.Main;
import com.sun.istack.internal.NotNull;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Interceptor;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by root on 25.01.17.
 */
public class Controller_MainPanel {


    public Label clientNameLabel,clientSurnameLabel,clientEmailLabel,clientTelephoneLabel,clientAgeLabel,
                 saldoValueLabel,accTypeLabel,expDateLabel,creditsLabel,creditsValueLabel;

    public TableView historyTableView;
    public TextField recipientInputField,sendAmountInputField;
    public TextArea sendHeaderInputField,lastTransactionField;
    public TableColumn senderColumn,recipientColumn,valueColumn,currencyColumn,dateColumn,timeColumn;
    public String last_transaction;

    public void OnClickLogout(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
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

    public void updateData(String naming, int parameter){
        Session session = Main.getSession();
        session.beginTransaction();

        PersonEntity person = (PersonEntity)session.get(PersonEntity.class,Controller_basic.logged_account_id);

        switch (parameter){
            case 1: person.setFirstName(naming); break;
            case 2: person.setSecondName(naming); break;
            case 3: person.setEmailAdress(naming); break;
            case 4: person.setTelNumber(Integer.parseInt(naming)); break;
            case 5: person.setClientAge(Integer.parseInt(naming)); break;
            default: break;
        }
        session.update(person);
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

        AccountEntity account = (AccountEntity)session.get(AccountEntity.class,Controller_basic.logged_account_id);
        saldoValueLabel.setText(account.getMoneyValue()+" zł");
        accTypeLabel.setText(account.getAccountType());
        expDateLabel.setText(account.getAccountExpDate());
        creditsLabel.setText(Integer.toString((int )(Math.random() * 3 + 1)));
        String currType = account.getAccountType();
        creditsValueLabel.setText(Integer.toString((int )(Math.random() * 12000 + 200))+" "+currType);
        session.getTransaction().commit();
        session.close();

    }

    public void OnSelectHistory(Event event) {

        senderColumn.setCellValueFactory(
                new PropertyValueFactory<TransactionEntity, String>("senderAccountNum"));
        recipientColumn.setCellValueFactory(
                new PropertyValueFactory<TransactionEntity, String>("recipientAccountNum"));
        valueColumn.setCellValueFactory(
                new PropertyValueFactory<TransactionEntity, String>("transferredMoney"));
        currencyColumn.setCellValueFactory(
                new PropertyValueFactory<TransactionEntity, String>("currencyType"));
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<TransactionEntity, String>("transferDate"));
        timeColumn.setCellValueFactory(
                new PropertyValueFactory<TransactionEntity, String>("transferTime"));

        Session session = Main.getSession();
        session.beginTransaction();

        AccountEntity account = (AccountEntity)session.get(AccountEntity.class,Controller_basic.logged_account_id);
        Integer accNum = account.getAccountNum();
        List<TransactionEntity> groupList = session.createQuery("FROM TransactionEntity where senderAccountNum="+accNum+"").list();

        historyTableView.setItems(FXCollections.observableList(groupList));
        historyTableView.getSelectionModel().select(0);

        session.getTransaction().commit();
        session.close();
    }

    public void OnSelectOptions(Event event) {

        Session session = Main.getSession();
        session.beginTransaction();

        PersonEntity person = (PersonEntity)session.get(PersonEntity.class,Controller_basic.logged_account_id);
        clientNameLabel.setText(person.getFirstName());
        clientSurnameLabel.setText(person.getSecondName());
        clientEmailLabel.setText(person.getEmailAdress());
        clientAgeLabel.setText(String.valueOf(person.getClientAge()));
        clientTelephoneLabel.setText(String.valueOf(person.getTelNumber()));
        session.getTransaction().commit();
        session.close();

    }

    public void OnSelectTransactions(Event event) {

        lastTransactionField.setText(last_transaction);


    }

    private boolean CheckTransactionAvailability(int recipientNum,int sendAmount){

        Session session = Main.getSession();
        session.beginTransaction();

        AccountEntity senderAcc = (AccountEntity)session.load(AccountEntity.class,Controller_basic.logged_account_id);
        Integer presentMoneyValue = senderAcc.getMoneyValue();
        Integer receivedID = 0;
        try{
            receivedID = Controller_basic.GetEntityIdbyAccNum(recipientNum);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        boolean confirmation;
        if(receivedID==0){
            Alert fail= new Alert(Alert.AlertType.INFORMATION);
            fail.initModality(Modality.WINDOW_MODAL);
            fail.setHeaderText("Coś się nie powiodło.");
            fail.setContentText("Nie można zlokalizować odbiorcy.");
            fail.showAndWait();
            confirmation = false;
        }else if(presentMoneyValue>sendAmount){
                confirmation = true;
              } else{
                confirmation = false;
        }
        session.getTransaction().commit();
        session.close();
        return confirmation;
    }

    public void SendTransaction(int recipientNum, int sendAmount, String sendHeader){

        Session session = Main.getSession();
        session.beginTransaction();

        AccountEntity senderAcc = (AccountEntity)session.load(AccountEntity.class,Controller_basic.logged_account_id);
        senderAcc.setMoneyValue(senderAcc.getMoneyValue()-sendAmount);
        session.save(senderAcc);
        AccountEntity recipientAcc = (AccountEntity)session.load(AccountEntity.class,Controller_basic.GetEntityIdbyAccNum(recipientNum));
        recipientAcc.setMoneyValue(recipientAcc.getMoneyValue()+sendAmount);
        session.save(recipientAcc);
        TransactionEntity transaction = new TransactionEntity();
        transaction.setSenderAccountNum(senderAcc.getAccountNum());
        transaction.setRecipientAccountNum(recipientAcc.getAccountNum());
        transaction.setTransferredMoney(sendAmount);
        transaction.setTransactionHeader(sendHeader);
        transaction.setCurrencyType(senderAcc.getAccountType());
        transaction.setTransferDate(GetCurrentDate());
        transaction.setTransferTime(GetCurrentTime());

        lastTransactionField.setText("Numer konta odbiorcy : "+recipientAcc.getAccountNum()+
                "\nKwota transakcji: "+sendAmount+"\nRodzaj waluty: "+recipientAcc.getAccountType()+
                "\nData transakcji: "+ GetCurrentDate()+" "+GetCurrentTime()+"\nTytuł przelewu: "+sendHeader);
        last_transaction = lastTransactionField.getText();
        session.save(transaction);
        session.getTransaction().commit();
        session.close();
    }

    public String GetCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String currentDate = dtf.format(now);
        return currentDate;
    }

    public String GetCurrentTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentDate = dtf.format(now);
        return currentDate;
    }

    public void OnClickTransactionSend(ActionEvent actionEvent) {
        Integer recipientNum = Integer.parseInt(recipientInputField.getText());
        Integer moneyTransferred = Integer.parseInt(sendAmountInputField.getText());

        if(CheckTransactionAvailability(recipientNum,moneyTransferred)){
            SendTransaction(recipientNum,moneyTransferred,sendHeaderInputField.getText());
            GetCurrentDate();
            Alert fail= new Alert(Alert.AlertType.INFORMATION);
            fail.initModality(Modality.WINDOW_MODAL);
            fail.setHeaderText("Transakcja udana.");
            fail.setContentText("Kwota zostałą pomyślnie przelana do odbiorcy.");
            fail.showAndWait();
        }
        else{
            Alert fail= new Alert(Alert.AlertType.ERROR);
            fail.initModality(Modality.WINDOW_MODAL);
            fail.setHeaderText("Transakcja nieudana.");
            fail.setContentText("Wystąpił nieznany błąd.");
            fail.showAndWait();
        }

    }


    public void OnClickTechHelp(ActionEvent actionEvent) {
        try {
            Parent techRoot = FXMLLoader.load(getClass().getResource("../resources/TechHelp.fxml"));
            Stage techstage = new Stage();
            techstage.initModality(Modality.APPLICATION_MODAL);
            techstage.setTitle("Pomoc techniczna");
            techstage.setScene(new Scene(techRoot));
            techstage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OnClickProgramInfo(ActionEvent actionEvent) {
        try {
            Parent root2 = FXMLLoader.load(getClass().getResource("../resources/ProgramInfo.fxml"));
            Stage stage2 = new Stage();
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.setTitle("O programie");
            stage2.setScene(new Scene(root2));
            stage2.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OnClickClosePanel(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }
}
