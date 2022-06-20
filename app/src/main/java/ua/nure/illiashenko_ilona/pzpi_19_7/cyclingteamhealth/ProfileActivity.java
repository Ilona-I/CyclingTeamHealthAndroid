package ua.nure.illiashenko_ilona.pzpi_19_7.cyclingteamhealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    private String login;
    private SharedPreferences sharedPreferences;
    private RequestParams params;
    private AsyncHttpClient client;
    private final String USER_URL = "http://192.168.211.219:8080/CyclingTeamHealth_war/user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ImageButton languageButton = findViewById(R.id.imageButton);
        TextView languageText = findViewById(R.id.textView11);
        LanguageManager.setLocale(sharedPreferences, languageButton, languageText, this);
        AppCompatActivity appCompatActivity = this;
        languageButton.setOnClickListener(view -> LanguageManager.changeLanguage(languageText.getText().toString(), appCompatActivity));
        ((TextView)findViewById(R.id.textView)).setText(R.string.app_name);
        ((TextView)findViewById(R.id.textView2)).setText(R.string.menu_profile);
        ((Button)findViewById(R.id.button3)).setText(R.string.save_changes);
        login = sharedPreferences.getString("login", "");
        String role = sharedPreferences.getString("role", "");
        String gender = sharedPreferences.getString("gender", "");
        TextView loginTextView = findViewById(R.id.textView4);
        TextView roleTextView = findViewById(R.id.textView3);
        TextView genderTextView = findViewById(R.id.textView5);
        loginTextView.setText(login);
        if("male".equals(gender)){
            genderTextView.setText(getString(R.string.gender) + " : " +getString(R.string.male));
        } else if ("female".equals(gender)){
            genderTextView.setText(getString(R.string.gender) + " : " +getString(R.string.female));
        } else {
            genderTextView.setText(getString(R.string.gender) + " -" );
        }
        if("trainer".equals(role)){
            roleTextView.setText(getString(R.string.role)  + " : " +getString(R.string.trainer));
        } else if("cyclist".equals(role)){
            roleTextView.setText(getString(R.string.role)  + " : " +getString(R.string.cyclist));
        }  else if("soigneur".equals(role)){
            roleTextView.setText(getString(R.string.role)  + " : " +getString(R.string.soigneur));
        } else if("doctor".equals(role)){
            roleTextView.setText(getString(R.string.role)  + " : " +getString(R.string.doctor));
        } else {
            roleTextView.setText(getString(R.string.role)  + " -");
        }
        EditText firstNameEditText = findViewById(R.id.editTextFirstName);
        EditText lastNameEditText = findViewById(R.id.editTextLastName);
        EditText emailEditText = findViewById(R.id.editTextEmail);
        EditText birthDateEditText = findViewById(R.id.editTextBirthDate);
        EditText heightEditText = findViewById(R.id.editTextHeight);
        EditText weightEditText = findViewById(R.id.editTextWeight);
        firstNameEditText.setHint(R.string.first_name);
        lastNameEditText.setHint(R.string.last_name);
        emailEditText.setHint(R.string.email);
        birthDateEditText.setHint(R.string.birth_date);
        heightEditText.setHint(R.string.height);
        weightEditText.setHint(R.string.weight);
        firstNameEditText.setText(sharedPreferences.getString("firstName", ""));
        lastNameEditText.setText(sharedPreferences.getString("lastName", ""));
        emailEditText.setText(sharedPreferences.getString("email", ""));
        birthDateEditText.setText(sharedPreferences.getString("birthDate", ""));
        heightEditText.setText(sharedPreferences.getString("height", ""));
        weightEditText.setText(sharedPreferences.getString("weight", ""));
        Button saveChangesButton = findViewById(R.id.button3);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        menu.getItem(0).setChecked(true);
        menu.getItem(1).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(ProfileActivity.this, ChatsActivity.class);
            startActivity(intent);
            return true;
        });
        menu.getItem(2).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(ProfileActivity.this, TrainingsActivity.class);
            startActivity(intent);
            return true;
        });
        menu.getItem(3).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(ProfileActivity.this, TeamsActivity.class);
            startActivity(intent);
            return true;
        });
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String birthday = birthDateEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String height = heightEditText.getText().toString();
                String weight = weightEditText.getText().toString();
                params = new RequestParams();
                params.put("login", login);
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("height", height);
                params.put("weight", weight);
                params.put("birthDate", birthday);
                params.put("email", email);
                params.put("gender", sharedPreferences.getString("gender", ""));
                params.put("status", "active");

                client = new AsyncHttpClient();
                String user = "{\"login\":\"" +sharedPreferences.getString("login", "") +
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
                client.post(USER_URL, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
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
                            editor.commit();
                            Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(ProfileActivity.this, "Invalid input! Try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}