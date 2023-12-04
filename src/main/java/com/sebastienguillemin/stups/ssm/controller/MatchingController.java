package com.sebastienguillemin.stups.ssm.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MatchingController {
    @GetMapping("/")
    public ModelAndView selectSamples() {
        ModelAndView mv = new ModelAndView("matching/samples_selection");

        List<String> samples = Arrays.asList("Echantillon 1", "Echantillon 2", "Echantillon 3", "Echantillon 4", "Echantillon 5");

        mv.addObject("samples", samples);

        return mv;
    }
}
