package main.environment.soil;

import main.core.Entity;
import main.core.Section;
import main.environment.plant.Plant;

import java.util.NavigableMap;
import java.util.TreeMap;

public abstract class Soil extends Entity {
    public static final double GROWTH_FACTOR = 0.2;

    private static final NavigableMap<Double, String> QUALITY_MAP = new TreeMap<>();

    static {
        QUALITY_MAP.put(0.0, "Poor");
        QUALITY_MAP.put(40.0, "Moderate");
        QUALITY_MAP.put(70.0, "Good");
    }

    private String type;
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    private double soilQuality = 0.0;
    private double blockingProbability = 0.0;
    public String qualityStatus;

    public Soil() {
        super();
        this.type = "Generic";
        this.nitrogen = 0.0;
        this.waterRetention = 0.0;
        this.soilpH = 7.0;
        this.organicMatter = 0.0;
    }

    public Soil(String name, double mass, String type,
                double nitrogen, double waterRetention,
                double soilpH, double organicMatter) {
        super(name, mass);

        this.type = type;
        this.nitrogen = nitrogen;
        this.waterRetention = waterRetention;
        this.soilpH = soilpH;
        this.organicMatter = organicMatter;
    }

    public abstract double calculateQuality();

    public abstract double calculateBlockingProbability();

    public void interpretQuality() {
        this.qualityStatus = QUALITY_MAP.floorEntry(this.soilQuality).getValue();
    }

    public void interactWithEnvironment(Section section, int iteration) {
       for(Plant plant : section.getPlants()) {
       plant.grow(GROWTH_FACTOR);
       }
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
        calculateQuality();
        calculateBlockingProbability();
    }

    public double getNitrogen() {
        return this.nitrogen;
    }

    public void setNitrogen(double nitrogen) {
        this.nitrogen = nitrogen;
        calculateQuality();
        calculateBlockingProbability();
    }

    public double getWaterRetention() {
        return this.waterRetention;
    }

    public void setWaterRetention(double waterRetention) {
        this.waterRetention = waterRetention;
        calculateQuality();
        calculateBlockingProbability();
    }

    public double getSoilpH() {
        return this.soilpH;
    }

    public void setSoilpH(double soilpH) {
        this.soilpH = soilpH;
        calculateQuality();
        calculateBlockingProbability();
    }

    public double getOrganicMatter() {
        return this.organicMatter;
    }

    public void setOrganicMatter(double organicMatter) {
        this.organicMatter = organicMatter;
        calculateQuality();
        calculateBlockingProbability();
    }

    public double getSoilQuality() {
        return this.soilQuality;
    }
    void setSoilQuality(double soilQuality) {
        this.soilQuality = soilQuality;
    }

    public double getBlockingProbability() {
        return this.blockingProbability;
    }

    void setBlockingProbability(double blockingProbability) {
        this.blockingProbability = blockingProbability;
    }
}
