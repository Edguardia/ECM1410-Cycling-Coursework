package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
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
    private HashMap<Integer, LocalTime> riderAdjustedTimes;
    
    static private AtomicInteger currentId = new AtomicInteger(0);
    static private int[] flatStagePoints = new int[] {50, 30, 20, 18, 16, 14, 12, 10, 8, 7, 6, 5, 4, 3, 2};
    static private int[] mediumMountainStagePoints = new int[] {30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2};
    static private int[] HighMountainAndTTStagePoints = new int[] {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

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
    public int[] getFlatStagePoints(){
        return flatStagePoints;
    }
    public int[] getMediumMountainStagePoints(){
        return mediumMountainStagePoints;
    }
    public int[] getHighMountainAndTTStagePoints(){
        return HighMountainAndTTStagePoints;
    }
    public LocalTime getRiderCompletionTimes(int riderId){
        return riderCompletionTimes.get(riderId);
    }
    public LocalTime getRiderAdjustedTimes(int riderId){
        return riderAdjustedTimes.get(riderId);
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
    public void setStageType(StageType type){
        this.type = type;
    }
    public void setstartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }

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
    public void addAdjustedTime(int riderId, LocalTime adjustedTime){
        riderAdjustedTimes.put(riderId, adjustedTime);
    }
    public void removeAdjustedTime(int riderId){
        riderAdjustedTimes.remove(riderId);
    }

    @Override
    public LocalTime calculateRidersAdjustedTime(int riderId){
        LocalTime chosenRiderTime = riderCompletionTimes.get(riderId);
        for (int tempRiderId : riderCompletionTimes.keySet()){
            LocalTime riderTime = riderCompletionTimes.get(tempRiderId);
            if (riderId != tempRiderId && chosenRiderTime.isAfter(riderTime.plusSeconds(-1)) && chosenRiderTime.isBefore(riderTime)){
                chosenRiderTime = calculateRidersAdjustedTime(riderId);
                riderAdjustedTimes.put(riderId, riderTime);
                break;
            }
            riderAdjustedTimes.put(riderId, chosenRiderTime);
        }
        return chosenRiderTime;
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
    public LocalTime[] getRankedAdjustedElapedTimesInStage(){
        int[] sortedRiderIds = calculateRidersRankInStages();
        ArrayList<LocalTime> adjustedTimesList = new ArrayList<>();
        for (int riderId : sortedRiderIds){
            adjustedTimesList.add(riderAdjustedTimes.get(riderId));
        }
        return adjustedTimesList.toArray(new LocalTime[adjustedTimesList.size()]);

    }

    static public void atomicReset(){
        currentId.set(0);
    }
}
