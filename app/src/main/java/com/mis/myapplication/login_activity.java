package com.mis.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class login_activity extends AppCompatActivity implements View.OnClickListener {

    //Views Used in the app
    private static final int RC_SIGN_IN=100;
    EditText email;
    EditText password;
    Button btnLogin,btnGmail;
    TextView createAccount;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email =findViewById(R.id.editTextEmail);
        password =findViewById(R.id.editTextPass);
        btnLogin=findViewById(R.id.btnLogin);
        btnGmail=findViewById(R.id.btnGmail);
        createAccount =findViewById(R.id.txtCreateAccount);

        btnLogin.setOnClickListener(this);
        createAccount.setOnClickListener(this);
        btnGmail.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onClick(View view)
    {
        if(view==btnLogin)
        {
            if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
            {
                Toast.makeText(this,"Rellenar todos los campos",Toast.LENGTH_LONG).show();
                return;
            }
            if( ! Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
            {
                Toast.makeText(this,"Email inválido",Toast.LENGTH_LONG).show();
                return;
            }
            if(password.getText().toString().length()<6)
            {
                Toast.makeText(this,"Contraseña Debe tener al menos 6 dígitos",Toast.LENGTH_LONG).show();
                return;
            }

            ProgressDialog inProcess=new ProgressDialog(this);
            inProcess.show();
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(inProcess.isShowing())
                        inProcess.dismiss();
                    if(task.isSuccessful())
                    {
                        finish();
                        Toast.makeText(login_activity.this,"Autenticación exitosa",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(login_activity.this,BuyedTicketActivity.class));
                    }
                    else
                    {
                        Toast.makeText(login_activity.this,"El correo electrónico o la contraseña son incorrectos.",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        if(view == createAccount)
        {
            startActivity(new Intent(this, SignInActivity.class));
        }
        if(view == btnGmail)
        {
            loginWithGmail();
        }
    }

    //Method to login with gmail
    private void loginWithGmail()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> taskSignIn  = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = taskSignIn.getResult(ApiException.class);

                if (account != null) {
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                    mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    finish();
                                    Toast.makeText(login_activity.this,"Autenticación exitosa",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(login_activity.this,BuyedTicketActivity.class));
                                } else {
                                    showErrorMessage(task.getException().getMessage());
                                }
                            });
                } else {
                    showErrorMessage("");
                }


            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                showErrorMessage(e.getMessage());
            }
        }
    }

    //Method to show error on screen
    private void showErrorMessage(String message) {

        Toast.makeText(login_activity.this,"Error : "+message,Toast.LENGTH_LONG).show();
    }
}