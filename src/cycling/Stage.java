package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Stage class. This class implements the StageInterface as well as
 * using a variety of original methods. This class also manages all
 * the stages within CyclingPortal.
 * 
 * @author Edward Pratt, Alexander Hay
 * @version 1.0
 * 
 */
public class Stage implements StageInterface{

    private int stageId;
    private String stageName;
    private String description;
    private int raceId;
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

    /**
     * The constructor method for the class, instantiates and initialises 
     * all the non-static variables
     * 
     * @param stageName The name of the stage.
     * @param description The description for the stage.
     * @param length The total length of the stage.
     * @param startTime The start time for the stage.
     * @param type The type of stage that the stage will be in the
     *             race.
     */
    public Stage(int raceId,String stageName, String description, double length, LocalDateTime startTime, StageType type){
        this.stageId = 0;

        this.state = new String();
        this.raceId = raceId;
        this.checkpointIds = new ArrayList<>();
        this.riderCompletionTimes = new HashMap<>();
        this.stageName = stageName;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type;

        this.stageId = currentId.getAndIncrement();
    }

    /**
     * GETTER method for the stage ID from the stage.
     * 
     * @return The ID of the stage.
     */
    public int getStageID(){
        return stageId;
    }

    /**
     * GETTER method for the stage name from the stage.
     * 
     * @return The name of the stage.
     */
    public String getStageName(){
        return stageName;
    }

    /**
     * GETTER method for the stage description from the
     * stage.
     * 
     * @return The description of the stage.
     */
    public String getDescription(){
        return description;
    }

    /**
     * GETTER method for the race ID from the
     * stage.
     * 
     * @return The ID of the race that the stage
     *         is in.
     */
    public int getRaceID(){
        return raceId;
    }

    /**
     * GETTER method for the length of the stage from 
     * the stage.
     * 
     * @return The length of the stage.
     */
    public Double getLength(){
        return length;
    }

    /**
     * GETTER method for the stage state from the
     * stage.
     * 
     * @return The state of the stage.
     */
    public String getState(){
        return state;
    }

    /**
     * GETTER method for the stage type from the
     * stage.
     * 
     * @return The type of stage that the stage
     *         is classified.
     */
    public StageType getStageType(){
        return type;
    }

    /**
     * GETTER method for the start time of the stage
     * from the stage.
     * 
     * @return The start time for the stage.
     */
    public LocalDateTime getStartTime(){
        return startTime;
    }

    /**
     * GETTER method for the array of checkpoint IDs within
     * the stage.
     * 
     * @return The array of checkpoint IDs from the stage.
     */
    public int[] getCheckpointIDs(){
        return checkpointIds.stream().mapToInt(i -> i).toArray();
    }

    /**
     * GETTER method for the array of points given to the
     * riders' respective positions for the flat stage type.
     * 
     * @return The array of integers of the respective points
     *         for each of the riders based on their position.
     */
    public int[] getFlatStagePoints(){
        return flatStagePoints;
    }

    /**
     * GETTER method for the array of points given to the
     * riders' respective positions for the medium mountain 
     * stage type.
     * 
     * @return The array of integers of the respective points 
     *         for each of the riders based on their position.
     */
    public int[] getMediumMountainStagePoints(){
        return mediumMountainStagePoints;
    }

    /**
     * GETTER method for the array of points given to the
     * riders' respective positions for high mountain and
     * Time Trial stage types.
     * 
     * @return The array of integers of the respective points
     *         for each of the riders based on their position.
     */
    public int[] getHighMountainAndTTStagePoints(){
        return HighMountainAndTTStagePoints;
    }

    /**
     * GETTER method for collecting the rider's time to 
     * complete the stage.
     * 
     * @param riderId The ID of the rider being enquired.
     * @return The time that it took for the rider to complete
     *         the stage.
     */
    public LocalTime getRiderCompletionTimes(int riderId){
        return riderCompletionTimes.get(riderId);
    }

    /**
     * GETTER method for collecting the rider's adjusted time
     * to complete the stage.
     * 
     * @param riderId The ID of the rider being enquired.
     * @return The adjusted completion time for the rider
     *         to clear the stage.
     */
    public LocalTime getRiderAdjustedTimes(int riderId){
        return riderAdjustedTimes.get(riderId);
    }

    /**
     * SETTER method for initialising the stage with an ID.
     * 
     * @param stageId The new ID being set to the stage.
     */
    public void setStageID(int stageId){
        this.stageId = stageId;
    }

    /**
     * SETTER method for initialising the stage with a name.
     * 
     * @param stageName The new name of the stage being set.
     */
    public void setStageName(String stageName){
        this.stageName = stageName;
    }

