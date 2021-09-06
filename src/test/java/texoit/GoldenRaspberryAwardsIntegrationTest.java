package texoit;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import texoit.dto.AwardDTO;
import texoit.dto.ResponseDTO;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoldenRaspberryAwardsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class GoldenRaspberryAwardsIntegrationTest {
	
	private static final Integer EXPECTED_MAX_INTERVAL = 13;
	private static final Integer EXPECTED_MIN_INTERVAL = 1;
	
	private static final Integer SOME_AWARD_ID = 1;
	private static final Integer INEXISTENT_AWARD_ID = 0;

	@Autowired
    private TestRestTemplate restTemplate;
	
    @LocalServerPort
    private int port;
    
    @Test
    public void retriveAwards() {
    	ResponseEntity<AwardDTO[]> response = restTemplate.getForEntity(getPath("/v1/awards"), AwardDTO[].class);
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertTrue(response.getBody().length > 0);
    }
    
    @Test
    public void retriveAwardsById() {
    	ResponseEntity<AwardDTO> response = restTemplate.getForEntity(getPath("/v1/awards/{id}"), AwardDTO.class, SOME_AWARD_ID);
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertNotNull(response.getBody());
    }
    @Test
    public void retriveAwardsByIdNotFound() {
    	ResponseEntity<AwardDTO> response = restTemplate.getForEntity(getPath("/v1/awards/{id}"), AwardDTO.class, INEXISTENT_AWARD_ID);
    	assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    public void testeMinInterval() {
    	ResponseEntity<ResponseDTO> response = restTemplate.getForEntity(getPath("/v1/winners"), ResponseDTO.class);
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(EXPECTED_MIN_INTERVAL, response.getBody().getMin().get(0).getInterval());
    }
    
    @Test
    public void testeMaxInterval() {
    	ResponseEntity<ResponseDTO> response = restTemplate.getForEntity(getPath("/v1/winners"), ResponseDTO.class);
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(EXPECTED_MAX_INTERVAL, response.getBody().getMax().get(0).getInterval());
    }
    
    private String getPath(String path) {
        return String.format("http://localhost:%s%s", port, path);
    }
}
