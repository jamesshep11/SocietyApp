package sample;

import java.util.Comparator;

public class MemberComparitor implements Comparator<Member>
{
    @Override
    public int compare(Member mem1, Member mem2)
    {
        return mem1.toString().compareTo(mem2.toString());
    }
}
