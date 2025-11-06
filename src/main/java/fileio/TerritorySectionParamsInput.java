package fileio;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TerritorySectionParamsInput {
    private List<SoilInput> soil;
    private List<PlantInput> plants;
    private List<AnimalInput> animals;
    private List<WaterInput> water;
    private List<AirInput> air;
}


