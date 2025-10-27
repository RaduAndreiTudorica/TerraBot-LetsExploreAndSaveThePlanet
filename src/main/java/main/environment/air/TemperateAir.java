package main.environment.air;

public class TemperateAir extends Air {
    public static final double TOXICITY_MAX_SCORE = 84.0;

    private double pollenLevel;
    private static final String[] SEASONS = {"Spring", "Summer", "Autumn", "Winter"};
    private String currentSeason = SEASONS[0];

    public TemperateAir() {
        super();
        this.pollenLevel = 0.0;
        calculateQuality();
        calculateToxicityAQ();
    }

    public TemperateAir(String name, double mass, String type, double humidity,
                        double temperature, double oxygenLevel, double pollenLevel, String currentSeason) {
        super(name, mass, type, humidity, temperature, oxygenLevel);

        this.pollenLevel = pollenLevel;
        calculateQuality();
        interpretQuality();
        calculateToxicityAQ();
    }

    @Override
    public double calculateQuality() {
        double airQuality = (getOxygenLevel() * 2) + (getHumidity() * 0.7) - (this.pollenLevel * 0.1);

        airQuality = Math.round(airQuality * 100.0) / 100.0;
        setAirQuality(airQuality);
        calculateToxicityAQ();
        return airQuality;
    }

    @Override
    public double updateQuality() {
        double seasonPenalty = currentSeason.equalsIgnoreCase(SEASONS[0]) ? 15 : 0;

        setAirQuality(seasonPenalty);
        calculateToxicityAQ();
        return seasonPenalty;
    }

    public double getPollenLevel() {
        return this.pollenLevel;
    }

    public void setPollenLevel(double pollenLevel) {
        this.pollenLevel = pollenLevel;
        calculateQuality();
    }

    public String getCurrentSeason() {
        return this.currentSeason;
    }

    public void setCurrentSeason(String currentSeason) {
        this.currentSeason = currentSeason;
        updateQuality();
    }
}
