package com.example.riot94.whizpacecontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/*!
  An Activity used to display all previous input commands and their respective truncated output.
*/
public class ListActivity extends AppCompatActivity {

    /*!
      A String representing the host's IP Address that the user is trying to connect to.
    */
    private String host;

    /*!
      A String representing the username that the user is trying to connect to the SSH Server with.
    */
    private String user;

    /*!
      A String representing the password that the user is trying to connect to the SSH Server with.
    */
    private String pass;

    /*!
      A Button object that sends the user's command, in, to the SSH Server using JSchConnectionProtocol
    */
    private Button button;

    /*!
      An EditText object that allows users to modify the command to send to the SSH Server.
    */
    private EditText in;

    /*!
      An ArrayList of Items containing all previous input commands as well as the truncated output.
    */
    private ArrayList<Item> list;

    /*!
      A RecyclerView displaying all Items in list.
    */
    private RecyclerView mRecyclerView;

    /*!
      An Adapter bridging mRecyclerView and list.
    */
    private RecyclerView.Adapter mAdapter;

    /*!
      A LayoutManager to update mRecyclerView when there are changes to the list.
    */
    private RecyclerView.LayoutManager mLayoutManager;

    /*!
      Initialises the ListActivity, searches for all components with the corresponding id's in the relevant xml file, and sets the correct values accordingly.
      The Activity then waits for the user to enter a command.
      Upon receiving a command, a JSchConnectionProtocol object is created and run. A new item is then added to the list and shown to the user.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        host = getIntent().getStringExtra("HOST");
        user = getIntent().getStringExtra("USER");
        pass = getIntent().getStringExtra("PASS");

        // try Jsch connection
        // if connection fails, make a Toast displaying the error
        // else Toast Connected
        try{
            //android widgets
            button = (Button) findViewById(R.id.send);
            in = (EditText) findViewById(R.id.comdLine);
            list = new ArrayList<Item>();

            //RecyclerView
            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            mRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getBaseContext(), DisplayActivity.class);
                            int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                            Item item = list.get(itemPosition);
                            intent.putExtra("ITEM_IN", item.getInput());
                            intent.putExtra("ITEM_OUT", item.getOutput());
                            startActivity(intent);
                        }

                        @Override public void onLongItemClick(View view, int position) {
                            Intent intent = new Intent(getBaseContext(), DisplayActivity.class);
                            int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                            Item item = list.get(itemPosition);
                            intent.putExtra("ITEM_IN", item.getInput());
                            intent.putExtra("ITEM_OUT", item.getOutput());
                            startActivity(intent);
                        }
                    })
            );

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            mAdapter = new MyAdapter(this, list);
            mRecyclerView.setAdapter(mAdapter);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String comd = in.getText().toString();
                    String output = null;
                    try {
                        final JSchConnectionProtocol jsch = new JSchConnectionProtocol(host, user, pass);
                        output = swapSpacesForLine(jsch.execute(comd).get());
                    } catch (InterruptedException e) {
                        Log.d("InterruptedException",e.getLocalizedMessage());
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        Log.d("ExecutionException",e.getLocalizedMessage());
                    }
                    //Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
                    list.add(new Item(comd, output));
                    in.setText("");
                    Log.d("ENTERED", "List size = " + list.size());
                }
            });
        }catch (Exception e){
            Toast.makeText(this, "JSch Connection Failed", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            finish();
        }
    }

    /*!
      Converts spaces into new lines. This function serves only to convert the formatting of the output to a more readable format.
    */
    private String swapSpacesForLine(String s){
        for (int i=8; i>1; i--){
            String spaces = "";
            for (int j=0; j<i; j++){
                spaces += " ";
            }
            s = s.replace(spaces, "\n");
        }
        return s;
    }
}