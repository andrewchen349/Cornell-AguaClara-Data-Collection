package com.andre.aguaclarapost;

import com.google.firebase.database.Exclude;

public class Upload {

    //Fields and Constants
    private String mName;
    private String mImageUrl;
    private String mKey;

    public Upload(){
        //Empty Constructor
    }

    //Constructor
    public Upload (String name, String imageUrl){
        if(name.trim().equals("")){
            name = "No Name";
        }
        mName = name;
        mImageUrl = imageUrl;
    }

    //GETTERS & SETTERS
    public String getmName (){
        return mName;
    }

    public void setName (String name){
        mName = name;
    }

    public String getmImageUrl (){
        return mImageUrl;
    }

    public void setmImageUrl (String imageUrl){
        mImageUrl = imageUrl;
    }

    @Exclude
    public String getKey(){
        return mKey;
    }

    @Exclude
    public void setKey(String s){
        mKey = s;
    }

}
