package main.environment.soil;

import main.core.Section;

public class DesertSoil extends Soil {
    private double salinity;

    public DesertSoil() {
        super();
        this.salinity = 0.0;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    public DesertSoil(String name, double mass, Section section, double nitrogen,
                        double waterRetention, double soilpH,
                        double organicMatter, double salinity) {
        super(name, mass, section, "DesertSoil",
                nitrogen, waterRetention,
                soilpH, organicMatter);
        this.salinity = salinity;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    @Override
    public double calculateQuality() {
        double quality = (this.nitrogen * 0.5) + (this.waterRetention * 0.3) -
                            (this.salinity * 2);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        double finalResult = Math.round(normalizedQuality * 100.0) / 100.0;

        this.soilQuality = finalResult;
        interpretQuality();
        return finalResult;
    }

    @Override
    public double calculateBlockingProbability() {
        double probability = (100 - this.waterRetention + this.salinity) / 100 * 100;
        return probability;
    }

    public double getSalinity() {
        return this.salinity;
    }

    public void setSalinity(double salinity) {
        this.salinity = salinity;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }
}
