package com.robingao.mongodemo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface DocRepository extends CrudRepository<NewPolicyPdfs,String> {
}
