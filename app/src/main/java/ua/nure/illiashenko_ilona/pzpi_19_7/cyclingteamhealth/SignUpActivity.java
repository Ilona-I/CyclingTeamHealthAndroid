package ua.nure.illiashenko_ilona.pzpi_19_7.cyclingteamhealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignUpActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private RequestParams params;
    private AsyncHttpClient client;
    private String teamType = "enterTeam";
    private String gender;
    private String role;
    private final String USER_URL = "http://192.168.211.219:8080/CyclingTeamHealth_war/signUp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ImageButton languageButton = findViewById(R.id.imageButton);
        TextView languageText = findViewById(R.id.textView11);
        LanguageManager.setLocale(sharedPreferences, languageButton, languageText, this);
        AppCompatActivity appCompatActivity = this;
        languageButton.setOnClickListener(view -> LanguageManager.changeLanguage(languageText.getText().toString(), appCompatActivity));
        ((TextView)findViewById(R.id.textView)).setText(R.string.app_name);
        ((TextView)findViewById(R.id.textView2)).setText(R.string.menu_signup);
        ((Button)findViewById(R.id.button3)).setText(R.string.menu_signup);
        EditText loginEditText = findViewById(R.id.editTextLogin);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        EditText repeatedPasswordEditText = findViewById(R.id.editTextRepeatedPassword);
        EditText firstNameEditText = findViewById(R.id.editTextFirstName);
        EditText lastNameEditText = findViewById(R.id.editTextLastName);
        EditText emailEditText = findViewById(R.id.editTextEmail);
        Switch enterTeam = findViewById(R.id.switch1);
        Switch newTeam = findViewById(R.id.switch2);
        EditText teamIdEditText = findViewById(R.id.editTeamId);
        EditText teamNameEditText = findViewById(R.id.editTextTeamName);
        EditText birthDateEditText = findViewById(R.id.editTextBirthDate);
        EditText heightEditText = findViewById(R.id.editTextHeight);
        EditText weightEditText = findViewById(R.id.editTextWeight);
        Switch femaleSwitch = findViewById(R.id.switchFemale);
        Switch maleSwitch = findViewById(R.id.switchMale);
        Switch trainerSwitch = findViewById(R.id.switch3);
        Switch cyclistSwitch = findViewById(R.id.switch4);
        loginEditText.setHint(R.string.login);
        passwordEditText.setHint(R.string.password);
        repeatedPasswordEditText.setHint(R.string.repeat_password);
        firstNameEditText.setHint(R.string.first_name);
        lastNameEditText.setHint(R.string.last_name);
        emailEditText.setHint(R.string.email);
        birthDateEditText.setHint(R.string.birth_date);
        heightEditText.setHint(R.string.height);
        weightEditText.setHint(R.string.weight);
        enterTeam.setText(R.string.enter_team);
        newTeam.setText(R.string.create_team);
        femaleSwitch.setText(R.string.female);
        maleSwitch.setText(R.string.male);
        teamIdEditText.setHint(R.string.team_id);
        teamNameEditText.setHint(R.string.team_name);
        trainerSwitch.setText(R.string.trainer);
        cyclistSwitch.setText(R.string.cyclist);
        enterTeam.setOnCheckedChangeListener((compoundButton, b) -> {
            if (enterTeam.isChecked()) {
                newTeam.setChecked(false);
                teamType = "enterTeam";
                teamIdEditText.setEnabled(true);
            } else {
                newTeam.setChecked(true);
                teamType = "newTeam";
                teamNameEditText.setEnabled(false);
            }
        });
        newTeam.setOnCheckedChangeListener((compoundButton, b) -> {
            if (newTeam.isChecked()) {
                enterTeam.setChecked(false);
                teamType = "newTeam";
                teamNameEditText.setEnabled(true);
            } else {
                enterTeam.setChecked(true);
                teamType = "enterTeam";
                teamIdEditText.setEnabled(false);
            }
        });
        femaleSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (femaleSwitch.isChecked()) {
                maleSwitch.setChecked(false);
                gender = "female";
            } else {
                maleSwitch.setChecked(true);
                gender = "male";
            }
        });
        maleSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (maleSwitch.isChecked()) {
                femaleSwitch.setChecked(false);
                gender = "male";
            } else {
                femaleSwitch.setChecked(true);
                gender = "female";
            }
        });
        trainerSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (trainerSwitch.isChecked()) {
                cyclistSwitch.setChecked(false);
                role = "trainer";
            } else {
                cyclistSwitch.setChecked(true);
                role = "cyclist";
            }
        });
        cyclistSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cyclistSwitch.isChecked()) {
                trainerSwitch.setChecked(false);
                role = "cyclist";
            } else {
                trainerSwitch.setChecked(true);
                role = "trainer";
            }
        });
        Button signUpButton = findViewById(R.id.button3);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String repeatedPassword = repeatedPasswordEditText.getText().toString();
                String birthday = birthDateEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String teamId = teamIdEditText.getText().toString();
                String teamName = teamNameEditText.getText().toString();
                String height = heightEditText.getText().toString();
                String weight = weightEditText.getText().toString();
                params = new RequestParams();
                params.put("login", login);
                params.put("password", password);
                params.put("repeatedPassword", repeatedPassword);
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("teamType", teamType);
                params.put("teamId", teamId);
                params.put("teamName", teamName);
                params.put("height", height);
                params.put("weight", weight);
                params.put("birthDate", birthday);
                params.put("email", email);
                params.put("gender", gender);
                params.put("role", role);
                client = new AsyncHttpClient();

                client.post(USER_URL, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            Toast.makeText(SignUpActivity.this, "Welcome " + response.get("login") + "!", Toast.LENGTH_SHORT).show();
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
                            Intent intent = new Intent(SignUpActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(SignUpActivity.this, "Invalid input! Try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}