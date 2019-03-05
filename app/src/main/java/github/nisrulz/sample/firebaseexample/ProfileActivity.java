package github.nisrulz.sample.firebaseexample;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

  //firebase auth object
  private FirebaseAuth firebaseAuth;

  //view objects
  private TextView textViewUserEmail;
  private Button buttonLogout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);

    firebaseAuth = FirebaseAuth.getInstance();

    //if the user is not logged in
    //that means current user will return null
    if (firebaseAuth.getCurrentUser() == null) {
      //closing this activity
      finish();
      //starting login activity
      startActivity(new Intent(this, LoginActivity.class));
    }

    //getting current user
    FirebaseUser user = firebaseAuth.getCurrentUser();

    //initializing views
    textViewUserEmail = findViewById(R.id.textViewUserEmail);
    buttonLogout = findViewById(R.id.buttonLogout);

    //displaying logged in user name
    textViewUserEmail.setText("Welcome\n" + user.getEmail());

    //adding listener to button
    buttonLogout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //logging out the user
        firebaseAuth.signOut();
        //closing activity
        finish();
        //starting login activity
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
      }
    });
  }
}
