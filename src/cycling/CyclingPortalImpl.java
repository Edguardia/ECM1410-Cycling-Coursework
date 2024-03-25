package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.*;

/**
 * CyclingPortalImpl class. This class is an implementation of the 
 * CyclingPortal interface.
 * 
 * @author Edward Pratt, Alexander Hay
 * @version 1.0
 * 
 */

public class CyclingPortalImpl implements CyclingPortal, Serializable {

    HashMap<Integer, Race> races = new HashMap<>();
    HashMap<Integer, Stage> stages = new HashMap<>();
    HashMap<Integer, Checkpoint> checkpoints = new HashMap<>();
    HashMap<Integer, Rider> riders = new HashMap<>();
    HashMap<Integer, Team> teams = new HashMap<>();

    /**
	 * Get the races currently created in the platform.
	 * 
	 * @return An array of race IDs in the system or an empty array if none exists.
	 */
    @Override
    public int[] getRaceIds() {
        ArrayList<Integer> raceList = new ArrayList<Integer>();
        raceList.addAll((races.keySet()));
        return raceList.stream().mapToInt(i -> i).toArray();
    }

    /**
	 * The method creates a staged race in the platform with the given name and
	 * description.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param name        Race's name.
	 * @param description Race's description (can be null).
	 * @throws IllegalNameException If the name already exists in the platform.
	 * @throws InvalidNameException If the name is null, empty, has more than 30
	 *                              characters, or has white spaces.
	 * @return the unique ID of the created race.
	 * 
	 */
    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        if(name == null){
            throw new InvalidNameException("Invalid name, the race name cannot be null.");
        }
        else if(name.equals("")){
            throw new InvalidNameException("Invalid name, the race name cannot be empty.");
        }
        else if(name.length() > 30){
            throw new InvalidNameException("Invalid name, the race name cannot contain more than 30 characters.");
        }
        else if(name.contains(" ")){
            throw new InvalidNameException("Invalid name, the race name cannot contain any white spaces.");
        }
        for(Race i : races.values()){
            if(i.getRaceName().equals(name)){
                throw new IllegalNameException("The race name already exists.");
            }
        }
        Race newRace = new Race(name, description);
        races.put(newRace.getRaceID(), newRace);
        return newRace.getRaceID();
    }

    /**
	 * Get the details from a race.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return Any formatted string containing the race ID, name, description, the
	 *         number of stages, and the total length (i.e., the sum of all stages'
	 *         length).
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
    @Override
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
        int totalLength = 0;
        if (races.containsKey(raceId)){

            for(Stage stage: stages.values()){
                if(stage.getRaceID() == raceId) {
                    totalLength += stage.getLength();
                }
            }
            return ("ID:"+raceId + " Name:"+ races.get(raceId).getRaceName() + " Description:"+ races.get(raceId).getRaceDescription() + " Number of Stages:"+ getNumberOfStages(raceId) + " Total Length:"+ totalLength);
        }
        else { throw new IDNotRecognisedException("Race ID does not exist."); }
    }

    /**
	 * The method removes the race and all its related information, i.e., stages,
	 * checkpoints, and results.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
    @Override
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        if (races.containsKey(raceId)){
            races.remove(raceId);
            for (Stage stage : stages.values()){
                if (stage.getRaceID() == raceId){
                    for (int checkpointId : stage.getCheckpointIDs()){
                        checkpoints.remove(checkpointId);
                    }
                    stages.remove(stage.getStageID());
                }
            }
        }
        else{ throw new IDNotRecognisedException("Race ID does not exist."); }
    }

    /**
	 * The method queries the number of stages created for a race.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return The number of stages created for the race.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        if (races.containsKey(raceId)){
            Race race = races.get(raceId);
            return race.getStageIDs().length;
        }
        else{
            throw new IDNotRecognisedException("Race ID does not exist.");
        }
    }

    /**
	 * Creates a new stage and adds it to the race.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId      The race which the stage will be added to.
	 * @param stageName   An identifier name for the stage.
	 * @param description A descriptive text for the stage.
	 * @param length      Stage length in kilometres.
	 * @param startTime   The date and time in which the stage will be raced. It
	 *                    cannot be null.
	 * @param type        The type of the stage. This is used to determine the
	 *                    amount of points given to the winner.
	 * @return the unique ID of the stage.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 * @throws IllegalNameException     If the name already exists in the platform.
	 * @throws InvalidNameException     If the name is null, empty, has more than 30
	 *                              	characters, or has white spaces.
	 * @throws InvalidLengthException   If the length is less than 5km.
	 */
    @Override
    public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        if (!races.containsKey(raceId)){ throw new IDNotRecognisedException("Race ID does not exist."); }
        for(Stage i : stages.values()){
            if(i.getStageName().equals(stageName)){
                throw new IllegalNameException("The name of the stage already exists.");
            }
        }
        if(stageName == null){
            throw new InvalidNameException("Invalid name, the stage name cannot be null.");
        }
        else if(stageName.equals("")){
            throw new InvalidNameException("Invalid name, the stage name cannot be empty.");
        }
        else if(stageName.length() > 30){
            throw new InvalidNameException("Invalid name, the stage name cannot contain more than 30 characters.");
        }
        else if(stageName.contains(" ")){
            throw new InvalidNameException("Invalid name, the stage name cannot contain any white spaces.");
        }
        if (length < 5){ throw new InvalidLengthException("The length of the stage cannot be less than 5km."); }
        Stage newStage = new Stage(raceId, stageName, description, length, startTime, type);
        Race race = races.get(raceId);
        race.addStage(newStage.getStageID());
        races.put(race.getRaceID(), race);
        stages.put(newStage.getStageID(), newStage);
        return newStage.getStageID();
    }

    /**
	 * Retrieves the list of stage IDs of a race.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return An array of stage IDs ordered (from first to last) by their sequence in the
	 *         race or an empty array if none exists.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        if (races.containsKey(raceId)){
            int[] listIds = races.get(raceId).getStageIDs();
            Arrays.sort(listIds);
            return listIds;
        }
        else{ throw new IDNotRecognisedException("Race ID does not exist."); }
    }

    /**
	 * Gets the length of a stage in a race, in kilometres.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The stage's length.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        if (stages.containsKey(stageId)){
            return stages.get(stageId).getLength();
        }
        else{ throw new IDNotRecognisedException("Stage ID does not exist."); }
    }

    /**
	 * Removes a stage and all its related data, i.e., checkpoints and results.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being removed.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        if (stages.containsKey(stageId)){
            int raceId = stages.get(stageId).getRaceID();
            races.get(raceId).deleteStage(stageId);
            races.remove(stageId);
            for(Rider rider : riders.values()){
                rider.deleteStageResults(stageId);
                for (int checkpointId : stages.get(stageId).getCheckpointIDs()){
                    rider.deleteCheckpointTimes(checkpointId);
                    rider.deleteCheckpointResults(checkpointId);
                }
            }
            for (int checkpointId : stages.get(stageId).getCheckpointIDs()){
                checkpoints.remove(checkpointId);
            }
            stages.remove(stageId);
        }
        else{ throw new IDNotRecognisedException("Stage ID does not exist."); }
    }

    /**
	 * Adds a climb checkpoint to a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId         The ID of the stage to which the climb checkpoint is
	 *                        being added.
	 * @param location        The kilometre location where the climb finishes within
	 *                        the stage.
	 * @param type            The category of the climb - {@link CheckpointType#C4},
	 *                        {@link CheckpointType#C3}, {@link CheckpointType#C2},
	 *                        {@link CheckpointType#C1}, or {@link CheckpointType#HC}.
	 * @param averageGradient The average gradient for the climb.
	 * @param length          The length of the climb in kilometre.
	 * @return The ID of the checkpoint created.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidLocationException   If the location is out of bounds of the
	 *                                    stage length.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 * @throws InvalidStageTypeException  Time-trial stages cannot contain any
	 *                                    checkpoint.
	 */
    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient, Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        else if (location < 0){
            throw new InvalidLocationException("The value cannot be location negative.");
        }
        else if (location > stages.get(stageId).getLength()){
            throw new InvalidLocationException("The location of the checkpoint cannot be out of bounds of the stage length.");
        }
        else if (stages.get(stageId).getState().equals("waiting for results")){
            throw new InvalidStageStateException("Cannot add checkpoint when the stage is waiting for results.");
        }
        else if (stages.get(stageId).getStageType().toString().equals("TT")){
            throw new InvalidStageTypeException("Cannot add checkpoint to Time Trial.");
        }
        Checkpoint newCheckpoint = new Checkpoint(stageId, location, type, length, averageGradient);
        Stage stage = stages.get(stageId);
        stage.addCheckpointID(newCheckpoint.getCheckpointID());
        checkpoints.put(newCheckpoint.getCheckpointID(), newCheckpoint);
        stages.put(stage.getStageID(), stage);
        return newCheckpoint.getCheckpointID();
    }

    /**
	 * Adds an intermediate sprint to a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId  The ID of the stage to which the intermediate sprint checkpoint
	 *                 is being added.
	 * @param location The kilometre location where the intermediate sprint finishes
	 *                 within the stage.
	 * @return The ID of the checkpoint created.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidLocationException   If the location is out of bounds of the
	 *                                    stage length.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 * @throws InvalidStageTypeException  Time-trial stages cannot contain any
	 *                                    checkpoint.
	 */
    @Override
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        else if (location < 0){
            throw new InvalidLocationException("The value cannot be location negative.");
        }
        else if (location > stages.get(stageId).getLength()){
            throw new InvalidLocationException("The location of the checkpoint cannot be out of bounds of the stage length.");
        }
        else if (stages.get(stageId).getState().equals("waiting for results")){
            throw new InvalidStageStateException("Cannot add checkpoints when the stage is 'waiting for results'.");
        }
        else if (stages.get(stageId).getStageType().toString().equals("TT")){
            throw new InvalidStageTypeException("Cannot add checkpoint to Time Trial.");
        }
        Checkpoint newCheckpoint = new Checkpoint(stageId, location);
        Stage stage = stages.get(stageId);
        stage.addCheckpointID(newCheckpoint.getCheckpointID());
        checkpoints.put(newCheckpoint.getCheckpointID(), newCheckpoint);
        stages.put(stage.getStageID(), stage);
        return newCheckpoint.getCheckpointID();
    }

    /**
	 * Removes a checkpoint from a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param checkpointId The ID of the checkpoint to be removed.
	 * @throws IDNotRecognisedException   If the ID does not match to any checkpoint in
	 *                                    the system.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 */
    @Override
    public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
        if (!checkpoints.containsKey(checkpointId)){
            throw new IDNotRecognisedException("Checkpoint ID does not exist.");
        }
        Checkpoint oldcheckpoint = checkpoints.get(checkpointId);
        Stage stage = stages.get(oldcheckpoint.getStageID());
        if (stages.get(stage.getStageID()).getState().equals("waiting for results")){
            throw new InvalidStageStateException("Cannot remove checkpoint when stage is 'waiting for results'.");
        }
        stage.deleteCheckpointID(checkpointId);
        stages.put(stage.getStageID(), stage);
        for (Rider rider : riders.values()){
            rider.deleteCheckpointResults(checkpointId);
        }
        checkpoints.remove(checkpointId);
    }

    /**
	 * Concludes the preparation of a stage. After conclusion, the stage's state
	 * should be "waiting for results".
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage to be concluded.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 */
    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        else if (stages.get(stageId).getState().toString().equals("waiting for results")){
            throw new InvalidStageStateException("The stage has already been set to 'waiting for results'.");
        }
        Stage stage = stages.get(stageId);
        stage.setState("waiting for results");
        stages.put(stage.getStageID(), stage);
    }

    /**
	 * Retrieves the list of checkpoint (mountains and sprints) IDs of a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The list of checkpoint IDs ordered (from first to last) by their location in the
	 *         stage.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
    @Override
    public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        int[] listIds = stages.get(stageId).getCheckpointIDs();
        Arrays.sort(listIds);
        return listIds; 
    }

    /**
	 * Creates a team with name and description.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param name        The identifier name of the team.
	 * @param description A description of the team.
	 * @return The ID of the created team.
	 * @throws IllegalNameException If the name already exists in the platform.
	 * @throws InvalidNameException If the name is null, empty, has more than 30
	 *                              characters, or has white spaces.
	 */
    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        if(name == null){
            throw new InvalidNameException("Invalid name, the team name cannot be null.");
        }
        else if(name.equals("")){
            throw new InvalidNameException("Invalid name, the team name cannot be empty.");
        }
        else if(name.length() > 30){
            throw new InvalidNameException("Invalid name, the team name cannot contain more than 30 characters.");
        }
        else if(name.contains(" ")){
            throw new InvalidNameException("Invalid name, the team name cannot contain any white spaces.");
        }
        for(Team i : teams.values()){
            if(i.getTeamName().equals(name)){
                throw new IllegalNameException("The name of the team already exists.");
            }
        }
        Team newTeam = new Team(name, description);
        teams.put(newTeam.getTeamID(), newTeam);
        return newTeam.getTeamID();
    }

    /**
	 * Removes a team from the system.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param teamId The ID of the team to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 */
    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        if (teams.containsKey(teamId)){
            teams.remove(teamId);
            for(Rider rider : riders.values()){
                if (rider.getTeamID() == teamId){
                    removeRider(rider.getRiderID());
                }
            }
        }
        else{ throw new IDNotRecognisedException("Team ID does not exist."); }
    }

    /**
	 * Get the list of teams' IDs in the system.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @return The list of IDs from the teams in the system. An empty list if there
	 *         are no teams in the system.
	 * 
	 */
    @Override
    public int[] getTeams() {
        ArrayList<Integer> teamlist = new ArrayList<Integer>();
        teamlist.addAll((teams.keySet()));
        return teamlist.stream().mapToInt(i -> i).toArray();
    }

    /**
	 * Get the riders of a team.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param teamId The ID of the team being queried.
	 * @return A list with riders' ID.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 */
    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        if (teams.containsKey(teamId)){
            return teams.get(teamId).getRiders();
        }
        else{
            throw new IDNotRecognisedException("Team ID does not exist.");
        }
    }

    /**
	 * Creates a rider.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param teamID      The ID rider's team.
	 * @param name        The name of the rider.
	 * @param yearOfBirth The year of birth of the rider.
	 * @return The ID of the rider in the system.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 * @throws IllegalArgumentException If the name of the rider is null or empty,
	 *                                  or the year of birth is less than 1900.
	 */
    @Override
    public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        if (!teams.containsKey(teamID)){
            throw new IDNotRecognisedException("Team ID does not exist.");
        }
        else if(name == null){
            throw new IllegalArgumentException("Invalid name, the name of the rider cannot be null.");
        }
        else if (name.equals("")){
            throw new IllegalArgumentException("Invalid name, the name of the rider cannot be empty.");
        }
        else if (yearOfBirth < 1900){
            throw new IllegalArgumentException("Invalid year of birth, the birth year of the rider cannot be less than 1900.");
        }
        Rider newRider = new Rider(teamID, name, yearOfBirth);
        riders.put(newRider.getRiderID(), newRider);
        teams.get(teamID).addRider(newRider.getRiderID());
        return newRider.getRiderID();
    }

    /**
	 * Removes a rider from the system. When a rider is removed from the platform,
	 * all of its results should be also removed. Race results must be updated.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param riderId The ID of the rider to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any rider in the
	 *                                  system.
	 */
    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        if (riders.containsKey(riderId)){
            int teamId = riders.get(riderId).getTeamID();
            teams.get(teamId).deleteRider(riderId);
            for (Race race : races.values()){
                race.deleteRider(riderId);
            }
            for (Stage stage : stages.values()){
                stage.deleteCompletionTime(riderId);
                stage.deleteAdjustedTime(riderId);
            }
            for (Checkpoint checkpoint : checkpoints.values()){
                checkpoint.deleteCompletionTime(riderId);
            }
            riders.remove(riderId);
        }
        else{
            throw new IDNotRecognisedException("Rider ID does not exist.");
        }
    }

    /**
	 * Record the times of a rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId     The ID of the stage the result refers to.
	 * @param riderId     The ID of the rider.
	 * @param checkpointTimes An array of times at which the rider reached each of the
	 *                    checkpoints of the stage, including the start time and the
	 *                    finish line.
	 * @throws IDNotRecognisedException    If the ID does not match to any rider or
	 *                                     stage in the system.
	 * @throws DuplicatedResultException   Thrown if the rider has already a result
	 *                                     for the stage. Each rider can have only
	 *                                     one result per stage.
	 * @throws InvalidCheckpointTimesException Thrown if the length of checkpointTimes is
	 *                                     not equal to n+2, where n is the number
	 *                                     of checkpoints in the stage; +2 represents
	 *                                     the start time and the finish time of the
	 *                                     stage.
	 * @throws InvalidStageStateException  Thrown if the stage is not "waiting for
	 *                                     results". Results can only be added to a
	 *                                     stage while it is "waiting for results".
	 */
    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpointTimes) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        else if (!riders.containsKey(riderId)){
            throw new IDNotRecognisedException("Rider ID does not exist.");
        }
        else if (riders.get(riderId).hasStageResults(stageId)){
            throw new DuplicatedResultException("The rider already has results for this stage.");
        }
        else if (checkpointTimes.length != (stages.get(stageId).getCheckpointIDs().length + 2)) {
            throw new InvalidCheckpointTimesException("The number of checkpoint times does not equal 2 more than the total number of checkpoints within the stage.");
        }
        else if (!stages.get(stageId).getState().equals("waiting for results")){
            throw new InvalidStageStateException("Cannot add results to the stage when it is not in the 'waiting for results' state.");
        }
        Rider currentRider = riders.get(riderId);
        currentRider.addCheckpointTimes(stageId, checkpointTimes);
        for(int checkpointId : stages.get(stageId).getCheckpointIDs()){
            for (LocalTime checkpointTime : checkpointTimes) {
                checkpoints.get(checkpointId).addCompletionTime(riderId, checkpointTime);
            }
        }
        riders.put(currentRider.getRiderID(), currentRider);
    }

    /**
	 * Get the times of a rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any exceptions are
	 * thrown.
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @return The array of times at which the rider reached each of the checkpoints
	 *         of the stage and the total elapsed time. The elapsed time is the
	 *         difference between the finish time and the start time. Return an
	 *         empty array if there is no result registered for the rider in the
	 *         stage. Assume the total elapsed time of a stage never exceeds 24h
	 *         and, therefore, can be represented by a LocalTime variable. There is
	 *         no need to check for this condition or raise any exception.
	 * @throws IDNotRecognisedException If the ID does not match to any rider or
	 *                                  stage in the system.
	 */
    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        else if (!riders.containsKey(riderId)){
            throw new IDNotRecognisedException("Rider ID does not exist.");
        }
        Rider currentRider = riders.get(riderId);
        LocalTime[] riderTimes = new LocalTime[currentRider.getCheckpointTimes(stageId).length + 1];
        int i = 0;
        for (LocalTime time : currentRider.getCheckpointTimes(stageId)) {
            riderTimes[i] = time;
            i +=1;
        }
        riderTimes[i] = currentRider.calculateRidersTotalElapsedTime(stageId);
        Duration elapsed = (Duration.between(riderTimes[0], riderTimes[riderTimes.length-2]));
        LocalTime elapsedTotal = LocalTime.of(0,0,0);
        elapsedTotal = elapsedTotal.plus(elapsed);
        riderTimes[i] = elapsedTotal;
        return riderTimes;
    }

    /**
	 * For the general classification, the aggregated time is based on the adjusted
	 * elapsed time, not the real elapsed time. Adjustments are made to take into
	 * account groups of riders finishing very close together, e.g., the peloton. If
	 * a rider has a finishing time less than one second slower than the
	 * previous rider, then their adjusted elapsed time is the smallest of both. For
	 * instance, a stage with 200 riders finishing "together" (i.e., less than 1
	 * second between consecutive riders), the adjusted elapsed time of all riders
	 * should be the same as the first of all these riders, even if the real gap
	 * between the 200th and the 1st rider is much bigger than 1 second. There is no
	 * adjustments on elapsed time on time-trials.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @return The adjusted elapsed time for the rider in the stage. Return null if 
	 * 		  there is no result registered for the rider in the stage.
	 * @throws IDNotRecognisedException   If the ID does not match to any rider or
	 *                                    stage in the system.
	 */
    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        else if (!riders.containsKey(riderId)){
            throw new IDNotRecognisedException("Rider ID does not exist.");
        }
        Stage currentStage = stages.get(stageId);
        if (!currentStage.getStageType().toString().equals("TT")){
            currentStage.calculateRidersAdjustedTime(riderId);
            return currentStage.getRiderAdjustedTimes(riderId);
        }
        return null;
    }

    /**
	 * Removes the stage results from the rider.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @throws IDNotRecognisedException If the ID does not match to any rider or
	 *                                  stage in the system.
	 */
    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        if (!riders.containsKey(riderId)){
            throw new IDNotRecognisedException("Rider ID does not exist.");
        }
        riders.get(riderId).deleteStageResults(stageId);
    }

    /**
	 * Get the riders finished position in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return A list of riders ID sorted by their elapsed time. An empty list if
	 *         there is no result for the stage.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        Stage currentStage = stages.get(stageId);
        return currentStage.calculateRidersRankInStage();
    }

    /**
	 * Get the adjusted elapsed times of riders in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any exceptions are
	 * thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of adjusted elapsed times sorted by their finish
	 *         time. An empty list if there is no result for the stage. These times
	 *         should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}. Assume the total elapsed time of
	 *         in a stage never exceeds 24h and, therefore, can be represented by a
	 *         LocalTime variable. There is no need to check for this condition or
	 *         raise any exception.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        Stage currentStage = stages.get(stageId);
        return currentStage.getRankedAdjustedElapsedTimesInStage();
    }

    /**
	 * Get the number of points obtained by each rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of points each rider received in the stage, sorted
	 *         by their elapsed time. An empty list if there is no result for the
	 *         stage. These points should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        Stage currentStage = stages.get(stageId);
        int[] sortedRiderIds = currentStage.calculateRidersRankInStage();
        switch (currentStage.getStageType()) {
            case FLAT:
            for (int i = 0; i < currentStage.getFlatStagePoints().length; i++) {
                riders.get(sortedRiderIds[i]).addStageResults(stageId, currentStage.getFlatStagePoints()[i]);
            }
            break;
            case MEDIUM_MOUNTAIN:
            for (int i = 0; i < currentStage.getMediumMountainStagePoints().length; i++) {
                riders.get(sortedRiderIds[i]).addStageResults(stageId, currentStage.getMediumMountainStagePoints()[i]);
            }
            break;
            case HIGH_MOUNTAIN:
            case TT:
            for (int i = 0; i < currentStage.getHighMountainAndTTStagePoints().length; i++) {
                riders.get(sortedRiderIds[i]).addStageResults(stageId, currentStage.getHighMountainAndTTStagePoints()[i]);
            }
            break;
        }
        int[] ridersPoints = new int[sortedRiderIds.length];
        for (int i = 0; i < sortedRiderIds.length; i++){
            ridersPoints[i] = riders.get(sortedRiderIds[i]).getStageResults(stageId);
        }
        return ridersPoints;
    }

    /**
	 * Get the number of mountain points obtained by each rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of mountain points each rider received in the stage,
	 *         sorted by their finish time. An empty list if there is no result for
	 *         the stage. These points should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        Stage currentStage = stages.get(stageId);
        int[] sortedStageRiderIds = currentStage.calculateRidersRankInStage();
        for (int checkpointId : currentStage.getCheckpointIDs()){
            Checkpoint currentCheckpoint = checkpoints.get(checkpointId);
            int[] sortedCheckpointRiderIds = currentCheckpoint.calculateRidersRankInCheckpoints();
            switch(currentCheckpoint.getType()){
                case SPRINT:
                for (int i = 0; i < currentCheckpoint.getIntermediateSprintPoints().length; i++){
                    riders.get(sortedCheckpointRiderIds[i]).addCheckpointResults(checkpointId, currentCheckpoint.getIntermediateSprintPoints()[i]);
                }
                break;
                case HC:
                for (int i = 0; i < currentCheckpoint.getMountainClimbHCPoints().length; i++){
                    riders.get(sortedCheckpointRiderIds[i]).addCheckpointResults(checkpointId, currentCheckpoint.getMountainClimbHCPoints()[i]);
                }
                break;
                case C1:
                for (int i = 0; i < currentCheckpoint.getMountainClimbC1Points().length; i++){
                    riders.get(sortedCheckpointRiderIds[i]).addCheckpointResults(checkpointId, currentCheckpoint.getMountainClimbC1Points()[i]);
                }
                break;
                case C2:
                for (int i = 0; i < currentCheckpoint.getMountainClimbC2Points().length; i++){
                    riders.get(sortedCheckpointRiderIds[i]).addCheckpointResults(checkpointId, currentCheckpoint.getMountainClimbC2Points()[i]);
                }
                break;
                case C3:
                for (int i = 0; i < currentCheckpoint.getMountainClimbC3Points().length; i++){
                    riders.get(sortedCheckpointRiderIds[i]).addCheckpointResults(checkpointId, currentCheckpoint.getMountainClimbC3Points()[i]);
                }
                break;
                case C4:
                for (int i = 0; i < currentCheckpoint.getMountainClimbC4Points().length; i++){
                    riders.get(sortedCheckpointRiderIds[i]).addCheckpointResults(checkpointId, currentCheckpoint.getMountainClimbC4Points()[i]);
                }
                break;
            }
        }
        int[] ridersMountainPoints = new int[sortedStageRiderIds.length];
        for (int i = 0; i < sortedStageRiderIds.length; i++){
            ridersMountainPoints[i] = riders.get(sortedStageRiderIds[i]).calculateRidersTotalMountainPoints(ridersMountainPoints);
        }
        return ridersMountainPoints;
    }

    /**
	 * Method empties this MiniCyclingPortal of its contents and resets all
	 * internal counters.
	 */
    @Override
    public void eraseCyclingPortal() {
        Race.atomicReset();
        races.clear();
        Stage.atomicReset();
        stages.clear();
        Checkpoint.atomicReset();
        checkpoints.clear();
        Rider.atomicReset();
        riders.clear();
        Team.atomicReset();
        teams.clear();
    }

    /**
	 * Method saves this MiniCyclingPortal contents into a serialised file,
	 * with the filename given in the argument.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 *
	 * @param filename Location of the file to be saved.
	 * @throws IOException If there is a problem experienced when trying to save the
	 *                     store contents to the file.
	 */
    @Override
    public void saveCyclingPortal(String filename) throws IOException {
        ArrayList<Object> objList = new ArrayList<>();
        objList.add(this.races);
        objList.add(this.stages);
        objList.add(this.checkpoints);
        objList.add(this.riders);
        objList.add(this.teams);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))){

            out.writeObject(objList);
        } catch (IOException ex) { throw new IOException("File not recognised.");}
    }


    /**
	 * Method should load and replace this MiniCyclingPortal contents with the
	 * serialised contents stored in the file given in the argument.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 *
	 * @param filename Location of the file to be loaded.
	 * @throws IOException            If there is a problem experienced when trying
	 *                                to load the store contents from the file.
	 * @throws ClassNotFoundException If required class files cannot be found when
	 *                                loading.
	 */
    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        ArrayList<Object> objList = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            objList = (ArrayList<Object>) in.readObject();
            this.races = (HashMap<Integer, Race>) objList.get(0);
            this.stages = (HashMap<Integer, Stage>) objList.get(1);
            this.checkpoints = (HashMap<Integer, Checkpoint>) objList.get(2);
            this.riders = (HashMap<Integer, Rider>) objList.get(3);
            this.teams = (HashMap<Integer, Team>) objList.get(4);
        } catch (IOException ex) {
            throw new IOException("File not recognised.");
        }
    }

    /**
	 * The method removes the race and all its related information, i.e., stages,
	 * checkpoints, and results.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param name The name of the race to be removed.
	 * @throws NameNotRecognisedException If the name does not match to any race in
	 *                                    the system.
	 */
    @Override
    public void removeRaceByName(String name) throws NameNotRecognisedException {
        ArrayList<Race> allRaces = new ArrayList<>(races.values());
        boolean nameExists = false;
        for (Race allRace : allRaces) {
            if (allRace.getRaceName().equals(name)) {
                nameExists = true;
                races.remove(allRace.getRaceID());
            }
            if(!nameExists){
                throw new NameNotRecognisedException("Race name does not exist.");
            }
        }
    }

    /**
     * The method sorts a HashMap based on the rider's total adjusted 
     * race times.
     * <p>
     * This is unrelated to the original MiniCyclingPortal and 
     * CyclingPortal methods.
     * 
     * @param raceId The ID of the race being queried.
     * @return A sorted hashmap of all the rider IDs and their respective total race times
     *         from all the races, with the first in the list being the winner (least time).
     */
    private LinkedHashMap<Integer, LocalTime> getRiderTotalAdjustedTimeSorted(int raceId) {
        HashMap<Integer, LocalTime> riderTotalAdjustedTime = new HashMap<>();
        int[] raceStages = races.get(raceId).getStageIDs();
        int[] ridersInRace = races.get(raceId).getRiderIDs();
        for (int riderId : ridersInRace) {
            riderTotalAdjustedTime.put(riderId, LocalTime.of(0, 0, 0));
        }
        for (int stage : raceStages) {
            for (int rider : ridersInRace) {
                LocalTime stageTime = stages.get(stage).getRiderAdjustedTimes(rider);
                riderTotalAdjustedTime.put(rider, riderTotalAdjustedTime.get(rider).plusHours(stageTime.getHour()).plusMinutes(stageTime.getMinute()).plusSeconds(stageTime.getSecond()));
            }
        }
        LinkedHashMap<Integer, LocalTime> sortedMap = new LinkedHashMap<>();
        riderTotalAdjustedTime.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    /**
	 * Get the general classification rank of riders in a race.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A ranked list of riders' IDs sorted ascending by the sum of their
	 *         adjusted elapsed times in all stages of the race. That is, the first
	 *         in this list is the winner (least time). An empty list if there is no
	 *         result for any stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
    @Override
    public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
        if(!races.containsKey(raceId)){
            throw new IDNotRecognisedException("Race ID does not exist.");
        }
        else {
            LinkedHashMap<Integer, LocalTime> riderSortedTimes = getRiderTotalAdjustedTimeSorted(raceId);
            return riderSortedTimes.keySet().stream().mapToInt(i -> i).toArray();
        }
    }

    /**
	 * Get the general classification times of riders in a race.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any exceptions are
	 * thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A list of riders' times sorted by the sum of their adjusted elapsed
	 *         times in all stages of the race. An empty list if there is no result
	 *         for any stage in the race. These times should match the riders
	 *         returned by {@link #getRidersGeneralClassificationRank(int)}. Assume
	 *         the total elapsed time of a race (the sum of all of its stages) never
	 *         exceeds 24h and, therefore, can be represented by a LocalTime
	 *         variable. There is no need to check for this condition or raise any
	 *         exception.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
    @Override
    public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
        if(!races.containsKey(raceId)){
            throw new IDNotRecognisedException("Race ID does not exist.");
        }else {
            return getRiderTotalAdjustedTimeSorted(raceId).values().toArray(LocalTime[]::new);
        }
    }

    /**
	 * Get the overall points of riders in a race.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return An array of riders' points (i.e., the sum of their points in all stages
	 *         of the race), sorted by the total adjusted elapsed time. An empty array if
	 *         there is no result for any stage in the race. These points should
	 *         match the riders returned by {@link #getRidersGeneralClassificationRank(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
    @Override
    public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
        if(!races.containsKey(raceId)) {
            throw new IDNotRecognisedException("Race ID does not exist.");
        } else {
            int[] ridersInRace = races.get(raceId).getRiderIDs();
            int[] raceStages = races.get(raceId).getStageIDs();
            LinkedHashMap<Integer, LocalTime> riderSortedTimes = getRiderTotalAdjustedTimeSorted(raceId);
            LinkedHashMap<Integer, Integer> riderPoints = new LinkedHashMap<>();
            for(int rider:ridersInRace){
                riderPoints.put(rider, 0);
            }
            for(int stage:raceStages) {
                int[] ridersPoints = getRidersPointsInStage(stage);
                for(int rider:riderSortedTimes.keySet()){
                    riderPoints.put(rider, riderPoints.get(rider) + ridersPoints[rider]);

                }
            }
            return riderPoints.values().stream().mapToInt(i -> i).toArray();
        }
    }

    /**
	 * Get the overall mountain points of riders in a race.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return An array of riders' mountain points (i.e., the sum of their mountain
	 *         points in all stages of the race), sorted by the total adjusted elapsed time.
	 *         An empty array if there is no result for any stage in the race. These
	 *         points should match the riders returned by
	 *         {@link #getRidersGeneralClassificationRank(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
    @Override
    public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
        if(!races.containsKey(raceId)) {
            throw new IDNotRecognisedException("Race ID does not exist.");
        } else {
            int[] ridersInRace = races.get(raceId).getRiderIDs();
            int[] raceStages = races.get(raceId).getStageIDs();
            LinkedHashMap<Integer, LocalTime> riderSortedTimes = getRiderTotalAdjustedTimeSorted(raceId);
            LinkedHashMap<Integer, Integer> riderPoints = new LinkedHashMap<>();
            for(int rider:ridersInRace){
                riderPoints.put(rider, 0);
            }
            for(int stage:raceStages) {
                int[] ridersPoints = getRidersMountainPointsInStage(stage);
                for(int rider:riderSortedTimes.keySet()){
                    riderPoints.put(rider, riderPoints.get(rider) + ridersPoints[rider]);

                }
            }
            return riderPoints.values().stream().mapToInt(i -> i).toArray();
        }
    }

    /**
	 * Get the ranked list of riders based on the points classification in a race.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A ranked list of riders' IDs sorted descending by the sum of their
	 *         points in all stages of the race. That is, the first in this list is
	 *         the winner (more points). An empty list if there is no result for any
	 *         stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
    @Override
    public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
        if(!races.containsKey(raceId)){
            throw new IDNotRecognisedException("Race ID does not exist.");
        } else {
            int[] ridersInRace = races.get(raceId).getRiderIDs();
            int[] raceStages = races.get(raceId).getStageIDs();
            LinkedHashMap<Integer, LocalTime> riderSortedTimes = getRiderTotalAdjustedTimeSorted(raceId);
            LinkedHashMap<Integer, Integer> riderPoints = new LinkedHashMap<>();
            for(int rider:ridersInRace){
                riderPoints.put(rider, 0);
            }
            for(int stage:raceStages) {
                int[] ridersPoints = getRidersPointsInStage(stage);
                for(int rider:riderSortedTimes.keySet()){
                    riderPoints.put(rider, riderPoints.get(rider) + ridersPoints[rider]);

                }
            }
            LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();
            riderPoints.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
            return sortedMap.keySet().stream().mapToInt(i -> i).toArray();
        }
    }

    /**
	 * Get the ranked list of riders based on the mountain classification in a race.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A ranked list of riders' IDs sorted descending by the sum of their
	 *         mountain points in all stages of the race. That is, the first in this
	 *         list is the winner (more points). An empty list if there is no result
	 *         for any stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
    @Override
    public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
        if(!races.containsKey(raceId)){
            throw new IDNotRecognisedException("Race ID does not exist.");
        } else {
            int[] ridersInRace = races.get(raceId).getRiderIDs();
            int[] raceStages = races.get(raceId).getStageIDs();
            LinkedHashMap<Integer, LocalTime> riderSortedTimes = getRiderTotalAdjustedTimeSorted(raceId);
            LinkedHashMap<Integer, Integer> riderPoints = new LinkedHashMap<>();
            for(int rider:ridersInRace){
                riderPoints.put(rider, 0);
            }
            for(int stage:raceStages) {
                int[] ridersPoints = getRidersMountainPointsInStage(stage);
                for(int rider:riderSortedTimes.keySet()){
                    riderPoints.put(rider, riderPoints.get(rider) + ridersPoints[rider]);

                }
            }
            LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();
            riderPoints.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
            return sortedMap.keySet().stream().mapToInt(i -> i).toArray();
        }

    }
}

