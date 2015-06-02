package by.airport.airport_master;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import by.airport.airport_master.utils.Globals;
import by.airport.airport_master.utils.StringUtils;


public class ArrivalActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView city = (TextView) findViewById(R.id.arrival_city);
        TextView code = (TextView) findViewById(R.id.arrival_code);
        TextView company = (TextView) findViewById(R.id.arrival_company);
        TextView timeScheduled = (TextView) findViewById(R.id.arrival_scheduled);
        TableRow actualRow = (TableRow) findViewById(R.id.arrival_actual_row);
        TextView timeActual = (TextView) findViewById(R.id.arrival_actual);
        TableRow statusRow = (TableRow) findViewById(R.id.arrival_status_row);
        TextView status = (TextView) findViewById(R.id.arrival_status);

        actualRow.setVisibility(View.GONE);
        statusRow.setVisibility(View.GONE);

        if (city != null) {
            int resourceId = getResources().getIdentifier(
                    StringUtils.replaceSpecialChars(Globals.arrivalInfo.getCity()),
                    "string",
                    getPackageName());
            city.setText(Globals.arrivalInfo.getCity());

            if (resourceId > 0) {
                city.setText(getResources().getString(resourceId));
            } else {
                city.setText(Globals.arrivalInfo.getCity());
            }
        }

        if (code != null) {
            code.setText(Globals.arrivalInfo.getCode());
        }

        if (company != null) {
            company.setText(Globals.arrivalInfo.getCompany());
        }

        if (timeScheduled != null) {
            timeScheduled.setText(Globals.arrivalInfo.getExpectedTime());
        }

        if (timeActual != null && Globals.arrivalInfo.getActualTime() != null) {
            actualRow.setVisibility(View.VISIBLE);
            timeActual.setText(Globals.arrivalInfo.getActualTime());
        }

        if (status != null && Globals.arrivalInfo.getStatus() != null) {
            statusRow.setVisibility(View.VISIBLE);
            String tmpStatus = Globals.arrivalInfo.getStatus();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arrival, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}