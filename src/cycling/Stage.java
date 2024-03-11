package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Stage {

    private int stageId;
    private String stageName;
    private String description;
    private Double length;
    private String state;
    private StageType type;
    private LocalDateTime startTime;
    private Checkpoint[] checkpoints;

    static private AtomicInteger currentId = new AtomicInteger(0);

    public Stage(String stageName, String description, double length, LocalDateTime startTime, StageType type){
        this.stageName = stageName;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type;
        this.stageId = currentId.getAndIncrement();
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
    public LocalDateTime getStartTime(){
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
    public void setstartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }
    public void setStageID(Checkpoint[] checkpoints){
        this.checkpoints = checkpoints;
    }
    static public void atomicReset(){
        currentId.set(0);
    }
}
