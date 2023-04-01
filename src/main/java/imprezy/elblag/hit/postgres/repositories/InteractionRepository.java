package imprezy.elblag.hit.postgres.repositories;

import imprezy.elblag.hit.postgres.models.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {
}
