package com.mundia.msutilisateur.mappers;

import com.mundia.msutilisateur.dto.UserReq;
import com.mundia.msutilisateur.entities.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public User fromUserReq(UserReq userReq){
        User document = new User();
        BeanUtils.copyProperties(userReq, document);
        return document;
    }
}
