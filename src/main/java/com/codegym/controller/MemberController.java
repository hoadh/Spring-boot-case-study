package com.codegym.controller;

import com.codegym.model.FootballPlayer;
import com.codegym.service.FootballPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.util.Optional;

@Controller
public class MemberController {

//    @Autowired
//    private FootballPlayerService footballPlayerService;
//
//    @GetMapping("/")
//    public ModelAndView home(@RequestParam("s")Optional<String> s,
//                             @RequestParam(defaultValue = "0") int page,
//                             @RequestParam(defaultValue = "5") int size,
//                             Pageable pageable){
//        pageable = new PageRequest(page,size);
//        ModelAndView modelAndView= new ModelAndView("home");
//        Page<FootballPlayer> footballPlayers;
//        if (s.isPresent()){
//            footballPlayers=footballPlayerService.search(s.get(),pageable);
//            modelAndView.addObject("search",s.get());
//        }else {
//            footballPlayers=footballPlayerService.findAll(pageable);
//            modelAndView.addObject("search","");
//        }
//        modelAndView.addObject("persons",footballPlayers);
//        return modelAndView;
//
//    }




}
