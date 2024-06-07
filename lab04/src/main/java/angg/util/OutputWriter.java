package angg.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputWriter {
    
    public void writeToTerminal(String text) {
        System.out.println(text);
    }    

    public void writeToFile(String text, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(text);
        } catch (IOException e) {
            System.out.println("There were problems with output to file - try again.");
            System.exit(0);
        }
    }
}
