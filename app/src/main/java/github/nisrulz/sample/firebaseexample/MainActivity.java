package github.nisrulz.sample.firebaseexample;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

  // Create an object of FirebaseRemoteConfig
  FirebaseRemoteConfig remoteConfig;

  Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Init FirebaseRemoteConfig
    remoteConfig = FirebaseRemoteConfig.getInstance();

    // Note : Developer mode is enabled so as to make sure that the cache gets refreshed often
    FirebaseRemoteConfigSettings remoteConfigSettings =
        new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(true).build();
    remoteConfig.setConfigSettings(remoteConfigSettings);

    // Set Defaults
    HashMap<String, Object> defaults = new HashMap<>();
    defaults.put("is_happy", "Very Happy :D");
    defaults.put("is_sad", "Sad :(");
    defaults.put("color_primary", "#3F51B5");
    defaults.put("color_primary_dark", "#303F9F");
    remoteConfig.setDefaults(defaults);

    // Configure remote config to fetch updated configurations
    // cache expiration in seconds
    long cacheExpiration = 3600;

    //expire the cache immediately for development mode.
    if (remoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
      cacheExpiration = 0;
    }

    // fetch
    remoteConfig.fetch(cacheExpiration).addOnCompleteListener(this, new OnCompleteListener<Void>() {
      @Override
      public void onComplete(Task<Void> task) {
        if (task.isSuccessful()) {
          // task successful. Activate the fetched data
          remoteConfig.activateFetched();

          //update views?
          updateViews();

          // update the toolbar
          if (toolbar != null) {
            toolbar.setBackgroundColor(Color.parseColor(remoteConfig.getString("color_primary")));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
              getWindow().setStatusBarColor(
                  Color.parseColor(remoteConfig.getString("color_primary_dark")));
            }
          }

        } else {
          //task failed
          Toast.makeText(MainActivity.this, "RemoteConfig Task Failed!", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private void updateViews() {
    // check whether promo is on
    String isHappyString = remoteConfig.getString("is_happy");
    String isSadString = remoteConfig.getString("is_sad");

    Toast.makeText(this, isHappyString + "\n" + isSadString, Toast.LENGTH_LONG).show();
  }
}
