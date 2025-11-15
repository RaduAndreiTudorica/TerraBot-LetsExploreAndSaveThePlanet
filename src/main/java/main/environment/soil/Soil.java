package main.environment.soil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.core.Entity;
import main.core.Section;
import main.environment.plant.Plant;

import java.util.NavigableMap;
import java.util.TreeMap;

public abstract class Soil extends Entity {
    public static final double GROWTH_FACTOR = 0.2;

    private static final NavigableMap<Double, String> QUALITY_MAP = new TreeMap<>();

    static {
        QUALITY_MAP.put(0.0, "poor");
        QUALITY_MAP.put(40.0, "moderate");
        QUALITY_MAP.put(70.0, "good");
    }

    protected String type;
    protected double nitrogen;
    protected double waterRetention;
    protected double soilpH;
    protected double organicMatter;
    protected double soilQuality = 0.0;
    @JsonIgnore
    protected double blockingProbability = 0.0;
    @JsonIgnore
    protected String qualityStatus;

    public Soil() {
        super();
        this.type = "Generic";
        this.nitrogen = 0.0;
        this.waterRetention = 0.0;
        this.soilpH = 7.0;
        this.organicMatter = 0.0;
    }

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

    public abstract double calculateQuality();

    public abstract double calculateBlockingProbability();

    public void interpretQuality() {
        this.qualityStatus = QUALITY_MAP.floorEntry(this.soilQuality).getValue();
    }

    public void interactWithEnvironment(Section section, int iteration) {
        Plant plant = section.getPlant();
        if (plant == null) {
            return;
        }

        if (plant.isScanned()) {
            plant.addGrowthLevel(GROWTH_FACTOR);
        }

        if (plant.isDead()) {
            section.setPlant(null);
        }
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    public double getNitrogen() {
        return this.nitrogen;
    }

    public void setNitrogen(double nitrogen) {
        this.nitrogen = nitrogen;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    public double getWaterRetention() {
        return this.waterRetention;
    }

    public void setWaterRetention(double waterRetention) {
        this.waterRetention = Math.max(0, Math.min(100, waterRetention));
        this.waterRetention = Math.round(this.waterRetention * 100.0) / 100.0;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    public double getSoilpH() {
        return this.soilpH;
    }

    public void setSoilpH(double soilpH) {
        this.soilpH = soilpH;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    public double getOrganicMatter() {
        return this.organicMatter;
    }

    public void setOrganicMatter(double organicMatter) {
        this.organicMatter = organicMatter;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
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

    public String getQualityStatus() {
        return this.qualityStatus;
    }
}
