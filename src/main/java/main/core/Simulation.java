package main.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;

import main.output.*;

import main.environment.animal.Animal;
import main.environment.soil.*;
import main.environment.air.*;
import main.environment.water.Water;
import main.environment.plant.Plant;

import java.util.*;
import java.util.stream.Collectors;

public class Simulation {
    private TerraBot robot;
    private Terrain terrain;
    private InputLoader inputLoader;
    private boolean simulationStarted = false;
    private int currentTimestamp = 0;
    private int simulationIndex = 0;

    private final ObjectMapper mapper;
    private final Map<String, CommandExecutor> commandMap = new HashMap<>();

    public Simulation(ObjectMapper mapper, InputLoader inputLoader) {
        this.mapper = mapper;
        this.inputLoader = inputLoader;

        initializeCommandMap();
    }

    private void initializeCommandMap() {
        commandMap.put("startSimulation", this::executeStartSimulation);
        commandMap.put("endSimulation", this::executeEndSimulation);
        commandMap.put("changeWeatherConditions", this::executeChangeWeather);
        commandMap.put("moveRobot", this::executeMoveRobot);
        commandMap.put("rechargeBattery", this::executeRechargeBattery);
        commandMap.put("scanObject", this::executeScanObject);
        commandMap.put("learnFact", this::executeLearnFact);
        commandMap.put("improveEnvironment", this::executeImproveEnvironment);
        commandMap.put("getEnergyStatus", this::executeGetEnergyStatus);
        commandMap.put("printEnvConditions", this::executePrintEnvConditions);
        commandMap.put("printMap", this::executePrintMap);
        commandMap.put("printKnowledgeBase", this::executePrintKnowledgeBase);
    }

    public void run(ArrayNode output) {
        List<CommandInput> commands = inputLoader.getCommands().stream()
                .sorted(Comparator.comparingInt(c -> c.getTimestamp()))
                .toList();

        for(CommandInput command : commands) {
            while(currentTimestamp < command.getTimestamp()) {
                boolean charging = robot != null && robot.isCharging(currentTimestamp);

                if (!charging) {
                    runEnvironmentUpdates();
                }
                currentTimestamp++;
            }
            this.currentTimestamp = command.getTimestamp();

            CommandExecutor executor = commandMap.get(command.getCommand());

            if (executor != null) {
                BaseOutput pojoOutput;

                String error = checkCommandErrors(command);

                if (error != null) {
                    pojoOutput = new MessageOutput(command.getCommand(), command.getTimestamp(), error);
                } else {

                    pojoOutput = executor.execute(command);
                }

                if (pojoOutput != null) {
                    ObjectNode nodeOutput = mapper.valueToTree(pojoOutput);
                    output.add(nodeOutput);
                }
            }
        }
    }

    private void runEnvironmentUpdates() {
        if(!simulationStarted) {
            return;
        }

        terrain.forEachSection((section) -> {
            if (section.getAir() != null) {
                section.getAir().interactWithEnvironment(section, currentTimestamp);
            }
            if (section.getSoil() != null) {
                section.getSoil().interactWithEnvironment(section, currentTimestamp);
            }

            if (section.getWater() != null && section.getWater().isScanned()) {
                section.getWater().interactWithEnvironment(section, currentTimestamp);
            }
            if (section.getPlant() != null && section.getPlant().isScanned()) {
                section.getPlant().interactWithEnvironment(section, currentTimestamp);
            }
        });

        if(currentTimestamp % 2 == 0) {
            List<Animal> allAnimals = terrain.getAllAnimals();
            Map<Animal, Section> moveToMake = new HashMap<>();

            for(Animal animal : allAnimals) {
                if(animal.isScanned()) {
                    List<Section> neighbours = terrain.getNeighbors(animal.getSection());
                    Section bestMove = animal.findBestSectionToMove(neighbours);
                    moveToMake.put(animal, bestMove);
                }
            }

            for(Map.Entry<Animal, Section> entry : moveToMake.entrySet()) {
                Animal animal = entry.getKey();
                Section newSection = entry.getValue();

                if(animal.isScanned() && newSection != null) {
                    animal.getSection().setAnimal(null);
                    newSection.setAnimal(animal);
                    animal.setSection(newSection);
                }
            }
        }

        List<Animal> allAnimals = terrain.getAllAnimals();
        for(Animal animal : allAnimals) {
            if(animal.isScanned()) {
                animal.feed(animal.getSection());
                animal.interactWithEnvironment(animal.getSection());
            }
        }
    }

