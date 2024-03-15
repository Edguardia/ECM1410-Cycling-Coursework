package cycling;

import java.time.LocalTime;

public interface RiderInterface {
    
    LocalTime calculateRidersTotalElapsedTime(int stageId);

    int calculateAdjustedElapsedTime();
    
}
