package subway.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName : subway.domain
 * fileName : Member
 * author : haedoang
 * date : 2021/11/11
 * description :
 */
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Favorite> favorites = new ArrayList<>();

    protected Member() {
    }

    public Member(String memberA) {
        this.name = name;
    }

    public void addFavorite(Favorite favoriteA) {
        this.favorites.add(favoriteA);
    }
}

