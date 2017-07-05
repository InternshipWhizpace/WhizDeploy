package com.example.riot94.whizpacecontroller;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
/**
 * Created by riot94 on 1/6/2017.
 */

public class JSchConnectionProtocol extends AsyncTask<String, Void, String>{
    private String host;
    private String user;
    private String password;

    public JSchConnectionProtocol(String h, String u, String p){
        host = h;
        user = u;
        password = p;
    }

    /*
    The server/shell is misconfigured somehow. It does not set the PATH correctly, when a shell session is not started.
    That's, why the ifconfig/iwconfig binaries cannot be found.
    Either fix your startup scripts to set the PATH correctly for all situations. Or use a full path to the ifconfig/iwconfig.
    To find the full path, open a regular shell session using your SSH client and type:
    which ifconfig
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
