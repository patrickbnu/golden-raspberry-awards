package texoit.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Nascimento
 * @since 2021-09-05
 */
public final class ResponseDTO {

	private final List<AwardsWinnersDTO> min = new ArrayList<>();
	private final List<AwardsWinnersDTO> max = new ArrayList<>();

	public List<AwardsWinnersDTO> getMin() {
		return min;
	}

	public List<AwardsWinnersDTO> getMax() {
		return max;
	}
}
