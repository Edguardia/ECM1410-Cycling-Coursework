package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Stage implements StageInterface{

    private int stageId;
    private String stageName;
    private String description;
    private Double length;
    private String state;
    private StageType type;
    private LocalDateTime startTime;
    private ArrayList<Integer> checkpointIds;
    private HashMap<Integer, LocalTime> riderCompletionTimes;
    

    static private AtomicInteger currentId = new AtomicInteger(0);

    public Stage(String stageName, String description, double length, LocalDateTime startTime, StageType type){
        this.stageId = 0;
        this.stageName = new String();
        this.description = new String();
        this.length = 0.0;
        this.state = new String();

        this.checkpointIds = new ArrayList<>();
        this.riderCompletionTimes = new HashMap<>();
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
        return checkpointIds.stream().mapToInt(i -> i).toArray();
    }
    public LocalTime getRiderCompletionTimes(int stageId){
        return riderCompletionTimes.get(stageId);
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

    public void addCheckpointID(int checkpointId){
        checkpointIds.add(checkpointId);
    }
    public void deleteCheckpointID(int checkpointId){
        checkpointIds.remove(checkpointId);
    }
    public void addCompletionTime(int riderId, LocalTime completeTime){
        riderCompletionTimes.put(riderId, completeTime);
    }
    public void removeCompletionTime(int riderId){
        riderCompletionTimes.remove(riderId);
    }

    @Override
    public int[] calculateRidersRankInStages(){
        LinkedList<Map.Entry<Integer, LocalTime>> timeList = new LinkedList<Map.Entry<Integer, LocalTime>>(riderCompletionTimes.entrySet());
        Collections.sort(timeList, (i1, i2) -> i1.getValue().compareTo(i2.getValue())); //Lambda function
        Collections.sort(timeList, Collections.reverseOrder()); //Really needs to be tested
        ArrayList<Integer> idList = new ArrayList<>();
        for (Map.Entry<Integer, LocalTime> entry : timeList){
            idList.add(entry.getKey());
        }
        return idList.stream().mapToInt(i -> i).toArray();
    }
    @Override
    public LocalTime[] calculateRankedAdjustedElapedTimesInStage(){

    }
    @Override
    public int[] calculateRidersPointsInStage(){

    }
    @Override
    public int[] calculateRidersMountainPointsInStage(){

    }

    static public void atomicReset(){
        currentId.set(0);
    }
}
