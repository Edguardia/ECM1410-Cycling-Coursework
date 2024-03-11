package cycling;

import java.time.LocalTime;

public class Stage {

    private int stageId;
    private String stageName;
    private String description;
    private Double length;
    private String state;
    private StageType type;
    private LocalTime startTime;
    private Checkpoint[] checkpoints;

    public Stage(){

    }

    public int getStageID(){
        return stageId;
    }
    public String getStageName(){
        return stageName;
    }
    public String getDescription(){
        return description;
    }
    public Double getLength(){
        return length;
    }
    public String getState(){
        return state;
    }
    public StageType getStageType(){
        return type;
    }
    public LocalTime getStartTime(){
        return startTime;
    }
    public Checkpoint[] getCheckpoints(){
        return checkpoints;
    }
    public void setStageID(int stageId){
        this.stageId = stageId;
    }
    public void setStageName(String stageName){
        this.stageName = stageName;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setLength(Double length){
        this.length = length;
    }
    public void setState(String state){
        this.state = state;
    }
    public void setType(StageType type){
        this.type = type;
    }
    public void setstartTime(LocalTime startTime){
        this.startTime = startTime;
    }
    public void setStageID(Checkpoint[] checkpoints){
        this.checkpoints = checkpoints;
    }
}
