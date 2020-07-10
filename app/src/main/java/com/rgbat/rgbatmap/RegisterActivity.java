package com.rgbat.rgbatmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Driver;

public class RegisterActivity extends AppCompatActivity {
    private Button DriverLoginButton;
    private Button DriverRegisterButton;
    private TextView DriverRegisterLink;
    private TextView DriverStatus;
    private EditText EmailDriver,PasswordDriver;
    private FirebaseAuth mAuth;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DriverLoginButton = findViewById(R.id.driver_login_button);
        DriverRegisterButton = findViewById(R.id.driver_register_button);
        DriverStatus = findViewById(R.id.driver_status);
        DriverRegisterLink = findViewById(R.id.driver_Register_link);
        EmailDriver = findViewById(R.id.email_driver);
        PasswordDriver = findViewById(R.id.password_driver);
        mAuth = FirebaseAuth.getInstance();

        loadingDialog = new Dialog(RegisterActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);



        DriverRegisterButton.setVisibility(View.INVISIBLE);
        DriverRegisterButton.setEnabled(false);

        DriverRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverLoginButton.setVisibility(View.INVISIBLE);
                DriverRegisterLink.setVisibility(View.INVISIBLE);
                DriverStatus.setText("Register Driver");
                DriverRegisterButton.setVisibility(View.VISIBLE);
                DriverRegisterButton.setEnabled(true);

            }
        });

        DriverRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = EmailDriver.getText().toString();
                String password = PasswordDriver.getText().toString();
                if (email.isEmpty()){
                    EmailDriver.setError("Write Your Email Please");
                    return;

                }
                else if (password.isEmpty()){
                    PasswordDriver.setError("Write Your Password");
                    return;
                }
                else {
                    RegisterDriver(email,password);
                }

            }
        });
        DriverLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = EmailDriver.getText().toString();
                String password = PasswordDriver.getText().toString();

                if (email.isEmpty()){
                    EmailDriver.setError("Enter Your Email Please");
                    return;

                }
                else if (password.isEmpty()){
                    PasswordDriver.setError("Enter Your Password");
                    return;
                }
                else

                signInDriver(email,password);
            }
        });
    }

    private void signInDriver(String email, String password) {
        loadingDialog.show();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Driver Login is Successfully......", Toast.
                                    LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                            Intent intent = new Intent(RegisterActivity.this,DriversMapActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Driver login UnSuccessfully......", Toast.
                                    LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                        }
                    }
                });


    }

    private void RegisterDriver(final String email, String password) {
        loadingDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Driver Register Successfully......", Toast.
                                    LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                            EmailDriver.setText("");
                            PasswordDriver.setText("");
                        }
                        else Toast.makeText(RegisterActivity.this, "Registration UnSuccessful please Try Again", Toast.
                                LENGTH_SHORT).show();
                        loadingDialog.dismiss();

                    }
                });

    }
}
