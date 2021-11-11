package subway.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName : subway.domain
 * fileName : LineTest
 * author : haedoang
 * date : 2021/11/11
 * description :
 */
@DataJpaTest
public class LineTest {
    @Autowired
    LineRepository lines;
    @Autowired
    StationRepository stations;

    @Test
    void saveWithLine() {
        final Station expected = new Station("잠실역");
//        expected.setLine(new Line("2호선")); //JPA에서 엔티티를 저장할 때 연관된 모든 엔티티는 영속 상태여야 한다.
        expected.setLine(lines.save(new Line("2호선")));
        final Station actual = stations.save(expected);
        stations.flush();
    }

    @Test
    @DisplayName("select 쿼리가 2번 발생한다")
    void findByNameWithLine() {
        final Station station = stations.findByName("교대역");
        assertThat(station).isNotNull();
        assertThat(station.getLine().getName()).isEqualTo("3호선");
    }

    @Test
    void updateWithLine() {
        final Station station = stations.findByName("교대역");
        station.setLine(lines.save(new Line("2호선")));
        stations.flush();
    }

    //노선을 삭제하려면 기존에 있던 연관 관계를 먼저 제거하고 삭제해야 한다.
    @Test
    @DisplayName("연관 관계를 끊는 법")
    void removeLine() {
        final Station station = stations.findByName("교대역");
        station.setLine(null);
        stations.flush();
    }

    @Test
    void findById() {
        final Line line = lines.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
    }

    @Test
    @DisplayName("연관관계주인이아닌 곳은 읽기만 가능")
    void save() {
        final Line expected = new Line("2호선");
        expected.addStation(stations.save(new Station("잠실역")));
        lines.save(expected);
        lines.flush();
    }
}
