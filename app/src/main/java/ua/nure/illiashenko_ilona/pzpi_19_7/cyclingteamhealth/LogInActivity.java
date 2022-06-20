package ua.nure.illiashenko_ilona.pzpi_19_7.cyclingteamhealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LogInActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private RequestParams params;
    private AsyncHttpClient client;
    private final String LOG_IN_URL = "http://192.168.211.219:8080/CyclingTeamHealth_war/logIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ImageButton languageButton = findViewById(R.id.imageButton);
        TextView languageText = findViewById(R.id.textView11);
        LanguageManager.setLocale(sharedPreferences, languageButton, languageText, this);
        AppCompatActivity appCompatActivity = this;
        languageButton.setOnClickListener(view -> LanguageManager.changeLanguage(languageText.getText().toString(), appCompatActivity));
        ((TextView)findViewById(R.id.textView)).setText(R.string.app_name);
        ((TextView)findViewById(R.id.textView2)).setText(R.string.menu_login);
        EditText loginEditText = findViewById(R.id.editTextLogin);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        Button logInButton = findViewById(R.id.button3);
        logInButton.setText(R.string.menu_login);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                params = new RequestParams();
                params.put("login", login);
                params.put("password", password);
                client = new AsyncHttpClient();

                client.post(LOG_IN_URL, params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            Toast.makeText(LogInActivity.this, "Welcome "+response.get("login")+"!", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("login", response.get("login").toString());
                            editor.putString("firstName", response.get("firstName").toString());
                            editor.putString("lastName", response.get("lastName").toString());
                            editor.putString("email", response.get("email").toString());
                            editor.putString("role", response.get("role").toString());
                            editor.putString("teamId", response.get("teamId").toString());
                            editor.putString("birthDate", response.get("birthDate").toString());
                            editor.putString("height", response.get("height").toString());
                            editor.putString("weight", response.get("weight").toString());
                            editor.putString("gender", response.get("gender").toString());
                            editor.putString("status", response.get("status").toString());
                            editor.apply();
                            Intent intent = new Intent(LogInActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(LogInActivity.this, "Wrong login or password!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}