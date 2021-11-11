package subway.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName : subway.domain
 * fileName : LineRepository
 * author : haedoang
 * date : 2021/11/11
 * description :
 */
public interface LineRepository extends JpaRepository<Line, Long> {

    Line findByName(String name);
}
