package com.ggktech.bookmydrive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView loginUsername;
    TextView loginPassword;
    Button loginBtn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences("Drive",MODE_PRIVATE);
        editor = preferences.edit();
        dbHelper = new DBHelper(this);
        bindUI();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = loginUsername.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                if(name.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please provide credentials", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(name.equals("admin") && password.equals("welcome")) {
                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this, "Invalid username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(preferences.getBoolean("isFirst",true)){
            addPanel();
            editor.putBoolean("isFirst",false);
            editor.commit();
        }
    }

    private void addPanel() {
        dbHelper.addPanelMember("James Shelbourne");
        dbHelper.addPanelMember("Rajan");
        dbHelper.addPanelMember("Abhilash");
        dbHelper.addPanelMember("Noah Mccalment");
        dbHelper.addPanelMember("Agni");
        dbHelper.addPanelMember("Chaitanya");
        dbHelper.addPanelMember("Sudheer");
        dbHelper.addPanelMember("Karthik");
        dbHelper.addPanelMember("Shane Oswald");
        dbHelper.addPanelMember("Ravinder");
        dbHelper.addPanelMember("Rakesh");
        dbHelper.addPanelMember("Sathvik");
        dbHelper.addPanelMember("Sai Pawan");
    }

    private void bindUI(){
        loginUsername = (TextView) findViewById(R.id.tv_login_username);
        loginPassword = (TextView) findViewById(R.id.tv_login_password);
        loginBtn = (Button) findViewById(R.id.btn_login);
    }
}
