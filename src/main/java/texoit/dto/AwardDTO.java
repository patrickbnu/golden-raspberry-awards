package texoit.dto;

import java.util.List;

/**
 * @author Patrick Nascimento
 * @since 2021-09-05
 */
public final class AwardDTO {

	private Integer id;
	private Integer year;
	private String title;
	private List<String> studios;
	private List<String> producers;
	private String winner;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public void setStudios(List<String> studios) {
		this.studios = studios;
	}

	public List<String> getProducers() {
		return producers;
	}

	public void setProducers(List<String> producers) {
		this.producers = producers;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

}
