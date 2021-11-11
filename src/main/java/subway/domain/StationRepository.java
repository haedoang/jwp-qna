package subway.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName : subway.domain
 * fileName : StationRepository
 * author : haedoang
 * date : 2021/11/11
 * description :
 */
public interface StationRepository extends JpaRepository<Station, Long> {
    Station findByName(String name);
}
