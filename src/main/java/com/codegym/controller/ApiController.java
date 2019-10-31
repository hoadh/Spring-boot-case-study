package com.codegym.controller;

import com.codegym.model.FootballPlayer;
import com.codegym.model.Location;
import com.codegym.service.FootballPlayerService;
import com.codegym.service.FootballPlayerServiceInterface;
import com.codegym.service.LocationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class ApiController {
    @Autowired
    private FootballPlayerServiceInterface footballPlayerServiceInterface;

    @Autowired
    private LocationServiceInterface locationServiceInterface;

    @ResponseBody
    @RequestMapping(value = "/api", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FootballPlayer>> listMember(){
        List<FootballPlayer> footballPlayers = footballPlayerServiceInterface.findAll();
        if (footballPlayers.isEmpty()){
            return new ResponseEntity<List<FootballPlayer>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<FootballPlayer>>(footballPlayers,HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/api/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FootballPlayer> getMember(@PathVariable long id){
        FootballPlayer footballPlayer= footballPlayerServiceInterface.findOne(id);
        if(footballPlayer==null){
            return new ResponseEntity<FootballPlayer>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<FootballPlayer>(footballPlayer,HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/api/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FootballPlayer> deleteMember(@PathVariable long id){
        if (footballPlayerServiceInterface.findOne(id)==null){
            return new ResponseEntity<FootballPlayer>(HttpStatus.NO_CONTENT);
        }else {
            FootballPlayer footballPlayer = footballPlayerServiceInterface.findOne(id);
            footballPlayerServiceInterface.delete(id);
            return new ResponseEntity<FootballPlayer>(footballPlayer,HttpStatus.OK);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/api/add" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addMember(@Validated @ModelAttribute FootballPlayer footballPlayer){
        footballPlayerServiceInterface.save(footballPlayer);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(value = "/api/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FootballPlayer> updateMember(@PathVariable("id") long id,
                                                       @RequestBody FootballPlayer footballPlayer) {
        FootballPlayer originMember = footballPlayerServiceInterface.findOne(id);

        if (originMember == null) {
            return new ResponseEntity<FootballPlayer>(HttpStatus.NOT_FOUND);
        }

        originMember.setFirstName(footballPlayer.getFirstName());
        originMember.setLastName(footballPlayer.getLastName());
        originMember.setAddress(footballPlayer.getAddress());
        originMember.setAge(footballPlayer.getAge());
        originMember.setHeight(footballPlayer.getHeight());
        originMember.setWeight(footballPlayer.getWeight());


        if (footballPlayer.getLocation() != null) {
            Location originLocation = locationServiceInterface.findOne(footballPlayer.getLocation().getId());
            if (originLocation == null) {
                return new ResponseEntity<FootballPlayer>(HttpStatus.BAD_REQUEST);
            }
            originMember.setLocation(originLocation);
        }

        footballPlayerServiceInterface.save(originMember);
        return new ResponseEntity<FootballPlayer>(originMember, HttpStatus.OK);
    }

}
