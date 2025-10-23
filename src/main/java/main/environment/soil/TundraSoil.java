package main.environment.soil;

import main.core.Section;

public class TundraSoil extends Soil {
    private double permafrostDepth;

    public TundraSoil(String name, double mass, Section section,
                      double nitrogen, double waterRetention,
                      double soilpH, double organicMatter,
                      double permafrostDepth) {
        super(name, mass, section, "Tundra",
              nitrogen, waterRetention,
              soilpH, organicMatter);
        this.permafrostDepth = permafrostDepth;
        calculateSoilQuality();
        calculateBlockingProbability();
    }

    @Override
    public void calculateSoilQuality() {
        double quality = (getNitrogen() * 0.7) + (getOrganicMatter() * 0.5) -
                         (this.permafrostDepth * 1.5);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        setSoilQuantity(normalizedQuality);
    }

    @Override
    public void calculateBlockingProbability() {
        double probability = (50 - this.permafrostDepth) / 50 * 100;

        setBlockingProbability(probability);
    }

    public double getPermafrostDepth() {
        return this.permafrostDepth;
    }

    public void setPermafrostDepth(double permafrostDepth) {
        this.permafrostDepth = permafrostDepth;
        calculateSoilQuality();
        calculateBlockingProbability();
    }
}
