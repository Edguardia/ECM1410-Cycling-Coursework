package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rider class. This class manages all the rider's within CyclingPortal
 * and the results that the rider's get from each stage as well as the
 * mountain points that they collect from each checkpoint they score
 * during a race.
 * 
 * @author Edward Pratt, Alexander Hay
 * @version 1.0
 * 
 */

public class Rider implements Serializable {

    private int riderId;
    private String name;
    private int yearOfBirth;
    private int teamId;
    private HashMap<Integer, Integer> stageResults;
    private HashMap<Integer, Integer> checkpointResults;
    private HashMap<Integer, LocalTime[]> checkpointTimes;

    static private AtomicInteger currentId = new AtomicInteger(0);

    /**
     * The constructor method of this class, instantiates and initialises
     * all the non-static variables.
     * 
     * @param teamId The ID of the team that the rider is part of.
     * @param name The name of the rider.
     * @param yearOfBirth The year of birth for the rider.
     */
    public Rider(int teamId, String name, int yearOfBirth){
        this.riderId = 0;
        this.name = new String();
        this.yearOfBirth = 0;
        this.teamId = 0;
        this.stageResults = new HashMap<>();
        this.checkpointTimes = new HashMap<>();
        this.checkpointResults = new HashMap<>();
        this.teamId = teamId;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.riderId = currentId.getAndIncrement();
    }

    /**
     * GETTER method for the ID of the rider.
     * 
     * @return The ID of the rider.
     */
    public int getRiderID(){
        return riderId;
    }

    /**
     * GETTER method for the name of the rider.
     * 
     * @return The name of the rider.
     */
    public String getName(){
        return name;
    }

    /**
     * GETTER method for the year of birth of the
     * rider.
     * 
     * @return The year of birth of the rider.
     */
    public int getYearOfBirth(){
        return yearOfBirth;
    }

    /**
     * GETTER method for the ID of the team
     * that the rider is in, within the rider.
     * 
     * @return The ID of the team that the rider
     *         is in.
     */
    public int getTeamID(){
        return teamId;
    }

    /**
     * Gets the rider's results from the stage
     * that is being enquired.
     * 
     * @param stageId The ID of the stage that is
     *                enquired.
     * @return The results that the rider got from
     *         the stage.
     */
    public int getStageResults(int stageId){
        return stageResults.get(stageId);
    }

    public boolean hasStageResults(int stageId){
        return stageResults.containsKey(stageId);
    }

    /**
     * Gets the rider's results from the checkpoint
     * that is being enquired.
     * 
     * @param checkpointId The ID of the checkpoint
     *                     that is enquired.
     * @return The results that the rider got from
     *         the checkpoint.
     */
    public int getCheckpointResults(int checkpointId){
        return checkpointResults.get(checkpointId);
    }

    /**
     * Gets the array of all the times of the checkpoints
     * from the stage that the rider raced in.
     * 
     * @param stageId The ID of the stage that is
     *                enquired.
     * @return Array of all the times that the rider's
     *         checkpoint times from the stage.
     */
    public LocalTime[] getCheckpointTimes(int stageId){
        return checkpointTimes.get(stageId);
    }

    /**
     * SETTER method for giving a rider an ID.
     * 
     * @param riderId The new ID of the rider being set.
     */
    public void setRiderID(int riderId){
        this.riderId = riderId;
    }

    /**
     * SETTER method for giving a rider a name.
     * 
     * @param name The new name of the rider being set.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * SETTER method for giving the rider a year
     * of birth.
     * 
     * @param yearOfBirth The new year of birth of the 
     *                    rider being set.
     */
    public void setYearOfBirth(int yearOfBirth){
        this.yearOfBirth = yearOfBirth;
    }
    
    /**
     * SETTER method for setting the rider's team.
     * 
     * @param teamId The new ID of the team that the rider 
     *               is going to be a part of being set.
     */
    public void setTeamID(int teamId){
        this.teamId = teamId;
    }

    /**
     * Method that stores the ID of a stage and the result 
     * that the rider got from racing in the stage inside the
     * HashMap.
     * 
     * @param stageId The ID of the stage that the rider raced in.
     * @param result The result the rider got from the race.
     */
    public void addStageResults(int stageId, int result){
        stageResults.put(stageId, result);
    }



    /**
     * Method that stores the ID of the checkpoint and the mountain
     * points that the rider accumulated from racing in the checkpoint
     * inside the HashMap.
     * 
     * @param checkpointId The ID of the checkpoint that the rider
     *                     raced in.
     * @param result The amount of mountain points accumulated from the 
     *               checkpoint.
     */
    public void addCheckpointResults(int checkpointId, int result){
        this.checkpointResults.put(checkpointId, result);
    }

    /**
     * Method that stores the ID of the stage and all the times that
     * the rider got from all of the checkpoints in the stage inside
     * the HashMap.
     * 
     * @param stageId The ID of the stage that the rider raced in.
     * @param checkpointTimes An array of the times that the rider
     *                        scored for each of the checkpoints.
     */
    public void addCheckpointTimes(int stageId, LocalTime[] checkpointTimes){
        this.checkpointTimes.put(stageId, checkpointTimes);
    }

    /**
     * Method that deletes the stage results that the rider achieved
     * from the HashMap.
     * 
     * @param stageId The ID of the stage that the rider participated
     *                in.
     */
    public void deleteStageResults(int stageId){
        stageResults.remove(stageId);

    }

    /**
     * Methods that deletes the checkpoint results that the rider
     * achieved from the HashMap.
     * 
     * @param checkpointId The ID of the checkpoint that the rider
     *                     participated in.
     */
    public void deleteCheckpointResults(int checkpointId){
        checkpointResults.remove(checkpointId);
    }

    /**
     * Method that deletes the checkpoint times that the rider
     * scored from the HashMap.
     * 
     * @param stageId The ID of the stage that the rider
     *                participated in.
     */
    public void deleteCheckpointTimes(int stageId){
        checkpointTimes.remove(stageId);
    }

    /**
     * Method that calculates the rider's total elapsed time
     * from the stage enquired by finding the sum of all the
     * checkpoint times.
     * 
     * @param stageId The stage ID that the rider participated
     *                in.
     * @return The total time it took for the rider to complete
     *         the stage.
     */
    public LocalTime calculateRidersTotalElapsedTime(int stageId){
        LocalTime totalTime = LocalTime.of(0,0,0);
        for (LocalTime checkpointTime : getCheckpointTimes(stageId)){
            totalTime = totalTime.plusHours(checkpointTime.getHour()).plusMinutes(checkpointTime.getMinute()).plusSeconds(checkpointTime.getSecond());
        }
        return totalTime;
    }

    /**
     * Method that calculates the total amount of mountain
     * points that the rider gains from the all the checkpoints
     * within the race.
     * 
     * @param checkpointIds An array of all the checkpoint IDs
     *                      within the race.
     * @return The total amount of mountain points that the rider
     *         has accumulated from the race.
     */
    public int calculateRidersTotalMountainPoints(int[] checkpointIds){
        int totalMountainPoints = 0;
        for (int i = 0; i < checkpointIds.length; i++){
            totalMountainPoints += checkpointResults.get(checkpointIds[i]);
        }
        return totalMountainPoints;
    }

    /**
     * Method that resets the ID counter. The ID counter creates
     * a new ID for every single new rider that is created.
     */
    static public void atomicReset(){
        currentId.set(0);
    }
}
