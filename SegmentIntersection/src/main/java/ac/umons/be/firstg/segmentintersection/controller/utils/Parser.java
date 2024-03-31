package ac.umons.be.firstg.segmentintersection.controller.utils;

import ac.umons.be.firstg.segmentintersection.model.Segment;
import ac.umons.be.firstg.segmentintersection.model.StatusQueue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * used to extract Segment from a txt file.
 */
public class Parser
{
    private String path;

    /**
     * Verify if the file is a txt file.
     * @return true if a txt file, false otherwise.
     */
    private boolean isATxtFile()
    {
        return this.path.substring(this.path.length() - 3).equals("txt");
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
            try(BufferedReader buffer = new BufferedReader(new FileReader(path)))
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
            throw new IOException("The file <"+path+"> is not a txt file");
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
            this.path = txtFilePath;
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

        try(BufferedReader buffer = new BufferedReader(new FileReader(path)))
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
}
