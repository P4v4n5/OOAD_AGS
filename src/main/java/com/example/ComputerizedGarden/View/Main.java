package com.example.ComputerizedGarden.View;


import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import javafx.application.Application;


public class Main {
    public static void main(String[] args) {
        String logFile = "OOAD_ComputerizedGarden_Logs.txt";
        try {
            Files.newBufferedWriter(Paths.get(logFile)).close();
            System.out.println("Welcome to Prof. Navid's Garden!! :) \n");

            System.out.println("Points to remember before you simulate the garden:\n" +
                    "   - Select the plants you want to cultivate.\n" +
                    "   - Press \"Start Simulation\" to begin.\n" +
                    "   - Observe how the simulation progresses.\n" +
                    "   - Pause and view logs by pressing \"Show Logs\".\n" +
                    "   - Check garden metrics and the weather forecast on the left side of the screen.\n" +
                    "   - Happy Gardening! \n");

            System.out.println("We are clearing the content of old log file for you!!!");

        } catch (IOException exception) {
            System.err.println("An unexpected error occurred while clearing the old log file: " + exception.getMessage() + " Please try running again");
        }
        initiateApplication(args);
    }

    private static void initiateApplication(String[] args) {
        Application.launch(UserInterface.class, args);
    }
}
