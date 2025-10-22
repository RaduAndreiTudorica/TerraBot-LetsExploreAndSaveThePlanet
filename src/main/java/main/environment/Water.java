package main.environment;

public class Water {
    private double salinity;
    private double pH;
    private double purity;
    private int turbidity;
    private double contaminantIndex;
    private boolean isFrozen;
    private double water_quality;

    public Water(double salinity, double pH,
                 double purity, int turbidity,
                 double contaminantIndex, boolean isFrozen) {
        this.salinity = salinity;
        this.pH = pH;
        this.purity = purity;
        this.turbidity = turbidity;
        this.contaminantIndex = contaminantIndex;
        this.isFrozen = isFrozen;
        this.water_quality = calculateWaterQuality();
    }

    private double calculateWaterQuality() {
        double purity_score = this.purity / 100.0;
        double pH_score = 1.0 - Math.abs(this.pH - 7.5) / 7.5;
        double salinity_score = 1.0 - (this.salinity / 350.0);
        double turbidity_score = 1.0 - (this.turbidity / 100.0);
        double contaminant_score = 1.0 - (this.contaminantIndex / 100.0);
        double isFrozen = this.isFrozen ? 0.0 : 1.0;

        return (0.3 * purity_score + 0.2 * pH_score + 0.15 * salinity_score +
                               0.1 * turbidity_score + 0.15 * contaminant_score + 0.2 * isFrozen) * 100.0;
    }
}
