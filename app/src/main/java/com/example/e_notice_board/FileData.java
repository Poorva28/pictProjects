package com.example.e_notice_board;

import java.io.Serializable;

public class FileData implements Serializable {
    public String imageUrl;

    FileData(){}
    FileData(String imageUrl)
    {
        this.imageUrl=imageUrl;
    }

    public String getUrl()
    {
        return imageUrl;
    }

}
