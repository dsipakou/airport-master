package by.airport.airport_master;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import by.airport.airport_master.entity.ArrivalInfo;
import by.airport.airport_master.entity.DepartureInfo;
import by.airport.airport_master.helpers.AirportListAdapter;
import by.airport.airport_master.utils.Globals;
import by.airport.airport_master.utils.ParseTimetableImpl;

public class DepartureFragment extends Fragment {


    private ListView listView;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_departure, container, false);
        listView = (ListView) view.findViewById(R.id.departure_list_fragment);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_departure_fragment);
        updateData();
        return view;
    }

    private void updateData() {
        progressBar.setVisibility(View.VISIBLE);
        new ParseArrival().execute(Globals.DEPARTURE_URL);
    }

    private class ParseArrival extends AsyncTask<String, Void, List<DepartureInfo>> {
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
            progressBar.setVisibility(View.GONE);
            listView.setAdapter(new AirportListAdapter(getActivity(), R.layout.airport_list_adapter, output));
            listView.setOnItemClickListener(onArrivalClickListener);
        }
    }

    private AdapterView.OnItemClickListener onArrivalClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Globals.arrivalInfo = (ArrivalInfo) parent.getAdapter().getItem(position);
            Intent intent = new Intent(view.getContext(), ArrivalActivity.class);
            startActivity(intent);
        }
    };
}
