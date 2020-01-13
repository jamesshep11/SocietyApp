import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddMember
{
    //region reference to UI Components
    @FXML TextField txtID, txtName, txtSurname, txtStudentNumber, txtEmail, txtPhone, txtStreet, txtSuburb, txtDiet, txtMedical, txtDisabilities, txtImage;
    @FXML ComboBox cbGender, cbBLevel, cbLLevel;
    @FXML CheckBox chbPaid, chbCompetitive, chbStudent;
    @FXML Button btnSave;
    //endregion

    private static Stage stage;
    private static Member newMember = null;
    private static DatabaseConnection db;

    public AddMember() {
    }

    public void loadAddMember(Stage parent){
        db = new DatabaseConnection();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMember.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.setTitle("Add Member");
        stage.setScene(scene);
        stage.initOwner(parent);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void btnSelectImageClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null)
            txtImage.setText(file.toURI().toString());
    }

    public void btnSaveClicked(){
        //region Get info from UI
        String id = txtID.getText();
        String surname = txtSurname.getText();
        String name = txtName.getText();
        String gender = cbGender.getValue().toString();
        Boolean student = chbStudent.isSelected();
        String studentNumber = txtStudentNumber.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String street = txtStreet.getText();
        String suburb = txtSuburb.getText();
        String bLevel = cbBLevel.getValue().toString();
        String lLevel = cbLLevel.getValue().toString();
        Boolean paid = chbPaid.isSelected();
        Boolean competitive = chbCompetitive.isSelected();
        String diet = txtDiet.getText();
        String medical = txtMedical.getText();
        String disabilities = txtDisabilities.getText();
        Image image = new Image(txtImage.getText());
        //endregion

        String sql = "SELECT * FROM Members WHERE ID = '" + id + "';";
        ResultSet rs = db.runSQL(sql);
        try {
            if (rs.next()) {
                System.out.println("That ID already exists. Please enter a unique ID.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        newMember = new Member(id, name, surname, gender, student, studentNumber, email, phone, street, suburb, bLevel, lLevel, paid, competitive, diet, medical, disabilities, image);

        db.close();
        stage.hide();
    }

    public Member getNewMember(){
        return newMember;
    }
    
}
