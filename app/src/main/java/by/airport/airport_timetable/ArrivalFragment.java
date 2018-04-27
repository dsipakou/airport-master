package by.airport.airport_timetable;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

import by.airport.airport_timetable.entity.ArrivalInfo;
import by.airport.airport_timetable.entity.FullFlightInfo;
import by.airport.airport_timetable.helpers.AirportListAdapter;
import by.airport.airport_timetable.utils.Globals;
import by.airport.airport_timetable.utils.ParseTimetableImpl;
import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;


public class ArrivalFragment extends Fragment {

    private ProgressBar progressBar;
    private ListView listView;
    private Activity mParentActivity = null;
    private AirportListAdapter mAdapter;
    private ArrayList<SimpleSectionedListAdapter.Section> sections = new ArrayList<SimpleSectionedListAdapter.Section>();
    private SwipeRefreshLayout mSwipeRefresh;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mParentActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_arrival, container, false);
        listView = (ListView) view.findViewById(R.id.arrival_list_fragment);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_arrival_fragment);
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_arrival);
        updateData();
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(){
                updateData();
                mSwipeRefresh.setRefreshing(false);
            }
        });
        return view;
    }

    private void updateData() {
        progressBar.setVisibility(View.VISIBLE);
        new ParseArrival().execute(Globals.ARRIVAL_URL);
    }

    private class ParseArrival extends AsyncTask<String, Void, FullFlightInfo<ArrivalInfo>> {
        ParseTimetableImpl<ArrivalInfo> parsedTimetable;

        protected FullFlightInfo<ArrivalInfo> doInBackground(String... arg) {
            parsedTimetable = new ParseTimetableImpl<>();

            FullFlightInfo<ArrivalInfo> output = new FullFlightInfo<>();
            try {
                output = parsedTimetable.getDetailsList(new URL(arg[0]), ArrivalInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return output;
        }

        protected void onPostExecute(FullFlightInfo<ArrivalInfo> output) {
            progressBar.setVisibility(View.GONE);
            sections.clear();
            mAdapter = new AirportListAdapter(mParentActivity, R.layout.airport_list_adapter, output.getFlightInfo());
            for (int i = 0; i < output.getPositions().size(); i ++) {
                sections.add(new SimpleSectionedListAdapter.Section(output.getPositions().get(i), output.getHeaders().get(i)));
            }
            SimpleSectionedListAdapter simpleSectionedListAdapter;
            simpleSectionedListAdapter = new SimpleSectionedListAdapter(mParentActivity, mAdapter, R.layout.list_item_header, R.id.header);
            simpleSectionedListAdapter.setSections(sections.toArray(new SimpleSectionedListAdapter.Section[0]));
            listView.setAdapter(simpleSectionedListAdapter);
            listView.setOnItemClickListener(onArrivalClickListener);
        }
    }

    private AdapterView.OnItemClickListener onArrivalClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                Globals.arrivalInfo = (ArrivalInfo) parent.getAdapter().getItem(position);
                Intent intent = new Intent(view.getContext(), ArrivalActivity.class);
                startActivity(intent);
            }
            catch (ClassCastException ex) {

            }

        }
    };
}
