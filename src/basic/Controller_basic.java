package basic;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import java.io.IOException;
import java.security.MessageDigest;

public class Controller_basic {

    public TextField login;
    public PasswordField password;
    public static String logged_account_num;


    private String received_account_num;
    private String received_password;
    private String stored_acc_num;
    private String stored_password;

    public void OnClickLogin(ActionEvent actionEvent) {

        if(login.getText().trim().isEmpty() || password.getText().trim().isEmpty() ){
            Alert fail= new Alert(Alert.AlertType.INFORMATION);
            fail.initModality(Modality.WINDOW_MODAL);
            fail.setHeaderText("Coś się nie powiodło.");
            fail.setContentText("Uzupełnij brakujące dane.");
            fail.showAndWait();

            } else {

                received_account_num = sha256(login.getText());
                received_password = sha256(password.getText());
                Session session = Main.getSession();
                AccountEntity account = new AccountEntity();
                try
                {

                    session.beginTransaction();
                    String numbers = session.createQuery("select accountNum from AccountEntity where accountNum='"+received_account_num+"'")
                            .getSingleResult().toString();

                    String words = session.createQuery("select accountPass from AccountEntity where accountNum='"+received_account_num+"'")
                            .getSingleResult().toString();


                    stored_acc_num= numbers;
                    stored_password = words;

                    Long stored_id = account.getId();
                    System.out.println("Id zalogowanego klienta: "+stored_id);

                    System.out.println(account.getAccountNum());
                    System.out.println(account.getAccountPass());

                    session.getTransaction().commit();
                    session.save(account);

                        }
                        catch (RuntimeException e)
                        {
                            //e.printStackTrace();
                            System.out.println("Brak takiego konta.");
                        }
                        finally
                        {
                            session.close();
                        }

                if (received_account_num.equals(stored_acc_num) && received_password.equals(stored_password)){

                            logged_account_num = stored_acc_num;

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

        for (int i=0; i<100; i++) {
            AccountEntity account = new AccountEntity();
            String testuser = new String("User"+i);
            account.setAccountNum(sha256(testuser));
            String testassword = new String("test"+i);
            account.setAccountPass(sha256(testassword));
            account.setFirstName("Klient "+i);
            account.setsSecondName("Nazwisko "+i);
            session.save(account);
        }
        session.getTransaction().commit();
        session.close();

        Alert created= new Alert(Alert.AlertType.INFORMATION);
        created.initModality(Modality.WINDOW_MODAL);
        created.setHeaderText("Dodano 100 kont.");
        created.setContentText("Zasada: Userx / testx. Enjoy.");
        created.showAndWait();

    }
}
