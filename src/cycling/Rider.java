package cycling;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Rider{

    private int riderId;
    private String name;
    private int yearOfBirth;
    private int teamId;
    private HashMap<Integer, Integer> stageResults;
    private HashMap<Integer, Integer> CheckpointResults;
    private HashMap<Integer, LocalTime[]> checkpointTimes;

    static private AtomicInteger currentId = new AtomicInteger(0);

    public Rider(int teamId, String name, int yearOfBirth){
        this.riderId = 0;
        this.name = new String();
        this.yearOfBirth = 0;
        this.teamId = 0;
        this.stageResults = new HashMap<>();
        this.checkpointTimes = new HashMap<>();
        this.teamId = teamId;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.riderId = currentId.getAndIncrement();
    }

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
    public int getStageResults(int stageId){
        return stageResults.get(stageId);
    }
    public LocalTime[] getCheckpointTimes(int stageId){
        return checkpointTimes.get(stageId);
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

    public void addStageResults(int stageId, int result){
        stageResults.put(stageId, result);
    }
    public void addCheckpointTimes(int stageId, LocalTime[] checkpointTimes){
        this.checkpointTimes.put(stageId, checkpointTimes);
    }
    public void deleteStageResults(int stageId){
        stageResults.remove(stageId);
    }
    public void deleteCheckpointTimes(int stageId){
        checkpointTimes.remove(stageId);
    }

    public LocalTime calculateRidersTotalElapsedTime(int stageId){
        LocalTime totalTime = LocalTime.of(0,0,0);
        for (LocalTime checkpointTime : getCheckpointTimes(stageId)){
            totalTime.plusHours(checkpointTime.getHour()).plusMinutes(checkpointTime.getMinute()).plusSeconds(checkpointTime.getSecond());
        }
        return totalTime;
    }

    static public void atomicReset(){
        currentId.set(0);
    }
}
