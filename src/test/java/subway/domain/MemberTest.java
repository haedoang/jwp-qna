package subway.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * packageName : subway.domain
 * fileName : MemberTest
 * author : haedoang
 * date : 2021/11/11
 * description :
 */
@DataJpaTest
public class MemberTest {
    @Autowired
    MemberRepository members;
    @Autowired
    FavoriteRepository favorites;

    @Test
    @DisplayName("insert 2개, update쿼리가 날아간다.")
    void save() {
        final Member memberA = new Member("memberA");
        memberA.addFavorite(favorites.save(new Favorite("favoriteA")));
        members.save(memberA);
        members.flush();
    }
}
