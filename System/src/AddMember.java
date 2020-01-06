import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class AddMember
{
    //region reference to UI Components
    @FXML TextField txtID, txtName, txtSurname, txtStudentNumber, txtEmail, txtPhone, txtStreet, txtSuburb, txtDiet, txtMedical, txtDisabilities, txtImage;
    @FXML ComboBox cbGender, cbBLevel, cbLLevel;
    @FXML CheckBox chbPaid, chbCompetitive, chbStudent;
    @FXML Button btnSave;
    //endregion

    private Stage Stage;
    private Member newMember = null;

    public AddMember() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMember.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("Error");
        }
        Scene scene = new Scene(root);

        Stage = new Stage();
        Stage.setTitle("Add Member");
        Stage.setScene(scene);
        Stage.initStyle(StageStyle.UTILITY);
        Stage.initModality(Modality.APPLICATION_MODAL);
        Stage.show();
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

        newMember = new Member(id, name, surname, gender, student, studentNumber, email, phone, street, suburb, bLevel, lLevel, paid, competitive, diet, medical, disabilities, image);

        Stage.hide();
    }

    public Member getNewMember(){
        return newMember;
    }
}
