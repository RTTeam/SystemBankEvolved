package panel;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Created by root on 25.01.17.
 */
public class Controller_MainPanel {


    public void OnClickLogout(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void OnClickSendMoney(ActionEvent actionEvent) {
    }

    public void OnClickChangeFirstName(ActionEvent actionEvent) {
    }

    public void OnClickChangeSecondName(ActionEvent actionEvent) {
    }

    public void OnClickChangeEmail(ActionEvent actionEvent) {
    }

    public void OnClickChangeTelephoneNum(ActionEvent actionEvent) {
    }
}
