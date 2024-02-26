package parser;

import core.Segment;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    String filePath;

    int equals(Segment s1, Segment s2)
    {
        return s1.getLowerPoint().compareTo(s2.getLowerPoint()) & s1.getUpperPoint().compareTo(s2.getUpperPoint());
    }

    @Test
    void checkTxtFile() throws URISyntaxException, IOException
    {
        filePath = Parser.getPathFromResource("/test_parser/test0.py");
        Parser parser = new Parser(filePath);
        assertThrows(IOException.class, parser::getSegmentsFromFile);
    }

    @Test
    void checkFileFormatContent() throws URISyntaxException, IOException
    {
        filePath = Parser.getPathFromResource("/test_parser/test1.txt");
        Parser parser = new Parser(filePath);
        assertThrows(IOException.class, parser::getSegmentsFromFile);
    }

    @Test
    void getSegmentsFromFile() throws URISyntaxException, IOException
    {
        ArrayList<Segment> references = new ArrayList<>()
        {
            {
                add(new Segment(new double[]{0, 0, 5,0}, "s_1"));
                add(new Segment(new double[]{1, 2, 1, -1}, "s_2"));
            }
        };
        AtomicReference<ArrayList<Segment>> tested = new AtomicReference<>(new ArrayList<>());

        filePath = Parser.getPathFromResource("/test_parser/test2.txt");
        Parser parser = new Parser(filePath);

        assertDoesNotThrow(() ->
        {
            tested.set(parser.getSegmentsFromFile());
        });

        for(int i = 0; i < references.size(); i++)
        {
            assertEquals(0, equals(references.get(i), tested.get().get(i)));
        }
    }

}