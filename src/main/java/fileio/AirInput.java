package fileio;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public final class AirInput {
    private String type;
    private String name;
    private double mass;
    private double humidity;
    private double temperature;
    private double oxygenLevel;
    private double altitude;
    private double pollenLevel;
    private double co2Level;
    private double iceCrystalConcentration;
    private double dustParticles;
    private List<PairInput> sections;
}

