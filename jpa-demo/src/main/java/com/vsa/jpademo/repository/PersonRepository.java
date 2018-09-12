package com.vsa.jpademo.repository;

import com.vsa.jpademo.entity.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//http://www.ityouknow.com/springboot/2016/08/20/spring-boo-jpa.html
public interface PersonRepository extends CrudRepository<PersonEntity,Long> {
//PagingAndSortingRepository
    List<PersonEntity> findByName(String name);

    // 分页查询
    Page<PersonEntity> findAll(Pageable pageable);
    Page<PersonEntity> findByName(String name, Pageable pageable);


}
