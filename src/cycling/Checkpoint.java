package cycling;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Checkpoint class. This class manages all the checkpoints within 
 * CyclingPortal and the times and ranks that rider's got inside of 
 * the checkpoints.
 * 
 * @version 1.0
 */

public class Checkpoint {
    
    private int checkpointId;
    private Double location;
    private Double length;
    private Double averageGradient;
    private int stageId;
    private CheckpointType type;
    private HashMap<Integer, LocalTime> riderCompletionTimes = new HashMap<>();

    static private AtomicInteger currentId = new AtomicInteger(0);
    static final private int[] sprintCheckpointPoints = new int[] {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    static final private int[] mountainCheckpointHCPoints = new int[] {20, 15, 12, 10, 8, 6, 4, 2};
    static final private int[] mountainCheckpointC1Points = new int[] {10, 8, 6, 4, 2, 1};
    static final private int[] mountainCheckpointC2Points = new int[] {5, 3, 2, 1};
    static final private int[] mountainCheckpointC3Points = new int[] {2, 1};
    static final private int[] mountainCheckpointC4Points = new int[] {1};

    /**
     * The first constructor method for this class, instantiates and
     * initialises all the non-static variables.
     * <p>
     * Assigns each of then non-static variables values when all the
     * details about the checkpoint has been specified.
     * 
     * @param stageId The ID of the stage.
     * @param location The location of the stage.
     * @param type The type of stage.
     * @param length The length of the stage.
     * @param averageGradient The average gradient of the stage.
     */
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

    /**
     * The second constructor method for this class, instantiates and
     * initialises all the non-static variables.
     * <p>
     * Only assigns some of the variables values when the type, lenght
     * and average gradient have not been specified.
     * 
     * @param stageId The ID of the stage.
     * @param location The location of the stage.
     */
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

    /**
     * GETTER method for the ID of the checkpoint.
     * 
     * @return The ID of the checkpoint
     */
    public int getCheckpointID(){
        return checkpointId;
    }

    /**
     * GETTER method for the location of the checkpoint.
     * 
     * @return The location of the checkpoint.
     */
    public Double getLocation(){
        return location;
    }

    /**
     * GETTER method for the length of the checkpoint.
     * 
     * @return The length of the checkpoint.
     */
    public Double getLength(){
        return length;
    }

    /**
     * GETTER method for the average gradient
     * of the checkpoint.
     * 
     * @return The average gradient of the checkpoint.
     */
    public Double getAverageGradient(){
        return averageGradient;
    }

    /**
     * GETTER method for the type of checkpoint.
     * 
     * @return The type of checkpoint.
     */
    public CheckpointType getType(){
        return type;
    }

    /**
     * GETTER method for the ID of the stage
     * that the checkpoint is part of.
     * 
     * @return The ID of the stage that the
     * checkpoint is part of.
     */
    public int getStageID(){
        return stageId;
    }

    /**
     * GETTER method for the array of points given to the
     * rider's each based on their respective position that
     * they achieved in the intermediate sprint.
     * 
     * @return The array of integers of the respective points 
     *         for each of the riders based on their position.
     */
    public int[] getIntermediateSprintPoints(){
        return sprintCheckpointPoints;
    }

    /**
     * GETTER method for the array of points given to the
     * rider's each based on their respective position that
     * they achieved in the HC mountain climb checkpoint.
     * 
     * @return The arrray of integers of the respective points
     *         for each of the riders based on their position.
     */
    public int[] getMountainClimbHCPoints(){
        return mountainCheckpointHCPoints;
    }

    /**
     * GETTER method for the array of points given to the
     * rider's each based on their respective position that
     * they achieved in the C1 mountain climb checkpoint.
     * 
     * @return The arrray of integers of the respective points
     *         for each of the riders based on their position.
     */
    public int[] getMountainClimbC1Points(){
        return mountainCheckpointC1Points;
    }

    /**
     * GETTER method for the array of points given to the
     * rider's each based on their respective position that
     * they achieved in the C2 mountain climb checkpoint.
     * 
     * @return The arrray of integers of the respective points
     *         for each of the riders based on their position.
     */
    public int[] getMountainClimbC2Points(){
        return mountainCheckpointC2Points;
    }

    /**
     * GETTER method for the array of points given to the
     * rider's each based on their respective position that
     * they achieved in the C3 mountain climb checkpoint.
     * 
     * @return The arrray of integers of the respective points
     *         for each of the riders based on their position.
     */
    public int[] getMountainClimbC3Points(){
        return mountainCheckpointC3Points;
    }

    /**
     * GETTER method for the array of points given to the
     * rider's each based on their respective position that
     * they achieved in the C4 mountain climb checkpoint.
     * 
     * @return The arrray of integers of the respective points
     *         for each of the riders based on their position.
     */
    public int[] getMountainClimbC4Points(){
        return mountainCheckpointC4Points;
    }

    /**
     * Method that retrieves the completion/finish time of a
     * rider for the checkpoint.
     * 
     * @param riderId The ID of the rider who completed the
     *                checkpoint.
     * @return The finish time that the rider achieved from
     *         racing in the checkpoint.
     */
    public LocalTime getRiderCompletionTimes(int riderId){
        return riderCompletionTimes.get(riderId);
    }

    /**
     * SETTER method for inserting the ID of the checkpoint.
     * 
     * @param checkpointId The new ID of the checkpoint.
     */
    public void setCheckpointID(int checkpointId){
        this.checkpointId = checkpointId;
    }

    /**
     * SETTER method for inserting the location of the
     * checkpoint.
     * 
     * @param location The new location of the checkpoint.
     */
    public void setLocation(Double location){
        this.location = location;
    }

    /**
     * SETTER method for inserting the length of the
     * checkpoint.
     * 
     * @param length The new length of the checkpoint.
     */
    public void setLength(Double length){
        this.length = length;
    }

    /**
     * SETTER method for inserting the average gradient
     * of the checkpoint.
     * 
     * @param averageGradient The new average gradient of
     *                        the checkpoint.
     */
    public void setAverageGradient(Double averageGradient){
        this.averageGradient = averageGradient;
    }

    /**
     * SETTER method for inserting the type of the
     * checkpoint.
     * 
     * @param type The new type of checkpoint.
     */
    public void setType(CheckpointType type){
        this.type = type;
    }

    /**
     * SETTER method for inserting the ID of the
     * stage that the checkpoint is part of.
     * 
     * @param stageId The new ID of the stage that
     *                the checkpoint is in.
     */
    public void setStageID(int stageId){
        this.stageId = stageId;
    }

    /**
     * Method that adds and stores the rider ID and
     * the finish time that the rider got for the 
     * checkpoint in a HashMap.
     * 
     * @param riderId The ID of the rider, who finished the 
     *                checkpoint.
     * @param completeTime The finish time that the rider got
     *                     for the checkpoint.
     */
    public void addCompletionTime(int riderId, LocalTime completeTime){
        riderCompletionTimes.put(riderId, completeTime);
    }

    /**
     * Method that deletes a rider's finish time
     * from the HashMap of finish times.
     * 
     * @param riderId The ID of the rider that is going
     *                to be removed.
     */
    public void deleteCompletionTime(int riderId){
        riderCompletionTimes.remove(riderId);
    }

    /**
     * Method that calculates all the riders' ranks that they
     * achieved in the checkpoint by the finish times that they
     * achieved during the race, sorted with the winner (fastest 
     * time) being the first in the array.
     * 
     * @return An array of all the rider IDs, sorted based on the
     *         finish time they achieved in the checkpoint.
     */
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

    /**
     * Method that resets the ID counter. The ID counter creates
     * a new ID for every single new rider that is created.
     */
    static public void atomicReset(){
        currentId.set(0);
    }
}
