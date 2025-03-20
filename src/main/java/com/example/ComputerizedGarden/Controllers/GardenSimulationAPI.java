package com.example.ComputerizedGarden.Controllers;

import com.example.ComputerizedGarden.Model.Carrot;
import com.example.ComputerizedGarden.Model.Pineapple;
import com.example.ComputerizedGarden.Model.Corn;
import com.example.ComputerizedGarden.Model.Plant;
import com.example.ComputerizedGarden.Model.Watermelon;
import com.example.ComputerizedGarden.View.Logger;

import java.util.*;

/**
 * GardenSimulationAPI provides an interface for automated testing of the garden simulation system.
 * It exposes methods to initialize the garden, retrieve plant information, and simulate environmental conditions.
 */
public class GardenSimulationAPI {
    private static final Map<String, Plant> plants = new HashMap<>();
    private static final Map<String, List<String>> pestVulnerabilities = new HashMap<>();
    private static final Logger logger = new Logger();
    private static final Random random = new Random();
    private static int currentTemperature = 75; // Default temperature in Fahrenheit
    private static int dayCount = 0;

    /**
     * Initializes the garden with a predefined set of plants.
     * This method marks the beginning of the simulation clock.
     */
    public static void initializeGarden() {
        logger.addDayLogEntry("Initializing garden - Day 0 begins");

        // Creating predefined plants for simulation
        addPlant("Corn", 5, Arrays.asList("Spider", "Caterpillar"));
        addPlant("Pineapple", 8, Collections.singletonList("Caterpillar"));
        addPlant("Watermelon", 4, Arrays.asList("Spider"));
        addPlant("Carrot", 8, Arrays.asList("Spider"));

        logger.addDayLogEntry("Garden initialized with " + plants.size() + " plants.");
    }

    /**
     * Retrieves plant information including names, water requirements, and pest vulnerabilities.
     */
    public static Map<String, Object> getPlants() {
        Map<String, Object> plantInfo = new HashMap<>();
        List<String> plantNames = new ArrayList<>();
        List<Integer> waterRequirements = new ArrayList<>();
        List<List<String>> parasiteList = new ArrayList<>();

        for (Plant plant : plants.values()) {
            plantNames.add(plant.getName());
            waterRequirements.add(plant.getCurrentWater());
            parasiteList.add(pestVulnerabilities.getOrDefault(plant.getName(), new ArrayList<>()));
        }

        plantInfo.put("plants", plantNames);
        plantInfo.put("waterRequirement", waterRequirements);
        plantInfo.put("parasites", parasiteList);

        logger.addDayLogEntry("Retrieved plant information.");
        return plantInfo;
    }

    /**
     * Simulates rainfall in the garden by adjusting water levels for all plants.
     */
    public static void rain(int amount) {
        logger.addDayLogEntry("Rainfall event: " + amount + " units");

        for (Plant plant : plants.values()) {
            plant.setCurrentWater(plant.getCurrentWater() + amount);
            logger.addWateringLogEntry("Rain added water to " + plant.getName() + ". Current water level: " + plant.getCurrentWater());
        }

        dayCount++;
    }

    /**
     * Simulates temperature changes in the garden.
     */
    public static void temperature(int temp) {
        logger.addDayLogEntry("Temperature changed to " + temp + "°F");
        currentTemperature = temp;

        for (Plant plant : plants.values()) {
            plant.adjustLifespanForWeather(temp > 100 ? "Sunny" : temp < 45 ? "Cold" : "Moderate");
            logger.addHeatingLogEntry(plant.getName() + " temperature adjusted.");
        }

        dayCount++;
    }

    /**
     * Triggers a parasite infestation based on plant vulnerabilities.
     */
    public static void parasite(String parasiteType) {
        logger.addInsectLogEntry("Parasite infestation: " + parasiteType);

        for (Plant plant : plants.values()) {
            if (pestVulnerabilities.getOrDefault(plant.getName(), new ArrayList<>()).contains(parasiteType)) {
                plant.incrementPestAttacks();
                logger.addInsectLogEntry(plant.getName() + " attacked by " + parasiteType);
            }
        }

        dayCount++;
    }

    /**
     * Logs details about the garden's current state, including plant health and status.
     */
    public static void getState() {
        logger.addDayLogEntry("Garden State Report - Day " + dayCount);
        int alive = 0, dead = 0;

        for (Plant plant : plants.values()) {
            if (plant.isDead()) {
                dead++;
            } else {
                alive++;
            }
        }

        logger.addDayLogEntry("Alive: " + alive + ", Dead: " + dead);
    }

    /**
     * Helper method to create and add a plant to the system.
     */
    private static void addPlant(String name, int waterRequirement, List<String> parasites) {
        Plant plant;

        switch (name.toLowerCase()) {
            case "carrot":
                plant = new Carrot(1, 5); // Use appropriate row, col values
                break;
            case "corn":
                plant = new Corn(7, 5);
                break;
            case "pineapple":
                plant = new Pineapple(3, 3);
                break;
            case "watermelon":
                plant = new Watermelon(4, 2);
                break;
            default:
                throw new IllegalArgumentException("Unknown plant type: " + name);
        }

        plants.put(name, plant);
        pestVulnerabilities.put(name, parasites);
        logger.addDayLogEntry("Added plant: " + name);
    }


    /**
     * Initializes predefined pest vulnerabilities for plants.
     */
    static {
        pestVulnerabilities.put("Corn", Arrays.asList("Spider", "Caterpillar"));
        pestVulnerabilities.put("Pineapple", Arrays.asList("Caterpillar"));
        pestVulnerabilities.put("Watermelon", Arrays.asList("Spider"));
        pestVulnerabilities.put("Carrot", Arrays.asList("Spider"));
    }
}
