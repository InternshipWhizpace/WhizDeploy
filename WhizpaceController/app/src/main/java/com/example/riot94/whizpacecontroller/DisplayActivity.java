package com.example.riot94.whizpacecontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by riot94 on 30/5/2017.
 */

public class DisplayActivity extends AppCompatActivity {
    private TextView in;
    private TextView out;
    private Button back;

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