    private String checkCommandErrors(CommandInput command) {
        String cmdName = command.getCommand();

        if(!simulationStarted && !cmdName.equals("startSimulation")) {
            return "ERROR: Simulation not started. Cannot perform action";
        }

        if (robot != null && robot.isCharging(currentTimestamp)) {
            if (cmdName.equals("getEnergyStatus") || cmdName.equals("endSimulation")) {
                return null;
            }

            return "ERROR: Robot still charging. Cannot perform action";
        }

        return null;
    }

    private BaseOutput executeStartSimulation(CommandInput command) {
        simulationStarted = true;

        SimulationInput simData = inputLoader.getSimulations().get(simulationIndex);

        String[] dims = simData.getTerritoryDim().split("x");
        this.terrain = new Terrain(Integer.parseInt(dims[0]), Integer.parseInt(dims[1]));

        this.robot = new TerraBot(simData.getEnergyPoints());

        populateSoil(simData, terrain);
        populateAir(simData, terrain);
        populateWater(simData, terrain);
        populatePlants(simData, terrain);
        populateAnimals(simData, terrain);

        robot.setSection(terrain.getSection(0, 0));

        return new MessageOutput(command.getCommand(), command.getTimestamp(), "Simulation has started.");
    }

    private BaseOutput executeEndSimulation(CommandInput command) {
        simulationStarted = false;
        this.robot = null;
        this.terrain = null;
        return new MessageOutput(command.getCommand(), command.getTimestamp(), "Simulation has ended.");
    }

    private BaseOutput executeMoveRobot(CommandInput command) {
        List<Section> neighbors = terrain.getNeighbors(robot.getSection());
        //System.out.println("Robot at (" + robot.getSection().getX() + ", " + robot.getSection().getY() + ")");
        Section bestMove = robot.findBestSectionToMove(neighbors);

        int moveCost = robot.calculateMoveScore(bestMove);


        if (robot.getEnergy() < moveCost) {
            return new MessageOutput(command.getCommand(), command.getTimestamp(),
                            "ERROR: Not enough battery left. Cannot perform action");
        }

        robot.decreaseEnergy(moveCost);
        robot.setSection(bestMove);

        String message = String.format("The robot has successfully moved to position (%d, %d).",
                                        bestMove.getX(), bestMove.getY());

        return new MessageOutput(command.getCommand(), command.getTimestamp(), message);
    }

    private BaseOutput executeRechargeBattery(CommandInput command) {
        robot.startCharging(currentTimestamp, command.getTimeToCharge());
        return new MessageOutput(command.getCommand(), command.getTimestamp(), "Robot battery is charging.");
    }

    private BaseOutput executeScanObject(CommandInput command) {
        final int SCAN_COST = 7;

        if(robot.getEnergy() < SCAN_COST) {
            return new MessageOutput(command.getCommand(), command.getTimestamp() , "ERROR: Not enough battery left." +
                                                                                    "Cannot perform action");
        }

        String objectType = getString(command);

        Section currentSection = robot.getSection();
        String message = "ERROR: Object not found. Cannot perform action";

        switch (objectType) {
            case "water":
                Water water = currentSection.getWater();
                if (water != null && !water.isScanned()) {
                    robot.decreaseEnergy(SCAN_COST);
                    water.markScanned();
                    robot.addToInventory(water.getName(), water);
                    message = "The scanned object is water.";
                }
                break;

            case "plant":
                Plant plant = currentSection.getPlant();
                if (plant != null && !plant.isScanned()) {
                    robot.decreaseEnergy(SCAN_COST);
                    plant.markScanned();
                    robot.addToInventory(plant.getName(), plant);
                    message = "The scanned object is a plant.";
                }
                break;

            case "animal":
                Animal animal = currentSection.getAnimal();
                if (animal != null && !animal.isScanned()) {
                    robot.decreaseEnergy(SCAN_COST);
                    animal.markScanned();
                    robot.addToInventory(animal.getName(), animal);
                    message = "The scanned object is an animal.";
                }
                break;

            default:
                break;
        }

        return new MessageOutput(command.getCommand(), command.getTimestamp(), message);
    }

