package com.userstats.controller;


import com.userstats.DTO.Request;
import com.userstats.entity.UserStats;
import com.userstats.service.UserStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("stats/")
public class statsController {

    @Autowired
    private UserStatsService service;
    @PostMapping
    public ResponseEntity<String> addRecord(@RequestBody Request request){
        service.addRecord(request);

        return new ResponseEntity<>("record added",HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<UserStats>> getAllStats(){
      return new ResponseEntity<> (service.allStats(),HttpStatus.OK);
    }


}
