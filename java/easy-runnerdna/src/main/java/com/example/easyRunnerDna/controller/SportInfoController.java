package com.example.easyRunnerDna.controller;

import com.example.easyRunnerDna.entity.SportInfo;
import com.example.easyRunnerDna.repository.SportInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sportInfo")
@Slf4j
public class SportInfoController {
    @Autowired
    private SportInfoRepository sportInfoRepository;



    @PostMapping("")
    public SportInfo uploadData(@RequestBody SportInfo sportInfo){
        try {
            log.info("save info"+sportInfo);
            return sportInfoRepository.save(sportInfo);
        }catch (Exception e){
            e.printStackTrace();
            log.error("上传数据错误!!!");
        }
        return null;
    }
}
