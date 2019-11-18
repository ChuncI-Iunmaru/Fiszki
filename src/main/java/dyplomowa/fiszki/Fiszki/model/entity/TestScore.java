package dyplomowa.fiszki.Fiszki.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "test_score")
public class TestScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //Na razie bez dwukierunkowego związku, bo nwm czy będzie potrzebny

    @Column(name = "score")
    private int score;
}
