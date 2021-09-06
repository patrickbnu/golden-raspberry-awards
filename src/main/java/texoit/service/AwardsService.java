package texoit.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import texoit.dto.AwardDTO;
import texoit.dto.AwardsWinnersDTO;
import texoit.dto.ResponseDTO;
import texoit.entity.AwardEntity;
import texoit.repository.AwardsRepository;

/**
 * @author Patrick Nascimento
 * @since 2021-09-05
 */
@Service
public final class AwardsService {

	private static final String WINNER_YES = "yes";

	@Autowired
	private AwardsRepository repository;

	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<List<AwardDTO>> getAllAwards() {
		List<AwardDTO> winners = new ArrayList<>();
		repository.findAll().stream().forEach(a -> winners.add(mapper.map(a, AwardDTO.class)));

		return ResponseEntity.ok().body(winners);
	}

	public ResponseEntity<AwardDTO> findAwardsById(Integer id) {
		Optional<AwardEntity> findById = repository.findById(id);
		if (findById.isPresent()) {
			return ResponseEntity.ok().body(mapper.map(findById.get(), AwardDTO.class));
		}

		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<ResponseDTO> filterIntervalAwardWinners() {
		
		// ganhadores ordenados.
		List<AwardEntity> winners = repository.findByWinner(WINNER_YES).stream().sorted(Comparator.comparingInt(AwardEntity::getYear)).collect(Collectors.toList());
		
		Map<String, List<AwardEntity>> groupedByProducers = new HashMap<String, List<AwardEntity>>();
		for (AwardEntity award : winners) {
			for (String producer : award.getProducers()) {
				
				List<AwardEntity> producerAwards = groupedByProducers.get(producer);
				
				if (null == producerAwards) {
					producerAwards = new ArrayList<AwardEntity>();
					groupedByProducers.put(producer, producerAwards);
				}
				
				producerAwards.add(award);
			}
		}
		
		// reduzimos para apenas os quem tem mais do que um prêmio
		groupedByProducers = groupedByProducers.entrySet().stream()
	                .filter(producerAwards -> producerAwards.getValue().size() > 1)
	                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		
		return calculateWinners(groupedByProducers);
	}

	private ResponseEntity<ResponseDTO> calculateWinners(Map<String, List<AwardEntity>> groupedByProducers) {
		ResponseDTO responseDTO = new ResponseDTO();
		int max = 0;
		int min = Integer.MAX_VALUE;
		
		// leitura e cálculo no mesmo laço
		
		for (Entry<String, List<AwardEntity>> entry: groupedByProducers.entrySet()) {
			List<AwardEntity> awards = entry.getValue();
			
			for (int j = 1; j < awards.size(); j++) {
				AwardEntity currentAward = awards.get(j);
				AwardEntity previouslyAward = awards.get(j-1);
				
				int range = currentAward.getYear() - previouslyAward.getYear();
				
				if (range < min) { // achamos um menor, reseta a lista
					min = range;
					responseDTO.getMin().clear(); 
					responseDTO.getMin().add(new AwardsWinnersDTO(entry.getKey(), min, previouslyAward.getYear(), currentAward.getYear()));
				} else if (range == min) { // achamos um com o mesmo range, apenas adiciona
					responseDTO.getMin().add(new AwardsWinnersDTO(entry.getKey(), min, previouslyAward.getYear(), currentAward.getYear()));
				}
				
				if (range > max) {
					max = range;
					responseDTO.getMax().clear(); 
					responseDTO.getMax().add(new AwardsWinnersDTO(entry.getKey(), max, previouslyAward.getYear(), currentAward.getYear()));
				} else if (range == max) {
					responseDTO.getMax().add(new AwardsWinnersDTO(entry.getKey(), max, previouslyAward.getYear(), currentAward.getYear()));
				}
				
			}
		}
		
        return ResponseEntity.ok().body(responseDTO);
	}

}
