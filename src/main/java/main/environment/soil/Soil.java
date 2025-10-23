package main.environment.soil;

import main.core.Entity;
import main.core.Section;

public abstract class Soil extends Entity {
    public static final double GROWTH_FACTOR = 0.2;
    public static final double WATER_ABSORPTION = 0.1;

    private String type;
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    private double soilQuantity = 0.0;
    private double blockingProbability = 0.0;

    public Soil(String name, double mass, Section section, String type,
                double nitrogen, double waterRetention,
                double soilpH, double organicMatter) {
        super(name, mass, section);

        this.type = type;
        this.nitrogen = nitrogen;
        this.waterRetention = waterRetention;
        this.soilpH = soilpH;
        this.organicMatter = organicMatter;
    }

    public abstract void calculateSoilQuality();

    public abstract void calculateBlockingProbability();

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
        calculateSoilQuality();
        calculateBlockingProbability();
    }

    public double getNitrogen() {
        return this.nitrogen;
    }

    public void setNitrogen(double nitrogen) {
        this.nitrogen = nitrogen;
        calculateSoilQuality();
        calculateBlockingProbability();
    }

    public double getWaterRetention() {
        return this.waterRetention;
    }

    public void setWaterRetention(double waterRetention) {
        this.waterRetention = waterRetention;
        calculateSoilQuality();
        calculateBlockingProbability();
    }

    public double getSoilpH() {
        return this.soilpH;
    }

    public void setSoilpH(double soilpH) {
        this.soilpH = soilpH;
        calculateSoilQuality();
        calculateBlockingProbability();
    }

    public double getOrganicMatter() {
        return this.organicMatter;
    }

    public void setOrganicMatter(double organicMatter) {
        this.organicMatter = organicMatter;
        calculateSoilQuality();
        calculateBlockingProbability();
    }

    public double getSoilQuantity() {
        return this.soilQuantity;
    }
    void setSoilQuantity(double soilQuantity) {
        this.soilQuantity = soilQuantity;
    }

    public double getBlockingProbability() {
        return this.blockingProbability;
    }

    void setBlockingProbability(double blockingProbability) {
        this.blockingProbability = blockingProbability;
    }

    public void waterSoil() {
        this.waterRetention += Soil.WATER_ABSORPTION;
        calculateSoilQuality();
        calculateBlockingProbability();

    }

}
