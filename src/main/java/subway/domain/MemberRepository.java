package subway.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName : subway.domain
 * fileName : MemberRepository
 * author : haedoang
 * date : 2021/11/11
 * description :
 */
public interface MemberRepository extends JpaRepository<Member,Long> {
}
