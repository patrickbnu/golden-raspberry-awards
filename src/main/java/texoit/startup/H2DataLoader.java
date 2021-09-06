package texoit.startup;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import texoit.dto.CSVAwardsDTO;
import texoit.entity.AwardEntity;
import texoit.repository.AwardsRepository;

/**
 * @author Patrick Nascimento
 * @since 2021-09-05
 */
@Component
public class H2DataLoader {

	@Autowired
	private AwardsRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	@EventListener
	public void appReady(ApplicationReadyEvent event) throws IOException {
		Resource resource = new ClassPathResource("movielist.csv");
		CsvSchema csv = CsvSchema.emptySchema().withColumnSeparator(';').withHeader();

		MappingIterator<CSVAwardsDTO> readValues = new CsvMapper().reader(CSVAwardsDTO.class).with(csv).readValues(resource.getFile());

		for (CSVAwardsDTO csvDTO : readValues.readAll()) {
			repository.save(modelMapper.map(csvDTO, AwardEntity.class));
		}
	}
}
