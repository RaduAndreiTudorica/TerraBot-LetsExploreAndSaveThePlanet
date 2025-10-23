package main.environment.soil;

import main.core.Section;

public class DesertSoil extends Soil {
    private double salinity;

    public DesertSoil(String name, double mass, Section section,
                      double nitrogen, double waterRetention,
                      double soilpH, double organicMatter,
                      double salinity) {
        super(name, mass, section, "Desert",
              nitrogen, waterRetention,
              soilpH, organicMatter);
        this.salinity = salinity;
        calculateSoilQuality();
        calculateBlockingProbability();
    }

    @Override
    public void calculateSoilQuality() {
        double quality = (getNitrogen() * 0.5) + (getWaterRetention() * 0.3) -
                         (this.salinity * 2);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        setSoilQuantity(normalizedQuality);
    }

    @Override
    public void calculateBlockingProbability() {
        double probability = (100 - getWaterRetention() + this.salinity) / 100 * 100;

        setBlockingProbability(probability);
    }

    public double getSalinity() {
        return this.salinity;
    }

    public void setSalinity(double salinity) {
        this.salinity = salinity;
        calculateSoilQuality();
        calculateBlockingProbability();
    }
}
