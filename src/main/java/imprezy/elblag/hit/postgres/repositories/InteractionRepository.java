package imprezy.elblag.hit.postgres.repositories;

import imprezy.elblag.hit.postgres.models.Interaction;
import imprezy.elblag.hit.postgres.models.InteractionType;
import imprezy.elblag.hit.postgres.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    @Query(value = "SELECT i from Interaction i " +
            "where i.interactionId.receiver = ?2 and i.interactionId.sender = ?1 and i.interactionId.date = ?3")
    Optional<Interaction> findBy(User sender, User receiver, LocalDate date);

    @Query(value = "SELECT i.interactionType, count(*) from Interaction i where i.interactionId.date = CURRENT_DATE group by i.interactionId.receiver, i.interactionType having i.interactionId.receiver = ?1")
    Optional<List<Object[]>> getStats(User user);
}
