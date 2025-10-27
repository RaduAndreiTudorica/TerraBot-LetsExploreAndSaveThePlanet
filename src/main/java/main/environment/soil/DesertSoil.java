package main.environment.soil;

public class DesertSoil extends Soil {
    private double salinity;

    public DesertSoil() {
        super();
        this.salinity = 0.0;
        calculateQuality();
        calculateBlockingProbability();
    }

    public DesertSoil(String name, double mass, double nitrogen,
                      double waterRetention, double soilpH,
                      double organicMatter, double salinity) {
        super(name, mass, "Desert",
              nitrogen, waterRetention,
              soilpH, organicMatter);
        this.salinity = salinity;
        calculateQuality();
        calculateBlockingProbability();
    }

    @Override
    public double calculateQuality() {
        double quality = (getNitrogen() * 0.5) + (getWaterRetention() * 0.3) -
                         (this.salinity * 2);

        double normalizedQuality = Math.max(0, Math.min(quality, 100.0));

        setSoilQuality(normalizedQuality);
        interpretQuality();
        return normalizedQuality;
    }

    @Override
    public double calculateBlockingProbability() {
        double probability = (100 - getWaterRetention() + this.salinity) / 100 * 100;

        setBlockingProbability(probability);
        return probability;
    }

    public double getSalinity() {
        return this.salinity;
    }

    public void setSalinity(double salinity) {
        this.salinity = salinity;
        calculateQuality();
        calculateBlockingProbability();
    }
}
