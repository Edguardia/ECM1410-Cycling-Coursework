package cycling;

import java.time.LocalTime;

public class Race {
    
    private int raceId;
    private String raceName;
    private String raceDescription;
    private int[] riderIds;
    private Stage[] stages;

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
