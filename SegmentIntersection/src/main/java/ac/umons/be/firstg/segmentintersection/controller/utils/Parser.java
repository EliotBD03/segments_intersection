package ac.umons.be.firstg.segmentintersection.controller.utils;

import ac.umons.be.firstg.segmentintersection.model.Segment;
import ac.umons.be.firstg.segmentintersection.model.StatusQueue;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * used to extract Segment from a txt file.
 */
public class Parser
{
    private String inputPath;

    /**
     * Verify if the file is a txt file.
     * @return true if a txt file, false otherwise.
     */
    private boolean isATxtFile()
    {
        return this.inputPath.substring(this.inputPath.length() - 3).equals("txt");
    }

    /**
     * Verify if the current line of a file contains exactly four floats.
     * @param line the line of the file
     * @return true if the condition respected, false otherwise.
     */
    private boolean lineContainsFourFloats(String line)
    {
        String[] splitLine = line.split("\\s");
        boolean isCorrect = true;
        for(String element : splitLine)
        {
            isCorrect &= element.matches("[-+]?[0-9]*\\.?[0-9]+");
        }
        return isCorrect;
    }

    /**
     * Verify if all the needed conditions are respected in the current file.
     * - the file is a txt file
     * - the file contains exactly four floats at each line.
     * @throws IOException if one these conditions is not respected.
     */
    private void checkIntegrityOfFile() throws  IOException
    {
        if(isATxtFile())
        {
            try(BufferedReader buffer = new BufferedReader(new FileReader(inputPath)))
            {
                String line;
                int line_counter = 1;
                while( (line = buffer.readLine())  != null)
                {
                    if(!lineContainsFourFloats(line))
                        throw new IOException("The content of file does not math with the following conditions:" +
                                "each line contains exactly 4 floating points at line "+ line_counter);
                    line_counter++;
                }
            }
        }
        else
            throw new IOException("The file <"+ inputPath +"> is not a txt file");
    }

    /**
     * Default constructor of the Parser object
     *
     * @param txtFilePath the file path
     * @throws IOException if the file does not exist.
     */
    public Parser(String txtFilePath) throws IOException
    {
        File supposedFile = new File(txtFilePath);

        if(supposedFile.exists() && !supposedFile.isDirectory())
            this.inputPath = txtFilePath;
        else
            throw new IOException("The file <"+ txtFilePath +"> does not exist");
    }


    /**
     * Extract all the segment from the file.
     * @return an arraylist of segments.
     * @throws IOException if the file is not ok with the conditions listed above
     * or in the case of lecture error.
     */
    public ArrayList<Segment> getSegmentsFromFile() throws IOException
    {
        checkIntegrityOfFile();
        ArrayList<Segment> result = new ArrayList<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(inputPath)))
        {
            String line;
            int id = 1;
            while( (line = buffer.readLine()) != null)
            {
                Double[] parsedLine = Arrays.stream(line.split("\\s"))
                        .map(Double::parseDouble)
                        .toArray(Double[]::new);

                double[] coordinates = new double[parsedLine.length];
                for(int i = 0; i < parsedLine.length; i++)
                    coordinates[i] = parsedLine[i];

                result.add(new Segment(coordinates, "s_" + id));
                id ++;
            }
        }
        return result;
    }

    public static String getPathFromResource(String filePathFromResource) throws URISyntaxException
    {
        return Paths.get(StatusQueue.class.getResource(filePathFromResource).toURI()).toString();
    }

    public static void saveSegments(ArrayList<Segment> segments, String outputFilePath)
    {
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(outputFilePath)))
        {
            for(Segment segment : segments)
            {
                buffer.write(String.format("%.2f", segment.getUpperPoint().x) + " "
                        + String.format("%.2f", segment.getUpperPoint().y) + " "
                        + String.format("%.2f", segment.getLowerPoint().x) + " "
                        + String.format("%.2f", segment.getLowerPoint().y) + "\n");
            }
        } catch (IOException e)
        {
            System.out.println("cannot write in the file" + outputFilePath);
            throw new RuntimeException();
        }
    }
}
