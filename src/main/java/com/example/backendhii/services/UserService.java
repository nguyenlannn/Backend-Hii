package com.example.backendhii.services;

import com.example.backendhii.dto.consume.ActiveUserConsumeDto;
import com.example.backendhii.dto.consume.UserConsumeDto;
import com.example.backendhii.dto.produce.UserProduceDto;

public interface UserService {

    UserProduceDto register(UserConsumeDto userConsumeDto);

<<<<<<< HEAD
=======
    void active(ActiveUserConsumeDto activeUserConsumeDto);
>>>>>>> 46d84e5de92ee450cdbff3aa405213c5c0a965bc
}
