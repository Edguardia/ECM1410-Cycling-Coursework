package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CyclingPortalImpl implements MiniCyclingPortal {

    HashMap<Integer, Race> races = new HashMap<Integer, Race>();
    HashMap<Integer, Stage> stages = new HashMap<Integer, Stage>();
    HashMap<Integer, Checkpoint> checkpoints = new HashMap<Integer, Checkpoint>();
    HashMap<Integer, Rider> riders = new HashMap<Integer, Rider>();
    HashMap<Integer, Team> teams = new HashMap<Integer, Team>();


    @Override
    public int[] getRaceIds() {

        return new int[0];
    }

    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        races.
        return 0;
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
        return 0;
    }

    @Override
    public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        return 0;
    }

    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        return new int[0];
    }

    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        return 0;
    }

    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {

    }

    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient, Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        return 0;
    }

    @Override
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        return 0;
    }

    @Override
    public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {

    }

    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {

    }

    @Override
    public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
        return new int[0];
    }

    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        if(name == null || name == "" || name.length() > 30 || name.contains(" ")){
            throw new InvalidNameException();
        }
        for(Team i : teams.values()){
            if(i.getTeamName() == name){
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

        return new int[0];
    }

    @Override
    public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        if (!teams.containsKey(teamID)){
            throw new IDNotRecognisedException();
        }
        else if(name == null || name == "" || yearOfBirth < 1900){
            throw new IllegalArgumentException();
        }
        Rider newRider = new Rider(teamID, name, yearOfBirth);
        riders.put(newRider.getRiderID(), newRider);
        teams.get(teamID).add
        return newRider.getRiderID();
    }

    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {

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

    }

    @Override
    public void saveCyclingPortal(String filename) throws IOException {

    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {

    }
}
