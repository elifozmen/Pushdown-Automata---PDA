import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter input file name: ");
        String filePath = scanner.nextLine();

        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (!lines.isEmpty()) {
            String firstLine = lines.get(0).trim();
            String secondLine = lines.get(1).trim();
            String thirdLine = lines.get(2).trim();
            String fourthLine = lines.get(3).trim();
            String fifthLine = lines.get(4);
            String sixthLine = lines.get(5);
            String seventhLine = lines.get(6);
            String eighthLine = lines.get(7);
            String ninethLine = lines.get(8);
            String tenthLine = lines.get(9);

            try {
                int numberOfInputAlphabet = Integer.parseInt(firstLine);
                int numberOfStackAlphabet = Integer.parseInt(secondLine);
                int numberOfGoalStates = Integer.parseInt(thirdLine);
                int numberOfStates = Integer.parseInt(fourthLine);

                ArrayList<String> statesList = new ArrayList<>(Arrays.asList(fifthLine.split(" ")));
                ArrayList<String> startState = new ArrayList<>(Arrays.asList(sixthLine.split(" ")));
                ArrayList<String> finalStates = new ArrayList<>(Arrays.asList(seventhLine.split(" ")));
                ArrayList<String> stackAlphabet = new ArrayList<>(Arrays.asList(eighthLine.split(" ")));
                ArrayList<String> initialStackSymbol = new ArrayList<>(Arrays.asList(ninethLine.split(" ")));
                ArrayList<String> inputAlphabet = new ArrayList<>(Arrays.asList(tenthLine.split(" ")));

                int transitionLineCount = 5;

                ArrayList<String> transitionLines = new ArrayList<>();
                for (int i = 10; i < 10 + transitionLineCount && i < lines.size(); i++) {
                    String transitionLine = lines.get(i).trim();
                    transitionLines.add(transitionLine);
                }

                System.out.print("Enter output file name: ");
                String outputFileName = scanner.nextLine();

                PDA pda = new PDA(numberOfInputAlphabet, numberOfStackAlphabet,
                    numberOfGoalStates, numberOfStates,
                    statesList, startState, finalStates, stackAlphabet,
                    initialStackSymbol, inputAlphabet, transitionLines);

                ArrayList<String> inputLines = new ArrayList<>();
                int startLine = transitionLineCount + 10;
                for (int i = startLine; i < lines.size(); i++) {
                    String inputLine = lines.get(i).trim();
                    inputLines.add(inputLine);
                }

                //call PDA with inputLines and outputFileName
                pda.simulatePDA(inputLines, outputFileName);

            } catch (NumberFormatException e) {
                System.err.println("error");
                e.printStackTrace();
            }

        }
    }
}