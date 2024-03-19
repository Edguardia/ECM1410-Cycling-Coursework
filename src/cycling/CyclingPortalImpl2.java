package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class CyclingPortalImpl2 implements CyclingPortal, Serializable {

    HashMap<Integer, Race> races = new HashMap<>();
    HashMap<Integer, Stage> stages = new HashMap<>();
    HashMap<Integer, Checkpoint> checkpoints = new HashMap<>();
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
        else{ throw new IDNotRecognisedException("Stage ID does not exist."); }
    }

    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        if (stages.containsKey(stageId)){
            int raceId = stages.get(stageId).getStageID(); //Remove stage's checkpoints and results
            races.get(raceId).deleteStage(stageId);
            races.remove(stageId);


            stages.remove(stageId);
        }
        else{ throw new IDNotRecognisedException("Stage ID does not exist."); }
    }

    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient, Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
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
        Checkpoint newCheckpoint = new Checkpoint(stageId, location, type, length, averageGradient);
        Stage stage = stages.get(stageId);
        stage.addCheckpointID(newCheckpoint.getCheckpointID());
        checkpoints.put(newCheckpoint.getCheckpointID(), newCheckpoint);
        stages.put(stage.getStageID(), stage);
        return newCheckpoint.getCheckpointID();
    }

    @Override
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
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
        Checkpoint newCheckpoint = new Checkpoint(stageId, location);
        Stage stage = stages.get(stageId);
        stage.addCheckpointID(newCheckpoint.getCheckpointID());
        checkpoints.put(newCheckpoint.getCheckpointID(), newCheckpoint);
        stages.put(stage.getStageID(), stage);
        return newCheckpoint.getCheckpointID();
    }

    @Override
    public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
        if (!checkpoints.containsKey(checkpointId)){
            throw new IDNotRecognisedException("Checkpoint ID was not recognised.");
        }
        Checkpoint oldcheckpoint = checkpoints.get(checkpointId);
        Stage stage = stages.get(oldcheckpoint.getStageID());
        stage.deleteCheckpointID(checkpointId);
        stages.put(stage.getStageID(), stage);

        // More stuff here

        checkpoints.remove(checkpointId);
    }

    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
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
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        int[] listIds = stages.get(stageId).getCheckpointIDs();
        Arrays.sort(listIds);
        return listIds;
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
        else{ throw new IDNotRecognisedException("Team ID does not exist."); }
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
            throw new IDNotRecognisedException("Team ID does not exist.");
        }
    }

    @Override
    public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        if (!teams.containsKey(teamID)){
            throw new IDNotRecognisedException("Team ID does not exist.");
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
            // More stuff here

        }
        else{
            throw new IDNotRecognisedException();
        }
    }

    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpointTimes) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        else if (!riders.containsKey(riderId)){
            throw new IDNotRecognisedException("Rider ID does not exist.");
        }
        else if (riders.get(riderId).getStageResults(stageId) != 0){
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
        riders.put(currentRider.getRiderID(), currentRider);
    }

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
        for (LocalTime time : currentRider.getCheckpointTimes(stageId)){
            riderTimes[i] = time;
            i += 1;
        }
        riderTimes[i] = currentRider.calculateRidersTotalElapsedTime(stageId);
        return riderTimes;
    }

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
            currentStage.calculateRidersAdjustedTime(riderId); //need to change
            return currentStage.getRiderAdjustedTimes(riderId);
        }
        return null;
    }

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

    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        Stage currentStage = stages.get(stageId);
        return currentStage.calculateRidersRankInStages();
    }

    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
        Stage currentStage = stages.get(stageId);
        return currentStage.calculateRankedAdjustedElapedTimesInStage();
    }

    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }

        return ;
    }

    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        if (!stages.containsKey(stageId)){
            throw new IDNotRecognisedException("Stage ID does not exist.");
        }
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
        ArrayList<Object> objList = new ArrayList<>();
        objList.add(this.races);
        objList.add(this.stages);
        objList.add(this.checkpoints);
        objList.add(this.riders);
        objList.add(this.teams);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))){

            out.writeObject(objList);
            out.close();
        } catch (IOException ex) { throw new IOException("File not recognised.");}
    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        ArrayList<Object> objList = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
            Object obj = in.readObject();
            objList = (ArrayList<Object>) in.readObject();
            this.races = (HashMap<Integer, Race>) objList.get(0);
            this.stages = (HashMap<Integer, Stage>) objList.get(1);
            this.checkpoints = (HashMap<Integer, Checkpoint>) objList.get(2);
            this.riders = (HashMap<Integer, Rider>) objList.get(3);
            this.teams = (HashMap<Integer, Team>) objList.get(4);
        } catch (IOException ex) { throw new IOException("File not recognised."); }
    }

    @Override
    public void removeRaceByName(String name) throws NameNotRecognisedException {
        ArrayList<Race> allRaces = new ArrayList<>();
        allRaces = (ArrayList<Race>) races.values();
        boolean nameExists = false;
        for (Race allRace : allRaces) {
            if (allRace.getRaceName().equals(name)) {
                nameExists = true;
                races.remove(allRace.getRaceID());
            }
            if(!nameExists){
                throw new NameNotRecognisedException("Name does not exist.");
            }
        }
    }

    private LinkedHashMap<Integer, LocalTime> getRiderTotalAdjustedTime(int raceId) {
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
    @Override
    public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
        if(!races.containsKey(raceId)){
            throw new IDNotRecognisedException("ID not recognised");
        }
        else {
            LinkedHashMap<Integer, LocalTime> riderSortedTimes = getRiderTotalAdjustedTime(raceId);

            return riderSortedTimes.keySet().stream().mapToInt(i -> i).toArray();
        }
    }


    @Override
    public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
        if(!races.containsKey(raceId)){
            throw new IDNotRecognisedException("ID not recognised");
        }else {
            return getRiderTotalAdjustedTime(raceId).values().toArray(LocalTime[]::new);
        }
    }

    @Override
    public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
        if(!races.containsKey(raceId)) {
            throw new IDNotRecognisedException("ID not recognised");
        } else {
            int[] ridersInRace = races.get(raceId).getRiderIDs();
            for(int rider:ridersInRace) {
                races.get(raceId).getStageIDs();


            }
            return new int[0];
        }
    }

    @Override
    public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
        return new int[0];
    }

    @Override
    public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
        return new int[0];
    }

    @Override
    public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
        return new int[0];
    }
}
