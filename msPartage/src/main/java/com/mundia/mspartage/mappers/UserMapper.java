package com.mundia.mspartage.mappers;

import com.mundia.mspartage.dto.PartageReq;
import com.mundia.mspartage.entities.Partage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public Partage fromUserReq(PartageReq partageReq){
        Partage partage = new Partage();
        BeanUtils.copyProperties(partageReq, partage);
        return partage;

    }
}
