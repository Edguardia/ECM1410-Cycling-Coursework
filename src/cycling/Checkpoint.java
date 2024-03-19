package cycling;

import java.util.concurrent.atomic.AtomicInteger;

public class Checkpoint {
    
    private int checkpointId;
    private Double location;
    private Double length;
    private Double averageGradient;
    private int stageId;
    private CheckpointType type;

    static private AtomicInteger currentId = new AtomicInteger(0);
    static private int[] sprintCheckpointPoints = new int[] {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    static private int[] mountainCheckpointHCPoint = new int[] {20, 15, 12, 10, 8, 6, 4, 2};
    static private int[] mountainCheckpoint1CPoint = new int[] {10, 8, 6, 4, 2, 1};
    static private int[] mountainCheckpoint2CPoint = new int[] {5, 3, 2, 1};
    static private int[] mountainCheckpoint34CPoint = new int[] {2, 1};
    static private int[] mountainCheckpoint4CPoints = new int[] {1};


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

    public int[] calculateTotalRidersMountainPointsInStage(){

    }
    static public void atomicReset(){
        currentId.set(0);
    }
}
