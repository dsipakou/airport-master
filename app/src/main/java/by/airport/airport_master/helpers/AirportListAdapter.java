package by.airport.airport_master.helpers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import by.airport.airport_master.R;
import by.airport.airport_master.entity.FlightInfo;
import by.airport.airport_master.utils.StringUtils;

public class AirportListAdapter<T extends FlightInfo> extends ArrayAdapter<T> {

    public static final String TAG = "AirportListAdapter";

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
            TextView timeScheduled = (TextView) view.findViewById(R.id.time_scheduled);
            TextView timeScheduledLabel = (TextView) view.findViewById(R.id.time_scheduled_label);
            TextView timeActual = (TextView) view.findViewById(R.id.time_actual);
            TextView timeActualLabel = (TextView) view.findViewById(R.id.time_actual_label);
            TextView status = (TextView) view.findViewById(R.id.status);
            timeActualLabel.setVisibility(View.GONE);
            timeScheduledLabel.setVisibility(View.GONE);
            timeActual.setText("");
            status.setVisibility(View.GONE);

            if (city != null) {
                int resourceId = view.getResources().getIdentifier(
                        StringUtils.replaceSpecialChars(info.getCity()),
                        "string",
                        getContext().getPackageName());
                if (resourceId > 0) {
                    city.setText(view.getResources().getString(resourceId));
                } else {
                    city.setText(info.getCity());
                }
            }

            if (code != null) {
                code.setText("(" + info.getCode() + ")");
            }

            if (timeScheduled != null) {
                timeScheduledLabel.setVisibility(View.VISIBLE);
                timeScheduled.setText(info.getExpectedTime());
            }

            if (timeActual != null && info.getActualTime() != null) {
                timeActualLabel.setVisibility(View.VISIBLE);
                timeActual.setText(info.getActualTime());
            }

            if (status != null && info.getStatus() != null) {
                status.setVisibility(View.VISIBLE);
                String tmpStatus = info.getStatus();
                int resourceId = view.getResources()
                        .getIdentifier(StringUtils
                                        .replaceSpecialChars(tmpStatus),
                                        "string",
                                        getContext().getPackageName());

                if (resourceId > 0) {
                    status.setText(view.getResources().getString(resourceId));
                } else {
                    status.setText(tmpStatus);
                }

                GradientDrawable bgShape = (GradientDrawable) status.getBackground();

                switch (tmpStatus) {
                    case "LANDED":
                        bgShape.setColor(Color.parseColor("#0AC20A"));
                        status.setTextColor(Color.WHITE);
                        break;
                    case "DELAYED":
                        bgShape.setColor(Color.RED);
                        status.setTextColor(Color.WHITE);
                        break;
                    case "BOARDING":
                        bgShape.setColor(Color.parseColor("#0AC20A"));
                        status.setTextColor(Color.WHITE);
                        break;
                    case "CHECK-IN":
                        bgShape.setColor(Color.YELLOW);
                        status.setTextColor(Color.parseColor("#4E5200"));
                        break;
                }
            }
        }

        return view;
    }
}