    private static String getString(CommandInput command) {
        String objectType = "unknown";
        String color = command.getColor();
        String smell = command.getSmell();
        String sound  = command.getSound();

        if ("none".equals(color) && "none".equals(smell) && "none".equals(sound)) {
            objectType = "water";
        } else if ("pink".equals(color) && "sweet".equals(smell) && "none".equals(sound)) {
            objectType = "plant";
        } else if ("brown".equals(color) && "earthy".equals(smell) && "muu".equals(sound)) {
            objectType = "animal";
        }
        return objectType;
    }

    private BaseOutput executeLearnFact(CommandInput command) {
        final int LEARN_COST = 2;

        if(robot.getEnergy() < LEARN_COST) {
            return new MessageOutput(command.getCommand(), command.getTimestamp(), "ERROR: Not enough battery left." +
                                                                                    "Cannot perform action");
        }

        String subject =  command.getSubject();
        String components =  command.getComponents();

        if(robot.getInventory().containsKey(components)) {
            robot.decreaseEnergy(LEARN_COST);
            robot.learnFact(components, subject);

            return new MessageOutput(command.getCommand(), command.getTimestamp(),
                    "The fact has been successfully saved in the database.");
        }

        return new MessageOutput(command.getCommand(), command.getTimestamp(),
                "ERROR: Subject not yet saved. Cannot perform action");
    }

    private BaseOutput executeChangeWeather(CommandInput command) {
        boolean affectedSomething = false;

        for(int x = 0; x < terrain.getWidth(); x++) {
            for(int y = 0; y < terrain.getHeight(); y++) {
                Section section = terrain.getSection(x, y);
                Air air = section.getAir();

                if(air != null) {
                    if(air.applyWeatherEvent(command)) {
                        affectedSomething = true;
                    }
                }
            }
        }

        if(affectedSomething) {
            return new MessageOutput(command.getCommand(), command.getTimestamp(), "The weather has changed.");
        } else {
            return new MessageOutput(command.getCommand(), command.getTimestamp(),
                    "ERROR: The weather change does not affect the environment. Cannot perform action");
        }
    }

    private BaseOutput executeImproveEnvironment(CommandInput command) {
        final int IMPROVE_COST = 10;

        if(robot.getEnergy() < IMPROVE_COST) {
            return new MessageOutput(command.getCommand(), command.getTimestamp(),
                    "ERROR: Not enough battery left. Cannot perform action");
        }

        String improvementType = command.getImprovementType();
        String componentName = command.getName();

        if(!robot.getInventory().containsKey(componentName)) {
            return new MessageOutput(command.getCommand(), command.getTimestamp(),
                    "ERROR: Subject not yet saved. Cannot perform action");
        }

        String requiredFact;
        switch (improvementType) {
            case "plantVegetation":
                requiredFact = "Method to plant " + componentName;
                break;
            case "fertilizeSoil":
                requiredFact = "Method to fertilize soil with " + componentName;
                break;
            case "increaseHumidity":
                requiredFact = "Method to increaseHumidity";
                break;
            case "increaseMoisture":
                requiredFact = "Method to increaseMoisture";
                break;
            default:
                return new MessageOutput(command.getCommand(), command.getTimestamp(),
                        "ERROR: Unknown improvement type.");
        }

        List<String> factsForComponent = robot.getKnowledgeBase().get(componentName);
        if (factsForComponent == null || !factsForComponent.contains(requiredFact)) {
            return new MessageOutput(command.getCommand(), command.getTimestamp(),
                    "ERROR: Fact not yet saved. Cannot perform action");
        }

        robot.decreaseEnergy(IMPROVE_COST);
        Section currentSection = robot.getSection();
        String successMessage = "";

        switch (improvementType) {
            case "plantVegetation":
                Air airPlant = currentSection.getAir();
                if (airPlant != null) {
                    airPlant.setOxygenLevel(airPlant.getOxygenLevel() + 0.3);
                }
                successMessage = "The " + componentName + " was planted successfully.";
                break;

            case "fertilizeSoil":
                Soil soilFertilize = currentSection.getSoil();
                if (soilFertilize != null) {
                    soilFertilize.setOrganicMatter(soilFertilize.getOrganicMatter() + 0.3);
                }
                successMessage = "The soil was successfully fertilized using " + componentName;
                break;

            case "increaseHumidity":
                Air airHumidity = currentSection.getAir();
                if (airHumidity != null) {
                    airHumidity.setHumidity(airHumidity.getHumidity() + 0.2);
                }
                successMessage = "The humidity was successfully increased using " + componentName;
                break;

            case "increaseMoisture":
                Soil soilMoisture = currentSection.getSoil();
                if (soilMoisture != null) {
                    soilMoisture.setWaterRetention(soilMoisture.getWaterRetention() + 0.2);
                }
                successMessage = "The moisture was successfully increased using " + componentName;
                break;
        }

        return new MessageOutput(command.getCommand(), command.getTimestamp(), successMessage);
    }

