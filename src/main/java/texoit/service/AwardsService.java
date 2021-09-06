package texoit.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import texoit.dto.AwardDTO;
import texoit.dto.ResponseDTO;

/**
 * @author Patrick Nascimento
 * @since 2021-09-05
 */
@Service
public final class AwardsService {

	public ResponseEntity<List<AwardDTO>> getAllAwards() {
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<AwardDTO> findAwardsById(Integer id) {
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<ResponseDTO> filterIntervalAwardWinners() {
		return ResponseEntity.notFound().build();
	}

}
