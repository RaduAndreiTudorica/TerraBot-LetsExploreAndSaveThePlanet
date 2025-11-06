package main.environment.soil;

import main.core.Section;

public class TundraSoil extends Soil {
    private double permafrostDepth;

    public TundraSoil() {
        super();
        this.permafrostDepth = 0.0;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    public TundraSoil(String name, double mass, Section section, double nitrogen,
                        double waterRetention, double soilpH,
                        double organicMatter, double permafrostDepth) {
        super(name, mass, section, "TundraSoil",
                nitrogen, waterRetention,
                soilpH, organicMatter);
        this.permafrostDepth = permafrostDepth;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }

    @Override
    public double calculateQuality() {
        double quality = (this.nitrogen * 0.7) + (this.organicMatter * 0.5) -
                (this.permafrostDepth * 1.5);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        double finalResult = Math.round(normalizedQuality * 100.0) / 100.0;

        this.soilQuality = finalResult;
        interpretQuality();
        return finalResult;
    }

    @Override
    public double calculateBlockingProbability() {
        double probability = (50 - this.permafrostDepth) / 50 * 100;

        return probability;
    }

    public double getPermafrostDepth() {
        return this.permafrostDepth;
    }

    public void setPermafrostDepth(double permafrostDepth) {
        this.permafrostDepth = permafrostDepth;
        this.soilQuality = calculateQuality();
        this.blockingProbability = calculateBlockingProbability();
    }
}
