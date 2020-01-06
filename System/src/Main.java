import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("Member Records");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //region reference to UI components
    @FXML
    private TextField txtID, txtSurname, txtName, txtStudentNumber, txtEmail, txtPhone, txtStreet, txtSuburb, txtDiet, txtMedical, txtDisabilities, txtSearch;
    @FXML private ComboBox cbGender, cbBLevel, cbLLevel;
    @FXML private CheckBox chbStudent, chbPaid, chbCompetitive;
    @FXML private Button btnLoad, btnSave, btnMemberAdd, btnMemberDelete, btnEditImage, btnSearch;
    @FXML private Label lblMemberCount;
    @FXML private ListView<Member> lvMembers;
    @FXML private ImageView imgMember;
    //endregion

    private static ArrayList<Member> memberArrayList = new ArrayList<>();
    private static ObservableList<Member> observableMembers = FXCollections.observableArrayList(memberArrayList);

    //region UI Methods
    public void btnLoadClicked(){
        try {
            readMember();
            btnLoad.setDisable(true);
            //region Enable Components
            btnSave.setDisable(false);
            btnMemberAdd.setDisable(false);
            btnMemberDelete.setDisable(false);
            btnEditImage.setDisable(false);

            txtName.setDisable(false);
            txtStudentNumber.setDisable(false);
            txtSurname.setDisable(false);
            txtID.setDisable(false);
            txtSurname.setDisable(false);
            txtName.setDisable(false);
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

            cbGender.setDisable(false);
            cbBLevel.setDisable(false);
            cbLLevel.setDisable(false);

            chbStudent.setDisable(false);
            chbPaid.setDisable(false);
            chbCompetitive.setDisable(false);
            //endregion

            firstMember();
        } catch (Exception e) {
            System.out.println("XML reading issue");
        }
    }

    public void btnSaveClicked(){
        try {
            writeStudents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnSearchClicked(){
        Search(txtSearch.getText());
    }

    public void btnMemberAddClicked(){
        newMember();
    }

    public void btnMemberDeleteClicked(){
        removeMember();
    }

    public void btnEditImageClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Member mem = lvMembers.getSelectionModel().getSelectedItem();
            mem.setImage(new Image(file.toURI().toString()));
        }
    }

    //endregion

    private void addBindings(){
        lvMembers.setItems(observableMembers);
        lvMembers.getSelectionModel().selectFirst();

        lvMembers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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

        lblMemberCount.textProperty().bind(lvMembers.getSelectionModel().selectedIndexProperty().add(1).asString().concat(" of ").concat(observableMembers.size()));
    }

    private void readMember() throws Exception {
        File IS = new File("MemberRecords.xml");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(IS);

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        String expr = "//Member";
        NodeList members = (NodeList) xPath.compile(expr).evaluate(doc, XPathConstants.NODESET);
        for (int x = 0; x < members.getLength(); x++) {
            Element member = (Element) xPath.compile("MemberInfo").evaluate(members.item(x), XPathConstants.NODE);

            String id = member.getChildNodes().item(1).getTextContent();
            String name = member.getChildNodes().item(3).getTextContent();
            String surname = member.getChildNodes().item(5).getTextContent();
            String gender = member.getChildNodes().item(7).getTextContent();
            Boolean student = Boolean.parseBoolean(member.getChildNodes().item(9).getTextContent());
            String studentNumber = member.getChildNodes().item(11).getTextContent();
            String email = member.getChildNodes().item(13).getTextContent();
            String phone = member.getChildNodes().item(15).getTextContent();
            String street = member.getChildNodes().item(17).getTextContent();
            String suburb = member.getChildNodes().item(19).getTextContent();
            String bLevel = member.getChildNodes().item(21).getTextContent();
            String lLevel = member.getChildNodes().item(23).getTextContent();
            Boolean paid = Boolean.parseBoolean(member.getChildNodes().item(25).getTextContent());
            Boolean competitive = Boolean.parseBoolean(member.getChildNodes().item(27).getTextContent());
            String diet = member.getChildNodes().item(29).getTextContent();
            String medical = member.getChildNodes().item(31).getTextContent();
            String disabilities = member.getChildNodes().item(33).getTextContent();
            Image image = new Image(this.getClass().getResourceAsStream("/resources/" + name + " " + surname + ".jpg"));

            Member newMember = new Member(id, name, surname, gender, student, studentNumber, email, phone, street, suburb, bLevel, lLevel, paid, competitive, diet, medical, disabilities, image);

            observableMembers.add(newMember);
        }
        observableMembers.sort(new MemberComparitor());
        addBindings();
    }

    //region Writing data to a XML file
    private static void writeStudents() throws Exception {
        Document outputDoc = createDoc();
        Element rootNode = outputDoc.createElement("MemberRecords");
        Element member;
        for (Member mem : observableMembers) {
            member = outputDoc.createElement("Member");
            member.appendChild(memberInfo(outputDoc, mem));
            rootNode.appendChild(member);

            String imageName = mem.getName() + " " + mem.getSurname() + ".jpg";
            ImageIO.write(SwingFXUtils.fromFXImage(mem.getImage(), null), "jpg", new File("./src//resources//" + imageName));
        }
        outputDoc.appendChild(rootNode);
        saveDoc(outputDoc, "MemberRecords.xml");
        System.out.println("Saved successfully");
    }

    private static Element memberInfo(Document outputDoc, Member member) {
        Element memberInfo = outputDoc.createElement("MemberInfo");

        //region Get data

        //region ID
        Element ID = outputDoc.createElement("ID");
        Text textID = outputDoc.createTextNode(member.getId());
        ID.appendChild(textID);
        //endregion

        //region Name
        Element name = outputDoc.createElement("Name");
        Text textName = outputDoc.createTextNode(member.getName());
        name.appendChild(textName);
        //endregion

        //region Surname
        Element surname = outputDoc.createElement("Surname");
        Text textSurname = outputDoc.createTextNode(member.getSurname());
        surname.appendChild(textSurname);
        //endregion

        //region Gender

        Element Gender = outputDoc.createElement("Gender");
        Text textGender = outputDoc.createTextNode(member.getGender());
        Gender.appendChild(textGender);
        //endregion

        //region Student
        Element student = outputDoc.createElement("Student");
        Text textStudent = outputDoc.createTextNode(member.isStudent().toString());
        student.appendChild(textStudent);
        //endregion

        //region StudentNum
        Element studentNum = outputDoc.createElement("StudentNum");
        Text textStudentNum = outputDoc.createTextNode(member.getStudentNumber());
        studentNum.appendChild(textStudentNum);
        //endregion

        //region Email
        Element email = outputDoc.createElement("Email");
        Text textEmail = outputDoc.createTextNode(member.getEmail());
        email.appendChild(textEmail);
        //endregion

        //region Phone
        Element phone = outputDoc.createElement("Phone");
        Text textPhone = outputDoc.createTextNode(member.getPhone());
        phone.appendChild(textPhone);
        //endregion

        //region Street
        Element street = outputDoc.createElement("Street");
        Text textStreet = outputDoc.createTextNode(member.getStreet());
        street.appendChild(textStreet);
        //endregion

        //region Suburb
        Element suburb = outputDoc.createElement("Suburb");
        Text textSuburb = outputDoc.createTextNode(member.getSuburb());
        suburb.appendChild(textSuburb);
        //endregion

        //region bLevel
        Element bLevel = outputDoc.createElement("bLevel");
        Text textBLevel = outputDoc.createTextNode(member.getbLevel());
        bLevel.appendChild(textBLevel);
        //endregion

        //region lLevel
        Element lLevel = outputDoc.createElement("lLevel");
        Text textLLevel = outputDoc.createTextNode(member.getlLevel());
        lLevel.appendChild(textLLevel);
        //endregion

        //region Paid
        Element Paid = outputDoc.createElement("Paid");
        Text textPaid = outputDoc.createTextNode(member.isPaid().toString());
        Paid.appendChild(textPaid);
        //endregion

        //region Competitive
        Element Competitive = outputDoc.createElement("Competitive");
        Text textCompetitive = outputDoc.createTextNode(member.isCompetitive().toString());
        Competitive.appendChild(textCompetitive);
        //endregion

        //region Diet
        Element Diet = outputDoc.createElement("Diet");
        Text textDiet = outputDoc.createTextNode(member.getDietary());
        Diet.appendChild(textDiet);
        //endregion

        //region Medical
        Element Medical = outputDoc.createElement("Medical");
        Text textMedical = outputDoc.createTextNode(member.getMedical());
        Medical.appendChild(textMedical);
        //endregion

        //region Disabilities
        Element Disabilities = outputDoc.createElement("Disabilities");
        Text textDisabilities = outputDoc.createTextNode(member.getDisabilities());
        Disabilities.appendChild(textDisabilities);
        //endregion

        //endregion

        //region Save data
        memberInfo.appendChild(ID);
        memberInfo.appendChild(name);
        memberInfo.appendChild(surname);
        memberInfo.appendChild(Gender);
        memberInfo.appendChild(student);
        memberInfo.appendChild(studentNum);
        memberInfo.appendChild(email);
        memberInfo.appendChild(phone);
        memberInfo.appendChild(street);
        memberInfo.appendChild(suburb);
        memberInfo.appendChild(bLevel);
        memberInfo.appendChild(lLevel);
        memberInfo.appendChild(Paid);
        memberInfo.appendChild(Competitive);
        memberInfo.appendChild(Diet);
        memberInfo.appendChild(Medical);
        memberInfo.appendChild(Disabilities);
        //endregion

        return memberInfo;
    }

    private static Document createDoc() throws Exception {
        // create DocumentBuilder to parse XML document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // create empty document
        return builder.newDocument();
    }

    private static void saveDoc(Document doc, String filename) throws Exception {
        // obtain serializer
        DOMImplementation impl = doc.getImplementation();
        DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
        LSSerializer ser = implLS.createLSSerializer();
        ser.getDomConfig().setParameter("format-pretty-print", true);

        // create file to save too
        FileOutputStream fout = new FileOutputStream(filename);

        // set encoding options
        LSOutput lsOutput = implLS.createLSOutput();
        lsOutput.setEncoding("UTF-8");

        // tell to save xml output to file
        lsOutput.setByteStream(fout);

        // FINALLY write output
        ser.write(doc, lsOutput);

        // close file
        fout.close();
    }
    //endregion

    //region Functions for Students
    private void firstMember() {
        System.out.println("|<\tFirst Member.");

        lvMembers.getSelectionModel().selectFirst();
        lvMembers.scrollTo(0);
    }

    private void lastMember() {
        System.out.println(">|\tLast.");

        if (memberArrayList.size() > 0)
        {
            lvMembers.getSelectionModel().selectLast();
            lvMembers.scrollTo(observableMembers.size()-1);
        }
    }

    private void newMember() {
        System.out.println("[]\tNew Member.");

        AddMember addMember = new AddMember();
        Member newMember = null;
        /*do {
            newMember = addMember.getNewMember();
        }while (newMember == null);*/

        observableMembers.add(newMember);
        observableMembers.sort(new MemberComparitor());

        lvMembers.getSelectionModel().select(newMember);
        lvMembers.scrollTo(newMember);
    }

    private void removeMember() {
        System.out.println("[]\tRemove Student.");

        Member mem = lvMembers.getSelectionModel().getSelectedItem();
        if(mem != null) {
//            String imageName = mem.getName() + " " + mem.getSurname() + ".jpg";
//            File image = new File("./src//resources//" + imageName);
//            image.delete();

            observableMembers.remove(mem);
        }
        firstMember();
    }

    private void Search(String name){
        for (Member mem : observableMembers)
            if (mem.toString().toUpperCase().contains(name.toUpperCase())) {
                lvMembers.getSelectionModel().select(mem);
                lvMembers.scrollTo(mem);
                return;
            }

        System.out.println("Not Found");
    }
    //endregion

}
