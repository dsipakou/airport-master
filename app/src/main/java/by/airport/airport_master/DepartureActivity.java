package by.airport.airport_master;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import by.airport.airport_master.helpers.DetailsCard;
import by.airport.airport_master.helpers.Statuses;
import by.airport.airport_master.utils.Globals;
import by.airport.airport_master.utils.StringUtils;

public class DepartureActivity extends AppCompatActivity {
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

        DetailsCard detailsCard = new DetailsCard(Globals.departureInfo);

        int imgRes = R.drawable.ic_takeoff;
        city.setCompoundDrawablesWithIntrinsicBounds(imgRes, 0, 0, 0);

        if (city != null) {
            String format_city = StringUtils.replaceSpecialChars(detailsCard.getCity());
            int resourceId = getResources().getIdentifier(
                    format_city,
                    "string",
                    getPackageName());

            if (resourceId > 0) {
                city.setText(getResources().getString(resourceId));
            } else {
                city.setText(detailsCard.getCity());
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
            code.setText(detailsCard.getCode());
        }

        if (company != null) {
            String format_company = StringUtils.replaceSpecialChars(detailsCard.getCompany());
            company.setText(detailsCard.getCompany());
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
            timeScheduled.setText(detailsCard.getExpectedTime());
            timeScheduled.setTypeface(null, Typeface.BOLD);
            timeScheduled.setPaintFlags(0);
            timeScheduled.setTextSize(40);
        }

        if (timeActual != null && detailsCard.getActualTime() != null) {
            timeActual.setVisibility(View.VISIBLE);
            timeActual.setText(detailsCard.getActualTime());
            timeScheduled.setTypeface(null, Typeface.NORMAL);
            timeScheduled.setTextSize(35);
            timeScheduled.setPaintFlags(timeScheduled.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if (status != null && detailsCard.getStatus() != null) {
            Statuses tmpStatus = detailsCard.getStatus();
            status.setVisibility(View.VISIBLE);
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

        if (gate != null && detailsCard.getGate() != null) {
            gate.setVisibility(View.VISIBLE);
            gate.setText(gate.getText() + "  " + detailsCard.getGate());
        }
    }
}
