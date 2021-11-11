package subway.domain;

import javax.persistence.*;

/**
 * packageName : subway.domain
 * fileName : Station
 * author : haedoang
 * date : 2021/11/11
 * description :
 */
@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY인 경우 save() 시 바로 DB에 반영된다..
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
//    @JoinColumn(name = "line_id")
    private Line line;

    public Station(String station) {
        this.name = station;
    }

    protected Station() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Line getLine() {
        return this.line;
    }
}
