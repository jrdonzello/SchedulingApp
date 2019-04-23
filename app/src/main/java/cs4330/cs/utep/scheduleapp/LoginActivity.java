package cs4330.cs.utep.scheduleapp;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;

    protected String result;

    protected void login(View view) {
        new Login().execute(username.getText().toString(), password.getText().toString());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);
        login = findViewById(R.id.loginButton);


    }

}
