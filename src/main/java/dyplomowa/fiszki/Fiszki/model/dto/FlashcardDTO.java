package dyplomowa.fiszki.Fiszki.model.dto;

import java.util.Set;

public class FlashcardDTO {

    //TODO może refactor, bo to nie jest bardzo potrzebna warstwa - czy przesyłanie użytkownika to tak duży problem

    private long id;
    private String frontText;
    private String backText;
    private String extraText;
    private Set<String> tags;
    //Nie przesyłać w jsonie całego użytkownika, tylko jego id?
    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFrontText() {
        return frontText;
    }

    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }

    public String getBackText() {
        return backText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }

    public String getExtraText() {
        return extraText;
    }

    public void setExtraText(String extraText) {
        this.extraText = extraText;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
