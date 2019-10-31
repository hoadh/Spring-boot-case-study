package com.codegym.controller;

import com.codegym.model.*;
import com.codegym.repository.RoleRepository;
import com.codegym.repository.UserRepository;
import com.codegym.service.FootballPlayerServiceInterface;
import com.codegym.service.LocationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Controller
@SessionAttributes("squad")
public class MemberController {

    @ModelAttribute("squad")
    public Squad setupSquad(){
        return new Squad();
    }
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private FootballPlayerServiceInterface footballPlayerServiceInterface;

    @Autowired
    private LocationServiceInterface locationServiceInterface;

    @ModelAttribute("location")
    public Iterable<Location> allPosition() {
        return locationServiceInterface.findAll();


    }
//
//    @GetMapping("registry")
//    public String registryForm() {
//        return "registry";
//    }
//
//    @PostMapping("registry")
//    public String registry(Model model, @RequestParam String username,
//                           @RequestParam String role,
//                           @RequestParam String password) {
//        User admin = new User();
//        admin.setEmail(username);
//        admin.setPassword(passwordEncoder.encode(password));
//        HashSet<Role> roles = new HashSet<>();
//        if (role.equals("ROLE_ADMIN")) {
//            roles.add(roleRepository.findByName("ROLE_ADMIN"));
//            roles.add(roleRepository.findByName("ROLE_MEMBER"));
//        } else {
//
//            roles.add(roleRepository.findByName(role));
//        }
//        admin.setRoles(roles);
//        userRepository.save(admin);
//
//        model.addAttribute("message", "Registed success");
//        return "registry";
//    }




    @GetMapping("/403")
    public String accessDenied() {
        return "403";
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
            personPage = footballPlayerServiceInterface.search(s.get(), pageable);
            modelAndView.addObject("search", s.get());
        } else {
            personPage = footballPlayerServiceInterface.findAll(pageable);
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
        FootballPlayer newPerson = footballPlayerServiceInterface.save(footballPlayer);
        modelAndView.addObject("person", newPerson);
        modelAndView.addObject("myFile", new MyFile());

        return modelAndView;

    }

    //edit

    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("person", footballPlayerServiceInterface.findOne(id));
        modelAndView.addObject("myFile", new MyFile());


        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView edit(FootballPlayer footballPlayer) {
        ModelAndView modelAndView = new ModelAndView("edit");
        footballPlayerServiceInterface.save(footballPlayer);
        modelAndView.addObject("person", footballPlayer);
        modelAndView.addObject("myFile",new MyFile());
        modelAndView.addObject("message", "update success");
        return modelAndView;
    }

    //delete
    @GetMapping("/delete/{id}")
    public ModelAndView deleteForm(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("delete");
        modelAndView.addObject("person", footballPlayerServiceInterface.findOne(id));

        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("delete");
        FootballPlayer person = footballPlayerServiceInterface.findOne(id);
        footballPlayerServiceInterface.delete(id);
        modelAndView.addObject("person", person);
        modelAndView.addObject("message", "Delete success");
        return modelAndView;
    }

    //Info
    @GetMapping("/info/{id}")
    public ModelAndView info(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("info");
        modelAndView.addObject("person", footballPlayerServiceInterface.findOne(id));
        modelAndView.addObject("myFile", new MyFile());
        return modelAndView;
    }


    //Up load images
    @RequestMapping(value = "/uploadFile/{id}", method = RequestMethod.POST)
    public String uploadFile(@PathVariable long id, MyFile myFile, Model model) throws IOException {


        MultipartFile multipartFile = myFile.getMultipartFile();
        String fileName = multipartFile.getOriginalFilename();
        FootballPlayer footballPlayer = footballPlayerServiceInterface.findOne(id);
        footballPlayer.setImg(fileName.intern());
        footballPlayerServiceInterface.save(footballPlayer);

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
        FootballPlayer footballPlayer = footballPlayerServiceInterface.findOne(id);
        footballPlayer.setImg(fileName.intern());
        footballPlayerServiceInterface.save(footballPlayer);

        System.out.println(fileName.intern());
        File file = new File("/home/min2208/Documents/pictures/", fileName);
        multipartFile.transferTo(file);
        model.addAttribute("person", footballPlayerServiceInterface.findOne(id));


        return "edit";
    }

    // add Squad
    @GetMapping("/addSquad/{id}")
    public String addSquad(@PathVariable long id, @ModelAttribute("squad") Squad squad){

        if (!squad.contain(id)){
            squad.addSquad(footballPlayerServiceInterface.findOne(id));
            System.out.println(squad.getSize());
        }

        return "redirect:/home";
    }
    @GetMapping("/listSquad")
    public String listSquad(){
        return "listSquad";
    }

    @GetMapping("/deleteSquad/{id}")
    public String deleteSquad(@PathVariable long id, @ModelAttribute("squad") Squad squad) {
        squad.remove(id);
        return "listSquad";
    }

    //WEB Service


}
