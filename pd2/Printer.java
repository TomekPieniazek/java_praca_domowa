package z2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Printer {
    private List<Integer> queue = new ArrayList<>();
    private List<String> commands = new ArrayList<>();

    public void start(String inputFilePath, String outputFilePath) {
        try {
            readCommandsFromFile(inputFilePath);
            processCommands(outputFilePath);
        } catch (FileNotFoundException e) {
            handleFileNotFoundError(outputFilePath);
        } catch (IOException e) {
            handleIOException();
        }
    }

    private void readCommandsFromFile(String inputFilePath) throws FileNotFoundException {
        File inputFile = new File(inputFilePath);
        Scanner scanner = new Scanner(inputFile);

        while (scanner.hasNext()) {
            commands.add(scanner.nextLine());
        }
    }

    private void processCommands(String outputFilePath) throws IOException {
        FileWriter writer = new FileWriter(outputFilePath);

        for (String command : commands) {
            switch (command) {
                case "drukuj":
                    printLastElement(writer);
                    break;
                case "koniec":
                    finishPrinting(writer);
                    break;
                default:
                    addToQueueAndSort(Integer.parseInt(command));
            }
        }

        writer.close();
    }

    private void printLastElement(FileWriter writer) throws IOException {
        if (queue.isEmpty()) {
            writer.write("none\n");
            return;
        }

        int lastElement = queue.remove(queue.size() - 1);
        writer.write(lastElement + "\n");
    }

    private void finishPrinting(FileWriter writer) throws IOException {
        for (int i = queue.size() - 1; i >= 0; i--) {
            writer.write(queue.get(i) + "\n");
        }
    }

    private void addToQueueAndSort(int number) {
        queue.add(number);
        Collections.sort(queue);
    }

    private void handleFileNotFoundError(String outputFilePath) {
        try {
            FileWriter writer = new FileWriter(new File(outputFilePath));
            writer.write("File not found");
            writer.close();
        } catch (IOException e) {
            handleIOException();
        }
    }

    private void handleIOException() {
        System.err.println("An error occurred while processing the file.");
    }
}
