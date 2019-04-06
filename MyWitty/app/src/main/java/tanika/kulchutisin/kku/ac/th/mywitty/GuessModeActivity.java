package tanika.kulchutisin.kku.ac.th.mywitty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GuessModeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> latList;
    ArrayList<String> lngList;
    ArrayList<String> fmList;
    ArrayList<String> nameList;
    ArrayList<String> highList;
    ArrayList<String> addressList;
    ArrayList<String> telList;
    private DatabaseReference databaseReference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_mode);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MapsMenu mapsMenu = new MapsMenu();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,mapsMenu).commit();

        fmList = new ArrayList<>();
        latList = new ArrayList<>();
        lngList = new ArrayList<>();
        nameList = new ArrayList<>();
        telList = new ArrayList<>();
        addressList = new ArrayList<>();
        highList = new ArrayList<>();


        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.guess_mode, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            setTitle("Search");
            Intent myIntent = new Intent(GuessModeActivity.this, GuessSearchActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_Station) {
            setTitle("My Location");
            MapsMenu mapsMenu = new MapsMenu();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,mapsMenu).commit();
        }else if (id == R.id.nav_showSt) {
            putLatLngName();
        }
        else if (id == R.id.nav_guestlogout) {
            Intent myIntent = new Intent(GuessModeActivity.this,MainActivity.class);
            startActivity(myIntent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void putLatLngName (){

        databaseReference.child("station").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                fmList.clear();
                nameList.clear();
                latList.clear();
                lngList.clear();
                telList.clear();
                addressList.clear();
                highList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    String lat = snapshot.child("lat").getValue(String.class);
                    String lng = snapshot.child("lng").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    String fm = snapshot.child("fm").getValue(String.class);
                    String high = snapshot.child("high").getValue(String.class);
                    String address = snapshot.child("location").getValue(String.class);
                    String tel = snapshot.child("tel").getValue(String.class);



                    fmList.add(fm);
                    nameList.add(name);
                    latList.add(lat);
                    lngList.add(lng);

                }
                final Bundle extra = new Bundle();
                extra.putSerializable("fmList",fmList);
                extra.putSerializable("latList",latList);
                extra.putSerializable("nameList",nameList);
                extra.putSerializable("lngList",lngList);

                Intent intent = new Intent(GuessModeActivity.this,MapsActivity.class);
                intent.putExtra("extra",extra);
                intent.putExtra("fmlist",fmList);
                intent.putExtra("latList",latList);
                intent.putExtra("nameList",nameList);
                intent.putExtra("lngList",lngList);
                startActivity(intent);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
