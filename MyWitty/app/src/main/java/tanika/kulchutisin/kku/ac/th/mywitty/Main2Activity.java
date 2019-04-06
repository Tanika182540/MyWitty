package tanika.kulchutisin.kku.ac.th.mywitty;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.gms.maps.model.Circle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG = Main2Activity.class.getSimpleName();
    android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView emailtxt, mName;
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
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        String email = getIntent().getExtras().getString("email");
        String name = getIntent().getExtras().getString("name");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hedearview = navigationView.getHeaderView(0);
        emailtxt  = (TextView) hedearview.findViewById(R.id.header_email);
        mName = (TextView) hedearview.findViewById(R.id.usertxt);
        emailtxt.setText(email);
        mName.setText(name);

        fmList = new ArrayList<>();
        latList = new ArrayList<>();
        lngList = new ArrayList<>();
        nameList = new ArrayList<>();
        telList = new ArrayList<>();
        addressList = new ArrayList<>();
        highList = new ArrayList<>();


        databaseReference = FirebaseDatabase.getInstance().getReference();

        navigationView.setNavigationItemSelectedListener(this);


        MapsMenu mapsMenu = new MapsMenu();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,mapsMenu).commit();

        String datas= getIntent().getStringExtra("email");



        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){

                    startActivity(new Intent(Main2Activity.this,MainActivity.class));
                }
            }
        };



    }

    @Override
    protected void onStart() {
        super.onStart();
        String email = getIntent().getExtras().getString("email");
        String name = getIntent().getExtras().getString("name");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hedearview = navigationView.getHeaderView(0);
        emailtxt  = (TextView) hedearview.findViewById(R.id.header_email);
        mName = (TextView) hedearview.findViewById(R.id.usertxt);
        emailtxt.setText(email);
        mName.setText(name);

        navigationView.setNavigationItemSelectedListener(this);
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        //moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
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
            Intent myIntent = new Intent(Main2Activity.this, SearchActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_Station) {
            setTitle("My Location");
           MapsMenu mapsMenu = new MapsMenu();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,mapsMenu).commit();


        } else if (id == R.id.nav_fav) {
            setTitle("Favourite station");
            FavFragment FavMenu = new FavFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,FavMenu).commit();

        }
        else if (id == R.id.nav_showSt) {
            setTitle("All radio station");
            putLatLngName();
        } else if (id == R.id.nav_logout) {

            mAuth.signOut();
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

                Intent intent = new Intent(Main2Activity.this,MapsActivity.class);
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