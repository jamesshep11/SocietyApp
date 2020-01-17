import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;

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
}