package main.environment.air;

public class MountainAir extends Air {
    public static final double TOXICITY_MAX_SCORE = 78.0;
    private double altitude;
    private int numberOfHikers = 0;

    public MountainAir() {
        super();
        this.altitude = 0.0;
        calculateQuality();
        calculateToxicityAQ();
    }

    public MountainAir(String name, double mass, String type, double humidity,
                       double temperature, double oxygenLevel, double altitude) {
        super(name, mass, type, humidity, temperature, oxygenLevel);

        this.altitude = altitude;
        calculateQuality();
        interpretQuality();
        calculateToxicityAQ();
    }

    @Override
    public double calculateQuality() {
        double oxygenFactor = getOxygenLevel() - (this.altitude / 1000.0 * 0.5);
        double airQuality = (oxygenFactor * 2) + (getHumidity() * 0.6);

        airQuality = Math.round(airQuality * 100.0) / 100.0;
        setAirQuality(airQuality);
        calculateToxicityAQ();
        return airQuality;
    }

    @Override
    public double updateQuality() {
        double newAirQuality = getAirQuality() - (this.numberOfHikers * 0.1);

        newAirQuality = Math.round(newAirQuality * 100.0) / 100.0;
        setAirQuality(newAirQuality);
        calculateToxicityAQ();
        return newAirQuality;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
        calculateQuality();
        calculateToxicityAQ();
    }
}
