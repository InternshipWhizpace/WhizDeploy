package com.example.riot94.whizpacecontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText host;
    private EditText user;
    private EditText password;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        host = (EditText) findViewById(R.id.host);
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);

        /* /sbin/ifconfig
        *  /usr/sbin/iwconfig*/

        /* testing purposes*/
        host.setText("192.168.100.41");
        user.setText("root");
        password.setText("ami");

        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TabbedActivity.class);
                intent.putExtra("HOST", host.getText().toString());
                intent.putExtra("USER", user.getText().toString());
                intent.putExtra("PASS", password.getText().toString());
                startActivity(intent);
            }
        });
    }
}
