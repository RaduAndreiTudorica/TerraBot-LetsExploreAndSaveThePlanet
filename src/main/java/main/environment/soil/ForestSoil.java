package main.environment.soil;

public class ForestSoil extends Soil {
    private double leafLitter;

    public ForestSoil() {
        super();
        this.leafLitter = 0.0;
        calculateQuality();
        calculateBlockingProbability();
    }

    public ForestSoil(String name, double mass, double nitrogen,
                      double waterRetention, double soilpH,
                      double organicMatter, double leafLitter) {
        super(name, mass, "Forest",
              nitrogen, waterRetention,
              soilpH, organicMatter);
        this.leafLitter = leafLitter;
        calculateQuality();
        calculateBlockingProbability();
    }

    @Override
    public double calculateQuality() {
        double quality = (getNitrogen() * 1.2) + (getWaterRetention() * 1.5) +
                         (getOrganicMatter() * 2) + (this.leafLitter * 0.3);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        setSoilQuality(normalizedQuality);
        interpretQuality();
        return normalizedQuality;
    }

    @Override
    public double calculateBlockingProbability() {
        double probability = ((getWaterRetention() * 0.6) + (this.leafLitter * 0.4)) / 80 * 100;

        setBlockingProbability(probability);
        return probability;
    }

    public double getLeafLitter() {
        return this.leafLitter;
    }

    public void setLeafLitter(double leafLitter) {
        this.leafLitter = leafLitter;
        calculateQuality();
        calculateBlockingProbability();
    }
}
