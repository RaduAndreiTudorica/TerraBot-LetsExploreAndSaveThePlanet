package main.environment.soil;

import main.core.Section;

public class ForestSoil extends Soil {
    private double leafLitter;

    public ForestSoil() {
        super();
        this.leafLitter = 0.0;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    public ForestSoil(String name, double mass, Section section, double nitrogen,
                        double waterRetention, double soilpH,
                        double organicMatter, double leafLitter) {
        super(name, mass, section, "ForestSoil",
                nitrogen, waterRetention,
                soilpH, organicMatter);
        this.leafLitter = leafLitter;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    @Override
    public double calculateQuality() {
        double quality = (this.nitrogen * 1.2) + (this.waterRetention * 1.5) +
                            (this.getOrganicMatter() * 2) + (this.leafLitter * 0.3);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        double finalResult = Math.round(normalizedQuality * 100.0) / 100.0;

        this.soilQuality = finalResult;
        interpretQuality();
        return finalResult;
    }

    @Override
    public double calculateBlockingProbability() {
        double probability = ((this.waterRetention * 0.6) + (this.leafLitter * 0.4)) / 80 * 100;
        return probability;
    }

    public double getLeafLitter() {
        return this.leafLitter;
    }

    public void setLeafLitter(double leafLitter) {
        this.leafLitter = leafLitter;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }
}
