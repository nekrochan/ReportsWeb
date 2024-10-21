package org.example.controllers;

import org.example.models.Host;
import org.example.service.dto.HostDto;
import org.example.service.dto.HostDto;
import org.example.service.interfaces.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hosts")
public class HostController {

    private final HostService hostService;

    @Autowired
    public HostController(HostService hostService) {
        this.hostService = hostService;
    }

    @PostMapping("hosts/add")
    public Host addHost(@RequestBody String name) {
        hostService.addHost(name);
        return hostService.findHostByHostName(name);
    }

    @PutMapping("/hosts/{name}")
    public Host updateHost(@PathVariable String name, @PathVariable String newName){
        this.hostService
                .findHostByHostName(name)
                .setHostName(newName);
        return this.hostService.findHostByHostName(newName);
    }

    @DeleteMapping("hosts/{id}")
    public void deleteHost(@PathVariable String name){
        this.hostService.deleteHost(name);
    }

    @GetMapping("/hosts")
    public List<HostDto> getAllHosts() {
        return hostService.findAllHosts();
    }
}
