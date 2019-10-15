package com.codegym.controller;

import com.codegym.model.FootballPlayer;
import com.codegym.model.Location;
import com.codegym.model.MyFile;
import com.codegym.model.Squad;
import com.codegym.service.FootballPlayerService;
import com.codegym.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@SessionAttributes("squad")
public class MemberController {

    @ModelAttribute("squad")
    public Squad setupSquad(){
        return new Squad();
    }

    @Autowired
    private FootballPlayerService footballPlayerService;

    @Autowired
    private LocationService locationService;

    @ModelAttribute("location")
    public Iterable<Location> allPosition() {
        return locationService.findAll();
    }

    //home page
    @GetMapping("home")
    public ModelAndView home(@RequestParam("s") Optional<String> s,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size, Pageable pageable) {
        pageable = new PageRequest(page, size);
        ModelAndView modelAndView = new ModelAndView("home");
        Page<FootballPlayer> personPage;
        if (s.isPresent()) {
            personPage = footballPlayerService.search(s.get(), pageable);
            modelAndView.addObject("search", s.get());
        } else {
            personPage = footballPlayerService.findAll(pageable);
            modelAndView.addObject("search", "");
        }

        modelAndView.addObject("persons", personPage);


        return modelAndView;
    }

    //Add member
    @GetMapping("create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("create", "footballPlayer", new FootballPlayer());

        return modelAndView;
    }



    @PostMapping("create")
    public ModelAndView addMember(@Validated @ModelAttribute FootballPlayer footballPlayer, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()){
            ModelAndView modelAndView=new ModelAndView("create");
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("upload");
        FootballPlayer newPerson = footballPlayerService.save(footballPlayer);
        modelAndView.addObject("person", newPerson);
        modelAndView.addObject("myFile", new MyFile());

        return modelAndView;

    }

    //edit

    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("person", footballPlayerService.findOne(id));
        modelAndView.addObject("myFile", new MyFile());


        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView edit(FootballPlayer footballPlayer) {
        ModelAndView modelAndView = new ModelAndView("edit");
        footballPlayerService.save(footballPlayer);
        modelAndView.addObject("person", footballPlayer);
        modelAndView.addObject("myFile",new MyFile());
        modelAndView.addObject("message", "update success");
        return modelAndView;
    }

    //delete
    @GetMapping("/delete/{id}")
    public ModelAndView deleteForm(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("delete");
        modelAndView.addObject("person", footballPlayerService.findOne(id));

        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("delete");
        FootballPlayer person = footballPlayerService.findOne(id);
        footballPlayerService.delete(id);
        modelAndView.addObject("person", person);
        modelAndView.addObject("message", "Delete success");
        return modelAndView;
    }

    //Info
    @GetMapping("/info/{id}")
    public ModelAndView info(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("info");
        modelAndView.addObject("person", footballPlayerService.findOne(id));
        modelAndView.addObject("myFile", new MyFile());
        return modelAndView;
    }


    //Up load images
    @RequestMapping(value = "/uploadFile/{id}", method = RequestMethod.POST)
    public String uploadFile(@PathVariable long id, MyFile myFile, Model model) throws IOException {


        MultipartFile multipartFile = myFile.getMultipartFile();
        String fileName = multipartFile.getOriginalFilename();
        FootballPlayer footballPlayer = footballPlayerService.findOne(id);
        footballPlayer.setImg(fileName.intern());
        footballPlayerService.save(footballPlayer);

        System.out.println(fileName.intern());
        File file = new File("/home/min2208/Documents/pictures/", fileName);
        multipartFile.transferTo(file);


        return "redirect:/home";
    }
    //edit image
    @RequestMapping(value = "/editFile/{id}", method = RequestMethod.POST)
    public String editdFile(@PathVariable long id, MyFile myFile, Model model) throws IOException {


        MultipartFile multipartFile = myFile.getMultipartFile();
        String fileName = multipartFile.getOriginalFilename();
        FootballPlayer footballPlayer = footballPlayerService.findOne(id);
        footballPlayer.setImg(fileName.intern());
        footballPlayerService.save(footballPlayer);

        System.out.println(fileName.intern());
        File file = new File("/home/min2208/Documents/pictures/", fileName);
        multipartFile.transferTo(file);
        model.addAttribute("person",footballPlayerService.findOne(id));


        return "edit";
    }

    // add Squad
    @GetMapping("/addSquad/{id}")
    public String addSquad(@PathVariable long id, @ModelAttribute("squad") Squad squad){

        if (!squad.contain(id)){
            squad.addSquad(footballPlayerService.findOne(id));
            System.out.println(squad.getSize());
        }

        return "redirect:/home";
    }

    @GetMapping("/listSquad")
    public String listSquad(){
        return "listSquad";
    }

    //WEB Service

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FootballPlayer>> listMember(){
        List<FootballPlayer> footballPlayers = footballPlayerService.findAll();
        if (footballPlayers.isEmpty()){
            return new ResponseEntity<List<FootballPlayer>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<FootballPlayer>>(footballPlayers,HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FootballPlayer> getMember(@PathVariable long id){
        FootballPlayer footballPlayer=footballPlayerService.findOne(id);
        if(footballPlayer==null){
            return new ResponseEntity<FootballPlayer>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<FootballPlayer>(footballPlayer,HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FootballPlayer> deleteMember(@PathVariable long id){
        if (footballPlayerService.findOne(id)==null){
            return new ResponseEntity<FootballPlayer>(HttpStatus.NO_CONTENT);
        }else {
            FootballPlayer footballPlayer = footballPlayerService.findOne(id);
            footballPlayerService.delete(id);
            return new ResponseEntity<FootballPlayer>(footballPlayer,HttpStatus.OK);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/add" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addMember(@Validated @ModelAttribute FootballPlayer footballPlayer){
        footballPlayerService.save(footballPlayer);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping(value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FootballPlayer> updateMember(@PathVariable("id") long id,
                                                      @RequestBody FootballPlayer footballPlayer) {
        FootballPlayer originMember = footballPlayerService.findOne(id);

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
            Location originLocation = locationService.findOne(footballPlayer.getLocation().getId());
            if (originLocation == null) {
                return new ResponseEntity<FootballPlayer>(HttpStatus.BAD_REQUEST);
            }
            originMember.setLocation(originLocation);
        }

        footballPlayerService.save(originMember);
        return new ResponseEntity<FootballPlayer>(originMember, HttpStatus.OK);
    }

}
