package com.example.riot94.whizpacecontroller;

/**
 * Created by riot94 on 31/5/2017.
 */

public class Item {
    private String input;
    private String output;
    private String truncated;

    public Item(String in, String out){
        input = in;
        output = out;
        if (out.length()<10){
            truncated = out;
        }else{
            truncated = out.substring(0,10) + "...";
        }
    }

    public String getInput(){
        return input;
    }

    public String getOutput(){
        return output;
    }

    public String getTruncated(){ return truncated; }
}
