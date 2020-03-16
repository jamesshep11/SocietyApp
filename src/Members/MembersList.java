package Members;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MembersList
{
    private ArrayList<Member> memberArrayList = new ArrayList<>();
    private ObservableList<Member> observableMembers = FXCollections.observableArrayList(memberArrayList);
    private ListView<Member> lvMembers;

    public MembersList(ListView<Member> lvMembers) {
        this.lvMembers = lvMembers;
    }

    //region Getters and Setters

    public ObservableList<Member> getObservableMembers()
    {
        return observableMembers;
    }

    public void setObservableMembers(ObservableList<Member> observableMembers) {
        this.observableMembers = observableMembers;
    }

    public ListView<Member> getLvMembers()
    {
        return lvMembers;
    }

    public void setLvMembers(ListView<Member> lvMembers)
    {
        this.lvMembers = lvMembers;
    }

    public ArrayList<Member> getChanges(){
        ArrayList<Member> changedMembers = new ArrayList<>();

        for (Member x : observableMembers)
            if (x.isChanged())
                changedMembers.add(x);

        return changedMembers;
    }

    public int size(){
        return observableMembers.size();
    }

    //endregion

    public void goToFirst() {
        System.out.println("|<\tFirst Member.");

        lvMembers.getSelectionModel().selectFirst();
        lvMembers.scrollTo(0);
    }

    public void goToLast() {
        System.out.println(">|\tLast.");

        if (memberArrayList.size() > 0)
        {
            lvMembers.getSelectionModel().selectLast();
            lvMembers.scrollTo(observableMembers.size()-1);
        }
    }

    public void add(Member newMember) {
        observableMembers.add(newMember);
        observableMembers.sort(new MemberComparator());

        lvMembers.getSelectionModel().select(newMember);
        lvMembers.scrollTo(newMember);
    }

    public void remove(Member member) {
        System.out.println("[]\tRemove Student.");

        observableMembers.remove(member);

        goToFirst();
    }

    public void find(String name){
        for (Member mem : observableMembers)
            if (mem.toString().toUpperCase().contains(name.toUpperCase())) {
                lvMembers.getSelectionModel().select(mem);
                lvMembers.scrollTo(mem);
                return;
            }

        System.out.println("Not Found");
    }

    public void filter(String id, String name, String surname, String gender, Boolean student, String studentNumber, String bLevel, String lLevel, Boolean paid, Boolean competitive, String email, String phone, String street, String suburb, String diet, String medical, String disabilities){

        if(id == null && name == null && surname == null && gender == null && studentNumber == null && bLevel == null && lLevel == null
                && email == null && phone == null && street == null && suburb == null && diet == null && medical == null && disabilities == null
                && student == null && paid == null && competitive == null)
        {
            lvMembers.setItems(observableMembers);
            return;
        }

        List<Member> filteredList = observableMembers;
        if (id != null)
            filteredList = filteredList.stream()
                .filter(member -> member.getId().toUpperCase().contains(id.toUpperCase()))
                .collect(Collectors.toList());
        if (name != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.getName().toUpperCase().contains(name.toUpperCase()))
                    .collect(Collectors.toList());
        if (surname != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.getSurname().toUpperCase().contains(surname.toUpperCase()))
                    .collect(Collectors.toList());
        if (gender != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.getGender().toUpperCase().equals(gender.toUpperCase()))
                    .collect(Collectors.toList());
        if (student != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.isStudent() == student)
                    .collect(Collectors.toList());
        if (studentNumber != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.getStudentNumber().toUpperCase().contains(studentNumber.toUpperCase()))
                    .collect(Collectors.toList());
        if (bLevel != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.getbLevel().toUpperCase().equals(bLevel.toUpperCase()))
                    .collect(Collectors.toList());
        if (lLevel != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.getlLevel().toUpperCase().equals(lLevel.toUpperCase()))
                    .collect(Collectors.toList());
        if (paid != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.isPaid() == paid)
                    .collect(Collectors.toList());
        if (competitive != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.isCompetitive() == competitive)
                    .collect(Collectors.toList());
        if (email != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.getEmail().toUpperCase().contains(email.toUpperCase()))
                    .collect(Collectors.toList());
        if (phone != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.getPhone().toUpperCase().contains(phone.toUpperCase()))
                    .collect(Collectors.toList());
        if (street != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.getStreet().toUpperCase().contains(street.toUpperCase()))
                    .collect(Collectors.toList());
        if (suburb != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.getSuburb().toUpperCase().contains(suburb.toUpperCase()))
                    .collect(Collectors.toList());
        if (diet != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.getDietary().toUpperCase().contains(diet.toUpperCase()))
                    .collect(Collectors.toList());
        if (medical != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.getMedical().toUpperCase().contains(medical.toUpperCase()))
                    .collect(Collectors.toList());
        if (disabilities != null)
            filteredList = filteredList.stream()
                    .filter(member -> member.getDisabilities().toUpperCase().contains(disabilities.toUpperCase()))
                    .collect(Collectors.toList());

        lvMembers.setItems(FXCollections.observableList(filteredList));
    }
}