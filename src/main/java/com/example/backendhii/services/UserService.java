package com.example.backendhii.services;

import com.example.backendhii.dto.consume.UserConsumeDto;
import com.example.backendhii.dto.produce.UserProduceDto;

public interface UserService {

    UserProduceDto register(UserConsumeDto userConsumeDto);
}
