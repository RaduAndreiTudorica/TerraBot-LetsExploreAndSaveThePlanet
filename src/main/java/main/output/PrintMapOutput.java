package main.output;

public class PrintMapOutput {
    private int[] section;
    private int totalNrOfObjects;
    private String airQuality;
    private String soilQuality;

    public PrintMapOutput(int[] section, int totalNrOfObjects, String airQuality, String soilQuality) {
        this.section = section;
        this.totalNrOfObjects = totalNrOfObjects;
        this.airQuality = airQuality;
        this.soilQuality = soilQuality;
    }

    public int[] getSection() { return section; }
    public int getTotalNrOfObjects() { return totalNrOfObjects; }
    public String getAirQuality() { return airQuality; }
    public String getSoilQuality() { return soilQuality; }
}
