package sample;

import javafx.beans.property.*;
import javafx.scene.image.Image;

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

    public void setId(String id)
    {
        this.id.set(id);
    }

    public String getName()
    {
        return name.get();
    }

    public StringProperty nameProperty()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public String getSurname()
    {
        return surname.get();
    }

    public StringProperty surnameProperty()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname.set(surname);
    }

    public String getGender()
    {
        return gender.get();
    }

    public StringProperty genderProperty()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender.set(gender);
    }

    public Boolean isStudent()
    {
        return student.get();
    }

    public BooleanProperty studentProperty()
    {
        return student;
    }

    public void setStudent(boolean student)
    {
        this.student.set(student);
    }

    public String getStudentNumber()
    {
        return studentNumber.get();
    }

    public StringProperty studentNumberProperty()
    {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber)
    {
        this.studentNumber.set(studentNumber);
    }

    public String getEmail()
    {
        return email.get();
    }

    public StringProperty emailProperty()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email.set(email);
    }

    public String getPhone()
    {
        return phone.get();
    }

    public StringProperty phoneProperty()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone.set(phone);
    }

    public String getStreet()
    {
        return street.get();
    }

    public StringProperty streetProperty()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street.set(street);
    }

    public String getSuburb()
    {
        return suburb.get();
    }

    public StringProperty suburbProperty()
    {
        return suburb;
    }

    public void setSuburb(String suburb)
    {
        this.suburb.set(suburb);
    }

    public String getbLevel()
    {
        return bLevel.get();
    }

    public StringProperty bLevelProperty()
    {
        return bLevel;
    }

    public void setbLevel(String bLevel)
    {
        this.bLevel.set(bLevel);
    }

    public String getlLevel()
    {
        return lLevel.get();
    }

    public StringProperty lLevelProperty()
    {
        return lLevel;
    }

    public void setlLevel(String lLevel)
    {
        this.lLevel.set(lLevel);
    }

    public Boolean isPaid()
    {
        return paid.get();
    }

    public BooleanProperty paidProperty()
    {
        return paid;
    }

    public void setPaid(boolean paid)
    {
        this.paid.set(paid);
    }

    public Boolean isCompetitive()
    {
        return competitive.get();
    }

    public BooleanProperty competitiveProperty()
    {
        return competitive;
    }

    public void setCompetitive(boolean competitive)
    {
        this.competitive.set(competitive);
    }

    public String getDietary()
    {
        return dietary.get();
    }

    public StringProperty dietaryProperty()
    {
        return dietary;
    }

    public void setDietary(String dietary)
    {
        this.dietary.set(dietary);
    }

    public String getMedical()
    {
        return medical.get();
    }

    public StringProperty medicalProperty()
    {
        return medical;
    }

    public void setMedical(String medical)
    {
        this.medical.set(medical);
    }

    public String getDisabilities()
    {
        return disabilities.get();
    }

    public StringProperty disabilitiesProperty()
    {
        return disabilities;
    }

    public void setDisabilities(String disabilities)
    {
        this.disabilities.set(disabilities);
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

    //endregion

    @Override
    public String toString() {
        return String.format("%s %s", name.get(), surname.get());
    }
}
