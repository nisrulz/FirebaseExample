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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "Firebase Database";
  // declare textview
  TextView txt_hello;

  // declare firebase database
  FirebaseDatabase database;

  // Declare listview and arraylist
  ListView listview;
  ArrayList<String> data;
  ArrayAdapter<String> adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Init textview
    txt_hello = (TextView) findViewById(R.id.txt_hello);

    // Init info for listview
    listview = (ListView) findViewById(R.id.listview);
    data = new ArrayList<>();
    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
    listview.setAdapter(adapter);

    // Init firebase database
    database = FirebaseDatabase.getInstance();

    // get reference to key
    final DatabaseReference myRef = database.getReference("message");

    // Read from the database
    myRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        String value = dataSnapshot.getValue(String.class);
        Log.d(TAG, "Value is: " + value);
        txt_hello.setText(value);
      }

      @Override
      public void onCancelled(DatabaseError error) {
        // Failed to read value
        showToastAndLog("Failed to read value." + error.toException());
      }
    });

    // get reference to key
    final DatabaseReference myKeyValueRef = database.getReference("KeyValue");
    myKeyValueRef.addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        String value = dataSnapshot.getValue(String.class);
        showToastAndLog("Child Added : " + value);

        // modify listview
        data.add(value);
        adapter.notifyDataSetChanged();
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        String value = dataSnapshot.getValue(String.class);
        showToastAndLog("Child Changed : " + value);
      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {
        String value = dataSnapshot.getValue(String.class);
        showToastAndLog("Child Removed : " + value);
      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Wrote to database, updated textview as well as firebase console ",
            Snackbar.LENGTH_LONG).setAction("Ok", null).show();

        // Write to database
        myRef.setValue("Hello, World!");
      }
    });
  }

  private void showToastAndLog(String data) {
    Log.d(TAG, data);
    Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
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
