package com.userstats.service;

import com.userstats.DTO.Request;
import com.userstats.entity.UserStats;
import com.userstats.repository.UserStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserStatsService {


    @Autowired
    private UserStatsRepository repository;

    public void addRecord(Request request) {
        UserStats userStats= UserStats
                .builder()
                .id(0)
                .userId(request.getUserId())
                .userName(request.getUserName())
                .userType(request.getUserType())
                .dateOfSignup(LocalDateTime.now())
                .build();

        repository.save(userStats);
    }


    public List<UserStats> allStats(){
        return repository.findAllByOrderByDateOfSignupDesc();
    }
}
