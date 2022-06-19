package ua.nure.illiashenko_ilona.pzpi_19_7.cyclingteamhealth;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LanguageManager {
    private static SharedPreferences sharedPreferences;


    public static void setLocale(SharedPreferences sharedPreferences1, ImageButton languageButton, TextView languageText, AppCompatActivity appCompatActivity) {
        if (sharedPreferences == null) {
            sharedPreferences = sharedPreferences1;
        }
        String defaultLocale = Locale.getDefault().toString();
        if (!sharedPreferences.contains("language")) {
            changeLanguage(defaultLocale, appCompatActivity);
        } else {
            String newLocale = sharedPreferences.getString("language", "en");
            if (!defaultLocale.equals(newLocale)) {
                Locale locale = new Locale(newLocale);
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.locale = locale;
                appCompatActivity.getBaseContext().getResources().updateConfiguration(configuration, null);
            }
        }
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals("en")) {
            languageButton.setImageResource(R.drawable.en);
            languageText.setText("uk");
        } else if (locale.getLanguage().equals("uk")) {
            languageButton.setImageResource(R.drawable.ua);
            languageText.setText("en");
        }
    }

    public static void changeLanguage(String language, AppCompatActivity appCompatActivity) {
        sharedPreferences.edit()
                .putString("language", language)
                .apply();
        appCompatActivity.recreate();
    }
}