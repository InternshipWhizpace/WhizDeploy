package com.example.riot94.whizpacecontroller;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
/*!
  A class used to create a JSch object and run the object

  Created by Ryan Tan on 1/6/2017.
*/
public class JSchConnectionProtocol extends AsyncTask<String, Void, String>{
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
    private String password;

    /*!
      Contructs a new JSchConnectionProtocol object to connect to an SSH Server with the provided host IP, h, username, u, and password, p.
     */
    public JSchConnectionProtocol(String h, String u, String p){
        host = h;
        user = u;
        password = p;
    }

    /*!
      Overrides AsyncTask's doInBackground method and sends command to the SSH Server.

      Returns the output from the SSH Server.
    */
    @Override
    protected String doInBackground(String... command) {
        String output = "";
        try{
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session=jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.setTimeout(10000);
            Log.d("CONNECTION", "Attempting to connect to " + host + " as user: " + user);
            session.connect();
            Log.d("CONNECTION", "Connected to " + host + " as user: " + user);

            Channel channel=session.openChannel("exec");
            //((ChannelExec)channel).setPty(true);
            ((ChannelExec)channel).setCommand(command[0]);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);

            output = printOutputAfterXMilliSeconds(channel,1000);

            channel.disconnect();
            session.disconnect();
            Log.d("DONE","DONE");
        }catch(Exception e){
            e.printStackTrace();
        }
        return output;
    }

    /*!
      Prints the output generated from the SSH Server after every X MilliSeconds

      Returns the full output from the SSH Server for the given command.
    */
    private String printOutputAfterXMilliSeconds(Channel channel, int ms) throws Exception {
        InputStream in=channel.getInputStream();
        channel.connect();
        String totalOutput = "";
        byte[] tmp=new byte[1024];
        while(true){
            while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i<0){
                    break;
                }
                String output = new String(tmp, 0, i);
                totalOutput += output;
                if (output.length()>200){
                    channel.sendSignal("2");
                    break;
                }
                Log.d("OUTPUT", output);
            }

            if(channel.isClosed()){
                totalOutput += "\nexit-status: "+channel.getExitStatus();
                Log.d("EXIT_STAT","exit-status: "+channel.getExitStatus());
                break;
            }

            try{
                Log.d("PRE-SLEEP","About to sleep");
                Thread.sleep(ms);
                //channel.sendSignal("2");
                Log.d("POST-SLEEP","Slept and woken");
            }catch(Exception ee){
                ee.printStackTrace();
                channel.disconnect();
            }
        }
        return totalOutput;
    }

}
