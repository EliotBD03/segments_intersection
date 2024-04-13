package ac.umons.be.firstg.segmentintersection.controller.utils;

import ac.umons.be.firstg.segmentintersection.model.Segment;
import ac.umons.be.firstg.segmentintersection.model.StatusQueue;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

public class ParserTest
{
    @Test
    void saveSegments() throws URISyntaxException
    {
        ArrayList<Segment> segments = new ArrayList<>()
        {
            {
                add(new Segment(new double[]{0, 0, 1, 1}, "s1"));
                add(new Segment(new double[]{0, 1, 1, 0}, "s2"));
            }
        };
        //String path = "path/to/your/resource/folder"; //TODO if you want to make this work please replace the string :)
        //assertDoesNotThrow(() -> Parser.saveSegments(segments, (path)));

    }
}
