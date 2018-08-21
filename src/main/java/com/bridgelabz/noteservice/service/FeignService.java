package com.bridgelabz.noteservice.service;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/*****************************************************************************
 * @author Ankita Kalgutkar
 *
 * 
 *****************************************************************************/

@FeignClient(name="ToDoUserservice",url="http://localhost:8081")
@Service
public interface FeignService
{
    @RequestMapping("/user/getUsers")
    public  List<?>  getAllUsers();
}

