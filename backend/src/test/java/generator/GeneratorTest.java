package generator;

import core.Segment;
import generator.Range;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static core.AVLTestTools.*;
import static org.junit.jupiter.api.Assertions.*;
public class GeneratorTest
{


    @Test
    public void generate()
    {
        Generator generator = new Generator(100);
        ArrayList<Segment> segments = generator.generate();
        for(Segment segment : segments)
        {
            assertTrue(segment.getLowerPoint().x >= generator.getRange().getLowerBound().getItem1());
            assertTrue(segment.getLowerPoint().y >= generator.getRange().getLowerBound().getItem2());
            assertTrue(segment.getUpperPoint().x <= generator.getRange().getUpperBound().getItem1());
            assertTrue(segment.getUpperPoint().y <= generator.getRange().getUpperBound().getItem2());
            System.out.println(segment.getLowerPoint());
            System.out.println(segment.getUpperPoint());
        }
    }


}
