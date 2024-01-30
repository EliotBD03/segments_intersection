import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser
{
    public static ArrayList<Segment> getSegmentsFromFile(String path)
    {
        ArrayList<Segment> result = new ArrayList<>();
        try
        {
            BufferedReader buffer = new BufferedReader(new FileReader(path));
            String line;
            while( (line = buffer.readLine()) != null)
            {
                Float[] parsedLine = Arrays.stream(line.split("\\s"))
                        .map(Float::parseFloat)
                        .toArray(Float[]::new);

                float[] coordinates = new float[parsedLine.length];
                for(int i = 0; i < parsedLine.length; i++)
                    coordinates[i] = parsedLine[i];

                result.add(new Segment(coordinates));
            }
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args)
    {
        ArrayList<Segment> segments = getSegmentsFromFile("/Users/julienladeuze/Desktop/bac3Stuff/segments_intersection/backend/cartes/fichier1.txt");
        for(Segment segment : segments)
        {
            System.out.println(segment);
        }
    }
}
