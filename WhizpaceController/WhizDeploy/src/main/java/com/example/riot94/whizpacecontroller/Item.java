package com.example.riot94.whizpacecontroller;

/*!
  A class used to represent an item in the list in ListActivity.

  Created by Ryan Tan on 31/5/2017.
*/
public class Item {
    /*!
      A String representing the input command sent to the Whizpace device
    */
    private String input;

    /*!
      A String that displays the output from the Whizpace Device given the input command.
    */
    private String output;

    /*!
      A truncated String with the first 10 characters of output and "..." appended to it.
    */
    private String truncated;

    /*!
      Constructs a String with the given input and output.
    */
    public Item(String in, String out){
        input = in;
        output = out;
        if (out.length()<10){
            truncated = out;
        }else{
            truncated = out.substring(0,10) + "...";
        }
    }

    /*!
      Returns the input String of the Item.
    */
    public String getInput(){
        return input;
    }

    /*!
      Returns the output String of the Item.
    */
    public String getOutput(){
        return output;
    }

    /*!
      Returns the truncated output String of the Item.
    */
    public String getTruncated(){ return truncated; }
}
