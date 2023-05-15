package com.userstats.repository;


import com.userstats.entity.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStatsRepository extends JpaRepository<UserStats,Long> {



    List<UserStats> findAllByOrderByDateOfSignupDesc();


}
