package com.example.e_notice_board;

import java.io.Serializable;

public class FileInfo implements Serializable {

    public String year,section,lab;

    FileInfo(){}

    FileInfo(String year,String section,String lab)
    {
        this.year=year;
        this.section=section;
        this.lab=lab;
    }

}
