package com.example.mayoolwin.ssisteam2_android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.ConnectException;

public class LoginActivity extends Activity {

    SharedPreferences pref;
    EditText user;
    EditText pass;
    Button btn;
    boolean valid = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(isConnected())
        {

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
        else
        {
            AlertDialog.Builder b=new AlertDialog.Builder(this, R.style.AppTheme_Dark_Dialog);
            b.setTitle("No internet connection!");
            b.setMessage("Opps! you are not connnected to internet!");
            b.setCancelable(false);
            b.show();
        }




    }

    public void login() {

      final String   name = user.getText().toString();
      final String   password = pass.getText().toString();

        if (name.isEmpty() ) {
            user.setError("User Name is empty");
            valid = false;
        } else {
            user.setError(null);
        }

        if (password.isEmpty()) {
            pass.setError("Password is empty");
            valid = false;
        } else {
            pass.setError(null);
            validate();
        }
    }
    public void validate() {


       final String name = user.getText().toString();
        final String password = pass.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();



        try {
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onSignupSuccess or onSignupFailed
                            // depending on success
                            new AsyncTask<String, Void, User>() {
                                @Override
                                protected User doInBackground(String... strings) {
                                    return User.getUser(strings[0], strings[1]);
                                }

                                protected void onPostExecute(User u) {
                                    if (u.get("UserName").equals("failed")) {
                                        Toast.makeText(getBaseContext(), "User Name or Password is incorrect", Toast.LENGTH_LONG).show();
                                    } else {
                                        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("username", u.get("UserName"));
                                        editor.putString("role", u.get("Role"));
                                        editor.putString("dept_code", u.get("DeptCode"));
                                        editor.putString("flag", u.get("Flag"));
                                        editor.commit();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }


                            }.execute(name, password);
                            // onSignupFailed();
                            progressDialog.dismiss();
                        }
                    }, 3000);

        }
        catch (NullPointerException e)
        {

            Toast.makeText(getBaseContext(), "No internet Connetion! Check your network first!", Toast.LENGTH_LONG).show();

        }



    }


    @Override
    public void onBackPressed()
    {
        //do nothing;
    }

    public boolean isConnected()
    {

        ConnectivityManager conn=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=conn.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
            return  true;
        else
            return false;
    }



}
