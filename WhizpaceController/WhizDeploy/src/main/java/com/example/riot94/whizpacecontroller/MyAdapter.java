package com.example.riot94.whizpacecontroller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/*!
Adapted from https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView.
See section on Creating the RecyclerView.Adapter

Created by Ryan Tan on 29/5/2017.
*/

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView comdIn;
        public TextView comdOut;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            comdIn = (TextView) itemView.findViewById(R.id.comd_input);
            comdOut = (TextView) itemView.findViewById(R.id.comd_output);

        }
    }

    /*!
    ArrayList of Items storing the input input commands and output Strings from the SSH Server.
     */
    private ArrayList<Item> mCommands;
    /*!
    Context is stored for easy access.
     */
    private Context mContext;

    /*!
    Constructor for a new MyAdapter.
     */
    public MyAdapter(Context context, ArrayList<Item> commands) {
        mCommands = commands;
        mContext = context;
    }

    /*!
    Getter to return the Context.
     */
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Item command = mCommands.get(position);

        // Set item views based on your views and data model
        TextView input = viewHolder.comdIn;
        input.setText(command.getInput());

        // Set item views based on your views and data model
        TextView output = viewHolder.comdOut;
        output.setText(command.getTruncated());
    }

    /*!
    Returns the total count of items in the list
     */
    @Override
    public int getItemCount() {
        return mCommands.size();
    }
}