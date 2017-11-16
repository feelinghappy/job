package com.hrg.anew.bean;
public  class Affix
{
    private String file_path;
    private int is_image;
    public  Affix(String file_path,int is_image)
    {
        this.file_path = file_path;
        this.is_image = is_image;
    }
    public String GetFile_path()
    {
        return file_path;
    }
    public int GetIs_image()
    {
        return is_image;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public void setIs_image(int is_image) {
        this.is_image = is_image;
    }

}