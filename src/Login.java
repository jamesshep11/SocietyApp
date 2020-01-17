import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends Application {

    @FXML private TextField txtUsername, txtPassword;

    private static DatabaseConnection db;
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        db = new DatabaseConnection();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage = primaryStage;
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public void load(DatabaseConnection db) throws IOException {
        this.db = db;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public void btnLoginClicked() throws SQLException, IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        String sql = String.format("SELECT * FROM Committee WHERE Username = '%s' AND Password = '%s'", username, password);
        ResultSet rs = db.runSQL(sql);
        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, "Incorrect username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        stage.close();
        new Menu().load(db);
    }
}
