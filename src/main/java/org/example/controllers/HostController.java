package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.service.dto.HostDto;
import org.example.service.impl.HostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
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
    private HostServiceImpl hostServiceImpl;

    public HostController(HostServiceImpl hostServiceImpl) {
        this.hostServiceImpl = hostServiceImpl;
    }

    @GetMapping("/add")
    public String addHost() {
        log.info("Get Request:\thost-add page");
        return "host-add";
    }

    @ModelAttribute("hostModel")
    public HostDto initHost() {
        return new HostDto();
    }

    @PostMapping("/add")
    public String addHost(@Valid HostDto hostModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("Post Request:\thost-add page");

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("hostModel", hostModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.hostModel",
                    bindingResult);

            log.info("Response for Post Request host-add:\tunable to add host ".concat(hostModel.getHostName()));
            return "redirect:/hosts/add";
        }
        hostServiceImpl.addHost(hostModel.getHostName());

        log.info("Response for Post Request host-add:\thost added successfully: ".concat(hostModel.getHostName()));
        return "redirect:/hosts/all";
    }

    @GetMapping("/all")
    public String showAllCompanies(Model model) {
        log.info("Get Request:\thost-all page");
        model.addAttribute("allHosts", hostServiceImpl.findAllHosts());

        log.info("Response for Get Request host-all:\treturning host-all page");
        return "host-all";
    }

    @GetMapping("/host-by-name/{host-name}")
    public String hostDetails(@PathVariable("host-name") String hostName, Model model) {
        log.info("Get Request:\thost-by-name page with host name ".concat(hostName));

        model.addAttribute("hostByName", hostServiceImpl.findHostByHostName(hostName));

        log.info("Response for Get Request host-by-name:\treturning host-by-name/".concat(hostName));
        return "host-by-name";
    }

    @GetMapping("/host-delete/{host-name}")
    public String deleteHost(@PathVariable("host-name") String hostName, RedirectAttributes redirectAttributes) {
        log.info("Get Request:\tdelete host with name ".concat(hostName));
        try {
            hostServiceImpl.deleteHost(hostName);
            log.info("Response for Get Request delete host:\thost deleted: ".concat(hostName));
            return "redirect:/hosts/all";
        } catch (DataIntegrityViolationException e) {
            log.info("Response for Get Request delete host:\tunable to delete host: ".concat(hostName));
            redirectAttributes.addFlashAttribute("UnableToDelete", true);
        }

        return "redirect:/hosts/host-by-name/{host-name}";
    }
}