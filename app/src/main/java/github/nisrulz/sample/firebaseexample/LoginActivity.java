package github.nisrulz.sample.firebaseexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

  //defining views
  private Button buttonSignIn;
  private EditText editTextEmail;
  private EditText editTextPassword;
  private TextView textViewSignup;

  //firebase auth object
  private FirebaseAuth firebaseAuth;

  //progress dialog
  private ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    firebaseAuth = FirebaseAuth.getInstance();

    //if the objects getcurrentuser method is not null
    //means user is already logged in
    if (firebaseAuth.getCurrentUser() != null) {
      //close this activity
      finish();
      //opening profile activity
      startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
    }

    //initializing views
    editTextEmail = findViewById(R.id.editTextEmail);
    editTextPassword = findViewById(R.id.editTextPassword);
    buttonSignIn =  findViewById(R.id.buttonSignin);
    textViewSignup =  findViewById(R.id.textViewSignUp);

    progressDialog = new ProgressDialog(this);

    //attaching click listener
    buttonSignIn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        userLogin();
      }
    });
    textViewSignup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
      }
    });
  }

  private void userLogin() {
    String email = editTextEmail.getText().toString().trim();
    String password = editTextPassword.getText().toString().trim();

    //checking if email and passwords are empty
    if (TextUtils.isEmpty(email)) {
      Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
      return;
    }

    if (TextUtils.isEmpty(password)) {
      Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
      return;
    }

    //if the email and password are not empty
    //displaying a progress dialog
    progressDialog.setMessage("Signing in, Please Wait...");
    progressDialog.show();

    //logging in the user
    firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            progressDialog.dismiss();
            //if the task is successfull
            if (task.isSuccessful()) {
              //start the profile activity
              finish();
              startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
          }
        });
  }
}
