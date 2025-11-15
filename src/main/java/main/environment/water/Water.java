package main.environment.water;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.core.Entity;
import main.core.Section;
import main.environment.soil.*;
import main.environment.air.*;
import main.environment.plant.*;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Water extends Entity {
    public static final double GROWTH_FACTOR = 0.2;

    private static final NavigableMap<Double, String> QUALITY_MAP = new TreeMap<>();

    static {
        QUALITY_MAP.put(0.0, "Poor");
        QUALITY_MAP.put(40.0, "Moderate");
        QUALITY_MAP.put(70.0, "Good");
    }

    private String type;
    @JsonIgnore
    private double salinity;
    @JsonIgnore
    private double pH;
    @JsonIgnore
    private double purity;
    @JsonIgnore
    private double turbidity;
    @JsonIgnore
    private double contaminantIndex;
    @JsonIgnore
    private boolean isFrozen;
    @JsonIgnore
    private double waterQuality;
    @JsonIgnore
    private String qualityStatus;
    @JsonIgnore
    private boolean isScanned = false;
    @JsonIgnore
    private int activationTimestamp = -1;

    public Water() {
        super();
        this.salinity = 0.0;
        this.pH = 0.0;
        this.purity = 100.0;
        this.turbidity = 0;
        this.contaminantIndex = 0.0;
        this.isFrozen = false;
        calculateWaterQuality();
    }

    public Water(String name, double mass, Section section, String type, double salinity, double pH,
                    double purity, double turbidity,
                    double contaminantIndex, boolean isFrozen) {
        super(name, mass, section);

        this.type = type;
        this.salinity = salinity;
        this.pH = pH;
        this.purity = purity;
        this.turbidity = turbidity;
        this.contaminantIndex = contaminantIndex;
        this.isFrozen = isFrozen;
        this.waterQuality = calculateWaterQuality();
    }

    private double calculateWaterQuality() {
        double purity_score = this.purity / 100.0;
        double pH_score = 1.0 - Math.abs(this.pH - 7.5) / 7.5;
        double salinity_score = 1.0 - (this.salinity / 350.0);
        double turbidity_score = 1.0 - (this.turbidity / 100.0);
        double contaminant_score = 1.0 - (this.contaminantIndex / 100.0);
        double isFrozen = this.isFrozen ? 0.0 : 1.0;

        double waterQuality =  (0.3 * purity_score + 0.2 * pH_score + 0.15 * salinity_score +
                                0.1 * turbidity_score + 0.15 * contaminant_score + 0.2 * isFrozen) * 100.0;

        this.qualityStatus = QUALITY_MAP.floorEntry(this.waterQuality).getValue();
        return waterQuality;
    }

    public void interactWithEnvironment(Section section, int iteration) {
        if(!isScanned()) {
            return;
        }

        boolean canInteract = this.activationTimestamp != -1 &&
                iteration >= this.activationTimestamp + 2;

        if(canInteract) {
            if ((iteration - (this.activationTimestamp + 2)) % 2 == 0) {
                Soil soil = section.getSoil();
                if(soil != null) {
                    double newRetention = soil.getWaterRetention() + 0.1;
                    soil.setWaterRetention(newRetention);
                }

                Air air = section.getAir();
                if(air != null) {
                    double newHumidity = air.getHumidity() + 0.1;
                    air.setHumidity(newHumidity);
                }
            }
        }

        Plant plant = section.getPlant();
        if(plant != null) {
            plant.addGrowthLevel(GROWTH_FACTOR);
        }
    }

    public void markScanned(int timestamp) {
        this.isScanned = true;
        this.activationTimestamp = timestamp;
    }
    @JsonIgnore
    public boolean isScanned() {
        return this.isScanned;
    }

    public double getSalinity() {
        return this.salinity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setSalinity(double salinity) {
        this.salinity = salinity;
        this.waterQuality = calculateWaterQuality();
    }

    public double getpH() {
        return this.pH;
    }

    public void setpH(double pH) {
        this.pH = pH;
        this.waterQuality = calculateWaterQuality();
    }

    public double getPurity() {
        return this.purity;
    }

    public void setPurity(double purity) {
        this.purity = purity;
        this.waterQuality = calculateWaterQuality();
    }

    public double getTurbidity() {
        return this.turbidity;
    }

    public void setTurbidity(double turbidity) {
        this.turbidity = turbidity;
        this.waterQuality = calculateWaterQuality();
    }

    public double getContaminantIndex() {
        return this.contaminantIndex;
    }

    public void setContaminantIndex(double contaminantIndex) {
        this.contaminantIndex = contaminantIndex;
        this.waterQuality = calculateWaterQuality();
    }

    public boolean getIsFrozen() {
        return this.isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
        this.waterQuality = calculateWaterQuality();
    }

    public double getWaterQuality() {
        return this.waterQuality;
    }

    public void setWaterQuality(double waterQuality) {
        this.waterQuality = waterQuality;
    }

    public String getQualityStatus() {
        return this.qualityStatus;
    }

}

