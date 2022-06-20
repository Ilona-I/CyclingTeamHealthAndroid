package ua.nure.illiashenko_ilona.pzpi_19_7.cyclingteamhealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class TrainingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private AsyncHttpClient client;
    private String TRAININGS_URL = "http://192.168.211.219:8080/CyclingTeamHealth_war/training/goals?teamId=";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainings);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ImageButton languageButton = findViewById(R.id.imageButton);
        TextView languageText = findViewById(R.id.textView11);
        LanguageManager.setLocale(sharedPreferences, languageButton, languageText, this);
        AppCompatActivity appCompatActivity = this;
        languageButton.setOnClickListener(view -> LanguageManager.changeLanguage(languageText.getText().toString(), appCompatActivity));
        ((TextView) findViewById(R.id.textView)).setText(R.string.app_name);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        menu.getItem(2).setChecked(true);
        menu.getItem(0).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(TrainingsActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        });
        menu.getItem(1).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(TrainingsActivity.this, ChatsActivity.class);
            startActivity(intent);
            return true;
        });
        menu.getItem(3).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(TrainingsActivity.this, TeamsActivity.class);
            startActivity(intent);
            return true;
        });
        client = new AsyncHttpClient();
        String user = "{\"login\":\"" + sharedPreferences.getString("login", "") +
                "\", \"firstName\":\"" + sharedPreferences.getString("firstName", "") +
                "\", \"lastName\":\"" + sharedPreferences.getString("lastName", "") +
                "\", \"email\":\"" + sharedPreferences.getString("email", "") +
                "\", \"birthDate\":\"" + sharedPreferences.getString("birthDate", "") +
                "\", \"height\":\"" + sharedPreferences.getString("height", "") +
                "\", \"weight\":\"" + sharedPreferences.getString("weight", "") +
                "\", \"role\":\"" + sharedPreferences.getString("role", "") +
                "\", \"gender\":\"" + sharedPreferences.getString("gender", "") +
                "\", \"status\":\"" + sharedPreferences.getString("status", "") +
                "\"}";
        client.addHeader("Authorization", Base64Util.encodeString(user));
        TRAININGS_URL += sharedPreferences.getString("teamId", "");
        client.get(TRAININGS_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Map<String, List<String>> trainingsMap = new ObjectMapper().readValue(response.toString(), HashMap.class);
                    List<String> trainingsStringList = trainingsMap.get("trainingGoals");
                    LinearLayout linearLayout = findViewById(R.id.trainings);
                    linearLayout.removeAllViews();
                    for (String training : trainingsStringList) {
                        Map<String, String> trainingMap = new ObjectMapper().readValue(training, HashMap.class);
                        View view = getLayoutInflater().inflate(R.layout.training_item, null);
                        TextView startDateTimeTextView = (TextView) view.findViewById(R.id.textViewStartDatetime);
                        startDateTimeTextView.setText(trainingMap.get("startDateTime").substring(0, 16));
                        TextView endDateTimeTextView = (TextView) view.findViewById(R.id.textView9);
                        endDateTimeTextView.setText(trainingMap.get("endDateTime").substring(0, 16));
                        TextView textViewPulse = (TextView) view.findViewById(R.id.textViewPulse);
                        textViewPulse.setText(trainingMap.get("pulse"));
                        TextView textViewSpeed = (TextView) view.findViewById(R.id.textViewSpeed);
                        textViewSpeed.setText(trainingMap.get("speed"));
                        linearLayout.addView(view);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(TrainingsActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}