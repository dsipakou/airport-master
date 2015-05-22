package by.airport.airport_master.container;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import by.airport.airport_master.R;
import by.airport.airport_master.entity.FlightInfo;

public class AirportListAdapter<T extends FlightInfo> extends ArrayAdapter<T> {
    public AirportListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public AirportListAdapter(Context context, int resource, List<T> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.airport_list_adapter, null);
        }

        FlightInfo info = getItem(position);

        if (info != null) {
            TextView city = (TextView) view.findViewById(R.id.city);
            TextView code = (TextView) view.findViewById(R.id.code);
            TextView timeExpected = (TextView) view.findViewById(R.id.time_expected);
            TextView timeActual = (TextView) view.findViewById(R.id.time_actual);
            TextView status = (TextView) view.findViewById(R.id.status);

            if (city != null) {
                city.setText(info.getCity());
            }

            if (code != null) {
                code.setText("(" + info.getCode() + ")");
            }

            if (timeExpected != null) {
                timeExpected.setText("Scheduled: " + info.getExpectedTime());
            }

            if (timeActual != null && info.getActualTime() != null) {
                timeActual.setText("Estimated: " + info.getActualTime());
            }

            if (status != null && info.getStatus() != null) {
                status.setText(info.getStatus());
            }
        }

        return view;
    }
}
