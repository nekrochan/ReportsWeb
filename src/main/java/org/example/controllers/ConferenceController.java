package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.service.dto.ConferenceDto;
import org.example.service.interfaces.ConferenceService;
import org.example.service.interfaces.FounderService;
import org.example.service.interfaces.HostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/conferences")
public class ConferenceController {

    @Autowired
    private final ConferenceService conferenceService;
    @Autowired
    private final HostService hostService;
    @Autowired
    private final FounderService founderService;

    private static final Logger log = LoggerFactory.getLogger(ConferenceController.class);

    public ConferenceController(ConferenceService conferenceService,
                                HostService hostService,
                                FounderService founderService) {
        this.conferenceService = conferenceService;
        this.hostService = hostService;
        this.founderService = founderService;
    }

    @GetMapping("/add")
    public String addConference(Model model) {
        model.addAttribute("hosts", hostService.findAllHosts());
        model.addAttribute("founders", founderService.findAllFounders());

        log.info("Requested page conference-add");
        return "conference-add";
    }

    @ModelAttribute("conferenceModel")
    public ConferenceDto initConference() {
        return new ConferenceDto();
    }

    @PostMapping("/add")
    public String addConference(@Valid ConferenceDto conferenceModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("conferenceModel", conferenceModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.conferenceModel",
                    bindingResult);
            log.info("Unable to add conference: ".concat(conferenceModel.getConfName()));
            return "redirect:/conferences/add";
        }
        conferenceService.addConference(conferenceModel);
        log.info("Conference added successfully: ".concat(conferenceModel.getConfName()));
        return "redirect:/conferences/all";
    }

    @GetMapping("/all")
    public String showAllConferences(Model model) {
        model.addAttribute("allConferences", conferenceService.findAllConferences());

        log.info("conference-all page opening requested");
        return "conference-all";
    }

    @GetMapping("/conference-by-name/{conference-name}")
    public String conferenceByName(@PathVariable("conference-name") String conferenceName, Model model) {
        model.addAttribute("conferenceByName", conferenceService.findConferenceByConfName(conferenceName));
        System.out.println("conferenceByName method called with name: " + conferenceName);

        log.info("conference-by-name page opening requested with conference name: ".concat(conferenceName));
        return "conference-by-name";
    }

    @GetMapping("/conference-delete/{conference-name}")
    public String deleteConference(@PathVariable("conference-name") String conferenceName) {
        conferenceService.deleteConference(conferenceName);

        log.info("Deleted conference: ".concat(conferenceName));
        return "redirect:/conferences/all";
    }
    
}
