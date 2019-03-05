package github.nisrulz.sample.firebaseexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class MainActivity extends AppCompatActivity {

  //defining view objects
  private EditText editTextEmail;
  private EditText editTextPassword;
  private Button buttonSignup;
  private ProgressDialog progressDialog;

  private TextView textViewSignin;

  //defining firebaseauth object
  private FirebaseAuth firebaseAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //initializing firebase auth object
    firebaseAuth = FirebaseAuth.getInstance();

    //if getCurrentUser does not returns null
    if (firebaseAuth.getCurrentUser() != null) {
      //that means user is already logged in
      //so close this activity
      finish();

      //and open profile activity
      startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
    }

    //initializing views
    editTextEmail = findViewById(R.id.editTextEmail);
    editTextPassword = findViewById(R.id.editTextPassword);
    buttonSignup = findViewById(R.id.buttonSignup);

    progressDialog = new ProgressDialog(this);

    //attaching listener to button
    buttonSignup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        registerUser();
      }
    });

    textViewSignin = findViewById(R.id.textViewSignin);
    textViewSignin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //open login activity when user taps on the already registered textview
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
      }
    });
  }

  private void registerUser() {

    //getting email and password from edit texts
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

    progressDialog.setMessage("Registering Please Wait...");
    progressDialog.show();

    //creating a new user
    firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            //checking if success
            if (task.isSuccessful()) {
              //display some message here
              finish();
              startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
              Toast.makeText(MainActivity.this, "Successfully registered", Toast.LENGTH_LONG)
                  .show();
            } else {
              //display some message here
              Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
          }
        });
  }
}
