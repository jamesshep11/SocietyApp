import Members.Member;
import Members.MembersList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDetails
{
    private static DatabaseConnection db;
    private static Stage stage;
    private MembersList membersList;

    //region reference to UI components
    @FXML private TextField txtID, txtSurname, txtName, txtStudentNumber, txtEmail, txtPhone, txtStreet, txtSuburb, txtDiet, txtMedical, txtDisabilities, txtSearch;
    @FXML private ComboBox cbGender, cbBLevel, cbLLevel;
    @FXML private CheckBox chbStudent, chbPaid, chbCompetitive;
    @FXML private Button btnLoad, btnSave, btnMemberAdd, btnMemberDelete, btnEditImage, btnSearch;
    @FXML private Label lblMemberCount;
    @FXML private ListView<Member> lvMembers;
    @FXML private ImageView imgMember;
    @FXML private MenuButton btnFilter;
    //endregion

    public MemberDetails() {
    }

    public void load(DatabaseConnection db) throws IOException {
        this.db = db;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberDetails.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.setTitle("MemberDetails");
        stage.setScene(scene);
        stage.show();
    }

    private void readMembersFromDB() throws SQLException {
        String sql = "SELECT * FROM Members";
        ResultSet rs = db.runSQL(sql);

        while (rs.next()){
            String id = rs.getString("ID");
            String name = rs.getString("Name");
            String surname = rs.getString("Surname");
            String gender = rs.getString("Gender");
            Boolean student = rs.getBoolean("Student");
            String studentNum = rs.getString("StudentNum");
            String bLevel = rs.getString("bLevel");
            String lLevel = rs.getString("lLevel");
            Boolean paid = rs.getBoolean("Paid");
            Boolean competitive = rs.getBoolean("Competitive");
            String email = rs.getString("Email");
            String phone = rs.getString("Phone");
            String street = rs.getString("Street");
            String suburb = rs.getString("Suburb");
            String diet = rs.getString("Diet");
            String medical = rs.getString("Medical");
            String disabilities = rs.getString("Disabilities");
            Image image = new Image(this.getClass().getResourceAsStream(rs.getString("Image")));

            Member newMember = new Member(id, name, surname, gender, student, studentNum, email, phone, street, suburb, bLevel, lLevel, paid, competitive, diet, medical, disabilities, image);

            membersList.add(newMember);
        }
        addBindings();
    }

    private void addBindings(){
        lvMembers.setItems(membersList.getObservableMembers());
        lvMembers.getSelectionModel().selectFirst();

        lvMembers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (oldValue!= null && oldValue.isChanged())
                saveChanges(oldValue);

            if (oldValue != null){
                //region Unbind Old
                txtID.textProperty().unbindBidirectional(oldValue.idProperty());
                txtName.textProperty().unbindBidirectional(oldValue.nameProperty());
                txtSurname.textProperty().unbindBidirectional(oldValue.surnameProperty());
                cbGender.valueProperty().unbindBidirectional(oldValue.genderProperty());
                chbStudent.selectedProperty().unbindBidirectional(oldValue.studentProperty());
                txtStudentNumber.textProperty().unbindBidirectional(oldValue.studentNumberProperty());
                txtEmail.textProperty().unbindBidirectional(oldValue.emailProperty());
                txtPhone.textProperty().unbindBidirectional(oldValue.phoneProperty());
                txtStreet.textProperty().unbindBidirectional(oldValue.streetProperty());
                txtSuburb.textProperty().unbindBidirectional(oldValue.suburbProperty());
                chbPaid.selectedProperty().unbindBidirectional(oldValue.paidProperty());
                chbCompetitive.selectedProperty().unbindBidirectional(oldValue.competitiveProperty());
                cbBLevel.valueProperty().unbindBidirectional(oldValue.bLevelProperty());
                cbLLevel.valueProperty().unbindBidirectional(oldValue.lLevelProperty());
                txtDiet.textProperty().unbindBidirectional(oldValue.dietaryProperty());
                txtMedical.textProperty().unbindBidirectional(oldValue.medicalProperty());
                txtDisabilities.textProperty().unbindBidirectional(oldValue.disabilitiesProperty());
                imgMember.imageProperty().unbindBidirectional(oldValue.imageProperty());
                //endregion
            }

            if (newValue != null) {
                //region Bind New
                txtID.textProperty().bindBidirectional(newValue.idProperty());
                txtName.textProperty().bindBidirectional(newValue.nameProperty());
                txtSurname.textProperty().bindBidirectional(newValue.surnameProperty());
                cbGender.valueProperty().bindBidirectional(newValue.genderProperty());
                chbStudent.selectedProperty().bindBidirectional(newValue.studentProperty());
                txtStudentNumber.textProperty().bindBidirectional(newValue.studentNumberProperty());
                txtEmail.textProperty().bindBidirectional(newValue.emailProperty());
                txtPhone.textProperty().bindBidirectional(newValue.phoneProperty());
                txtStreet.textProperty().bindBidirectional(newValue.streetProperty());
                txtSuburb.textProperty().bindBidirectional(newValue.suburbProperty());
                chbPaid.selectedProperty().bindBidirectional(newValue.paidProperty());
                chbCompetitive.selectedProperty().bindBidirectional(newValue.competitiveProperty());
                cbBLevel.valueProperty().bindBidirectional(newValue.bLevelProperty());
                cbLLevel.valueProperty().bindBidirectional(newValue.lLevelProperty());
                txtDiet.textProperty().bindBidirectional(newValue.dietaryProperty());
                txtMedical.textProperty().bindBidirectional(newValue.medicalProperty());
                txtDisabilities.textProperty().bindBidirectional(newValue.disabilitiesProperty());
                imgMember.imageProperty().bindBidirectional(newValue.imageProperty());
                //endregion
            }
        });

        lblMemberCount.textProperty().bind(lvMembers.getSelectionModel().selectedIndexProperty().add(1).asString().concat(" of ").concat(membersList.size()));
    }

    private void saveChanges(Member member){
        //region Get data from member
        String ID = member.getId();
        String Name = member.getName();
        String Surname = member.getSurname();
        String Gender = member.getGender();
        Boolean Student = member.isStudent();
        String StudentNum = member.getStudentNumber();
        String bLevel = member.getbLevel();
        String lLevel = member.getlLevel();
        Boolean Paid = member.isPaid();
        Boolean Competitive = member.isCompetitive();
        String Email = member.getEmail();
        String Phone = member.getPhone();
        String Street = member.getStreet();
        String Suburb = member.getSuburb();
        String Diet = member.getDietary();
        String Medical = member.getMedical();
        String Disabilities = member.getDisabilities();
        String imageName = "/resources/" + ID + ".jpg";
        //endregion

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(member.getImage(), null), "jpg", new File("./src//resources//" + ID + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sql = String.format("UPDATE Members SET Name = '%s', Surname = '%s', Gender = '%s', Student = %b, StudentNum = '%s', bLevel = '%s', lLevel = '%s', Paid = %b, Competitive = %b, Email = '%s', Phone = '%s', Street = '%s', Suburb = '%s', Diet = '%s', Medical = '%s', Disabilities = '%s', Image = '%s' " +
                "WHERE ID = '%s'", Name, Surname, Gender, Student, StudentNum, bLevel, lLevel, Paid, Competitive, Email, Phone, Street, Suburb, Diet, Medical, Disabilities, imageName, ID);

        db.runSQL(sql);

        member.setChanged(false);

        System.out.println("Changes saved");
    }

    //region UI Methods

    public void btnLoadClicked(){
        membersList = new MembersList(lvMembers);
        try {
            System.out.println("Loading...");
            btnLoad.setDisable(true);
            readMembersFromDB();
            //region Enable Components
            btnSave.setDisable(false);
            btnMemberAdd.setDisable(false);
            btnMemberDelete.setDisable(false);
            btnEditImage.setDisable(false);

            txtName.setDisable(false);
            txtSurname.setDisable(false);
            txtStudentNumber.setDisable(false);
            txtEmail.setDisable(false);
            txtPhone.setDisable(false);
            txtStreet.setDisable(false);
            txtSuburb.setDisable(false);
            txtDiet.setDisable(false);
            txtMedical.setDisable(false);
            txtDisabilities.setDisable(false);

            txtSearch.setDisable(false);
            btnSearch.setDisable(false);
            btnFilter.setDisable(false);

            cbGender.setDisable(false);
            cbBLevel.setDisable(false);
            cbLLevel.setDisable(false);

            chbStudent.setDisable(false);
            chbPaid.setDisable(false);
            chbCompetitive.setDisable(false);
            //endregion

            membersList.goToFirst();
        } catch (Exception e) {
            System.out.println("XML reading issue");
            e.printStackTrace();
        }
    }

    public void btnSaveClicked(){
        Member member = lvMembers.getSelectionModel().getSelectedItem();
        if (member.isChanged())
            saveChanges(member);
        else
            JOptionPane.showMessageDialog(null, "No changes made", "", JOptionPane.INFORMATION_MESSAGE);
    }

    public void btnSearchClicked(){
        membersList.find(txtSearch.getText());
    }

    public void btnMemberAddClicked(){
        System.out.println("[]\tNew Member.");

        AddMember addMember = new AddMember();
        addMember.load((Stage)txtID.getScene().getWindow());
        Member newMember = addMember.getNewMember();

        if(newMember != null) {
            membersList.add(newMember);

            //region Get data from member
            String ID = newMember.getId();
            String Name = newMember.getName();
            String Surname = newMember.getSurname();
            String Gender = newMember.getGender();
            Boolean Student = newMember.isStudent();
            String StudentNum = newMember.getStudentNumber();
            String bLevel = newMember.getbLevel();
            String lLevel = newMember.getlLevel();
            Boolean Paid = newMember.isPaid();
            Boolean Competitive = newMember.isCompetitive();
            String Email = newMember.getEmail();
            String Phone = newMember.getPhone();
            String Street = newMember.getStreet();
            String Suburb = newMember.getSuburb();
            String Diet = newMember.getDietary();
            String Medical = newMember.getMedical();
            String Disabilities = newMember.getDisabilities();
            String imageName = "/resources/" + ID + ".jpg";
            //endregion

            String sql = String.format("INSERT INTO Members VALUES ('%s', '%s', '%s', '%s', %b, '%s', '%s', '%s', %b, %b, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    ID, Name, Surname, Gender, Student, StudentNum, bLevel, lLevel, Paid, Competitive, Email, Phone, Street, Suburb, Diet, Medical, Disabilities, imageName);
            db.runSQL(sql);

            sql = String.format("INSERT INTO Payments (MemberID) VALUES ('%s')", ID);
            db.runSQL(sql);

            sql = String.format("INSERT INTO Register (MemberID) VALUES ('%s')", ID);
            db.runSQL(sql);

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(newMember.getImage(), null), "jpg", new File("./src//resources//" + ID + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("New member added");
        }
    }

    public void btnMemberDeleteClicked(){
        Member member = lvMembers.getSelectionModel().getSelectedItem();

        if (member != null) {
            membersList.remove(member);

            String ID = member.getId();
            String sql = String.format("DELETE FROM Members WHERE ID = '%s';", ID);
            db.runSQL(sql);

            File image = new File("./src//resources//" + ID + ".jpg");
            if (!image.delete())
                try {
                    throw new Exception("Image " + image.getName() + " not deleted");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            System.out.println("Member removed");
        }
    }

    public void btnEditImageClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Member member = lvMembers.getSelectionModel().getSelectedItem();
            member.setImage(new Image(file.toURI().toString()));
        }
    }

    public void btnBackClicked() throws IOException {
        Member member = lvMembers.getSelectionModel().getSelectedItem();
        if (member != null && member.isChanged())
            saveChanges(member);

        stage.close();
        new Menu().load(db);
    }

    //region Filter UI Components
    @FXML CheckBox chbFilterID, chbFilterName, chbFilterSurname, chbFilterGender, chbFilterStudent, chbFilterStudentNumber, chbFilterBLevel, chbFilterLLevel;
    @FXML CheckBox chbFilterPaid, chbFilterCompetitive, chbFilterEmail, chbFilterPhone, chbFilterStreet, chbFilterSuburb, chbFilterDiet, chbFilterMedical, chbFilterDisabilities;
    @FXML TextField txtFilterID, txtFilterName, txtFilterSurname, txtFilterStudentNumber, txtFilterEmail, txtFilterPhone, txtFilterStreet, txtFilterSuburb, txtFilterDiet, txtFilterMedical, txtFilterDisabilities;
    @FXML ComboBox<String> cbFilterGender, cbFilterBLevel, cbFilterLLevel;
    @FXML RadioButton rbFilterStudent, rbFilterPaid, rbFilterCompetitive;
    //endregion
    public void btnFilterClicked(){
        String id , name, surname, gender, studentNumber, bLevel, lLevel, email, phone, street, suburb, diet, medical, disabilities;
        Boolean student, paid, competitive;
        id = name = surname = gender = studentNumber = bLevel = lLevel = email = phone = street = suburb = diet = medical = disabilities = null;
        student = paid = competitive = null;


        if (chbFilterID.isSelected()) id = txtFilterID.getText();
        if (chbFilterName.isSelected()) name = txtFilterName.getText();
        if (chbFilterSurname.isSelected()) surname = txtFilterSurname.getText();
        if (chbFilterGender.isSelected()) gender = cbFilterGender.getValue();
        if (chbFilterStudent.isSelected()) student = rbFilterStudent.isSelected();
        if (chbFilterStudentNumber.isSelected()) studentNumber = txtFilterStudentNumber.getText();
        if (chbFilterBLevel.isSelected()) bLevel = cbFilterBLevel.getValue();
        if (chbFilterLLevel.isSelected()) lLevel = cbFilterLLevel.getValue();
        if (chbFilterPaid.isSelected()) paid = rbFilterPaid.isSelected();
        if (chbFilterCompetitive.isSelected()) competitive = rbFilterCompetitive.isSelected();
        if (chbFilterEmail.isSelected()) email = txtFilterEmail.getText();
        if (chbFilterPhone.isSelected()) phone = txtFilterPhone.getText();
        if (chbFilterStreet.isSelected()) street = txtFilterStreet.getText();
        if (chbFilterSuburb.isSelected()) suburb = txtFilterSuburb.getText();
        if (chbFilterDiet.isSelected()) diet = txtFilterDiet.getText();
        if (chbFilterMedical.isSelected()) medical = txtFilterMedical.getText();
        if (chbFilterDisabilities.isSelected()) disabilities = txtFilterDisabilities.getText();

        membersList.filter(id , name, surname, gender, student, studentNumber, bLevel, lLevel, paid, competitive, email, phone, street, suburb, diet, medical, disabilities);
    }

    //endregion
}
