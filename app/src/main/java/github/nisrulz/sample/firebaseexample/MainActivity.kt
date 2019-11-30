package github.nisrulz.sample.firebaseexample

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import java.util.*

class MainActivity : AppCompatActivity() {

  // Create an object of FirebaseRemoteConfig and initialize it
  private val remoteConfig = FirebaseRemoteConfig.getInstance()

  internal var toolbar: Toolbar? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)

    // Setup the remote config settings
    FirebaseRemoteConfigSettings.Builder().also {
      if (BuildConfig.DEBUG) {
        it.setMinimumFetchIntervalInSeconds(3600)
      } else {
        it.setMinimumFetchIntervalInSeconds(2)
      }
      remoteConfig.setConfigSettingsAsync(it.build())
    }

    // Set Defaults
    val defaults = HashMap<String, Any>()
    defaults["is_happy"] = "Very Happy :D"
    defaults["is_sad"] = "Sad :("
    defaults["color_primary"] = "#3F51B5"
    defaults["color_primary_dark"] = "#303F9F"
    remoteConfig.setDefaultsAsync(defaults)

    // fetch
    remoteConfig.fetchAndActivate().addOnCompleteListener(this) { task ->
      if (task.isSuccessful) {
        val updated = task.result

        Log.d("FirebaseRemoteConfig", "Config params updated: $updated")
        showToast("Fetch and activate succeeded")

        //update views
        updateViews()
      } else {
        //task failed
        showToast("RemoteConfig Task Failed! ${task.exception}")
      }
    }
  }

  private fun updateViews() {
    // check whether promo is on
    remoteConfig.apply {
      val isHappyString = getString("is_happy")
      val isSadString = getString("is_sad")

      showToast(isHappyString + "\n" + isSadString)

      // update the toolbar
      toolbar?.setBackgroundColor(Color.parseColor(getString("color_primary")))
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = Color.parseColor(getString("color_primary_dark"))
      }
    }

  }

  private fun showToast(str: String) {
    Toast.makeText(applicationContext, str, Toast.LENGTH_LONG).show()
  }
}
