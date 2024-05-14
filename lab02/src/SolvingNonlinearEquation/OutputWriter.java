package SolvingNonlinearEquation;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class OutputWriter {

    public void output(String text) {
        InputReader inputReader = new InputReader();
        System.out.println("""
                Select how to display the result:
                    1) Terminal
                    2) File
                """);
        int outputTypeIndex = inputReader.readIndex("Enter method number: ", "There is no method with this number.", 2);
        switch (outputTypeIndex) {
            case 0:
                System.out.println(text);
                break;
            case 1:
                while (true) {
                    String filename = inputReader.readFilename("Enter file name: ");
                    File file = new File(filename);
                    try (Writer writer = new FileWriter(file)) {
                        writer.write(text);
                        return;
                    } catch (Exception e) {
                        System.out.println("An error occurred during output. Try again.");
                    }
                }
        }
    }


}