    /**
     * SETTER method for initialising the stage with a description.
     * 
     * @param description The new description for the stage being set.
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * SETTER method for initalising the race that the stage is in
     * with an ID.
     * 
     * @param raceId The new ID of the race that the stage is in 
     *               being set.
     */
    public void setRaceID(int raceId){
        this.raceId = raceId;
    }

    /**
     * SETTER method for initalising the length of the stage.
     * 
     * @param length The new length of the stage being set.
     */
    public void setLength(Double length){
        this.length = length;
    }

    /**
     * SETTER method for initialising the state of the stage.
     * 
     * @param state The new state that the stage is current in 
     *              being set.
     */
    public void setState(String state){
        this.state = state;
    }

    /**
     * SETTER method for initialising the type of stage.
     * 
     * @param type The new type of stage that is being set.
     */
    public void setStageType(StageType type){
        this.type = type;
    }

    /**
     * SETTER method for initialising the start time for the
     * stage.
     * 
     * @param startTime The new start time for the stage.
     */
    public void setstartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }

    /**
     * Adds a checkpoint ID to the array of checkpoint IDs
     * within the stage.
     * 
     * @param checkpointId The ID of the checkpoint being
     *                     inserted.
     */
    public void addCheckpointID(int checkpointId){
        checkpointIds.add(checkpointId);
    }

    /**
     * Deletes a checkpoint ID from the array of checkpoint
     * IDs within the stage.
     * 
     * @param checkpointId The ID of checkpoint being
     *                     removed.
     */
    public void deleteCheckpointID(int checkpointId){
        checkpointIds.remove(checkpointId);
    }

    /**
     * Adds a rider's finish time for the stage to the hashmap
     * of other riders' finish times.
     * 
     * @param riderId The ID of the rider put into the hashmap.
     * @param completeTime The finish time of the rider also
     *                     being stored in the hashmap.
     */
    public void addCompletionTime(int riderId, LocalTime completeTime){
        riderCompletionTimes.put(riderId, completeTime);
    }

    /**
     * Deletes a rider's ID and their finish time in the
     * hashmap.
     * 
     * @param riderId The ID of the rider, whose finish
     *                time is being deleted from the hashmap.
     */
    public void deleteCompletionTime(int riderId){
        riderCompletionTimes.remove(riderId);
    }

    /**
     * Adds a rider's adjusted finish time to the hashmap.
     * 
     * @param riderId The ID of the rider put into the hashmap.
     * @param adjustedTime The adjusted finish time of the rider
     *                     also being stored in the hashmap.
     */
    public void addAdjustedTime(int riderId, LocalTime adjustedTime){
        riderAdjustedTimes.put(riderId, adjustedTime);
    }

    /**
     * Deletes a rider's ID and their adjusted finish time
     * in the hashmap.
     * 
     * @param riderId The ID of the rider, whose adjusted
     *                finish time is being deleted from the hashmap.
     */
    public void deleteAdjustedTime(int riderId){
        riderAdjustedTimes.remove(riderId);
    }

    /**
     * Method that calculates the rider's adjusted time based on
     * other rider finish times. This calculates the rider's adjusted
     * time by checking each consecutive rider, who has a finish time
     * within 1 second faster (smaller) than their previous rider.
     * Both times will then be set to the smallest of the two (the 
     * fastest).
     * 
     * @param riderId The ID of the rider, which there finish
     *                time is being adjusted.
     * @return The adjusted finish time of the rider.
     */
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

    /**
     * Methods that finds and calculates all of the riders'
     * ranks sorted based on their finish time in the stage, with the 
     * winner (1st place) being the fastest.
     * 
     * @return A sorted array of all the rider IDs based on
     *         their finish time.
     */
    @Override
    public int[] calculateRidersRankInStage(){
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
     * Method that finds all of the riders' adjusted elapsed
     * times sorted based on their rank that they received in
     * the stage.
     * 
     * @return A sorted array of all the riders' adjusted
     *         finish times based on their rank in the stage.
     */
    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(){
        int[] sortedRiderIds = calculateRidersRankInStage();
        ArrayList<LocalTime> adjustedTimesList = new ArrayList<>();
        for (int riderId : sortedRiderIds){
            adjustedTimesList.add(riderAdjustedTimes.get(riderId));
        }
        return adjustedTimesList.toArray(new LocalTime[adjustedTimesList.size()]);

    }

    /**
     * Method that resets the ID counter. The ID counter creates
     * a new ID for every single new rider that is created.
     */
    static public void atomicReset(){
        currentId.set(0);
    }
}
