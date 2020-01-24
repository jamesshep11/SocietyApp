import Members.Member;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class Attendance
{
    //region UI components
    @FXML Button btnBack, btnMembers, btnEvents, btnOverview, btnGraph, btnMarkAttendance, btnSearch;
    @FXML TextField txtSearch;
    @FXML Label lblCount, lblPercentage;
    @FXML ListView lvSelect, lvFiltered;
    @FXML AnchorPane apDetails, apOverview;
    //endregion

    private static ArrayList<Member> membersList = new ArrayList<>();
    private static ObservableList<Member> observableMembers = FXCollections.observableArrayList(membersList);
    private static ArrayList<String> eventsList = new ArrayList<>();
    private static ObservableList<String> observableEvents = FXCollections.observableArrayList(eventsList);
    private static DatabaseConnection db;
    private static Stage stage;
    private String state;

    public Attendance() {
    }

    public void load(DatabaseConnection db) throws IOException, SQLException {
        this.db = db;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Attendance.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.setTitle("Attendance");
        stage.setScene(scene);
        stage.show();

        state = "Members";
        loadLists();
    }

    //region UI Methods

    public void btnBackClicked() throws IOException {
        stage.close();
        new Menu().load(db);
    }

    public void btnMembersClicked(){
        observableMembers = FXCollections.observableArrayList(membersList);
        lvSelect.setItems(observableMembers);
        lvSelect.getSelectionModel().selectedItemProperty().removeListener((observable, oldValue, newValue) -> {
            try {
                filterMembers((String)newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        lvSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterEvents((Member)newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        lvSelect.getSelectionModel().selectFirst();
        lvFiltered.setItems(observableEvents);

        state = "Members";
    }

    public void btnEventsClicked(){
        observableEvents = FXCollections.observableArrayList(eventsList);
        lvSelect.setItems(observableEvents);
        lvSelect.getSelectionModel().selectedItemProperty().removeListener((observable, oldValue, newValue) -> {
            try {
                filterEvents((Member)newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        lvSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterMembers((String)newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        lvSelect.getSelectionModel().selectFirst();
        lvFiltered.setItems(observableMembers);

        state = "Events";
    }

    public void btnMarkAttendanceClicked() throws IOException, SQLException {
        new Register().load(db);
    }

    public void btnSearchClicked(){
        String name = txtSearch.getText();

        switch(state) {
            case "Members":
                    for (Member member : observableMembers)
                        if (member.toString().toUpperCase().contains(name.toUpperCase())) {
                            lvSelect.getSelectionModel().select(member);
                            lvSelect.scrollTo(member);
                            return;
                        }
                break;
            case "Events":
                for (String event : observableEvents)
                    if (event.toUpperCase().contains(name.toUpperCase())) {
                        lvSelect.getSelectionModel().select(event);
                        lvSelect.scrollTo(event);
                        return;
                    }
                break;
        }

        System.out.println("Not Found");
    }

    //endregion

    private void loadLists() throws SQLException {
        //region Members
        String sql = "SELECT ID, Name, Surname FROM Members";
        ResultSet rs = db.runSQL(sql);

        Member member;
        while (rs.next()) {
            member = new Member(rs.getString("ID"), rs.getString("Name"), rs.getString("Surname"));
            membersList.add(member);
        }

        membersList.sort(Comparator.comparing(Member::getName));
        //endregion

        //region Events
        sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Register'";
        rs = db.runSQL(sql);

        while (rs.next()) {
            if (!rs.getString("COLUMN_NAME").equals("MemberID"))
                eventsList.add(rs.getString("COLUMN_NAME"));
        }

        eventsList.sort(Comparator.comparing(String::toString));
        //endregion
    }

    private void filterEvents(Member member) throws SQLException {
        String sql = String.format("SELECT * FROM Register WHERE MemberID = '%s'", member.getId());
        ResultSet rs = db.runSQL(sql);

        if(!rs.next()) return;

        ArrayList<String> filteredEvents = new ArrayList<>();
        for (String event : eventsList)
            if (rs.getBoolean(event))
                filteredEvents.add(event);

        observableEvents = FXCollections.observableArrayList(filteredEvents);
    }

    private void filterMembers(String event) throws SQLException {
        String sql = String.format("SELECT ID, Name, Surname FROM Members INNER JOIN Register ON ID = MemberID WHERE %s = true", event);
        ResultSet rs = db.runSQL(sql);

        Member member;
        ArrayList<Member> filteredMembers = new ArrayList<>();
        while(rs.next()){
            member = new Member(rs.getString("ID"), rs.getString("Name"), rs.getString("Surname"));
            filteredMembers.add(member);
        }

        observableMembers = FXCollections.observableArrayList(filteredMembers);
    }

    private class Member
    {
        private String id, name, surname;

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

        @Override
        public String toString() {
            return name + " " + surname;
        }
    }
}
