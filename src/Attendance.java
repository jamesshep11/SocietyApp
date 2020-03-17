import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    @FXML ListView lvMembers, lvEvents;
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
        lvMembers.setItems(observableMembers);
        lvEvents.setItems(FXCollections.observableArrayList(new ArrayList()));

        lvMembers.setLayoutX(23);
        lvMembers.setDisable(false);
        lvEvents.setLayoutX(265);
        lvEvents.setDisable(true);

        lblCount.setVisible(false);
        lblPercentage.setVisible(false);

        state = "Members";
    }

    public void btnEventsClicked(){
        lvEvents.setItems(observableEvents);
        lvMembers.setItems(FXCollections.observableArrayList(new ArrayList()));

        lvEvents.setLayoutX(23);
        lvEvents.setDisable(false);
        lvMembers.setLayoutX(265);
        lvMembers.setDisable(true);

        lblCount.setVisible(false);
        lblPercentage.setVisible(false);

        state = "Events";
    }

    public void btnMarkAttendanceClicked() throws IOException, SQLException {
        new Register().load(db);
        stage.close();
    }

    public void btnSearchClicked(){
        String name = txtSearch.getText();

        switch(state) {
            case "Members":
                    for (Member member : observableMembers)
                        if (member.toString().toUpperCase().contains(name.toUpperCase())) {
                            lvMembers.getSelectionModel().select(member);
                            lvMembers.scrollTo(member);
                            return;
                        }
                break;
            case "Events":
                for (String event : observableEvents)
                    if (event.toUpperCase().contains(name.toUpperCase())) {
                        lvEvents.getSelectionModel().select(event);
                        lvEvents.scrollTo(event);
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
            if (!observableMembers.contains(member))
                observableMembers.add(member);
        }
        observableMembers.sort(Comparator.comparing(Member::getName));

        lvMembers = (ListView) stage.getScene().lookup("#lvMembers");
        lvMembers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue != null)
                    filterEvents((Member)newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        //endregion

        //region Events
        sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Register'";
        rs = db.runSQL(sql);

        while (rs.next()) {
            if (!rs.getString("COLUMN_NAME").equals("MemberID") && !observableEvents.contains(rs.getString("COLUMN_NAME")))
                observableEvents.add(rs.getString("COLUMN_NAME"));
        }
        observableEvents.sort(Comparator.comparing(String::toString));

        lvEvents = (ListView) stage.getScene().lookup("#lvEvents");
        lvEvents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue != null)
                    filterMembers((String)newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        //endregion
    }

    private void filterEvents(Member member) throws SQLException {
        String sql = String.format("SELECT * FROM Register WHERE MemberID = '%s'", member.getId());
        ResultSet rs = db.runSQL(sql);

        if(!rs.next()) return;

        ArrayList<String> filteredEvents = new ArrayList<>();
        for (String event : observableEvents)
            if (rs.getBoolean(event))
                filteredEvents.add(event);

        lvEvents.setItems(FXCollections.observableArrayList(filteredEvents));

        lblCount = (Label) stage.getScene().lookup("#lblCount");
        lblCount.setText(String.format("%s of %s", filteredEvents.size(), observableEvents.size()));
        lblCount.setVisible(true);
        lblPercentage = (Label) stage.getScene().lookup("#lblPercentage");
        lblPercentage.setText(filteredEvents.size()/observableEvents.size()*100 + "% Attendance");
        lblPercentage.setVisible(true);
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

        lvMembers.setItems(FXCollections.observableArrayList(filteredMembers));

        lblCount = (Label) stage.getScene().lookup("#lblCount");
        lblCount.setText("Total: " + filteredMembers.size());
        lblCount.setVisible(true);
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
        public boolean equals(Object obj)
        {
            if (obj == null)
                return false;

            return ((Member)obj).id.equals(this.id);
        }

        @Override
        public String toString() {
            return name + " " + surname;
        }
    }
}
