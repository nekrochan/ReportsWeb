package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.service.dto.HostDto;
import org.example.service.interfaces.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/hosts")
public class HostController {

    @Autowired
    private HostService hostService;

    public HostController(HostService hostService) {
        this.hostService = hostService;
    }

    @GetMapping("/add")
    public String addHost() {
        log.info("host-add page requested");
        return "host-add";
    }

    @ModelAttribute("hostModel")
    public HostDto initHost() {
        return new HostDto();
    }

    @PostMapping("/add")
    public String addHost(@Valid HostDto hostModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("hostModel", hostModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.hostModel",
                    bindingResult);

            log.info("!! Unable to add host: ".concat(hostModel.getHostName()));
            return "redirect:/hosts/add";
        }
        hostService.addHost(hostModel.getHostName());

        log.info("Host added successfully: ".concat(hostModel.getHostName()));
        return "redirect:/hosts/all";
    }

    @GetMapping("/all")
    public String showAllCompanies(Model model) {
        model.addAttribute("allHosts", hostService.findAllHosts());

        log.info("host-all page requested");
        return "host-all";
    }

    @GetMapping("/host-by-name/{host-name}")
    public String hostDetails(@PathVariable("host-name") String hostName, Model model) {
        model.addAttribute("hostByName", hostService.findHostByHostName(hostName));

        log.info("host-by-name page requested with host name: ".concat(hostName));
        return "host-by-name";
    }

    @GetMapping("/host-delete/{host-name}")
    public String deleteHost(@PathVariable("host-name") String hostName) {
        hostService.deleteHost(hostName);

        log.info("Deleted host: ".concat(hostName));
        return "redirect:/hosts/all";
    }
}
