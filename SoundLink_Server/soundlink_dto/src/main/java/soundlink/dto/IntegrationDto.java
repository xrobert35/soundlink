package soundlink.dto;

import java.util.ArrayList;
import java.util.List;

public class IntegrationDto {

    private List<ArtisteDto> artistes = new ArrayList<>();

    private List<FileIntegrationErrorDto> errors = new ArrayList<>();

    public List<ArtisteDto> getArtistes() {
        return artistes;
    }

    public void setArtistes(List<ArtisteDto> artistes) {
        this.artistes = artistes;
    }

    public List<FileIntegrationErrorDto> getErrors() {
        return errors;
    }

    public void setErrors(List<FileIntegrationErrorDto> errors) {
        this.errors = errors;
    }
}
