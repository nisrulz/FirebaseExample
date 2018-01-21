package github.nisrulz.sample.firebaseexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

  // Declare FirebaseAnalytics
  private FirebaseAnalytics firebaseAnalytics;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Init FirebaseAnalytics
    firebaseAnalytics = FirebaseAnalytics.getInstance(this);

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        // log to analytics
        logEventInFirebaseAnalytics();

        // show snackbar
        Snackbar.make(view, "Sending Analytics data", Snackbar.LENGTH_LONG)
            .setAction("OK", null)
            .show();
      }
    });
  }

  private void logEventInFirebaseAnalytics() {
    // Log event
    firebaseAnalytics.logEvent("checkout_complete", null);

    // Log parametrized event
    Bundle bundle = new Bundle();
    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Pizza");
    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

    // Log User Property
    firebaseAnalytics.setUserProperty("favourite_food", "Pizza");
  }
}
