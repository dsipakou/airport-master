package by.airport.airport_master;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import by.airport.airport_master.helpers.AirportViewPagerAdapter;
import by.airport.airport_master.helpers.SlidingTabLayout;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.refreshDrawableState();
        timetableCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, AirportPreferenceActivity.class);
                startActivity(i);
                invalidateOptionsMenu();
                return true;
            case R.id.refresh_button:
                timetableCreate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void timetableCreate() {
        mPager = (ViewPager) findViewById(R.id.main_pager);
        mPager.setAdapter(new AirportViewPagerAdapter(getSupportFragmentManager(), this));

        mTabs = (SlidingTabLayout) findViewById(R.id.main_tabs);
        mTabs.setViewPager(mPager);
    }
}
