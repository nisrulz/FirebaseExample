package github.nisrulz.sample.firebaseexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.crash.FirebaseCrash;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Log Custom Event
    FirebaseCrash.log("Activity created");

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        // Log that crash button was clicked. This version of Crash.log() will include the
        // message in the crash report as well as show the message in logcat.
        FirebaseCrash.logcat(Log.INFO, TAG, "Crash button clicked");

        Snackbar.make(view, "Cause a Runtime Exception ?", Snackbar.LENGTH_LONG)
            .setAction("Yes", new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                try {
                  // Cause a Runtime Exception
                  throw new RuntimeException("My first Android Runtime Exception");
                } catch (Exception e) {
                  Toast.makeText(MainActivity.this, "Runtime Exception caused and caught",
                      Toast.LENGTH_SHORT).show();
                  // Report the crash
                  FirebaseCrash.logcat(Log.ERROR, TAG, "RuntimeException caught");
                  FirebaseCrash.report(e);
                }
              }
            })
            .show();
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
