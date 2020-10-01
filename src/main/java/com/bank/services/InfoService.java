package com.bank.services;

import com.bank.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoService {

    private UserService userService;
    private UserInfoRepository infoRepository;

    @Autowired
    public InfoService(UserService userService, UserInfoRepository infoRepository) {
        this.userService = userService;
        this.infoRepository = infoRepository;
    }
}
