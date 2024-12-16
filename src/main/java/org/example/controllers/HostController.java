package org.example.controllers;

import org.example.service.dto.HostDto;
import org.example.service.interfaces.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            return "redirect:/hosts/add";
        }
        hostService.addHost(hostModel.getHostName());

        return "redirect:/hosts/all";
    }

    @GetMapping("/all")
    public String showAllCompanies(Model model) {
        model.addAttribute("allHosts", hostService.findAllHosts());

        return "host-all";
    }

    @GetMapping("/host-by-name/{host-name}")
    public String hostDetails(@PathVariable("host-name") String hostName, Model model) {
        model.addAttribute("hostByName", hostService.findHostByHostName(hostName));

        return "host-by-name";
    }

    @GetMapping("/host-delete/{host-name}")
    public String deleteHost(@PathVariable("host-name") String hostName) {
        hostService.deleteHost(hostName);

        return "redirect:/hosts/all";
    }
}
