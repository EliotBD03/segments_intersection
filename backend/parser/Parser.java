package parser;

import core.Segment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser
{
    private String path;

    private boolean isATxtFile()
    {
        return this.path.substring(this.path.length() - 3).equals("txt");
    }

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

    public Parser(String txtFilePath) throws IOException
    {
        File supposedFile = new File(txtFilePath);

        if(supposedFile.exists() && !supposedFile.isDirectory())
            this.path = txtFilePath;
        else
            throw new IOException("The file <"+ txtFilePath +"> does not exist");
    }


    public ArrayList<Segment> getSegmentsFromFile() throws IOException
    {
        checkIntegrityOfFile();
        ArrayList<Segment> result = new ArrayList<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(path)))
        {
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
        }
        return result;
    }
}
