package basic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;


public class Main extends Application {

    public static  SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("resources/hibernate.cfg.xml");

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Exception ex) {
            //throw new ExceptionInInitializerError(ex);
            ex.printStackTrace();
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../resources/Login.fxml"));
        primaryStage.setTitle("KuBank");
        primaryStage.setScene(new Scene(root, 373  , 244));

        root.setId("panel");
        primaryStage.getScene().getStylesheets().add(Main.class.getResource("style.css").toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.show();


    }
    @Override
    public void stop(){
                System.exit(0);
            }
    public static void main(String[] args) {
        launch(args);
    }
}
