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

public class Controller_basic {

    public TextField login;
    public PasswordField password;


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

                received_account_num = login.getText();
                received_password = password.getText();
                Session session = Main.getSession();
                AccountEntity account = new AccountEntity();
                try
                {

                    session.beginTransaction();
                    String numbers = session.createQuery("select accountNum from AccountEntity where accountNum='"+received_account_num+"'")
                            .getSingleResult().toString();

                    String words = session.createQuery("select accountPass from AccountEntity where accountPass='"+received_password+"'")
                            .getSingleResult().toString();


                    stored_acc_num= numbers;
                    stored_password = words;
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

                if (received_account_num.equals(stored_acc_num) || received_password.equals(stored_password)){

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

    public void OnClickDevelop(ActionEvent actionEvent) {

        Session session = Main.getSession();
        session.beginTransaction();

        for (int i=0; i<100; i++) {
            AccountEntity account = new AccountEntity();
            account.setAccountNum("50"+i);
            account.setAccountPass("test"+i);
            account.setFirstName("Klient "+i);
            account.setsSecondName("Nazwisko "+i);
            session.save(account);
        }
        session.getTransaction().commit();
        session.close();
    }
}