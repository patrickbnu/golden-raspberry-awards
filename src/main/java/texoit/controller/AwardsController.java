package texoit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import texoit.dto.AwardDTO;
import texoit.dto.ResponseDTO;
import texoit.service.AwardsService;

/**
 * @author Patrick Nascimento
 * @since 2021-09-05
 */
@RestController
public class AwardsController {

    @Autowired
    private AwardsService service;
    
    @GetMapping(value = "/v1/awards")
    public ResponseEntity<List<AwardDTO>> getAllAwards(){
        return service.getAllAwards();
    }
    
    @GetMapping(value = "/v1/awards/{id}")
    public ResponseEntity<AwardDTO> findById(@PathVariable("id") Integer id){
        return service.findAwardsById(id);
    }
    
    @GetMapping(value = "/v1/winners")
    public ResponseEntity<ResponseDTO> filterAwardsWinners(){
        return service.filterIntervalAwardWinners();
    }

}
