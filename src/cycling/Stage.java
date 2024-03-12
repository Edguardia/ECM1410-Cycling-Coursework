package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Stage {

    private int stageId;
    private String stageName;
    private String description;
    private Double length;
    private String state;
    private StageType type;
    private LocalDateTime startTime;
    private ArrayList<Integer> checkpointIDs;
    /*The checkpoint times need to be in Stage class because
    * you are getting each time that is elapsed when a rider
    * completes each checkpoint.
    *
    * The array of checkpoint times should ideally match the number
    * of checkpoints within a stage. If you want to store the elapsed checkpoint 
    * time inside of the respected checkpoint, that is fine. However, I suggest
    * storing the array here 
    */

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
    public int[] getCheckpointIDs(){ //Changed to checkpointIDs instead of the 'Checkpoints' array
        return checkpointIDs.stream().mapToInt(i -> i).toArray();
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
    //Removed setting the checkpoint IDs

    public void addCheckpoint(int checkpointID){ //Check if you still want to keep checkpoints instead of checkpoint IDs
        checkpointIDs.add(checkpointID);
    }
    public void deleteCheckpoint(int checkpointID){
        checkpointIDs.remove(checkpointID);
    }
    static public void atomicReset(){
        currentId.set(0);
    }
}
