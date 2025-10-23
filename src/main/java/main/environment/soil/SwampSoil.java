package main.environment.soil;

import main.core.Section;

public class SwampSoil extends Soil {
    private double waterLogging;

    public SwampSoil(String name, double mass, Section section,
                     double nitrogen, double waterRetention,
                     double soilpH, double organicMatter,
                     double waterLogging) {
        super(name, mass, section, "Swamp",
              nitrogen, waterRetention,
              soilpH, organicMatter);
        this.waterLogging = waterLogging;
        calculateSoilQuality();
        calculateBlockingProbability();
    }

    @Override
    public void calculateSoilQuality() {
        double quality = (getNitrogen() * 1.1) + (getOrganicMatter() * 2.2) -
                (this.waterLogging * 5);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        setSoilQuantity(normalizedQuality);
    }

    @Override
    public void calculateBlockingProbability() {
        double probability = this.waterLogging * 10;

        setBlockingProbability(probability);
    }

    public double getWatterLogging() {
        return this.waterLogging;
    }

    public void setWatterLogging(double watterLogging) {
        this.waterLogging = watterLogging;
        calculateSoilQuality();
        calculateBlockingProbability();
    }

}
