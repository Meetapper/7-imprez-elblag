package imprezy.elblag.hit.postgres.repositories;

import imprezy.elblag.hit.postgres.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

//    @Query(value = "SELECT u from User u where u in ?1")
//    List<User> mapIdToUser(List<Long> ids);


    @Query(value = "select users.username, liked / CAST(liked + seen as float) as likeRatio\n" +
            "from (select count(*) as liked, u.username\n" +
            "      from users u\n" +
            "               full outer join interactions i on u.id = i.receiver_id\n" +
            "      where interaction_type = 2\n" +
            "      group by username) d2\n" +
            "         full outer join (select count(*) as seen, u.username\n" +
            "                          from users u\n" +
            "                                   inner join interactions i on u.id = i.receiver_id\n" +
            "                          where interaction_type = 1\n" +
            "                          group by username) d1 on d1.username = d2.username\n" +
            "         full outer join users on users.username = d2.username\n" +
            "where users.id in ?1\n" +
            "order by likeRatio", nativeQuery = true)
    List<Object[]> mapIdToUser(List<Long> ids);
}