    private BaseOutput executeGetEnergyStatus(CommandInput command) {
        String message = String.format("TerraBot has %d energy points left.", robot.getEnergy());
        return new MessageOutput(command.getCommand(), command.getTimestamp(), message);
    }

    private BaseOutput executePrintEnvConditions(CommandInput command) {
        Section currentSection = robot.getSection();

        EnvConditionsOutput data = new EnvConditionsOutput();

        data.setSoil(currentSection.getSoil());
        data.setPlants(currentSection.getPlant());
        data.setAnimals(currentSection.getAnimal());
        data.setWater(currentSection.getWater());
        data.setAir(currentSection.getAir());

        return new DataOutput(command.getCommand(), command.getTimestamp(), data);
    }

    private BaseOutput executePrintMap(CommandInput command) {
        List<PrintMapOutput> mapDataList = new ArrayList<>();

        for (int y = 0; y < terrain.getHeight(); y++) {
            for (int x = 0; x < terrain.getWidth(); x++) {
                Section section = terrain.getSection(x, y);

                PrintMapOutput sectionData = getPrintMapOutput(section, x, y);

                mapDataList.add(sectionData);
            }
        }

        return new DataOutput(command.getCommand(), command.getTimestamp(), mapDataList);
    }

    private static PrintMapOutput getPrintMapOutput(Section section, int x, int y) {
        int objectCount = 0;
        if (section.getPlant() != null) objectCount++;
        if (section.getAnimal() != null) objectCount++;
        if (section.getWater() != null) objectCount++;

        String airQuality = (section.getAir() != null) ? section.getAir().getQualityStatus() : "N/A";
        String soilQuality = (section.getSoil() != null) ? section.getSoil().getQualityStatus() : "N/A";

        int[] coords = new int[]{x, y};
        return new PrintMapOutput(coords, objectCount, airQuality, soilQuality);
    }

    private BaseOutput executePrintKnowledgeBase(CommandInput command) {
        Map<String, List<String>> knowledge = robot.getKnowledgeBase();

        List<KnowledgeBaseOutput> knowledgeList = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : knowledge.entrySet()) {
            knowledgeList.add(new KnowledgeBaseOutput(entry.getKey(), entry.getValue()));
        }

