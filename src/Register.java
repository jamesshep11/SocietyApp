import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register
{
    @FXML ChoiceBox<String> chbMembers, chbEvents;
    @FXML Button btnAttendance, btnUnAttendance, btnAddEvent;

    DatabaseConnection db;
    Stage stage;

    public Register() {
    }

    public void loadRegisterScene(Stage parent)
    {
        db = new DatabaseConnection();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.initOwner(parent);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        stage.setOnCloseRequest(event -> {
            parent.show();
            stage.close();
        });
    }

    public void btnAddEventClicked() throws SQLException
    {
        String eventName = JOptionPane.showInputDialog(null, "Event name: ", "New Event", JOptionPane.QUESTION_MESSAGE);
        String sql = String.format("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Register' AND COLUMN_NAME = '%s'", eventName);
        ResultSet rs = db.runSQL(sql);

        if(rs.next()){
            JOptionPane.showMessageDialog(null, "That event already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        newEvent(eventName);
    }

    private void newEvent(String eventName){
        String sql = "";
    }

}
