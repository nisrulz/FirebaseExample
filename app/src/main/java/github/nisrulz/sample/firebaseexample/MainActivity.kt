package github.nisrulz.sample.firebaseexample

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {
  // Declare listview and arraylist
  var data = ArrayList<String?>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)

    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
    listview.adapter = adapter

    // Init firebase database
    val database = FirebaseDatabase.getInstance()

    // get reference to key
    val myRef = database.getReference("message")

    // Specifically updating the data in db to show case updating later on
    myRef.setValue("Hello, World! [This text is in firebase db]")

    // Read from the database
    myRef.addValueEventListener(object : ValueEventListener {
      override fun onDataChange(dataSnapshot: DataSnapshot) {
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        val value = dataSnapshot.getValue(String::class.java)
        txt_hello.text = value
        showToastAndLog("Value is: $value")
      }

      override fun onCancelled(error: DatabaseError) {
        // Failed to read value
        showToastAndLog("Failed to read value." + error.toException())
      }
    })

    // get reference to key
    val myKeyValueRef = database.getReference("KeyValue")
    myKeyValueRef.addChildEventListener(object : ChildEventListener {
      override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
        val value = dataSnapshot.getValue(String::class.java)
        showToastAndLog("Child Added : $value")

        // modify listview
        data.add(value)
        adapter.notifyDataSetChanged()
      }

      override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
        val value = dataSnapshot.getValue(String::class.java)
        showToastAndLog("Child Changed : $value")
      }

      override fun onChildRemoved(dataSnapshot: DataSnapshot) {
        val value = dataSnapshot.getValue(String::class.java)
        showToastAndLog("Child Removed : $value")
      }

      override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

      }

      override fun onCancelled(databaseError: DatabaseError) {

      }
    })

    val fab = findViewById<FloatingActionButton>(R.id.fab)
    fab.setOnClickListener { view ->
      Snackbar.make(view, "Wrote to database, updated textview as well as firebase console ",
          Snackbar.LENGTH_LONG).setAction("Ok", null).show()

      // Write to database
      myRef.setValue("Hello, World!")
    }
  }

  private fun showToastAndLog(data: String) {
    Log.d(TAG, data)
    Toast.makeText(applicationContext, data, Toast.LENGTH_SHORT).show()
  }

  companion object {

    private val TAG = "Firebase Database"
  }
}
