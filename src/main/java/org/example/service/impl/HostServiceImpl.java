package org.example.service.impl;

import jakarta.validation.ConstraintViolation;
import org.example.models.Founder;
import org.example.models.Host;
import org.example.repositories.HostRepository;
import org.example.service.dto.HostDto;
import org.example.service.interfaces.HostService;
import org.example.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostServiceImpl implements HostService {
    private final HostRepository hostRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public HostServiceImpl(HostRepository hostRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.hostRepository = hostRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addHost(String hostName) {
        HostDto hostDto = new HostDto();
        hostDto.setHostName(hostName);

        if (!this.validationUtil.isValid(hostDto)) {
            this.validationUtil
                    .violations(hostDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

        } else {
            try {
                this.hostRepository
                        .saveAndFlush(this.modelMapper.map(hostDto, Host.class));
            } catch (Exception e) {
                System.out.println("Some thing went wrong!");
            }
        }
    }

    @Override
    public Host findHostByHostName(String hostName) {
        return this.hostRepository.findHostByHostName(hostName);
    }

    @Override
    public List<HostDto> findAllHosts() {
        return this.hostRepository.
                findAll().
                stream().
                map(host -> this.modelMapper.map(host, HostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Host updateHost(String hostName) {
        HostDto hostDto = new HostDto();
        hostDto.setHostName(hostName);

        if (!this.validationUtil.isValid(hostDto)) {
            this.validationUtil
                    .violations(hostDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

        } else {
            try {
                return
                        this.hostRepository.saveAndFlush(
                                this.modelMapper.map(hostDto, Host.class)
                        );
            } catch (Exception e) {
                System.out.println("Some thing went wrong!");
            }
        }
        return null;
    }

    @Override
    public void deleteHost(String hostName) {
        this.hostRepository.delete(
                this.hostRepository.findHostByHostName(hostName)
        );
    }

}
