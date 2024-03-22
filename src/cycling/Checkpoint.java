package cycling;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Checkpoint {
    
    private int checkpointId;
    private Double location;
    private Double length;
    private Double averageGradient;
    private int stageId;
    private CheckpointType type;
    private HashMap<Integer, LocalTime> riderCompletionTimes;

    static private AtomicInteger currentId = new AtomicInteger(0);
    static private int[] sprintCheckpointPoints = new int[] {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    static private int[] mountainCheckpointHCPoint = new int[] {20, 15, 12, 10, 8, 6, 4, 2};
    static private int[] mountainCheckpointC1Point = new int[] {10, 8, 6, 4, 2, 1};
    static private int[] mountainCheckpointC2Point = new int[] {5, 3, 2, 1};
    static private int[] mountainCheckpointC3Point = new int[] {2, 1};
    static private int[] mountainCheckpointC4Points = new int[] {1};


    public Checkpoint(int stageId, Double location, CheckpointType type, Double length, Double averageGradient){
        this.checkpointId = 0;
        this.location = 0.0;
        this.length = 0.0;
        this.averageGradient = 0.0;
        this.stageId = 0;
        this.stageId = stageId;
        this.location = location;
        this.length = length;
        this.averageGradient = averageGradient;
        this.type = type;
        this.checkpointId = currentId.getAndIncrement();
    }
    public Checkpoint(int stageId, Double location){
        this.checkpointId = 0;
        this.location = 0.0;
        this.length = 0.0;
        this.averageGradient = 0.0;
        this.stageId = 0;
        this.stageId = stageId;
        this.location = location;
        this.checkpointId = currentId.getAndIncrement();
    }
    public int getCheckpointID(){
        return checkpointId;
    }
    public Double getLocation(){
        return location;
    }
    public Double getLength(){
        return length;
    }
    public Double getAverageGradient(){
        return averageGradient;
    }
    public CheckpointType getType(){
        return type;
    }
    public int getStageID(){
        return stageId;
    }
    public int[] getIntermediateSprintPoints(){
        return sprintCheckpointPoints;
    }
    public int[] getMountainClimbHCPoints(){
        return mountainCheckpointHCPoint;
    }
    public int[] getMountainClimbC1Points(){
        return mountainCheckpointC1Point;
    }
    public int[] getMountainClimbC2Points(){
        return mountainCheckpointC2Point;
    }
    public int[] getMountainClimbC3Points(){
        return mountainCheckpointC3Point;
    }
    public int[] getMountainClimbC4Points(){
        return mountainCheckpointC4Points;
    }
    public LocalTime getRiderCompletionTimes(int riderId){
        return riderCompletionTimes.get(riderId);
    }
    public void setCheckpointID(int checkpointId){
        this.checkpointId = checkpointId;
    }
    public void setLocation(Double location){
        this.location = location;
    }
    public void setLength(Double length){
        this.length = length;
    }
    public void setAverageGradient(Double averageGradient){
        this.averageGradient = averageGradient;
    }
    public void setType(CheckpointType type){
        this.type = type;
    }
    public void setStageID(int stageId){
        this.stageId = stageId;
    }

    public void addCompletionTime(int riderId, LocalTime completeTime){
        riderCompletionTimes.put(riderId, completeTime);
    }
    public void deleteCompletionTime(int riderId){
        riderCompletionTimes.remove(riderId);
    }

    public int[] calculateRidersRankInCheckpoints(){
        LinkedList<Map.Entry<Integer, LocalTime>> timeList = new LinkedList<Map.Entry<Integer, LocalTime>>(riderCompletionTimes.entrySet());
        Collections.sort(timeList, (i1, i2) -> i1.getValue().compareTo(i2.getValue())); //Lambda function
        Collections.sort(timeList, Collections.reverseOrder()); //Really needs to be tested
        ArrayList<Integer> idList = new ArrayList<>();
        for (Map.Entry<Integer, LocalTime> entry : timeList){
            idList.add(entry.getKey());
        }
        return idList.stream().mapToInt(i -> i).toArray();
    }

    static public void atomicReset(){
        currentId.set(0);
    }
}
