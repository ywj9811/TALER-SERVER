package com.demo;

import com.demo.repository.ParentRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTest {

    @Autowired
    ParentRepo parentRepo;


    @Test
    public void test(){
        int id = 1;
        boolean a = parentRepo.existByUserId(id);
        System.out.println(a);
    }

}
