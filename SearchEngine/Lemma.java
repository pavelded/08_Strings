import javax.persistence.*;

@Entity
@Table(name = "lemma")
public class Lemma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Column(name = "lemma", columnDefinition = "varchar(255)")
    private String lemma;

    @Column(name = "frequency")
    private int frequency = 1;

    @Column(name = "site_id")
    private int site_id;

    public Lemma(String lemma) {
        this.lemma = lemma;
    }

    public void setFrequency(int count) {
        frequency = count;
    }

    public int getFrequency() {
        return frequency;
    }
}