        return new DataOutput(command.getCommand(), command.getTimestamp(), knowledgeList);
    }

    private void populateSoil(SimulationInput simData, Terrain terrain) {
        for(SoilInput soilInput : simData.getTerritorySectionParams().getSoil()) {
            for(PairInput coords : soilInput.getSections()) {
                Section section = terrain.getSection(coords.getX(), coords.getY());
                if(section == null) {
                    continue;
                }

                Soil soilObject = switch (soilInput.getType()) {
                    case "ForestSoil" -> new ForestSoil(soilInput.getName(), soilInput.getMass(), section,
                            soilInput.getNitrogen(), soilInput.getWaterRetention(), soilInput.getSoilpH(),
                            soilInput.getOrganicMatter(), soilInput.getLeafLitter());
                    case "DesertSoil" -> new DesertSoil(soilInput.getName(), soilInput.getMass(), section,
                            soilInput.getNitrogen(), soilInput.getWaterRetention(), soilInput.getSoilpH(),
                            soilInput.getOrganicMatter(), soilInput.getSalinity());
                    case "SwampSoil" -> new SwampSoil(soilInput.getName(), soilInput.getMass(), section,
                            soilInput.getNitrogen(), soilInput.getWaterRetention(), soilInput.getSoilpH(),
                            soilInput.getOrganicMatter(), soilInput.getWaterLogging());
                    case "GrasslandSoil" -> new GrasslandSoil(soilInput.getName(), soilInput.getMass(), section,
                            soilInput.getNitrogen(), soilInput.getWaterRetention(), soilInput.getSoilpH(),
                            soilInput.getOrganicMatter(), soilInput.getRootDensity());
                    case "TundraSoil" -> new TundraSoil(soilInput.getName(), soilInput.getMass(), section,
                            soilInput.getNitrogen(), soilInput.getWaterRetention(), soilInput.getSoilpH(),
                            soilInput.getOrganicMatter(), soilInput.getPermafrostDepth());
                    default -> null;
                };

                section.setSoil(soilObject);

            }
        }
    }

    private void populateAir(SimulationInput simData, Terrain terrain) {
        for (AirInput airInput : simData.getTerritorySectionParams().getAir()) {
            for (PairInput coords : airInput.getSections()) {
                Section section = terrain.getSection(coords.getX(), coords.getY());
                if (section == null) {
                    continue;
                }

                Air airObject = switch (airInput.getType()) {
                    case "TropicalAir" -> new TropicalAir(airInput.getName(), airInput.getMass(), section, airInput.getType(),
                            airInput.getHumidity(), airInput.getTemperature(), airInput.getOxygenLevel(), airInput.getCo2Level());
                    case "PolarAir" -> new PolarAir(airInput.getName(), airInput.getMass(), section, airInput.getType(),
                            airInput.getHumidity(), airInput.getTemperature(), airInput.getOxygenLevel(), airInput.getIceCrystalConcentration());
                    case "TemperateAir" -> new TemperateAir(airInput.getName(), airInput.getMass(), section, airInput.getType(),
                            airInput.getHumidity(), airInput.getTemperature(), airInput.getOxygenLevel(), airInput.getPollenLevel());
                    case "DesertAir" -> new DesertAir(airInput.getName(), airInput.getMass(), section, airInput.getType(),
                            airInput.getHumidity(), airInput.getTemperature(), airInput.getOxygenLevel(), airInput.getDustParticles());
                    case "MountainAir" -> new MountainAir(airInput.getName(), airInput.getMass(), section, airInput.getType(),
                            airInput.getHumidity(), airInput.getTemperature(), airInput.getOxygenLevel(), airInput.getAltitude());
                    default -> null;
                };
                section.setAir(airObject);
            }
        }
    }

    private void populateWater(SimulationInput simData, Terrain terrain) {
        for (WaterInput waterInput : simData.getTerritorySectionParams().getWater()) {
            for (PairInput coords : waterInput.getSections()) {
                Section section = terrain.getSection(coords.getX(), coords.getY());
                if (section == null) {
                    continue;
                }

                Water waterObject = new Water(waterInput.getName(), waterInput.getMass(), section, waterInput.getType(),
                        waterInput.getSalinity(), waterInput.getPH(), waterInput.getPurity(),
                        waterInput.getTurbidity(), waterInput.getContaminantIndex(), waterInput.isFrozen());

                section.setWater(waterObject);
            }
        }
    }

    private void populatePlants(SimulationInput simData, Terrain terrain) {
        for (PlantInput plantInput : simData.getTerritorySectionParams().getPlants()) {
            for (PairInput coords : plantInput.getSections()) {
                Section section = terrain.getSection(coords.getX(), coords.getY());
                if (section == null) {
                    continue;
                }

                Plant plantObject = new Plant(plantInput.getName(), plantInput.getMass(), section, plantInput.getType());

                section.setPlant(plantObject);
            }
        }
    }

    private void populateAnimals(SimulationInput simData, Terrain terrain) {
        for (AnimalInput animalInput : simData.getTerritorySectionParams().getAnimals()) {
            for (PairInput coords : animalInput.getSections()) {
                Section section = terrain.getSection(coords.getX(), coords.getY());
                if (section == null) {
                    continue;
                }

                Animal animalObject = new Animal(animalInput.getName(), animalInput.getMass(), section, animalInput.getType());

                section.setAnimal(animalObject);
            }
        }
    }

}
