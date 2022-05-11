package com.mis.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextRPassword;
    Button btnRegister;
    TextView txtHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_actitivty);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPass);
        editTextRPassword = findViewById(R.id.editTextRPass);

        btnRegister = findViewById(R.id.btnRegister);
        txtHaveAccount = findViewById(R.id.txtHaveAccount);

        btnRegister.setOnClickListener(this);
        txtHaveAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnRegister){
            if(editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()){
                Toast.makeText(this,"Debe rellenar todos los campos",Toast.LENGTH_LONG).show();
                return;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString().trim()).matches()){
                Toast.makeText(this,"Email inválido",Toast.LENGTH_LONG).show();
                return;
            }

            if(editTextPassword.getText().toString().length() < 6){
                Toast.makeText(this,"La contraseña debe tener al menos 6 dígitos",Toast.LENGTH_LONG).show();
                return;
            }

            if(!editTextPassword.getText().toString().equals(editTextRPassword.getText().toString())){
                Toast.makeText(this,"Las contraseñas deben coincidir",Toast.LENGTH_LONG).show();
                return;
            }


            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

                    if(task.isSuccessful()){
                        Toast.makeText(SignInActivity.this,"La cuenta ha sido creada",Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                    else{
                        Toast.makeText(SignInActivity.this,"Error al crear usuario | El email ya está en uso",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        if(view == txtHaveAccount){
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}