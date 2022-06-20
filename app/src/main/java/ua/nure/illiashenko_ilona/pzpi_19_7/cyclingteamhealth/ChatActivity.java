package ua.nure.illiashenko_ilona.pzpi_19_7.cyclingteamhealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
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
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ChatActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private AsyncHttpClient client;
    private RequestParams params;
    private EditText editText;
    private String CHAT_URL = "http://192.168.211.219:8080/CyclingTeamHealth_war/chat?chatId=";
    private final String SEND_MESSAGE = "http://192.168.211.219:8080/CyclingTeamHealth_war/chat";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
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
            Intent intent = new Intent(ChatActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        });
        menu.getItem(1).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(ChatActivity.this, ChatsActivity.class);
            startActivity(intent);
            return true;
        });
        menu.getItem(2).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(ChatActivity.this, TrainingsActivity.class);
            startActivity(intent);
            return true;
        });
        menu.getItem(3).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(ChatActivity.this, TeamsActivity.class);
            startActivity(intent);
            return true;
        });
        ((TextView) findViewById(R.id.textView)).setText(R.string.app_name);
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
        CHAT_URL += getIntent().getExtras().getString("chatId");
        client.get(CHAT_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Map<String, List<String>> chat = new ObjectMapper().readValue(response.toString(), HashMap.class);
                    List<String> messages = chat.get("messages");
                    LinearLayout linearLayout = findViewById(R.id.messages);
                    linearLayout.removeAllViews();
                    for (String message : messages) {
                        Map<String, String> messageMap = new ObjectMapper().readValue(message, HashMap.class);
                        View view = getLayoutInflater().inflate(R.layout.message_item, null);
                        TextView dateTimeTextView = (TextView) view.findViewById(R.id.textView7);
                        dateTimeTextView.setText(messageMap.get("dateTime").substring(0, 16));
                        TextView loginTextView = (TextView) view.findViewById(R.id.textView13);
                        loginTextView.setText(messageMap.get("sender"));
                        TextView textTextView = (TextView) view.findViewById(R.id.textView14);
                        textTextView.setText(messageMap.get("text"));
                        linearLayout.addView(view);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(ChatActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton sendButton = findViewById(R.id.imageButton2);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                params = new RequestParams();
                params.put("sender", sharedPreferences.getString("login", ""));
                params.put("chatId", getIntent().getExtras().getString("chatId"));
                editText = ((EditText) findViewById(R.id.editTextTextPersonName));
                params.put("text", editText.getText().toString());
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
                client.post(SEND_MESSAGE, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        editText.setText("");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);

                    }
                });
                recreate();
            }
        });
    }
}