package subway.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName : subway.domain
 * fileName : StationTest
 * author : haedoang
 * date : 2021/11/11
 * description :
 */
@DataJpaTest
public class StationTest {

    @Autowired
    private StationRepository stations;

    @Test
    void save() {
        //begin
        final Station station = new Station("잠실역");
        final Station actual = stations.save(station);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("잠실역");
        //commit이지 않아..? =>   GenrationType.IDENTITY이기 때문에 save 시점에 DB에 반영이된다..
    }

    @Test
    void findByName() {
        final Station station1 = stations.save(new Station("잠실역"));
        Station station2 = stations.findByName("잠실역");
        assertThat(station1.getId()).isEqualTo(station2.getId());
        assertThat(station1.getName()).isEqualTo(station2.getName());
        assertThat(station1).isSameAs(station2); //주소값을 비교함
    }

    @Test
    void findById() {
        final Station station1 = stations.save(new Station("잠실역"));
        Station station2 = stations.findById(station1.getId()).get();  //1차 캐시 사용
        assertThat(station1).isSameAs(station2);
    }

    @Test
    void update() {
        final Station station1 = stations.save(new Station("잠실역"));
        station1.changeName("몽촌토성역");
//        station1.changeName("잠실역");  update query 가 나가는 이유 ? findByName이 DB에서 조회하기때문에 이전 영속성에 있는 것을 flush 함.
        final Station station2 = stations.findByName("몽촌토성역");
        assertThat(station2).isNotNull();
    }

}
