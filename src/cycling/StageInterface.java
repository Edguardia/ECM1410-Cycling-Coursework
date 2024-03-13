package cycling;

import java.time.LocalTime;

public interface StageInterface {
    
    int[] calculateRidersRankInStages();

    LocalTime[] calculateRankedAdjustedElapedTimesInStage();

    int[] calculateRidersPointsInStage();

    int[] calculateRidersMountainPointsInStage();
}
