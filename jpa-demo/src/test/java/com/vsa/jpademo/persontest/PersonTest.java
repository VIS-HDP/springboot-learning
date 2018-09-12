package com.vsa.jpademo.persontest;

import com.vsa.jpademo.entity.PersonEntity;
import com.vsa.jpademo.repository.PersonRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonTest {

    @Autowired
    PersonRepository personRepository;
    //@Test
    public void person(){
        Random random = new Random();
        int i = random.nextInt(1000);
        PersonEntity p = new PersonEntity("Tom"+i,23+i);
        PersonEntity person = personRepository.save(p);
        System.out.println("-----保存成功-------");
        PersonEntity pr =personRepository.findById(4L).get();
        System.out.println("查询id为4的数据= "+pr.getId()+","+pr.getName());
        List<PersonEntity> plist =personRepository.findByName("Tom");
        for(PersonEntity pe : plist){
            System.out.println("查询name为Tom的数据= "+pe.getId()+","+pe.getName());
        }
        Assert.assertEquals("保存成功","Tom",person.getName().substring(0,3));
    }

    @Test
    public void persontest(){
        Sort sort = Sort.by("name").descending().and(Sort.by("age").ascending());
        Pageable pageable = PageRequest.of(0,3,sort);
        Page<PersonEntity> pages = personRepository.findAll(pageable);
        System.out.println(pages.getTotalPages());
        for(PersonEntity e : pages){
            System.out.println(e.getId()+","+e.getName()+","+e.getAge());
        }
        Assert.assertEquals("保存成功","Tom","Tom");
    }

}
