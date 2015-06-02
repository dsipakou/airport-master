package by.airport.airport_master.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by dzianis.sipakou on 6/1/2015.
 */
public class Localization {
    public static void changeLocal(Context context, Context baseContext, Locale locale) {
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        baseContext.getResources().updateConfiguration(configuration, baseContext.getResources().getDisplayMetrics());
    }
}
