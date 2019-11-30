package github.nisrulz.sample.firebaseexample

import android.graphics.Color
import android.os.Build
import android.os.Bundle
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

    val remoteConfigSettings = FirebaseRemoteConfigSettings.Builder().build()
    remoteConfig.setConfigSettingsAsync(remoteConfigSettings)

    // Set Defaults
    val defaults = HashMap<String, Any>()
    defaults["is_happy"] = "Very Happy :D"
    defaults["is_sad"] = "Sad :("
    defaults["color_primary"] = "#3F51B5"
    defaults["color_primary_dark"] = "#303F9F"
    remoteConfig.setDefaultsAsync(defaults)

    // Configure remote config to fetch updated configurations
    // cache expiration in seconds
    var cacheExpiration: Long = 3600

    //expire the cache immediately for development mode.
    if (BuildConfig.DEBUG) {
      cacheExpiration = 0
    }

    // fetch
    remoteConfig.fetch(cacheExpiration).addOnCompleteListener(this) { task ->
      if (task.isSuccessful) {
        // task successful. Activate the fetched data
        remoteConfig.fetchAndActivate()

        //update views?
        updateViews()

        // update the toolbar
        toolbar?.setBackgroundColor(Color.parseColor(remoteConfig.getString("color_primary")))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          window.statusBarColor = Color.parseColor(remoteConfig.getString("color_primary_dark"))
        }

      } else {
        //task failed
        Toast.makeText(this@MainActivity, "RemoteConfig Task Failed!", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun updateViews() {
    // check whether promo is on
    val isHappyString = remoteConfig.getString("is_happy")
    val isSadString = remoteConfig.getString("is_sad")

    Toast.makeText(this, isHappyString + "\n" + isSadString, Toast.LENGTH_LONG).show()
  }
}
