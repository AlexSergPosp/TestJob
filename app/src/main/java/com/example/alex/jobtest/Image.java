package com.example.alex.jobtest;

/**
 * Created by Alex on 29.05.2015.
 */
public class Image  {
    private String ID;
    private String NUMBER;
    private String URL;
    private String COM;

    public String getCOM() {
        return COM;
    }

    public void setCOM(String COM) {
        this.COM = COM;
    }

    public  Image(){
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }


}
