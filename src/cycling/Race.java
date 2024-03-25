package cycling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Race class. This class manages all the races within CyclingPortal
 * and stores all the rider's participating in the race as well as
 * all the stages that are in the race.
 * 
 * @version 1.0
 */

public class Race implements Serializable {
    
    private int raceId;
    private String raceName;
    private String raceDescription;
    private ArrayList<Integer> riderIds;
    private ArrayList<Integer> stageIds;

    static private AtomicInteger currentId = new AtomicInteger(0);

    /**
     * The constructor method for this class. Instantiates and initialises
     * all the non-static variables of this class.
     * 
     * @param raceName The name of the race.
     * @param raceDescription The description of the race.
     */
    public Race(String raceName, String raceDescription){
        this.raceName = new String();
        this.raceDescription = new String();
        this.raceName = raceName;
        this.raceDescription = raceDescription;
        this.riderIds = new ArrayList<Integer>();
        this.stageIds = new ArrayList<Integer>();
        this.raceId = currentId.getAndIncrement();
    }

    /**
     * GETTER method for the ID of the race.
     * 
     * @return The ID of the race.
     */
    public int getRaceID(){
        return raceId;
    }

    /**
     * GETTER method for the name of the race.
     * 
     * @return The name of the race.
     */
    public String getRaceName(){
        return raceName;
    }

    /**
     * GETTER method for the description of the 
     * race.
     * 
     * @return The description of the race.
     */
    public String getRaceDescription(){
        return raceDescription;
    }

    /**
     * GETTER method for the IDs of all the
     * riders participating in the race.
     * 
     * @return An array of all the rider IDs
     *         in the race.
     */
    public int[] getRiderIDs(){
        return riderIds.stream().mapToInt(i -> i).toArray();
    }

    /**
     * GETTER method for the IDs of all the
     * stages within the race.
     * 
     * @return An array of all the stage IDs
     *         within the race.
     */
    public int[] getStageIDs(){
        return stageIds.stream().mapToInt(i -> i).toArray();
    }

    /**
     * SETTER method for inserting the race Id.
     * 
     * @param raceId The new ID of the race being
     *               set.
     */
    public void setRaceID(int raceId){
        this.raceId = raceId;
    }

    /**
     * SETTER method for inserting the name of
     * the race.
     * 
     * @param raceName The new name of the race
     *                 being set.
     */
    public void setRaceName(String raceName){
        this.raceName = raceName;
    }

    /**
     * SETTER method for inserting the description
     * of the race.
     * 
     * @param raceDescription The new description of
     *                        the race being set.
     */
    public void setRaceDescription(String raceDescription){
        this.raceDescription = raceDescription;
    }

    /**
     * Method that adds a stage to the list of
     * stage IDs within the race.
     * 
     * @param stageId The ID of the stage being
     *                added to the list.
     */
    public void addStage(int stageId){
        stageIds.add(stageId);
    }

    /**
     * Method that deletes a stage from the list
     * of stage IDs within the race.
     * 
     * @param stageId The ID of the stage being
     *                removed from the list.
     */
    public void deleteStage(int stageId){
        stageIds.remove(stageId);
    }

    /**
     * Method that adds a rider to the list of
     * riders participating in the race.
     * 
     * @param riderId The ID of the rider being
     *                added to the list of riders 
     *                participating in the race.
     */
    public void addRider(int riderId){
        riderIds.add(riderId);
    }

    /**
     * Method that deletes a rider from the list
     * of riders participating in the race.
     * 
     * @param riderId The ID of the rider being
     *                removed from the list of riders
     *                participating in the race.
     */
    public void deleteRider(int riderId){
        riderIds.remove(riderId);
    }
    
    /**
     * Method that resets the ID counter. The ID counter creates
     * a new ID for every single new rider that is created.
     */
    static public void atomicReset(){
        currentId.set(0);
    }
}
