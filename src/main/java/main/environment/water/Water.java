package main.environment.water;

import main.core.Entity;
import main.core.Section;

public class Water extends Entity {
    public static final double GROWTH_FACTOR = 0.2;

    private double salinity;
    private double pH;
    private double purity;
    private int turbidity;
    private double contaminantIndex;
    private boolean isFrozen;
    private double water_quality;

    public Water(String name, double mass, Section section, double salinity, double pH,
                 double purity, int turbidity,
                 double contaminantIndex, boolean isFrozen) {
        super(name, mass, section);

        this.salinity = salinity;
        this.pH = pH;
        this.purity = purity;
        this.turbidity = turbidity;
        this.contaminantIndex = contaminantIndex;
        this.isFrozen = isFrozen;
        this.water_quality = calculateWaterQuality();
    }

    private double calculateWaterQuality() {
        double purity_score = this.purity / 100.0;
        double pH_score = 1.0 - Math.abs(this.pH - 7.5) / 7.5;
        double salinity_score = 1.0 - (this.salinity / 350.0);
        double turbidity_score = 1.0 - (this.turbidity / 100.0);
        double contaminant_score = 1.0 - (this.contaminantIndex / 100.0);
        double isFrozen = this.isFrozen ? 0.0 : 1.0;

        return (0.3 * purity_score + 0.2 * pH_score + 0.15 * salinity_score +
                               0.1 * turbidity_score + 0.15 * contaminant_score + 0.2 * isFrozen) * 100.0;
    }

    public double getSalinity() {
        return this.salinity;
    }

    public void setSalinity(double salinity) {
        this.salinity = salinity;
        this.water_quality = calculateWaterQuality();
    }

    public double getPH() {
        return this.pH;
    }

    public void setPH(double pH) {
        this.pH = pH;
        this.water_quality = calculateWaterQuality();
    }

    public double getPurity() {
        return this.purity;
    }

    public void setPurity(double purity) {
        this.purity = purity;
        this.water_quality = calculateWaterQuality();
    }

    public int getTurbidity() {
        return this.turbidity;
    }

    public void setTurbidity(int turbidity) {
        this.turbidity = turbidity;
        this.water_quality = calculateWaterQuality();
    }

    public double getContaminantIndex() {
        return this.contaminantIndex;
    }

    public void setContaminantIndex(double contaminantIndex) {
        this.contaminantIndex = contaminantIndex;
        this.water_quality = calculateWaterQuality();
    }

    public boolean isFrozen() {
        return this.isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
        this.water_quality = calculateWaterQuality();
    }

    public double getWater_quality() {
        return this.water_quality;
    }

}

