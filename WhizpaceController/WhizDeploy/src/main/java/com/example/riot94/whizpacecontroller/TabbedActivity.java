package com.example.riot94.whizpacecontroller;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/*!
  An Activity used to display outputs from the ifconfig and iwconfig commands. They have been hardcoded into the application due to a known issue with the SSH Server. See Author's Note for more info.

  Author's Note: The server/shell is misconfigured somehow. It does not set the PATH correctly, when a shell session is not started.
  That's, why the ifconfig/iwconfig binaries cannot be found.
  Either fix your startup scripts to set the PATH correctly for all situations. Or use a full path to the ifconfig/iwconfig.
  To find the full path of a command, i.e. "ifconfig", open a regular shell session using your SSH client and type:
  "which ifconfig"
*/
public class TabbedActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

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
      A String representing the full output from the command, ifconfig.
    */
    private static String ifconfig;

    /*!
      A String representing the full output from the command, iwconfig.
    */
    private static String iwconfig;

    /*!
      Initialises the TabbedActivity, searches for all components with the corresponding id's in the relevant xml file, and sets the correct values accordingly.
      Upon creation, the Activity attempts to connect to the SSH Server with the given host IP Address, username and password.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        host = getIntent().getStringExtra("HOST");
        user = getIntent().getStringExtra("USER");
        pass = getIntent().getStringExtra("PASS");

        ifconfig = getIwconfig();
        iwconfig = getIwconfig();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ListActivity.class);
                intent.putExtra("HOST", host);
                intent.putExtra("USER", user);
                intent.putExtra("PASS", pass);
                startActivity(intent);
            }
        });
    }

    /*!
      Creates a new JSchConnectionProtocol object and sends the command "/sbin/ifconfig" to the SSH Server

      Returns the full output from the command, ifconfig.
    */
    public String getIfconfig(){
        String output = "getIfconfig() failed";
        try {
            final JSchConnectionProtocol jsch = new JSchConnectionProtocol(host, user, pass);
            output = jsch.execute("/sbin/ifconfig").get();
        } catch (InterruptedException e) {
            Log.d("InterruptedException",e.getLocalizedMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.d("ExecutionException",e.getLocalizedMessage());
        }
        return output;
    }

    /*!
      Creates a new JSchConnectionProtocol object and sends the command "/usr/sbin/iwconfig" to the SSH Server

      Returns the full output from the command, iwconfig.
    */
    public String getIwconfig(){
        String output = "getIfconfig() failed";
        try {
            final JSchConnectionProtocol jsch = new JSchConnectionProtocol(host, user, pass);
            output = jsch.execute("/usr/sbin/iwconfig").get();
        } catch (InterruptedException e) {
            Log.d("InterruptedException",e.getLocalizedMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.d("ExecutionException",e.getLocalizedMessage());
        }
        return output;
    }

    /*!
    Inflate the menu; this adds items to the action bar if it is present.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }

    /*!
    Handle action bar item clicks here. The action bar will
    automatically handle clicks on the Home/Up button, so long
    as you specify a parent activity in AndroidManifest.xml.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                textView.setText(ifconfig);
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                textView.setText(iwconfig);
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ifconfig";
                case 1:
                    return "iwconfig";
            }
            return null;
        }
    }
}
