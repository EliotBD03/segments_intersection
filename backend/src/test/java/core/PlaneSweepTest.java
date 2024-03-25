package core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static core.AVLTestTools.*;




import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Plane Sweep Test \uD83D\uDCC8")
class PlaneSweepTest
{

    @Test
    void getIntersections() throws Exception
    {
        ArrayList<Segment> segments = new ArrayList<>(List.of(s1, s2, s3, s4, s5));
        PlaneSweep planeSweep = new PlaneSweep(segments);
        planeSweep.getIntersections();

    }
}