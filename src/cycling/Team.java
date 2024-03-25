package cycling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Team class. This class manages all the teams within CyclingPortal.
 * 
 * @version 1.0
 */

public class Team implements Serializable {
    
    private int teamId;
    private String teamName;
    private String teamDescription;
    private ArrayList<Integer> riderIds;

    static private AtomicInteger currentId = new AtomicInteger(0);

    /**
     * The constructor method of this class, instantiates and initialises
     * the non-static variables of this class.
     * 
     * @param teamName The name of the team.
     * @param teamDescription The description of the team.
     */
    public Team(String teamName, String teamDescription){
        this.teamName = new String();
        this.teamDescription = new String();
        this.riderIds = new ArrayList<>();
        this.teamId = currentId.getAndIncrement();
        this.teamName = teamName;
        this.teamDescription = teamDescription;

        }

    /**
     * GETTER method for the ID of the team.
     * 
     * @return The ID of the team.
     */
    public int getTeamID(){
        return teamId;
    }

    /**
     * GETTER method for the name of the team.
     * 
     * @return The name of the team.
     */
    public String getTeamName(){
        return teamName;
    }

    /**
     * GETTER method for the description of the team
     * 
     * @return The description of the team.
     */
    public String getTeamDescription(){
        return teamDescription;
    }

    /**
     * GETTER method for all the riders, who are part
     * of the team.
     * 
     * @return An array of all the rider IDs who are
     *         affiliated with the team.
     */
    public int[] getRiders(){
        return riderIds.stream().mapToInt(i -> i).toArray();
    }

    /**
     * SETTER method for inserting the ID of the team.
     * 
     * @param teamId The new ID of the team being set.
     */
    public void setTeamID(int teamId){
        this.teamId = teamId;
    }

    /**
     * SETTER method for inserting the name of the team.
     * 
     * @param teamName The new name of the team being
     *                 set.
     */
    public void setTeamName(String teamName){
        this.teamName = teamName;
    }

    /**
     * SETTER method for inserting the description of
     * the team.
     * 
     * @param teamDescription The new description for
     *                        the team being set.
     */
    public void setTeamDescription(String teamDescription){
        this.teamDescription = teamDescription;
    }

    /**
     * Method that adds a rider to the list of riders 
     * who are affilitated with the team.
     * 
     * @param riderId The ID of the rider being
     *                added to the team.
     */
    public void addRider(int riderId){
        riderIds.add(riderId);
    }

    /**
     * Method that deletes a rider from the list of
     * riders who are affiliated with the team.
     * 
     * @param riderId The ID of the rider being
     *                removed from the team.
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
