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

import java.io.IOException;
import java.security.MessageDigest;

public class Controller_basic {

    public TextField login;
    public PasswordField password;
    public static  Integer logged_account_num;


    private Integer received_account_num;
    private String received_password;
    private Integer stored_acc_num;
    private String stored_password;

    public void OnClickLogin(ActionEvent actionEvent) {

        if(login.getText().trim().isEmpty() || password.getText().trim().isEmpty() ){
            Alert fail= new Alert(Alert.AlertType.INFORMATION);
            fail.initModality(Modality.WINDOW_MODAL);
            fail.setHeaderText("Coś się nie powiodło.");
            fail.setContentText("Uzupełnij brakujące dane.");
            fail.showAndWait();

            } else {

                received_account_num = Integer.parseInt(login.getText());
                received_password = sha256(password.getText());
                Session session = Main.getSession();
                AccountEntity account = new AccountEntity();
                try
                {
                    session.beginTransaction();
                    String numbers = session.createQuery("select accountNum from AccountEntity where accountNum="+received_account_num+"")
                            .getSingleResult().toString();

                    String words = session.createQuery("select accountPass from AccountEntity where accountNum="+received_account_num+"")
                            .getSingleResult().toString();

                    stored_acc_num= Integer.parseInt(numbers);
                    stored_password = words;
                    session.getTransaction().commit();
                    session.save(account);

                        }
                        catch (RuntimeException e)
                        {
                            e.printStackTrace();
                        }
                        finally
                        {
                            session.close();
                        }

                if (received_account_num.equals(stored_acc_num) && received_password.equals(stored_password)){
                            logged_account_num = received_account_num;
                        try{
                                    Parent root1 = FXMLLoader.load(getClass().getResource("../resources/MainPanel.fxml"));
                                    Stage stage1 = new Stage();

                                    final Node source = (Node) actionEvent.getSource();
                                    final Stage stage = (Stage) source.getScene().getWindow();
                                    stage.close();

                                    stage1.initModality(Modality.WINDOW_MODAL);
                                    stage1.setTitle("KuBank");
                                    stage1.setScene(new Scene(root1));
                                    stage1.getScene().getStylesheets().add(Controller_basic.class.getResource("style.css").toExternalForm());
                                    stage1.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                      }
                      else{

                        Alert fail= new Alert(Alert.AlertType.INFORMATION);
                        fail.initModality(Modality.WINDOW_MODAL);
                        fail.setHeaderText("Coś się nie powiodło.");
                        fail.setContentText("Błędnie wprowadzone dane.");
                        fail.showAndWait();
                      }
        }
    }
    public void CloseButton(ActionEvent actionEvent) {

        Platform.exit();
        System.exit(0);

    }

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

    public void OnClickDevelop(ActionEvent actionEvent) {

        Session session = Main.getSession();
        session.beginTransaction();

        for (int i=1; i<21; i++) {
            AccountEntity account = new AccountEntity();
            Integer testuser = i+1000;
            account.setAccountNum(testuser);
            String testassword = new String("test"+i);
            account.setAccountPass(sha256(testassword));
            account.setMoneyValue((int )(Math.random() * 50000 + 100));
            account.setAccountType("PLN");
            account.setAccountExpDate("21.12.2022");
            session.save(account);

            PersonEntity person = new PersonEntity();
            person.setFirstName("Janusz"+i);
            person.setSecondName("Biznes"+i);
            person.setEmailAdress("Janusz"+i+"@Biznes.com");
            person.setTelNumber((int )(Math.random() * 999999999 + 100000000));
            person.setClientAge((int )(Math.random() * 80 + 18));
            session.save(person);


        }
        session.getTransaction().commit();
        session.close();

        Alert created= new Alert(Alert.AlertType.INFORMATION);
        created.initModality(Modality.WINDOW_MODAL);
        created.setHeaderText("Dodano 20 kont.");
        created.setContentText("Zasada: 100x / testx. Enjoy.");
        created.showAndWait();

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
