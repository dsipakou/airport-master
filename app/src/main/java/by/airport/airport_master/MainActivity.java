package by.airport.airport_master;

import android.app.ActionBar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import by.airport.airport_master.container.AirportListAdapter;
import by.airport.airport_master.entity.ArrivalInfo;
import by.airport.airport_master.entity.DepartureInfo;
import by.airport.airport_master.utils.ParseTimetableImpl;


public class MainActivity extends ActionBarActivity {

    private static final String ARRIVAL_URL = "http://airport.by/timetable/online-arrival";
    private static final String DEPARTURE_URL = "http://airport.by/timetable/online-departure";
    private ProgressBar progressBar;
    private ProgressBar progressBarDeparture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Arrival");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Departure");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBarDeparture = (ProgressBar) findViewById(R.id.progressDep);
        new ParseArrival().execute(ARRIVAL_URL);
        new ParseDeparture().execute(DEPARTURE_URL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ParseArrival extends AsyncTask<String, Void, List<ArrivalInfo>> {
        ParseTimetableImpl<ArrivalInfo> parsedTimetable;
        protected List<ArrivalInfo> doInBackground(String... arg) {
            parsedTimetable = new ParseTimetableImpl<>();
            List<ArrivalInfo> output = new ArrayList<>();
            try {
                output = parsedTimetable.getDetailsList(new URL(arg[0]), ArrivalInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return output;
        }

        protected void onPostExecute(List<ArrivalInfo> output) {
            progressBar.setVisibility(View.GONE);
            progressBar.setIndeterminate(false);
            ListView listView = (ListView) findViewById(R.id.arrival_list);
            listView.setAdapter(new AirportListAdapter(MainActivity.this, R.layout.airport_list_adapter, output));
        }
    }

    private class ParseDeparture extends AsyncTask<String, Void, List<DepartureInfo>> {
        ParseTimetableImpl<DepartureInfo> parsedTimetable;
        protected List<DepartureInfo> doInBackground(String... arg) {
            parsedTimetable = new ParseTimetableImpl<>();
            List<DepartureInfo> output = new ArrayList<>();
            try {
                output = parsedTimetable.getDetailsList(new URL(arg[0]), DepartureInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return output;
        }

        protected void onPostExecute(List<DepartureInfo> output) {
            progressBarDeparture.setVisibility(View.GONE);
            progressBarDeparture.setIndeterminate(false);
            ListView listView = (ListView) findViewById(R.id.departure_list);
            listView.setAdapter(new AirportListAdapter(MainActivity.this, R.layout.airport_list_adapter, output));
        }
    }
}
