package by.airport.airport_timetable.helpers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import by.airport.airport_timetable.R;
import by.airport.airport_timetable.entity.FlightInfo;
import by.airport.airport_timetable.utils.StringUtils;

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

        if (position % 2 != 1) {
            view.setBackgroundColor(Color.parseColor("#282B31"));
        } else {
            view.setBackgroundColor(Color.parseColor("#42454A"));
        }

        FlightInfo info = getItem(position);

        if (info != null) {
            TextView city = (TextView) view.findViewById(R.id.city);
            TextView code = (TextView) view.findViewById(R.id.code);
            TextView timeScheduled = (TextView) view.findViewById(R.id.time_scheduled);
            TextView timeActual = (TextView) view.findViewById(R.id.time_actual);
            TextView status = (TextView) view.findViewById(R.id.status);
            timeActual.setVisibility(View.GONE);
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
                code.setText(info.getCode());
            }

            if (timeScheduled != null) {
                timeScheduled.setText(info.getExpectedTime());
                timeScheduled.setTypeface(null, Typeface.BOLD);
            }

            if (timeActual != null && info.getActualTime() != null) {
                timeActual.setVisibility(View.VISIBLE);
                timeActual.setText(info.getActualTime());
                timeScheduled.setTypeface(null, Typeface.NORMAL);
                timeScheduled.setTextSize(14);
                timeScheduled.setPaintFlags(timeScheduled.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                timeScheduled.setPaintFlags(0);
                timeScheduled.setTextSize(16);
            }

            if (status != null && info.getStatus() != null) {
                status.setVisibility(View.VISIBLE);
                Statuses tmpStatus = info.getStatus();
                int resourceId = view.getResources()
                        .getIdentifier(StringUtils
                                        .replaceSpecialChars(tmpStatus.name()),
                                "string",
                                getContext().getPackageName());

                if (resourceId > 0) {
                    status.setText(view.getResources().getString(resourceId));
                }

                GradientDrawable bgShape = (GradientDrawable) status.getBackground();

                switch (tmpStatus) {
                    case LANDED:
                        bgShape.setColor(Color.parseColor("#0AC20A"));
                        status.setTextColor(Color.WHITE);
                        break;
                    case DELAYED:
                        bgShape.setColor(Color.RED);
                        status.setTextColor(Color.WHITE);
                        break;
                    case BOARDING:
                        bgShape.setColor(Color.parseColor("#0AC20A"));
                        status.setTextColor(Color.WHITE);
                        break;
                    case CHECKIN:
                        bgShape.setColor(Color.YELLOW);
                        status.setTextColor(Color.parseColor("#4E5200"));
                        break;
                    default:
                        status.setVisibility(View.GONE);
                        break;
                }
            }
        }

        return view;
    }
}
