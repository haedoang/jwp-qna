package subway.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * packageName : subway.domain
 * fileName : Favorite
 * author : haedoang
 * date : 2021/11/11
 * description :
 */
@Entity
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected Favorite(){
    }

    public Favorite(String favoriteA) {

    }
}
