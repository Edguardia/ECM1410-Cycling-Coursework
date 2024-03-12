package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CyclingPortalImpl implements MiniCyclingPortal {

    HashMap<Integer, Race> races = new HashMap<>();
    HashMap<Integer, Stage> stages = new HashMap<>();
    HashMap<Integer, Rider> riders = new HashMap<>();
    HashMap<Integer, Team> teams = new HashMap<>();


    @Override
    public int[] getRaceIds() {
        ArrayList<Integer> raceList = new ArrayList<Integer>();
        raceList.addAll((races.keySet()));
        return raceList.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        if(name == null || name.equals("") || name.length() > 30 || name.contains(" ")){
            throw new InvalidNameException();
        }
        for(Race i : races.values()){
            if(i.getRaceName().equals(name)){
                throw new IllegalNameException();
            }
        }
        Race newRace = new Race(name, description);
        races.put(newRace.getRaceID(), newRace);
        return newRace.getRaceID();
    }

    @Override
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
        if (races.containsKey(raceId)){
            return races.get(raceId).getRaceDescription();
        }
        else { throw new IDNotRecognisedException(); }
    }

    @Override
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        if (races.containsKey(raceId)){
            races.remove(raceId);
        }
        else{ throw new IDNotRecognisedException(); }
    }

    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        if (races.containsKey(raceId)){
            Race race = races.get(raceId);
            return race.getStageIDs().length;
        }
        else{
            throw new IDNotRecognisedException();
        }
    }

    @Override
    public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        if (!races.containsKey(raceId)){ throw new IDNotRecognisedException(); }
        for(Stage i : stages.values()){
            if(i.getStageName().equals(stageName)){
                throw new IllegalNameException();
            }
        }
        if(stageName == null || stageName.equals("") || stageName.length() > 30 || stageName.contains(" ")){ throw new InvalidNameException(); }
        if (length < 5){ throw new InvalidLengthException(); }
        Stage newStage = new Stage(stageName, description, length, startTime, type);
        Race race = races.get(raceId);
        race.addStage(newStage.getStageID());
        races.put(race.getRaceID(), race);
        stages.put(newStage.getStageID(), newStage); //Check this one as well
        return newStage.getStageID();
    }

    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        if (races.containsKey(raceId)){
            int[] listIds = races.get(raceId).getStageIDs();
            //Diogo wishes the arrays to be sorted, so decide if you want to manually sort the stage IDs or do it automatically
            Arrays.sort(listIds);
            return listIds;
        }
        else{ throw new IDNotRecognisedException("Race ID does not exist."); }
    }

    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        if (stages.containsKey(stageId)){
            return stages.get(stageId).getLength();
        }
        else{ throw new IDNotRecognisedException(); }
    }

    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        if (stages.containsKey(stageId)){
            int raceId = stages.get(stageId).getStageID(); //Remove stage's checkpoints and results
            races.get(raceId).deleteStage(stageId);
            races.remove(stageId);


            stages.remove(stageId);
        }
        else{ throw new IDNotRecognisedException(); }
    }

    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient, Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException();
        }
        else if (location < 0 || location > stages.get(stageId).getLength()){
            throw new InvalidLocationException();
        }
        else if (stages.get(stageId).getState().equals("waiting for results")){
            throw new InvalidStageStateException();
        }
        else if (stages.get(stageId).getStageType().toString().equals("TT")){
            throw new InvalidStageTypeException();
        }
        Checkpoint newCheckpoint = new Checkpoint(location, type, length, averageGradient);
        Stage stage = stages.get(stageId);
        stage.addCheckpoint(newCheckpoint.getCheckpointID());
        stages.put(stage.getStageID(), stage);
        checkpoints.put(newCheckpoint.getCheckpointID(), newCheckpoint);
        return newCheckpoint.getCheckpointID();
    }

    @Override
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException();
        }
        else if (location < 0 || location > stages.get(stageId).getLength()){
            throw new InvalidLocationException();
        }
        else if (stages.get(stageId).getState().equals("waiting for results")){
            throw new InvalidStageStateException();
        }
        else if (stages.get(stageId).getStageType().toString().equals("TT")){
            throw new InvalidStageTypeException();
        }
        Checkpoint newCheckpoint = new Checkpoint(location);
        Stage stage = stages.get(stageId);
        stage.addCheckpoint(newCheckpoint.getCheckpointID());
        stages.put(stage.getStageID(), stage);
        checkpoints.put(newCheckpoint.getCheckpointID(), newCheckpoint);
        return newCheckpoint.getCheckpointID();
    }

    @Override
    public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
        if (!checkpoints.containsKey(checkpointId)){
            throw new IDNotRecognisedException();
        }
        for(Stage i : stages.values()){
            ArrayList<Integer> checkpointIds = i.getCheckpointIDs(); //Need assistance
        }
    }

    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException();
        }
        else if (stages.get(stageId).getState().toString().equals("waiting for results")){
            throw new InvalidStageStateException();
        }
        Stage stage = stages.get(stageId);
        stage.setState("waiting for results");
        stages.put(stage.getStageID(), stage);
    }

    @Override
    public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException();
        }
        Stage stage = stages.get(stageId);
        int[] list = Arrays.sort(stage.getCheckpointIDs()); //Ideally, it would be best if we store the whole 'Checkpoint' class inside of Stage instead of just IDs
        return stage.getCheckpointIDs(); //Same problem with 2nd previous method
    }

    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        if(name == null || name.equals("") || name.length() > 30 || name.contains(" ")){
            throw new InvalidNameException();
        }
        for(Team i : teams.values()){
            if(i.getTeamName().equals(name)){
                throw new IllegalNameException();
            }
        }
        Team newTeam = new Team(name, description);
        teams.put(newTeam.getTeamID(), newTeam);
        return newTeam.getTeamID();
    }

    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        if (teams.containsKey(teamId)){
            teams.remove(teamId);
        }
        else{ throw new IDNotRecognisedException(); }
    }

    @Override
    public int[] getTeams() {
        ArrayList<Integer> teamlist = new ArrayList<Integer>();
        teamlist.addAll((teams.keySet()));
        return teamlist.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        if (teams.containsKey(teamId)){
            return teams.get(teamId).getRiders();
        }
        else{
            throw new IDNotRecognisedException();
        }
    }

    @Override
    public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        if (!teams.containsKey(teamID)){
            throw new IDNotRecognisedException();
        }
        else if(name == null || name.equals("") || yearOfBirth < 1900){
            throw new IllegalArgumentException();
        }
        Rider newRider = new Rider(teamID, name, yearOfBirth);
        riders.put(newRider.getRiderID(), newRider);
        teams.get(teamID).addRider(newRider.getRiderID());
        return newRider.getRiderID();
    }

    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        if (riders.containsKey(riderId)){
            int teamId = riders.get(riderId).getTeamID();
            teams.get(teamId).deleteRider(riderId);
            riders.remove(riderId); //Check again
        }
        else{
            throw new IDNotRecognisedException();
        }
    }

    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpointTimes) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException {

    }

    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        return new LocalTime[0];
    }

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        return null;
    }

    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {

    }

    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        return new int[0];
    }

    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        return new LocalTime[0];
    }

    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        return new int[0];
    }

    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        return new int[0];
    }

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

    @Override
    public void saveCyclingPortal(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))){
            out.writeObject(races);
            out.writeObject(stages);
            out.writeObject(checkpoints); //Might just save the values from the hashmap instead of the hashmaps themselves
            out.writeObject(riders);
            out.writeObject(teams);
            out.close();
        } catch (IOException ex) { throw new IOException();}
    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
            Object obj = in.readObject();
            if (obj instanceof Race){
                races = new HashMap<Integer, Race>();
                races = 
            }
            if (obj instanceof Stage){

            }
            if (obj instanceof Checkpoint){
                
            }
            if (obj instanceof Rider){
                
            }
            if (obj instanceof Team){
                
            }
            in.close();
        }
    }
}
