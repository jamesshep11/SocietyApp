import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Menu
{
    private static DatabaseConnection db;
    private static Stage stage;

    public Menu() {
    }

    public void load(DatabaseConnection db) throws IOException {
        this.db = db;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void btnMemberDetailsClicked() throws IOException {
        stage.close();
        new MemberDetails().load(db);
    }

    public void btnRegisterClicked() throws IOException, SQLException {
        stage.close();
        new Register().load(db);
    }

    public void btnAttendanceClicked() throws IOException, SQLException {
        stage.close();
        new Attendance().load(db);
    }

    public void btnLogOutClicked() throws IOException {
        stage.close();
        new Login().load(db);
    }
}
