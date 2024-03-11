package cycling;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Team {
    
    private int teamId;
    private String teamName;
    private String teamDescription;
    private ArrayList<Integer> riderIds;

    private AtomicInteger currentId = new AtomicInteger(0);

    public Team(String teamName, String teamDescription){
        this.teamId = currentId.getAndIncrement();
        this.teamName = teamName;
        this.teamDescription = teamDescription;

        }

    public int getTeamID(){
        return teamId;
    }
    public String getTeamName(){
        return teamName;
    }
    public String getTeamDescription(){
        return teamDescription;
    }
    public int[] getRiders(){
        return riderIds.stream().mapToInt(i -> i).toArray();
    }
    public void setTeamID(int teamId){
        this.teamId = teamId;
    }
    public void setTeamName(String teamName){
        this.teamName = teamName;
    }
    public void setTeamDescription(String teamDescription){
        this.teamDescription = teamDescription;
    }


    public void addRider(int riderId){
        riderIds.add(riderId);
    }
    public void deleteRider(int riderId){
        riderIds.remove(riderId);
    }
}
