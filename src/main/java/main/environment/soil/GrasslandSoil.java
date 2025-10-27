package main.environment.soil;

public class GrasslandSoil extends Soil {
    private double rootDensity;

    public GrasslandSoil() {
        super();
        this.rootDensity = 0.0;
        calculateQuality();
        calculateBlockingProbability();
    }

    public GrasslandSoil(String name, double mass, double nitrogen,
                         double waterRetention, double soilpH,
                         double organicMatter, double rootDensity) {
        super(name, mass, "Grassland",
              nitrogen, waterRetention,
              soilpH, organicMatter);
        this.rootDensity = rootDensity;
        calculateQuality();
        calculateBlockingProbability();
    }

    @Override
    public double calculateQuality() {
        double quality = (getNitrogen() * 1.3) + (getOrganicMatter() * 1.5) +
                (this.rootDensity * 0.8);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        setSoilQuality(normalizedQuality);
        interpretQuality();
        return normalizedQuality;
    }

    @Override
    public double calculateBlockingProbability() {
        double probability = ((50 - this.rootDensity) + (getWaterRetention() * 0.5)) / 75 * 100;

        setBlockingProbability(probability);
        return probability;
    }

    public double getRootDensity() {
        return this.rootDensity;
    }

    public void setRootDensity(double rootDensity) {
        this.rootDensity = rootDensity;
        calculateQuality();
        calculateBlockingProbability();
    }
}
