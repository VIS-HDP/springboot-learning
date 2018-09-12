package com.vsa.jpademo;

import com.vsa.jpademo.entity.PersonEntity;
import com.vsa.jpademo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

    @Autowired
    PersonRepository personRepository;
    @RequestMapping("/save")
    public String test(){
        PersonEntity entity = new PersonEntity("Tom",23);
        personRepository.save(entity);
        return  "save one !";
    }
}
