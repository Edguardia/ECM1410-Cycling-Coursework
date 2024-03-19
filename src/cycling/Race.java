package cycling;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Race {
    
    private int raceId;
    private String raceName;
    private String raceDescription;
    private ArrayList<Integer> riderIds;
    private ArrayList<Integer> stageIds;

    static private AtomicInteger currentId = new AtomicInteger(0);

    public Race(String raceName, String raceDescription){
        this.raceName = new String();
        this.raceDescription = new String();
        this.raceName = raceName;
        this.raceDescription = raceDescription;
        this.raceId = currentId.getAndIncrement();
    }

    public int getRaceID(){
        return raceId;
    }
    public String getRaceName(){
        return raceName;
    }
    public String getRaceDescription(){
        return raceDescription;
    }
    public int[] getRiderIDs(){
        return riderIds.stream().mapToInt(i -> i).toArray();
    }
    public int[] getStageIDs(){
        return stageIds.stream().mapToInt(i -> i).toArray();
    }
    public void setRaceID(int raceId){
        this.raceId = raceId;
    }
    public void setRaceName(String raceName){
        this.raceName = raceName;
    }
    public void setRaceDescription(String raceDescription){
        this.raceDescription = raceDescription;
    }

    public void addStage(int stageId){
        stageIds.add(stageId);
    }
    public void deleteStage(int stageId){
        stageIds.remove(stageId);
    }
    public void addRider(int riderId){
        riderIds.add(riderId);
    }
    public void deleteRider(int riderId){
        riderIds.remove(riderId);
    }
    static public void atomicReset(){
        currentId.set(0);
    }
}
