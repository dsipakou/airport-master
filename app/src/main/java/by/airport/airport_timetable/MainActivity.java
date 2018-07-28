package by.airport.airport_timetable;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

import by.airport.airport_timetable.helpers.AirportViewPagerAdapter;
import by.airport.airport_timetable.helpers.Period;
import by.airport.airport_timetable.helpers.SlidingTabLayout;


public class MainActivity extends AppCompatActivity {

    public static Context contextOfApp;

    private Locale locale = null;

    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contextOfApp = getApplicationContext();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(contextOfApp);
        String lang = settings.getString(getString(R.string.locale_lang), "");
        changeLang(lang);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.dl);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionBar.setTitle(getTitleForBar(Period.fromString(settings.getString("dayMode", Period.NOW.toString()))));

        NavigationView navigationView = findViewById(R.id.navDayPicker);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        SharedPreferences.Editor ed = PreferenceManager.getDefaultSharedPreferences(contextOfApp).edit();

                        switch (item.getItemId()) {
                            case R.id.menuNow:
                                actionBar.setTitle(R.string.now);
                                ed.putString("dayMode", Period.NOW.toString());
                                ed.apply();
                                timetableCreate();
                                break;
                            case R.id.menuToday:
                                actionBar.setTitle(R.string.today);
                                ed.putString("dayMode", Period.TODAY.toString());
                                ed.apply();
                                timetableCreate();
                                break;
                            case R.id.menuTomorrow:
                                actionBar.setTitle(R.string.tomorrow);
                                ed.putString("dayMode", Period.TOMORROW.toString());
                                ed.apply();
                                timetableCreate();
                                break;
                            case R.id.menuYesterday:
                                actionBar.setTitle(R.string.yesterday);
                                ed.putString("dayMode", Period.YESTERDAY.toString());
                                ed.apply();
                                timetableCreate();
                                break;
                            case R.id.action_settings:
                                Intent i = new Intent(contextOfApp, AirportPreferenceActivity.class);
                                startActivity(i);
                                invalidateOptionsMenu();
                                return true;
                            default:
                                actionBar.setTitle(R.string.now);

                        }
                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                }
        );



//
//        toolbar = (Toolbar) findViewById(R.id.app_bar);
//        setSupportActionBar(toolbar);
//
//        toolbar.refreshDrawableState();
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
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
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

    public static Context getContextOfApp() {
        return contextOfApp;
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

    private int getTitleForBar(Period period) {
        switch (period) {
            case NOW:
                return R.string.now;
            case YESTERDAY:
                return R.string.yesterday;
            case TODAY:
                return R.string.today;
            case TOMORROW:
                return R.string.tomorrow;
            default:
                return R.string.now;
        }
    }
}
