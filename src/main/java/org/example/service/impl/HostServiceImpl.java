package org.example.service.impl;

import jakarta.validation.ConstraintViolation;
import org.example.models.Host;
import org.example.repositories.HostRepository;
import org.example.service.dto.HostDto;
import org.example.service.interfaces.HostService;
import org.example.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
