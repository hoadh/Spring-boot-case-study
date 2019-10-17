package com.codegym.controller;

import com.codegym.model.FootballPlayer;
import com.codegym.model.MyFile;
import com.codegym.service.FootballPlayerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private FootballPlayerServiceInterface footballPlayerServiceInterface;

    @GetMapping
    public ModelAndView home(@RequestParam("s") Optional<String> s,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size, Pageable pageable) {
        pageable = new PageRequest(page, size);
        ModelAndView modelAndView = new ModelAndView("homeUser");
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

    @GetMapping("/info/{id}")
    public ModelAndView info(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("infoUser");
        modelAndView.addObject("person", footballPlayerServiceInterface.findOne(id));
        return modelAndView;
    }
}
