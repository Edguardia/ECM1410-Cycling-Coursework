
import cycling.*;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortal interface -- note you
 * will want to increase these checks, and run it on your CyclingPortalImpl class
 * (not the BadCyclingPortal class).
 *
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
		testCreateTeam(portal1);
		testRemoveTeam(portal1);

	}
		private static void testCreateTeam(CyclingPortalImpl portal1) {
			System.out.println("The system compiled and started the execution...");

			assert (portal1.getRaceIds().length == 0)
					: "Initial Portal not empty as required or not returning an empty array.";
			assert (portal1.getTeams().length == 0)
					: "Initial Portal not empty as required or not returning an empty array.";

			try {
				portal1.createTeam("", "Team1Desc");
			} catch (IllegalNameException e) {
				System.out.println("Unexpected Exception");
			} catch (InvalidNameException e) {
				System.out.println("Expected Exception");
			}
			try {
				portal1.createTeam("white space", "Team1Desc");
			} catch (IllegalNameException e) {
				System.out.println("Unexpected Exception");
			} catch (InvalidNameException e) {
				System.out.println("Expected Exception");
			}
			try {
				portal1.createTeam("GreaterThanThirtyCharacters123456789", "Team1Desc");
			} catch (IllegalNameException e) {
				System.out.println("Unexpected Exception");
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


		}
		private static void testRemoveTeam(CyclingPortalImpl portal1){
			try{
				portal1.removeTeam(100);
			} catch (IDNotRecognisedException e){
				System.out.println("Remove Team Expected Exception");
			}
			try{
				portal1.removeTeam(0);
			} catch (IDNotRecognisedException e){
				System.out.println("Remove Team Unexpected Exception");
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
		}

	}
