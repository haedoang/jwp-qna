package subway.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName : subway.domain
 * fileName : Line
 * author : haedoang
 * date : 2021/11/11
 * description :
 */
@Entity
public class Line {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "line") //FK를 관리하는 것을 지정해주어야 한다.
    private List<Station> stations = new ArrayList<>();
    protected Line() {
    }

    public Line(String s) {
        this.name = s;
    }

    public String getName() {
        return this.name;
    }

    public List<Station> getStations() {
        return this.stations;
    }

    public void addStation(final Station station) {
        station.setLine(this);
        stations.add(station);
    }
}
