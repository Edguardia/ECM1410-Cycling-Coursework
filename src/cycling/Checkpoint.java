package cycling;

import java.util.concurrent.atomic.AtomicInteger;

public class Checkpoint {
    
    private int checkpointId;
    private Double location;
    private Double length;
    private Double averageGradient;
    private CheckpointType type;

    static private AtomicInteger currentId = new AtomicInteger(0);

    public Checkpoint(Double location, CheckpointType type, Double length, Double averageGradient){
        this.location = location;
        this.length = length;
        this.averageGradient = averageGradient;
        this.type = type;
        this.checkpointId = currentId.getAndIncrement();
    }
    public Checkpoint(Double location){
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
    static public void atomicReset(){
        currentId.set(0);
    }
}
