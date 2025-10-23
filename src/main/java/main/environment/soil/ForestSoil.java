package main.environment.soil;

import main.core.Section;

public class ForestSoil extends Soil {
    private double leafLitter;

    public ForestSoil(String name, double mass, Section section,
                      double nitrogen, double waterRetention,
                      double soilpH, double organicMatter,
                      double leafLitter) {
        super(name, mass, section, "Forest",
              nitrogen, waterRetention,
              soilpH, organicMatter);
        this.leafLitter = leafLitter;
        calculateSoilQuality();
        calculateBlockingProbability();
    }

    @Override
    public void calculateSoilQuality() {
        double quality = (getNitrogen() * 1.2) + (getWaterRetention() * 1.5) +
                         (getOrganicMatter() * 2) + (this.leafLitter * 0.3);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        setSoilQuantity(normalizedQuality);
    }

    @Override
    public void calculateBlockingProbability() {
        double probability = ((getWaterRetention() * 0.6) + (this.leafLitter * 0.4)) / 80 * 100;

        setBlockingProbability(probability);
    }

    public double getLeafLitter() {
        return this.leafLitter;
    }

    public void setLeafLitter(double leafLitter) {
        this.leafLitter = leafLitter;
        calculateSoilQuality();
        calculateBlockingProbability();
    }
}
