package basic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import panel.PersonEntity;
import sun.rmi.runtime.Log;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.security.MessageDigest;

public class Controller_basic {

    public TextField login;
    public PasswordField password;
    public static Integer logged_account_id;
    public MenuItem developButton,closeButton,programInfo,techHelp;

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static Integer GetEntityIdbyAccNum(Integer accNum){

        Session session = Main.getSession();
        session.beginTransaction();
        String searchedID = session.createQuery("select id from AccountEntity where accountNum="+ accNum +"")
                .getSingleResult().toString();
        Integer searchedIntID = Integer.parseInt(searchedID);
        session.getTransaction().commit();
        session.close();

        return searchedIntID;
    }

    private boolean LoginUser() {
        Integer inputAccNum = Integer.parseInt(login.getText());
        String inputPassword = sha256(password.getText());
        Session session = Main.getSession();
        session.beginTransaction();

        AccountEntity account = (AccountEntity) session.load(AccountEntity.class, GetEntityIdbyAccNum(inputAccNum));
        boolean confirmation;

            if (account.getAccountPass().equals(inputPassword)) {
                    logged_account_id = GetEntityIdbyAccNum(inputAccNum);
                    confirmation = true;
                } else {
                    confirmation = false;
                }
        session.getTransaction().commit();
        session.save(account);
        session.close();
        return confirmation;
    }

    public void OnClickLogin(ActionEvent actionEvent) {

        if(login.getText().trim().isEmpty() || password.getText().trim().isEmpty() ){
            Alert fail= new Alert(Alert.AlertType.INFORMATION);
            fail.initModality(Modality.WINDOW_MODAL);
            fail.setHeaderText("Coś się nie powiodło.");
            fail.setContentText("Uzupełnij brakujące dane.");
            fail.showAndWait();
            }
            else if (LoginUser()){
                    try{

                        Parent panelRoot = FXMLLoader.load(getClass().getResource("../resources/MainPanel.fxml"));
                        panelRoot.setId("main-panel");
                        Stage panelStage = new Stage();

                        final Node source = (Node) actionEvent.getSource();
                        final Stage stage = (Stage) source.getScene().getWindow();
                        stage.close();

                        panelStage.initModality(Modality.WINDOW_MODAL);
                        panelStage.setTitle("KuBank");
                        panelStage.setScene(new Scene(panelRoot));
                        panelStage.getScene().getStylesheets().add(Controller_basic.class.getResource("style.css").toExternalForm());
                        panelStage.setResizable(false);
                        panelStage.show();
                    }catch (IOException e) {e.printStackTrace();}
                } else{

                    Alert fail= new Alert(Alert.AlertType.INFORMATION);
                    fail.initModality(Modality.WINDOW_MODAL);
                    fail.setHeaderText("Coś się nie powiodło.");
                    fail.setContentText("Błędnie wprowadzone dane.");
                    fail.showAndWait();

                  }
        }

    public void CloseButton(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void OnClickDevelop(ActionEvent actionEvent) {

        CreateTestUsers();
        Alert created= new Alert(Alert.AlertType.INFORMATION);
        created.initModality(Modality.WINDOW_MODAL);
        created.setTitle("Utworzenie kont testowych");
        created.setHeaderText("Dodano 20 kont w celu testowania działania aplikacji.");
        created.setContentText("Zasada: 1001,1002,1003... / test1,test2,test3... Enjoy.\nPrzykład: 1001/test1");
        created.showAndWait();
        developButton.setText("Niedostępny");
        developButton.setDisable(true);

    }

    public void CreateTestUsers(){
        Session session = Main.getSession();
        session.beginTransaction();

        for (int i=1; i<21; i++) {
            AccountEntity account = new AccountEntity();
            PersonEntity person = new PersonEntity();
            Integer testuser = i+1000;
            account.setAccountNum(testuser);
            String testpassword = new String("test"+i);
            account.setAccountPass(sha256(testpassword));
            account.setMoneyValue((int )(Math.random() * 50000 + 100));
            account.setAccountType("PLN");
            account.setAccountExpDate("21.12.2022");
            account.setAccountOwnerId(person);
            session.save(account);
            person.setFirstName("Janusz"+i);
            person.setSecondName("Biznes"+i);
            person.setEmailAdress("Janusz"+i+"@Biznes.com");
            person.setTelNumber((int )(Math.random() * 999999999 + 100000000));
            person.setClientAge((int )(Math.random() * 80 + 18));
            session.save(person);
        }
        session.getTransaction().commit();
        session.close();

    }

    public void ProgramInfo(ActionEvent actionEvent) {

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

    public void TechHelp(ActionEvent actionEvent) {
    try {
            Parent root3 = FXMLLoader.load(getClass().getResource("../resources/TechHelp.fxml"));
            Stage stage3 = new Stage();
            stage3.initModality(Modality.APPLICATION_MODAL);
            stage3.setTitle("Pomoc techniczna");
            stage3.setScene(new Scene(root3));
            stage3.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
