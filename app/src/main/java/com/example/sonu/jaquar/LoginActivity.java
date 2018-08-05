package com.example.sonu.jaquar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonu.jaquar.Constants.CheckInternetCoonnection;
import com.example.sonu.jaquar.Receivers.InternetReceiver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText loginUsername,loginPassword;
    Button loginButton;
    CheckBox loginCheckBox;
    public static   boolean intetnetonnection = true;
    String usernameValue,passwordValue;
    RelativeLayout relativeLayout;
    SharedPreferences sharedPreferences;
    boolean sharedPreferenceVlue;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progress;
    CoordinatorLayout coordinatorLayout;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    FirebaseUser firebaseUser;
    TextView passwordImage;
    InternetReceiver internetReceiver;
    private  int i=1;
    private AlertDialog.Builder mbuilder;
    public static final String TAG="LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
        initViews();
        if(networkInfo == null || !networkInfo.isConnected())
        {
           Snackbar.make(coordinatorLayout,"No Internet Connection",Snackbar.LENGTH_SHORT).show();
           intetnetonnection=false;
        }
        IntentFilter intentFilter =new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        internetReceiver =new InternetReceiver();
        registerReceiver(internetReceiver,intentFilter);

//        sharedPreferenceVlue =  checkSharedpreferenceForUserNamAndPassword();
//        if(sharedPreferenceVlue)
//        {
//            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//            finish();
//        }
//        passwordImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!loginPassword.getText().toString().trim().isEmpty())
//                {
//                    String pass = loginPassword.getText().toString().trim();
//                    if(!pass.isEmpty())
//                    {
//                        if(i==1)
//                        {
//                            i=0;
//                            loginPassword.setTransformationMethod(null);
//                            loginPassword.setSelection(loginPassword.getText().length());
//                            passwordImage.setBackgroundResource(R.drawable.ic_visibility_black_24dp);
//                        }
//                        else{
//                            i=1;
//                            loginPassword.setTransformationMethod(new PasswordTransformationMethod());
//                            loginPassword.setSelection(loginPassword.getText().length());
//                            passwordImage.setBackgroundResource(R.drawable.ic_visibility_off_black_24dp);
//                        }
//                    }
//
//                }
//            }
//        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intetnetonnection) {
                    progress.setMessage("Loging ...");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.show();
                    usernameValue = loginUsername.getText().toString().trim();
                    passwordValue = loginPassword.getText().toString().trim();
                    if (!usernameValue.isEmpty() && !passwordValue.isEmpty()) {
                        Log.d("LoginActivity", "onclick");
                        firebaseAuth.signInWithEmailAndPassword(usernameValue, passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
//                                    if (loginCheckBox.isChecked()) {
//                                        storeUserNameAndPassword(usernameValue, passwordValue);
//                                       startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//                                        finish();
//                                    }
                                    Log.d("LoginActivity", "successfull");
                                    progress.dismiss();
                                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//                                    overridePendingTransition(R.anim.right_in,R.anim.left_out);

                                    finish();
                                    //Snackbar.make(coordinatorLayout, "Login Successsfull", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    Log.d("LoginActivity", task.getException().toString());
                                    progress.dismiss();
                                    Snackbar.make(coordinatorLayout, "Invalidae UserName or Password", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        progress.dismiss();
                        Toast.makeText(LoginActivity.this, "All field are required !!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Snackbar.make(coordinatorLayout,"No Internet Connection",Snackbar.LENGTH_SHORT).show();

                }
            }


        });

    }


//    private boolean checkSharedpreferenceForUserNamAndPassword() {
//        sharedPreferences          = getSharedPreferences("userNameAndPassword",MODE_PRIVATE);
//        String sharedUserNamValue = sharedPreferences.getString("loginUsername","");
//        String sharedPasswordValue = sharedPreferences.getString("loginPassword","");
//        if(!sharedUserNamValue.isEmpty() && !sharedPasswordValue.isEmpty())
//        {
//            Log.d("sharedUsername",sharedUserNamValue);
//            Log.d("sharedPassword",sharedPasswordValue);
//            //   Toast.makeText(getApplicationContext(), "New Activity", Toast.LENGTH_SHORT).show();
//            return  true;
//        }
//        else {
//            return false;
//        }
//
//
//    }
//
//    private void storeUserNameAndPassword(String usernameValue, String passwordValue) {
//        sharedPreferences               = getSharedPreferences("userNameAndPassword",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("loginUsername",usernameValue);
//        editor.putString("loginPassword",passwordValue);
//        editor.apply();
//
//    }

    private void initViews() {
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_btn);
       // relativeLayout = findViewById(R.id.relativeLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        Log.d("FirebaseInstance", firebaseAuth.toString());
        progress = new ProgressDialog(LoginActivity.this);
        coordinatorLayout = findViewById(R.id.cordinateLayout);
        firebaseUser = firebaseAuth.getCurrentUser();
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        //passwordImage = findViewById(R.id.passwordImage);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        if (internetReceiver != null) {
            unregisterReceiver(internetReceiver);
        }
    }
    @Override
    protected void onRestart() {

        super.onRestart();
        Log.d(TAG, "onRestart: ");


    }
}
