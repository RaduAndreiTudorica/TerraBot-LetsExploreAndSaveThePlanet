package main.environment.soil;

public class SwampSoil extends Soil {
    private double waterLogging;

    public SwampSoil() {
        super();
        this.waterLogging = 0.0;
        calculateQuality();
        calculateBlockingProbability();
    }

    public SwampSoil(String name, double mass, double nitrogen,
                     double waterRetention, double soilpH,
                     double organicMatter, double waterLogging) {
        super(name, mass, "Swamp",
              nitrogen, waterRetention,
              soilpH, organicMatter);
        this.waterLogging = waterLogging;
        calculateQuality();
        calculateBlockingProbability();
    }

    @Override
    public double calculateQuality() {
        double quality = (getNitrogen() * 1.1) + (getOrganicMatter() * 2.2) -
                (this.waterLogging * 5);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        setSoilQuality(normalizedQuality);
        interpretQuality();
        return normalizedQuality;
    }

    @Override
    public double calculateBlockingProbability() {
        double probability = this.waterLogging * 10;

        setBlockingProbability(probability);
        return probability;
    }

    public double getWaterLogging() {
        return this.waterLogging;
    }

    public void setWatterLogging(double watterLogging) {
        this.waterLogging = watterLogging;
        calculateQuality();
        calculateBlockingProbability();
    }

}
