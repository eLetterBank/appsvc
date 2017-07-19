package com.springdemo.greeting.repositories;

import com.vsolv.appframework.repository.DataRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeRepository implements DataRepository {
    public LocalDateTime getServerTime() {
        return (LocalDateTime.now());
    }
}
