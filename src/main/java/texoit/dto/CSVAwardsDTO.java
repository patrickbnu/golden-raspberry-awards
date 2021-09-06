package texoit.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Patrick Nascimento
 * @since 2021-09-05
 */
public final class CSVAwardsDTO {

	private Integer year;
	private String title;
	private List<String> studios;
	private List<String> producers;
	private String winner;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getStudios() {
		return studios;
	}

	public void setStudios(String studios) {
		List<String> studiosList = Arrays.asList(studios.replaceAll(" and ", ",").split(","));
		List<String> trimeList = studiosList.stream().map(String::trim).collect(Collectors.toList());

		this.studios = trimeList;
	}

	public List<String> getProducers() {
		return producers;
	}

	public void setProducers(String producers) {
		List<String> producersList = Arrays.asList(producers.replaceAll(" and ", ",").split(","));
		List<String> trimedStrings = producersList.stream().map(String::trim).collect(Collectors.toList());

		this.producers = trimedStrings;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
}
