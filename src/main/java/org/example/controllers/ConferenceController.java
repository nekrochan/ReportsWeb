package org.example.controllers;

import org.example.service.dto.ConferenceDto;
import org.example.service.interfaces.ConferenceService;
import org.example.service.interfaces.FounderService;
import org.example.service.interfaces.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/conferences")
public class ConferenceController {

    @Autowired
    private final ConferenceService conferenceService;
    @Autowired
    private final HostService hostService;
    @Autowired
    private final FounderService founderService;

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
            return "redirect:/conferences/add";
        }
        conferenceService.addConference(conferenceModel);

        return "redirect:/";
    }

    @GetMapping("/all")
    public String showAllConferences(Model model) {
        model.addAttribute("allConferences", conferenceService.findAllConferences());

        return "conference-all";
    }

    @GetMapping("/conference-by-name/{conference-name}")
    public String conferenceByName(@PathVariable("conference-name") String conferenceName, Model model) {
        model.addAttribute("conferenceByName", conferenceService.findConferenceByConfName(conferenceName));

        return "conference-by-name";
    }

    @GetMapping("/conference-delete/{conference-name}")
    public String deleteConference(@PathVariable("conference-name") String conferenceName) {
        conferenceService.deleteConference(conferenceName);

        return "redirect:/conferences/all";
    }
    
}
