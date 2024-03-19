package cycling;

import java.time.LocalTime;

public interface StageInterface {
    
    LocalTime calculateRidersAdjustedTime(int riderId);

    int[] calculateRidersRankInStages();

    LocalTime[] getRankedAdjustedElapedTimesInStage();
}
