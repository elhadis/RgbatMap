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

public class CustomerRegisterActivity extends AppCompatActivity {
    private Button customerLoginButton;
    private Button customerRegisterButton;
    private TextView customerRegisterLink;
    private TextView customerStatus;
    private EditText EmailCustomer,PasswordCustomer;
    private FirebaseAuth mAuth;
    private Dialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer_register);
        customerLoginButton = findViewById(R.id.customer_login_button);
        customerRegisterButton = findViewById(R.id.customer_register_button);
        customerStatus = findViewById(R.id.customer_status);
        customerRegisterLink = findViewById(R.id.register_customer_link);



        mAuth = FirebaseAuth.getInstance();

        loadingDialog = new Dialog(CustomerRegisterActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);



        EmailCustomer = findViewById(R.id.email_customer);
        PasswordCustomer = findViewById(R.id.password_customer);

        customerRegisterButton.setVisibility(View.INVISIBLE);
        customerRegisterButton.setEnabled(false);

        customerRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerLoginButton.setVisibility(View.INVISIBLE);
                customerRegisterLink.setVisibility(View.INVISIBLE);
                customerStatus.setText("Customer Register");
                customerRegisterButton.setVisibility(View.VISIBLE);
                customerRegisterButton.setEnabled(true);

            }
        });
        customerRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = EmailCustomer.getText().toString();
                String password = PasswordCustomer.getText().toString();
                if (email.isEmpty()){
                    EmailCustomer.setError("Write Your Email Please");
                    return;

                }
                else if (password.isEmpty()){
                    PasswordCustomer.setError("Write Your Password");
                    return;
                }
                else {
                    RegisterCustomer(email,password);
                }


            }
        });
        customerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = EmailCustomer.getText().toString();
                String password = PasswordCustomer.getText().toString();

                if (email.isEmpty()){
                    EmailCustomer.setError("Enter Your Email Please");
                    return;

                }
                else if (password.isEmpty()){
                    PasswordCustomer.setError("Enter Your Password");
                    return;
                }
                else

                    signInCustomer(email,password);
            }

        });
    }

    private void signInCustomer(String email, String password) {
        loadingDialog.show();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CustomerRegisterActivity.this, "Customer Login is Successfully......", Toast.
                                    LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                            Intent intent = new Intent(getBaseContext(),DriversMapActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(CustomerRegisterActivity.this, "Customer login UnSuccessfully......", Toast.
                                    LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                        }
                    }
                });

    }

    private void RegisterCustomer(String email, String password) {

        loadingDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CustomerRegisterActivity.this, "Customer Register Successfully......", Toast.
                                    LENGTH_SHORT).show();
                            loadingDialog.dismiss();

                        }
                        else Toast.makeText(CustomerRegisterActivity.this, "Registration UnSuccessful please Try Again", Toast.
                                LENGTH_SHORT).show();
                        loadingDialog.dismiss();

                    }
                });

    }
}
