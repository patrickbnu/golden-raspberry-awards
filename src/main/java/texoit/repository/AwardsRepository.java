package texoit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import texoit.entity.AwardEntity;

/**
 * @author Patrick Nascimento
 * @since 2021-09-05
 */
public interface AwardsRepository extends JpaRepository<AwardEntity, Integer> {

    List<AwardEntity> findByWinner(String winner);
}
