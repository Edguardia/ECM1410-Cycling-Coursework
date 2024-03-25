package cycling;

import java.time.LocalTime;

/**
 * The Stage Interface. This interface calculates methods for
 * the adjusted rider times as well as the ranking positions
 * for each of the riders within the stage based on their
 * finish times.
 *
 * @author Edward Pratt, Alexander Hay
 * @version 1.0
 */
public interface StageInterface {

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
    LocalTime calculateRidersAdjustedTime(int riderId);

    /**
     * Methods that finds and calculates all of the riders'
     * ranks sorted based on their finish time in the stage, with the
     * winner (1st place) being the fastest.
     *
     * @return A sorted array of all the rider IDs based on
     * their finish time.
     */
    int[] calculateRidersRankInStage();

    /**
     * Method that finds all of the riders' adjusted elapsed
     * times sorted based on their rank that they received in
     * the stage.
     *
     * @return A sorted array of all the riders' adjusted
     * finish times based on their rank in the stage.
     */
    LocalTime[] getRankedAdjustedElapsedTimesInStage();
}
