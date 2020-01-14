import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Member {
    //region Attributes
    private StringProperty id = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty surname = new SimpleStringProperty();
    private StringProperty gender = new SimpleStringProperty();
    private BooleanProperty student = new SimpleBooleanProperty();
    private StringProperty studentNumber = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty phone = new SimpleStringProperty();
    private StringProperty street = new SimpleStringProperty();
    private StringProperty suburb = new SimpleStringProperty();
    private StringProperty bLevel = new SimpleStringProperty();
    private StringProperty lLevel = new SimpleStringProperty();
    private BooleanProperty paid = new SimpleBooleanProperty();
    private BooleanProperty competitive = new SimpleBooleanProperty();
    private StringProperty dietary = new SimpleStringProperty();
    private StringProperty medical = new SimpleStringProperty();
    private StringProperty disabilities = new SimpleStringProperty();
    private ObjectProperty<Image> image = new SimpleObjectProperty<>();
    //endregion
    private Boolean changed;

    public Member(String id, String name, String surname, String gender, Boolean student, String studentNumber, String email, String phone, String street, String suburb, String bLevel, String lLevel, Boolean paid,Boolean competitive, String dietary, String medical, String disabilities, Image image)
    {
        this.id.setValue(id);
        this.name.setValue(name);
        this.surname.setValue(surname);
        this.gender.setValue(gender);
        this.student.setValue(student);
        this.studentNumber.setValue(studentNumber);
        this.email.setValue(email);
        this.phone.setValue(phone);
        this.street.setValue(street);
        this.suburb.setValue(suburb);
        this.bLevel.setValue(bLevel);
        this.lLevel.setValue(lLevel);
        this.paid.setValue(paid);
        this.competitive.setValue(competitive);
        this.dietary.setValue(dietary);
        this.medical.setValue(medical);
        this.disabilities.setValue(disabilities);
        this.image.setValue(image);

        changed = false;
        addBindings();
    }

    //region Getters and Setters
    public String getId()
    {
        return id.get();
    }

    public StringProperty idProperty()
    {
        return id;
    }

    public String getName()
    {
        return name.get();
    }

    public StringProperty nameProperty()
    {
        return name;
    }

    public String getSurname()
    {
        return surname.get();
    }

    public StringProperty surnameProperty()
    {
        return surname;
    }

    public String getGender()
    {
        return gender.get();
    }

    public StringProperty genderProperty()
    {
        return gender;
    }

    public Boolean isStudent()
    {
        return student.get();
    }

    public BooleanProperty studentProperty()
    {
        return student;
    }

    public String getStudentNumber()
    {
        return studentNumber.get();
    }

    public StringProperty studentNumberProperty()
    {
        return studentNumber;
    }

    public String getEmail()
    {
        return email.get();
    }

    public StringProperty emailProperty()
    {
        return email;
    }

    public String getPhone()
    {
        return phone.get();
    }

    public StringProperty phoneProperty()
    {
        return phone;
    }

    public String getStreet()
    {
        return street.get();
    }

    public StringProperty streetProperty()
    {
        return street;
    }

    public String getSuburb()
    {
        return suburb.get();
    }

    public StringProperty suburbProperty()
    {
        return suburb;
    }

    public String getbLevel()
    {
        return bLevel.get();
    }

    public StringProperty bLevelProperty()
    {
        return bLevel;
    }

    public String getlLevel()
    {
        return lLevel.get();
    }

    public StringProperty lLevelProperty()
    {
        return lLevel;
    }

    public Boolean isPaid()
    {
        return paid.get();
    }

    public BooleanProperty paidProperty()
    {
        return paid;
    }

    public Boolean isCompetitive()
    {
        return competitive.get();
    }

    public BooleanProperty competitiveProperty()
    {
        return competitive;
    }

    public String getDietary()
    {
        return dietary.get();
    }

    public StringProperty dietaryProperty()
    {
        return dietary;
    }

    public String getMedical()
    {
        return medical.get();
    }

    public StringProperty medicalProperty()
    {
        return medical;
    }

    public String getDisabilities()
    {
        return disabilities.get();
    }

    public StringProperty disabilitiesProperty()
    {
        return disabilities;
    }

    public Image getImage()
    {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image.set(image);
    }

    public Boolean isChanged(){
        return changed;
    }

    public void setChanged(Boolean changed){
        this.changed = changed;
    }

    //endregion

    private void addBindings(){
        id.addListener((observable, oldValue, newValue) -> changed = true);
        name.addListener((observable, oldValue, newValue) -> changed = true);
        surname.addListener((observable, oldValue, newValue) -> changed = true);
        gender.addListener((observable, oldValue, newValue) -> changed = true);
        student.addListener((observable, oldValue, newValue) -> changed = true);
        studentNumber.addListener((observable, oldValue, newValue) -> changed = true);
        email.addListener((observable, oldValue, newValue) -> changed = true);
        phone.addListener((observable, oldValue, newValue) -> changed = true);
        street.addListener((observable, oldValue, newValue) -> changed = true);
        suburb.addListener((observable, oldValue, newValue) -> changed = true);
        bLevel.addListener((observable, oldValue, newValue) -> changed = true);
        lLevel.addListener((observable, oldValue, newValue) -> changed = true);
        paid.addListener((observable, oldValue, newValue) -> changed = true);
        competitive.addListener((observable, oldValue, newValue) -> changed = true);
        dietary.addListener((observable, oldValue, newValue) -> changed = true);
        medical.addListener((observable, oldValue, newValue) -> changed = true);
        disabilities.addListener((observable, oldValue, newValue) -> changed = true);
        image.addListener((observable, oldValue, newValue) -> changed = true);
    }

    public void markAttendance(DatabaseConnection db) throws SQLException
    {

    }

    @Override
    public String toString() {
        return String.format("%s %s", name.get(), surname.get());
    }
}
