package main.environment.soil;

public class TundraSoil extends Soil {
    private double permafrostDepth;

    public TundraSoil() {
        super();
        this.permafrostDepth = 0.0;
        calculateQuality();
        calculateBlockingProbability();
    }

    public TundraSoil(String name, double mass, double nitrogen,
                      double waterRetention, double soilpH,
                      double organicMatter, double permafrostDepth) {
        super(name, mass, "Tundra",
              nitrogen, waterRetention,
              soilpH, organicMatter);
        this.permafrostDepth = permafrostDepth;
        calculateQuality();
        calculateBlockingProbability();
    }

    @Override
    public double calculateQuality() {
        double quality = (getNitrogen() * 0.7) + (getOrganicMatter() * 0.5) -
                         (this.permafrostDepth * 1.5);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        setSoilQuality(normalizedQuality);
        interpretQuality();
        return normalizedQuality;
    }

    @Override
    public double calculateBlockingProbability() {
        double probability = (50 - this.permafrostDepth) / 50 * 100;

        setBlockingProbability(probability);
        return probability;
    }

    public double getPermafrostDepth() {
        return this.permafrostDepth;
    }

    public void setPermafrostDepth(double permafrostDepth) {
        this.permafrostDepth = permafrostDepth;
        calculateQuality();
        calculateBlockingProbability();
    }
}
