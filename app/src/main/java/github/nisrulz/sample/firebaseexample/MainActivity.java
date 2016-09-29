package github.nisrulz.sample.firebaseexample;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        }
        else {
          //task failed
        }
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

  private void updateViews() {
    // check whether promo is on
    String isHappyString = remoteConfig.getString("is_happy");
    String isSadString = remoteConfig.getString("is_sad");

    Toast.makeText(this, isHappyString + "\n" + isSadString, Toast.LENGTH_LONG).show();
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
