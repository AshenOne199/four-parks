package com.groupc.fourparks.domain.port;

import java.util.List;

import com.groupc.fourparks.domain.model.Activity;

public interface ActivityPort {

    List<Activity> read();
    
} 