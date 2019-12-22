package dyplomowa.fiszki.Fiszki.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.aspectj.weaver.ast.Test;

import javax.persistence.*;

@Entity
@Table(name = "test_score")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = TestScore.class)
public class TestScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //Na razie bez dwukierunkowego związku, bo nwm czy będzie potrzebny

    @Column(name = "score")
    private int score;

    public TestScore() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
