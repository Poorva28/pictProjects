package com.example.e_notice_board;

import java.io.Serializable;

public class Student implements Serializable {

   public String name,email,year,lab,rollno;
    public String section;

    Student()
    {

    }
    Student(String name,String email,String year,String section,String lab,String rno)
    {
        this.name=name;
        this.email=email;
        this.year=year;
        this.section=section;
        this.lab=lab;
        this.rollno=rno;
    }

}
