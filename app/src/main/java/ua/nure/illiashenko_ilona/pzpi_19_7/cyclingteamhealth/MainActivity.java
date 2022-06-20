package ua.nure.illiashenko_ilona.pzpi_19_7.cyclingteamhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getPreferences(0);
        ImageButton languageButton = findViewById(R.id.imageButton);
        TextView languageText = findViewById(R.id.textView11);
        LanguageManager.setLocale(sharedPreferences, languageButton, languageText, this);
        ((TextView)findViewById(R.id.textView)).setText(R.string.app_name);
        ((Button)findViewById(R.id.button)).setText(R.string.menu_login);
        ((Button)findViewById(R.id.button2)).setText(R.string.menu_signup);
        AppCompatActivity appCompatActivity = this;
        languageButton.setOnClickListener(view -> LanguageManager.changeLanguage(languageText.getText().toString(), appCompatActivity));
        Button logIn = findViewById(R.id.button);
        Button signUp = findViewById(R.id.button2);
        logIn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(intent);
        });
        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}