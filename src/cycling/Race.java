package cycling;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Race {
    
    private int raceId;
    private String raceName;
    private String raceDescription;
    private int[] riderIds;
    private Stage[] stages;

    private AtomicInteger currentId = new AtomicInteger(0);

    public Race(String raceName, String raceDescription){
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
        return riderIds;
    }
    public Stage[] getStages(){
        return stages;
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
    public void setRiderIDs(int[] riderIds){
        this.riderIds = riderIds;
    }
    public void setStages(Stage[] stages){
        this.stages = stages;
    }
}
