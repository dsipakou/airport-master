package by.airport.airport_master;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import by.airport.airport_master.helpers.Statuses;
import by.airport.airport_master.utils.Globals;
import by.airport.airport_master.utils.StringUtils;


public class ArrivalActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView cover = (ImageView) findViewById(R.id.cover_image);
        TextView city = (TextView) findViewById(R.id.city);
        TextView code = (TextView) findViewById(R.id.code);
        TextView company = (TextView) findViewById(R.id.company);
        ImageView company_img = (ImageView) findViewById(R.id.company_img);
        TextView timeScheduled = (TextView) findViewById(R.id.scheduled);
        TextView timeActual = (TextView) findViewById(R.id.actual);
        TextView status = (TextView) findViewById(R.id.status);
        TextView gate = (TextView) findViewById(R.id.gate_title);

        int imgRes = R.drawable.ic_landing;
        city.setCompoundDrawablesWithIntrinsicBounds(0, 0, imgRes, 0);

        if (city != null) {
            String format_city = StringUtils.replaceSpecialChars(Globals.arrivalInfo.getCity());
            int resourceId = getResources().getIdentifier(
                    format_city,
                    "string",
                    getPackageName());
            city.setText(Globals.arrivalInfo.getCity());

            if (resourceId > 0) {
                city.setText(getResources().getString(resourceId));
            }
            int imageResourceID = getResources().getIdentifier(
                    format_city.toLowerCase(),
                    "drawable",
                    getPackageName());
            if (imageResourceID > 0) {
                cover.setImageResource(imageResourceID);
            }
        }

        if (code != null) {
            code.setText(Globals.arrivalInfo.getCode());
        }

        if (company != null) {
            String format_company = StringUtils.replaceSpecialChars(Globals.arrivalInfo.getCompany());
            company.setText(Globals.arrivalInfo.getCompany());
            int imageResourceID = getResources().getIdentifier(
                    format_company.toLowerCase(),
                    "drawable",
                    getPackageName());
            if (imageResourceID > 0) {
                company_img.setVisibility(View.VISIBLE);
                company.setVisibility(View.GONE);
                company_img.setImageResource(imageResourceID);
            } else {
                company.setVisibility(View.VISIBLE);
                company_img.setVisibility(View.GONE);
            }
        }

        if (timeScheduled != null) {
            timeScheduled.setText(Globals.arrivalInfo.getExpectedTime());
        }

        if (timeActual != null && Globals.arrivalInfo.getActualTime() != null) {
            timeActual.setText(Globals.arrivalInfo.getActualTime());
        }

        if (status != null && Globals.arrivalInfo.getStatus() != null) {
            Statuses tmpStatus = Globals.arrivalInfo.getStatus();
            int resourceId = getResources()
                    .getIdentifier(StringUtils
                                    .replaceSpecialChars(tmpStatus.name()),
                            "string",
                            getPackageName());

            if (resourceId > 0) {
                status.setText(getResources().getString(resourceId));
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

        if (gate != null && Globals.arrivalInfo.getGate() != null) {
            gate.setText(gate.getText() + "  " + Globals.arrivalInfo.getGate());
        }
    }
}