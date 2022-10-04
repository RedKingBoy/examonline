package dto;

import lombok.Data;
import pojo.PaperConfig;

import java.util.List;

@Data
public class PaperConfigDto {
    private List<PaperConfig> configs;
}
