import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ListView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MembersList
{
    private ArrayList<Member> newMembers = new ArrayList<>();
    private ArrayList<Member> removedMembers = new ArrayList<>();
    private ArrayList<Member> memberArrayList = new ArrayList<>();
    private ObservableList<Member> observableMembers = FXCollections.observableArrayList(memberArrayList);
    private ListView<Member> lvMembers;

    public MembersList(ListView lvMembers) {
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

    public ArrayList<Member> getNewMembers(){
        return newMembers;
    }

    public void setNewMembers(ArrayList<Member> newMembers){
        this.newMembers = newMembers;
    }

    public ArrayList<Member> getChanges(){
        ArrayList<Member> changedMembers = new ArrayList<>();

        for (Member x : observableMembers)
            if (x.isChanged())
                changedMembers.add(x);

        return changedMembers;
    }

    public ArrayList<Member> getRemovedMembers(){
        return removedMembers;
    }

    public void setRemovedMembers(ArrayList<Member> removedMembers){
        this.removedMembers = removedMembers;
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
        newMembers.add(newMember);
        observableMembers.add(newMember);
        observableMembers.sort(new MemberComparitor());

        lvMembers.getSelectionModel().select(newMember);
        lvMembers.scrollTo(newMember);
    }

    public void remove() {
        System.out.println("[]\tRemove Student.");

        Member member = lvMembers.getSelectionModel().getSelectedItem();
        if(member != null) {
            if(newMembers.contains(member))
                newMembers.remove(member);
            else
                removedMembers.add(member);

            observableMembers.remove(member);
        }
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