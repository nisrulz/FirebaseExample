package github.nisrulz.sample.firebaseexample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

  //defining firebaseauth object
  private FirebaseAuth firebaseAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //initializing firebase auth object
    firebaseAuth = FirebaseAuth.getInstance();

    //initializing views
    editTextEmail = (EditText) findViewById(R.id.editTextEmail);
    editTextPassword = (EditText) findViewById(R.id.editTextPassword);

    buttonSignup = (Button) findViewById(R.id.buttonSignup);

    progressDialog = new ProgressDialog(this);

    //attaching listener to button
    buttonSignup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        registerUser();
      }
    });

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
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
              Toast.makeText(MainActivity.this, "Successfully registered", Toast.LENGTH_LONG)
                  .show();
            }
            else {
              //display some message here
              Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
          }
        });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
