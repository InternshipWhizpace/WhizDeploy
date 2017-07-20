package com.example.riot94.whizpacecontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*!
  An activity that displays the full output of a command sent to the Whizpace Device. The activity is started when an Item on ListActivity is clicked.

  Created by Ryan Tan on 30/5/2017.
*/

public class DisplayActivity extends AppCompatActivity {
    /*!
      A TextView object that displays the input command sent to the Whizpace Device.
    */
    private TextView in;

    /*!
      A TextView object that displays the output from the Whizpace Device given the input command.
    */
    private TextView out;

    /*!
      A Button object that ends this DisplayActivity returning the user to ListActivity.
    */
    private Button back;

    /*!
      Initialises the DisplayActivity, searches for all components with the corresponding id's in the relevant xml file, and sets the correct values accordingly.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        in = (TextView) findViewById(R.id.input);
        in.setText(getIntent().getStringExtra("ITEM_IN"));
        out = (TextView) findViewById(R.id.output);
        out.setText(getIntent().getStringExtra("ITEM_OUT"));
        back = (Button) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
