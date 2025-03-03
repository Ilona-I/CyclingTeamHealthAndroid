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
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ChatsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Map<Integer, String> chats;
    private AsyncHttpClient client;
    private final String CHATS_URL = "http://192.168.211.219:8080/CyclingTeamHealth_war/chats";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ImageButton languageButton = findViewById(R.id.imageButton);
        TextView languageText = findViewById(R.id.textView11);
        LanguageManager.setLocale(sharedPreferences, languageButton, languageText, this);
        AppCompatActivity appCompatActivity = this;
        languageButton.setOnClickListener(view -> LanguageManager.changeLanguage(languageText.getText().toString(), appCompatActivity));
        ((TextView) findViewById(R.id.textView)).setText(R.string.app_name);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        menu.getItem(1).setChecked(true);
        menu.getItem(0).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(ChatsActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        });
        menu.getItem(2).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(ChatsActivity.this, TrainingsActivity.class);
            startActivity(intent);
            return true;
        });
        menu.getItem(3).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(ChatsActivity.this, TeamsActivity.class);
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
        client.get(CHATS_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    chats = new ObjectMapper().readValue(response.toString(), HashMap.class);
                    LinearLayout linearLayout = findViewById(R.id.chats);
                    linearLayout.removeAllViews();
                    if (!chats.isEmpty()) {
                        for (Map.Entry<Integer, String> entry : chats.entrySet()) {
                            View view = getLayoutInflater().inflate(R.layout.chat_item, null);
                            TextView titleTextView = (TextView) view.findViewById(R.id.textView7);
                            titleTextView.setText(entry.getValue());
                            view.setOnClickListener(view1 -> {
                                Intent intent = new Intent(ChatsActivity.this, ChatActivity.class);
                                intent.putExtra("chatId", entry.getKey());
                                startActivity(intent);
                            });
                            linearLayout.addView(view);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(ChatsActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
