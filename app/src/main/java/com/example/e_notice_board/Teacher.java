package com.example.e_notice_board;

import java.io.Serializable;

public class Teacher implements Serializable{

   public String name,email,phone,teacherid; //compulsorly public as pojo requires public variables

    Teacher()
    {

    }

    Teacher(String name,String email,String phone,String teacherid)
    {
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.teacherid=teacherid;
    }

}
