package by.airport.airport_main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import java.util.Locale;

import by.airport.airport_main.utils.Localization;

public class AirportPreferenceActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new AirportPreferenceFragment())
                .commit();
    }


    public static class AirportPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            //Preference preference = findPreference(key);
            if (key.equals(R.string.locale_lang)) {
                Localization.changeLocal(getActivity(), getActivity().getBaseContext(), new Locale(sharedPreferences.getString(key, "")));
            }
            /*Intent restartIntent = new Intent(getActivity(), MainActivity.class);
            restartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getActivity().finish();
            startActivity(restartIntent);*/

            Intent i = getActivity().getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getActivity().getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }
}
