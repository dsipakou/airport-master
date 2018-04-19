package by.airport.airport_timetable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

import by.airport.airport_timetable.helpers.AirportViewPagerAdapter;
import by.airport.airport_timetable.helpers.SlidingTabLayout;


public class MainActivity extends ActionBarActivity {

    private Locale locale = null;

    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String lang = settings.getString(getString(R.string.locale_lang), "");
        changeLang(lang);
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

    private void changeLang(String lang) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {

            SharedPreferences.Editor ed = PreferenceManager.getDefaultSharedPreferences(this).edit();
            ed.putString(getString(R.string.locale_lang), lang);
            ed.commit();

            locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration conf = new Configuration(config);
            conf.locale = locale;
            getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    private void timetableCreate() {
        mPager = (ViewPager) findViewById(R.id.main_pager);
        mPager.setAdapter(new AirportViewPagerAdapter(getSupportFragmentManager(), this));

        mTabs = (SlidingTabLayout) findViewById(R.id.main_tabs);
        mTabs.setViewPager(mPager);
    }
}
