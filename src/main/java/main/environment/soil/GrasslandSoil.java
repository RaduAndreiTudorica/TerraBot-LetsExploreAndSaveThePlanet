package main.environment.soil;

import main.core.Section;

public class GrasslandSoil extends Soil {
    private double rootDensity;

    public GrasslandSoil(String name, double mass, Section section,
                         double nitrogen, double waterRetention,
                         double soilpH, double organicMatter,
                         double rootDensity) {
        super(name, mass, section, "Grassland",
              nitrogen, waterRetention,
              soilpH, organicMatter);
        this.rootDensity = rootDensity;
        calculateSoilQuality();
        calculateBlockingProbability();
    }

    @Override
    public void calculateSoilQuality() {
        double quality = (getNitrogen() * 1.3) + (getOrganicMatter() * 1.5) +
                (this.rootDensity * 0.8);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        setSoilQuantity(normalizedQuality);
    }

    @Override
    public void calculateBlockingProbability() {
        double probability = ((50 - this.rootDensity) + (getWaterRetention() * 0.5)) / 75 * 100;

        setBlockingProbability(probability);
    }

    public double getRootDensity() {
        return this.rootDensity;
    }

    public void setRootDensity(double rootDensity) {
        this.rootDensity = rootDensity;
        calculateSoilQuality();
        calculateBlockingProbability();
    }
}
