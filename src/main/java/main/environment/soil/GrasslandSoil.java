package main.environment.soil;

import main.core.Section;

public class GrasslandSoil extends Soil {
    private double rootDensity;

    public GrasslandSoil() {
        super();
        this.rootDensity = 0.0;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    public GrasslandSoil(String name, double mass, Section section, double nitrogen,
                            double waterRetention, double soilpH,
                            double organicMatter, double rootDensity) {
        super(name, mass, section, "Grassland",
                nitrogen, waterRetention,
                soilpH, organicMatter);
        this.rootDensity = rootDensity;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    @Override
    public double calculateQuality() {
        double quality = (this.nitrogen * 1.3) + (this.organicMatter * 1.5) +
                (this.rootDensity * 0.8);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        interpretQuality();
        return normalizedQuality;
    }

    @Override
    public double calculateBlockingProbability() {
        double probability = ((50 - this.rootDensity) + (this.waterRetention * 0.5)) / 75 * 100;

        return probability;
    }

    public double getRootDensity() {
        return this.rootDensity;
    }

    public void setRootDensity(double rootDensity) {
        this.rootDensity = rootDensity;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }
}
