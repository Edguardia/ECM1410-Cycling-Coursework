package cycling;

import java.time.LocalTime;

public class Rider {
    
    private int riderId;
    private String name;
    private int yearOfBirth;
    private int teamId;
    private LocalTime[] checkpointTimes;

    public int getRiderID(){
        return riderId;
    }
    public String getName(){
        return name;
    }
    public int getYearOfBirth(){
        return yearOfBirth;
    }
    public int getTeamID(){
        return teamId;
    }
    public LocalTime[] getStageTimes(){
        return checkpointTimes;
    }
    public void setRiderID(int riderId){
        this.riderId = riderId;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setYearOfBirth(int yearOfBirth){
        this.yearOfBirth = yearOfBirth;
    }
    public void setTeamID(int teamId){
        this.teamId = teamId;
    }
    public void setCheckpointTimes(LocalTime[] checkpointTimes){
        this.checkpointTimes = checkpointTimes;
    }
}
