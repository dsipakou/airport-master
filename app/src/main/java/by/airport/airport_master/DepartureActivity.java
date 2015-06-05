package by.airport.airport_master;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import by.airport.airport_master.utils.Globals;
import by.airport.airport_master.utils.StringUtils;

public class DepartureActivity extends ActionBarActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView city = (TextView) findViewById(R.id.departure_city);
        TextView code = (TextView) findViewById(R.id.departure_code);
        TextView company = (TextView) findViewById(R.id.departure_company);
        TextView timeScheduled = (TextView) findViewById(R.id.departure_scheduled);
        TableRow actualRow = (TableRow) findViewById(R.id.departure_actual_row);
        TextView timeActual = (TextView) findViewById(R.id.departure_actual);
        TableRow statusRow = (TableRow) findViewById(R.id.departure_status_row);
        TextView status = (TextView) findViewById(R.id.departure_status);
        TextView gate = (TextView) findViewById(R.id.departure_gate);

        actualRow.setVisibility(View.GONE);
        statusRow.setVisibility(View.GONE);

        if (city != null) {
            int resourceId = getResources().getIdentifier(
                    StringUtils.replaceSpecialChars(Globals.departureInfo.getCity()),
                    "string",
                    getPackageName());
            city.setText(Globals.departureInfo.getCity());

            if (resourceId > 0) {
                city.setText(getResources().getString(resourceId));
            } else {
                city.setText(Globals.departureInfo.getCity());
            }
        }

        if (code != null) {
            code.setText(Globals.departureInfo.getCode());
        }

        if (company != null) {
            company.setText(Globals.departureInfo.getCompany());
        }

        if (timeScheduled != null) {
            timeScheduled.setText(Globals.departureInfo.getExpectedTime());
        }

        if (timeActual != null && Globals.departureInfo.getActualTime() != null) {
            actualRow.setVisibility(View.VISIBLE);
            timeActual.setText(Globals.departureInfo.getActualTime());
        }

        if (status != null && Globals.departureInfo.getStatus() != null) {
            statusRow.setVisibility(View.VISIBLE);

            String tmpStatus = Globals.departureInfo.getStatus();
            int resourceId = getResources()
                    .getIdentifier(StringUtils
                                    .replaceSpecialChars(tmpStatus),
                            "string",
                            getPackageName());

            if (resourceId > 0) {
                status.setText(getResources().getString(resourceId));
            } else {
                status.setText(tmpStatus);
            }
        }

        if (gate != null && Globals.departureInfo.getGate() != null) {
            gate.setText(Globals.departureInfo.getGate());
        }
    }
}
