package com.sebastienguillemin.stups.ssm.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sebastienguillemin.stups.ssm.model.Sample;
import com.sebastienguillemin.stups.ssm.repository.SampleRepository;
import com.sebastienguillemin.stups.ssm.request.RuleQuery;
import com.sebastienguillemin.stups.ssm.service.RuleQueryService;

@RestController
public class MatchingController {
    @Autowired
    private SampleRepository repository;

    @Autowired
    private RuleQueryService ruleQueryService;

    @GetMapping({"/", "/selectSamples"})
    public ModelAndView selectSamples() {
        ModelAndView mv = new ModelAndView("matching/samples_selection");

        List<Sample> samples = this.repository.findSamples();
        mv.addObject("samples", samples);

        return mv;
    }

    @PostMapping("/selectSamples")
    public ModelAndView selectSamplesFormHandle(@RequestParam String sample1Id, @RequestParam String sample2Id) throws Exception {
        List<RuleQuery> ruleQueries = this.ruleQueryService.exectuteQueries(sample1Id, sample2Id);
        boolean match = true;
        for (RuleQuery query : ruleQueries)
            if (!query.isPassed()) {
                match = false;
                break;
            }

        Sample sample1 = this.repository.findSampleById(sample1Id);
        Sample sample2 = this.repository.findSampleById(sample2Id);
        System.out.println(sample1.getActivePrinciples().get(0).getName());
        System.out.println(sample1.getActivePrinciples().get(0).getDosage());


        ModelAndView mv = new ModelAndView("matching/rules_results");
        mv.addObject("queries", ruleQueries);
        mv.addObject("sample1", sample1);
        mv.addObject("sample2", sample2);
        mv.addObject("match", match);

        return mv;
    }
}
