package fileio;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public final class SoilInput {
    private String type;
    private String name;
    private double mass;
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    private double leafLitter;
    private double waterLogging;
    private double permafrostDepth;
    private double rootDensity;
    private double salinity;
    private List<PairInput> sections;
}

