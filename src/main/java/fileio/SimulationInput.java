package fileio;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class SimulationInput {
    private String territoryDim;
    private int energyPoints;
    private TerritorySectionParamsInput territorySectionParams;
}
