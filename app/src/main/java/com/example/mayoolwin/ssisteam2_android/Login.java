package com.example.mayoolwin.ssisteam2_android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    SharedPreferences pref;
    EditText user;
    EditText pass;
    Button btn;
    boolean valid = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user=(EditText) findViewById(R.id.input_user);
        pass=(EditText) findViewById(R.id.input_password);
        btn=(Button) findViewById(R.id.btn_login);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {

        String name = user.getText().toString();
        String password = pass.getText().toString();

        if (name.isEmpty() ) {
            user.setError("User Name is empty");
            valid = false;
        } else {
            user.setError(null);
        }

        if (password.isEmpty()) {
            pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            pass.setError(null);
            validate();
        }


    }






    public void validate() {


        String name = user.getText().toString();
        String password = pass.getText().toString();



        new AsyncTask<String, Void, User>()
        {
            @Override
            protected User doInBackground(String... strings) {
                return User.getUser(strings[0],strings[1]);
            }

            protected void onPostExecute(User u)
            {
                if(u.get("UserName").equals("failed"))
                {
                    Toast.makeText(getBaseContext(), "User Name or Password is incorrect", Toast.LENGTH_LONG).show();
                }
                else
                {
                    pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", u.get("UserName"));
                    editor.putString("role", u.get("Role"));
                    editor.putString("dept_code",u.get("DeptCode"));
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }



        }.execute(name,password);


    }





}
