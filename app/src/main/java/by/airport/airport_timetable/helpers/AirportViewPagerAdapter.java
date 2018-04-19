package by.airport.airport_timetable.helpers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import by.airport.airport_timetable.ArrivalFragment;
import by.airport.airport_timetable.DepartureFragment;
import by.airport.airport_timetable.R;

public class AirportViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private Context context;

    public AirportViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ArrivalFragment();
            case 1:
                return new DepartureFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String tabTitles[] = new String[] {
                context.getResources().getString(R.string.arrival_tab),
                context.getResources().getString(R.string.departure_tab)};
        return tabTitles[position];
    }
}
