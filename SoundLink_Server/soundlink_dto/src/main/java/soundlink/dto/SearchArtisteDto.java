package soundlink.dto;

public class SearchArtisteDto extends ArtisteDto {

    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
