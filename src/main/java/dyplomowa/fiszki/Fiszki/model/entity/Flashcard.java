package dyplomowa.fiszki.Fiszki.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "flashcard")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Flashcard.class)
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIdentityReference
    private User user;

    @Column(name = "front_text")
    private String frontText;
    @Column(name = "back_text")
    private String backText;
    @Column(name = "extra_text")
    private String extraText;

    @ElementCollection
    @CollectionTable(name = "tag", joinColumns = @JoinColumn(name = "id_flashcard"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    @ManyToMany(mappedBy = "flashcards")
    @JsonIdentityReference
    private Set<FlashcardSet> flashcardSets;

    public Flashcard() {
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
