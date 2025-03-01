package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.service.dto.ConferenceDto;
import org.example.service.impl.ConferenceServiceImpl;
import org.example.service.impl.FounderServiceImpl;
import org.example.service.impl.HostServiceImpl;
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
    private final ConferenceServiceImpl conferenceServiceImpl;
    @Autowired
    private final HostServiceImpl hostServiceImpl;
    @Autowired
    private final FounderServiceImpl founderServiceImpl;

    public ConferenceController(ConferenceServiceImpl conferenceServiceImpl,
                                HostServiceImpl hostServiceImpl,
                                FounderServiceImpl founderServiceImpl) {
        this.conferenceServiceImpl = conferenceServiceImpl;
        this.hostServiceImpl = hostServiceImpl;
        this.founderServiceImpl = founderServiceImpl;
    }

    @GetMapping("/add")
    public String addConference(Model model) {

        log.info("Get Request:\tconference-add page");

        model.addAttribute("hosts", hostServiceImpl.findAllHosts());
        model.addAttribute("founders", founderServiceImpl.findAllFounders());

        log.info("Response for Get Request conference-add:\treturning conference-add page");

        return "conference-add";
    }

    @ModelAttribute("conferenceModel")
    public ConferenceDto initConference() {
        return new ConferenceDto();
    }

    @PostMapping("/add")
    public String addConference(@Valid ConferenceDto conferenceModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("Post Request:\tconference-add page");

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("conferenceModel", conferenceModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.conferenceModel",
                    bindingResult);

            log.info("Response for Post Request conference-add:\tunable to add conference ".concat(conferenceModel.getConfName()));
            return "redirect:/conferences/add";
        }
        conferenceServiceImpl.addConference(conferenceModel);

        log.info("Response for Post Request conference-add:\tconference added successfully: ".concat(conferenceModel.getConfName()));
        return "redirect:/conferences/all";
    }

    @GetMapping("/all")
    public String showAllConferences(Model model) {

        log.info("Get Request:\tconference-all page");
        model.addAttribute("allConferences", conferenceServiceImpl.findAllConferences());

        log.info("Response for Get Request conference-all:\treturning conference-all page");

        return "conference-all";
    }

    @GetMapping("/conference-by-name/{conference-name}")
    public String conferenceByName(@PathVariable("conference-name") String conferenceName, Model model) {

        log.info("Get Request:\tconference-by-name page with conference name ".concat(conferenceName));

        model.addAttribute("conferenceByName", conferenceServiceImpl.findConferenceByConfName(conferenceName));
        //System.out.println("conferenceByName method called with name: " + conferenceName);

        log.info("Response for Get Request conference-all:\treturning conference-by-name/".concat(conferenceName));

        return "conference-by-name";
    }

    @GetMapping("/conference-delete/{conference-name}")
    public String deleteConference(@PathVariable("conference-name") String conferenceName) {

        log.info("Get Request:\tdelete conference with name ".concat(conferenceName));
        conferenceServiceImpl.deleteConference(conferenceName);

        log.info("Response for Get Request delete conference:\tConference deleted: ".concat(conferenceName));
        return "redirect:/conferences/all";
    }
    
}
