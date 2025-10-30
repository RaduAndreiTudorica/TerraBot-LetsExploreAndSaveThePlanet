package main.environment.soil;

import main.core.Section;

public class SwampSoil extends Soil {
    private double waterLogging;

    public SwampSoil() {
        super();
        this.waterLogging = 0.0;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    public SwampSoil(String name, double mass, Section section, double nitrogen,
                        double waterRetention, double soilpH,
                        double organicMatter, double waterLogging) {
        super(name, mass, section, "Swamp",
                nitrogen, waterRetention,
                soilpH, organicMatter);
        this.waterLogging = waterLogging;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    @Override
    public double calculateQuality() {
        double quality = (this.nitrogen * 1.1) + (this.organicMatter * 2.2) -
                (this.waterLogging * 5);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        interpretQuality();
        return normalizedQuality;
    }

    @Override
    public double calculateBlockingProbability() {
        double probability = this.waterLogging * 10;

        return probability;
    }

    public double getWaterLogging() {
        return this.waterLogging;
    }

    public void setWaterLogging(double watterLogging) {
        this.waterLogging = watterLogging;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

}
