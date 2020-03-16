import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Register
{
    @FXML ComboBox<String> chbEvents;
    @FXML TextField txtSearch;
    @FXML Button btnAttendance, btnUnAttendance, btnAddEvent;
    @FXML ListView<Member> lvMembers;

    private static ArrayList<Member> membersList = new ArrayList<>();
    private static ObservableList<Member> observableMembers = FXCollections.observableArrayList(membersList);
    private static DatabaseConnection db;
    private static Stage stage;

    public Register() {
    }

    public void load(DatabaseConnection db) throws IOException, SQLException {
        this.db = db;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();

        loadChoiceBoxes();
    }

    //region UI Methods

    public void btnBackClicked() throws IOException {
        stage.close();
        new Menu().load(db);
    }

    public void btnAddEventClicked() throws SQLException {
        String eventName = JOptionPane.showInputDialog(null, "Event name (no spaces): ", "New Event", JOptionPane.QUESTION_MESSAGE);
        if (eventName != null)
            newEvent(eventName);
    }

    public void btnMarkAttendanceClicked(){
        ObservableList<Member> selectedMembers = lvMembers.getItems().filtered(Member::isSelected);

        String eventName = chbEvents.getSelectionModel().getSelectedItem();
        for(Member member : selectedMembers) {
            String memberName = member.toString();

            if (eventName != null) {
                String memberID = find(memberName).getId();
                String sql = String.format("UPDATE Register SET %s = true WHERE MemberID = '%s'", eventName, memberID);
                db.runSQL(sql);
            }
        }

        System.out.println("Attendance marked");
    }

    public void btnUnMarkAttendanceClicked(){
        ObservableList<Member> selectedMembers = lvMembers.getItems().filtered(Member::isSelected);

        String eventName = chbEvents.getSelectionModel().getSelectedItem();
        for(Member member : selectedMembers) {
            String memberName = member.toString();

            if (eventName != null) {
                String memberID = find(memberName).getId();
                String sql = String.format("UPDATE Register SET %s = false WHERE MemberID = '%s'", eventName, memberID);
                db.runSQL(sql);
            }
        }

        System.out.println("Attendance unmarked.");
    }

    public void btnSearchClicked(){
        String name = txtSearch.getText();

        for (Member member : observableMembers)
            if (member.toString().toUpperCase().contains(name.toUpperCase())) {
                lvMembers.getSelectionModel().select(member);
                lvMembers.scrollTo(member);
                return;
            }

        System.out.println("Not Found");
    }

    //endregion

    private void newEvent(String eventName) throws SQLException {
        if(eventName.contains(" ")) {
            JOptionPane.showMessageDialog(null, "The event name cannot contain a space.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = String.format("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Register' AND COLUMN_NAME = '%s'", eventName);
        ResultSet rs = db.runSQL(sql);
        if(rs.next()){
            JOptionPane.showMessageDialog(null, "That event already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sql = "ALTER TABLE Register ADD " + eventName + " boolean(1) DEFAULT 0";
        db.runSQL(sql);
        chbEvents.getItems().add(eventName);
    }

    private void loadChoiceBoxes() throws SQLException {
        //region Members
        String sql = "SELECT ID, Name, Surname FROM Members";
        ResultSet rs = db.runSQL(sql);


        Member member;
        while (rs.next()) {
            member = new Member(rs.getString("ID"), rs.getString("Name"), rs.getString("Surname"));
            if (!observableMembers.contains(member))
                observableMembers.add(member);
        }

        observableMembers.sort(Comparator.comparing(Member::getName));
        ListView<Member> lvMembers = (ListView<Member>) stage.getScene().lookup("#lvMembers");
        lvMembers.setItems(observableMembers);
        lvMembers.setCellFactory(CheckBoxListCell.forListView(Member::selectedProperty));
        //endregion

        //region Events
        sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Register'";
        rs = db.runSQL(sql);

        ArrayList<String> events = new ArrayList<>();
        while (rs.next())
            if (!rs.getString("COLUMN_NAME").equals("MemberID"))
                events.add(rs.getString("COLUMN_NAME"));

        ObservableList<String> observableList = FXCollections.observableList(events).sorted();
        chbEvents = (ComboBox) stage.getScene().lookup("#chbEvents");
        chbEvents.setItems(observableList);
        //endregion
    }

    private class Member
    {
        private String id, name, surname;
        private BooleanProperty selected = new SimpleBooleanProperty();

        public Member(String id, String name, String surname) {
            this.name = name;
            this.surname = surname;
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public String getName()
        {
            return name;
        }

        public boolean isSelected()
        {
            return selected.get();
        }

        public BooleanProperty selectedProperty()
        {
            return selected;
        }

        public void setSelected(boolean selected)
        {
            this.selected.set(selected);
        }

        @Override
        public boolean equals(Object obj)
        {
            return ((Member)obj).id.equals(this.id);
        }

        @Override
        public String toString() {
            return name + " " + surname;
        }
    }

    private Member find(String name){
        for (Member member : observableMembers)
            if (member.toString().equals(name))
                return member;

        return null;
    }
}
