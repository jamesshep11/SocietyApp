package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Controller controller = new Controller();

        /*Stage addStud = addMemberStage(primaryStage);
        controller.ConnectToUI(scene, addStud);
*/
        primaryStage.setTitle("System");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    public void doSomething(){
        System.out.println("It worked");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
