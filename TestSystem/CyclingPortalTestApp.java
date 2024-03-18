
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidNameException;

import cycling.CyclingPortalImpl;

import java.util.Arrays;

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
		CyclingPortalImpl portal1 = initialisePortals();
		System.out.println("The system compiled and started the execution...");







		assert (portal1.getTeams().length == 10)
				: "Portal1 should have ten team.";





		System.out.println(Arrays.toString(portal1.getTeams()));
		System.out.println(Arrays.toString(portal1.getRaceIds()));

	}



	public static CyclingPortalImpl initialisePortals() {
		CyclingPortalImpl portal = new CyclingPortalImpl();

		assert (portal.getRaceIds().length == 0)
				: "Initial Portal not empty as required or not returning an empty array.";
		assert (portal.getTeams().length == 0)
				: "Initial Portal not empty as required or not returning an empty array.";

		for (int i = 0; i < 10; i++) {
			String teamName = "Team" + i;
			String teamDesc = "Team" + i + "Desc";
			try {
				portal.createTeam(teamName, teamDesc);
			} catch (IllegalNameException e) {
				e.printStackTrace();
			} catch (InvalidNameException e) {
				e.printStackTrace();
			}

			for (i = 0; i < portal.getTeams().length; i++) {
				for (int j = 0; j < 10; j++) {

					try {
						portal.createRider(j, "Rider" + j, 1990 + j);
					} catch (IDNotRecognisedException e) {
						throw new RuntimeException(e);
					}


				}

			}

		}
		return portal;
	}

}