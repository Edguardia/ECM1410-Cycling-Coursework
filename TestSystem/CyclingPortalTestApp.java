import cycling.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;


/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortal interface -- note you
 * will want to increase these checks, and run it on your CyclingPortalImpl class
 * (not the BadCyclingPortal class).
 *
 * @author Diogo Pacheco
 * @version 2.0
 */
public class CyclingPortalTestApp {


    /**
     * Test method.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        //CyclingPortalImpl2 portal1 = initialisePortals();
        CyclingPortalImpl portal1 = new CyclingPortalImpl();


        System.out.println("The system compiled and started the execution...");

        assert (portal1.getRaceIds().length == 0)
                : "Initial Portal not empty as required or not returning an empty array.";
        assert (portal1.getTeams().length == 0)
                : "Initial Portal not empty as required or not returning an empty array.";

        assert (portal1.getRaceIds().length == 0)
                : "getRaceIds() not returning an empty array";
        assert (portal1.getTeams().length == 0)
                : "getTeams() not returning an empty array";


        try {
            portal1.createTeam("", "Team1Desc");
        } catch (IllegalNameException e) {
            throw new RuntimeException(e);
        } catch (InvalidNameException e) {
            System.out.println("Expected Exception");
        }
        try {
            portal1.createTeam("white space", "Team1Desc");
        } catch (IllegalNameException e) {
            throw new RuntimeException(e);
        } catch (InvalidNameException e) {
            System.out.println("Expected Exception");
        }
        try {
            portal1.createTeam("GreaterThanThirtyCharacters123456789", "Team1Desc");
        } catch (IllegalNameException e) {
            throw new RuntimeException(e);
        } catch (InvalidNameException e) {
            System.out.println("Expected Exception");
        }
        try {
            portal1.createTeam("Team1", "Rider1");
        } catch (IllegalNameException | InvalidNameException e) {
            System.out.println("Unexpected Exception");
        }
        try {
            portal1.createTeam("Team1", "Rider1");
        } catch (IllegalNameException e) {
            System.out.println("Expected Exception");
        } catch (InvalidNameException e) {
            System.out.println("Unexpected Exception");
        }
        assert (portal1.getTeams().length == 1)
                : "Team not created as required or not returning an array with one element.";


        try {
            portal1.removeTeam(100);
        } catch (IDNotRecognisedException e) {
            System.out.println("Remove Team Expected Exception");
        }
        try {
            portal1.removeTeam(0);
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }
        assert (portal1.getTeams().length == 0)
                : "Team not removed as required or not returning an empty array.";
        try {
            portal1.createTeam("Team1", "Team1Desc");
        } catch (IllegalNameException | InvalidNameException e) {
            throw new RuntimeException(e);
        }

        assert (portal1.getTeams().length == 1)
                : "Team not created as required or not returning an array with one element.";


        try {
            portal1.createRider(1, "Team1Rider1", 1990);
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }


        try {
            assert (portal1.getTeamRiders(portal1.getTeams()[0]).length == 1)
                    : "Rider not created as required.";
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }

        try {
            portal1.removeRider(0);
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }

        try {
            assert (portal1.getTeamRiders(portal1.getTeams()[0]).length == 0)
                    : "Rider not deleted as required.";
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }

        try {
            portal1.createRider(1, "Team1Rider1", 1990);
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }


		try {
			portal1.createRace("Race1", "Race1Desc");
		} catch (IllegalNameException | InvalidNameException e) {
			throw new RuntimeException(e);
		}
		assert (portal1.getRaceIds().length == 1)
				: "Race not created as required.";

		try{portal1.removeRaceById(0);
		} catch (IDNotRecognisedException e) {
			throw new RuntimeException(e);}
		assert (portal1.getRaceIds().length==0)
				: "Race not deleted as required";

		try {portal1.createRace("Race1", "Race1Desc");
		}catch (IllegalNameException | InvalidNameException e) {
			throw new RuntimeException(e);
		}

		try{portal1.removeRaceByName("Race1");}
		catch (NameNotRecognisedException e) {
            throw new RuntimeException(e);
        }

		assert (portal1.getRaceIds().length==0)
				:"Race not deleted as required";

		try {portal1.createRace("Race1", "Race1Desc");
		}catch (IllegalNameException | InvalidNameException e) {
			throw new RuntimeException(e);
		}


        try {
            assert (portal1.getRaceStages(portal1.getRaceIds()[0]).length == 0)
                    : "Number of stages not as expected";
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }


        try{
            assert(portal1.getNumberOfStages(portal1.getRaceIds()[0])==0)
                    :"Number of stages not as expected";
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }

        try{
            portal1.addStageToRace(portal1.getRaceIds()[0], "Stage1", "Stage1Desc", 20, LocalDateTime.of(2021, 12, 12, 12, 12), StageType.FLAT);
            portal1.addStageToRace(portal1.getRaceIds()[0], "Stage2", "Stage2Desc", 10, LocalDateTime.of(2021, 11, 12, 12, 12), StageType.HIGH_MOUNTAIN);
            portal1.addStageToRace(portal1.getRaceIds()[0], "Stage3", "Stage3Desc", 10, LocalDateTime.of(2021, 10, 12, 12, 12), StageType.MEDIUM_MOUNTAIN);
        } catch (IDNotRecognisedException | IllegalNameException | InvalidNameException | InvalidLengthException e) {
            throw new RuntimeException(e);
        }

        try{
            assert(portal1.getNumberOfStages(portal1.getRaceIds()[0])==3)
                    :"Number of stages not as expected";
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(portal1.viewRaceDetails(portal1.getRaceIds()[0]));
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }

        try{
            System.out.println(portal1.getStageLength(0));
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }

        try {
            assert (portal1.getRaceStages(portal1.getRaceIds()[0]).length == 3)
                    : "Number of stages not as expected";
            System.out.println(Arrays.toString(portal1.getRaceStages(portal1.getRaceIds()[0])));
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }

        try{
            portal1.removeStageById(1);
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }

        try {
            assert (portal1.getRaceStages(portal1.getRaceIds()[0]).length == 2)
                    : "Number of stages not as expected";
            System.out.println(Arrays.toString(portal1.getRaceStages(portal1.getRaceIds()[0])));
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }



        try{portal1.addCategorizedClimbToStage(0, 3.0, CheckpointType.C1, 5.0, 10.0);
        } catch (IDNotRecognisedException | InvalidLocationException |
                 InvalidStageStateException | InvalidStageTypeException e) {
            throw new RuntimeException(e);
        }

        try{
            assert(portal1.getStageCheckpoints(portal1.getRaceStages(portal1.getRaceIds()[0])[0]).length==1)
                    :"Number of checkpoints not as expected";
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }


        try{
            portal1.removeCheckpoint(0);
        } catch (InvalidStageStateException | IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }

        try{
            assert(portal1.getStageCheckpoints(portal1.getRaceStages(portal1.getRaceIds()[0])[0]).length==0)
                    :"Number of checkpoints not as expected";
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }

        try{
            portal1.addIntermediateSprintToStage(0, 3.0);
            portal1.addCategorizedClimbToStage(0, 5.0, CheckpointType.C1, 5.0, 10.0);
        } catch (InvalidStageStateException | InvalidLocationException | IDNotRecognisedException |
                 InvalidStageTypeException e) {
            throw new RuntimeException(e);
        }

        try{
            assert(portal1.getStageCheckpoints(portal1.getRaceStages(portal1.getRaceIds()[0])[0]).length==2)
                    :"Number of checkpoints not as expected";
        } catch (IDNotRecognisedException e) {
            throw new RuntimeException(e);
        }

        try{portal1.concludeStagePreparation(0);
        } catch (IDNotRecognisedException | InvalidStageStateException e) {
            throw new RuntimeException(e);
        }

        try{
            System.out.println(portal1.getTeamRiders(portal1.getTeams()[0])[0]);
            LocalTime[] times = {LocalTime.of(12, 0), LocalTime.of(12, 5), LocalTime.of(12, 8), LocalTime.of(12,30)};
            portal1.registerRiderResultsInStage(0,1, times);
        } catch (InvalidStageStateException | DuplicatedResultException | IDNotRecognisedException |
                 InvalidCheckpointTimesException e) {
            throw new RuntimeException(e);
        }
    }





    }


