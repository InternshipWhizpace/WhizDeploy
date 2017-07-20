package com.example.riot94.whizpacecontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*!
  An Activity for the user to enter the host's IP Address, username and password for the SSH Server they wish to connect to.
*/
public class MainActivity extends AppCompatActivity {
    /*!
      An EditText that allows the user to enter the IP Address of the host that the user is trying to connect to.
    */
    private EditText host;

    /*!
      An EditText that allows the user to enter the username with which the user is trying to connect.
    */
    private EditText user;

    /*!
      An EditText that allows the user to enter the password with which the user is trying to connect.
    */
    private EditText password;

    /*!
      A Button object that suspends MainActivity and starts TabbedActivity.
    */
    private Button submit;

    /*!
      Initialises the MainActivity, searches for all components with the corresponding id's in the relevant xml file, and sets the correct values accordingly.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        host = (EditText) findViewById(R.id.host);
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);

        // For testing purposes, comment out or delete before creating the APK
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
