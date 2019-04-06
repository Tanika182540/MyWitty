package tanika.kulchutisin.kku.ac.th.mywitty;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText mSearchField;
    Button mSearchButton;

    RecyclerView recyclerView;

    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private String tel;

    ArrayList<String> codeList;
    ArrayList<String> fmList;
    ArrayList<String> highList;
    ArrayList<String> latList;
    ArrayList<String> lngList;
    ArrayList<String> locationList;
    ArrayList<String> nameList;
    ArrayList<String> telList;
    SearchAdapter searchAdapter;
    ImageButton calltel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchField = (EditText)findViewById(R.id.editSearch);
        mSearchButton = (Button)findViewById(R.id.button_search);
        recyclerView = (RecyclerView) findViewById(R.id.search_field);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        codeList = new ArrayList<>();
        fmList = new ArrayList<>();
        highList = new ArrayList<>();
        latList = new ArrayList<>();
        lngList = new ArrayList<>();
        locationList = new ArrayList<>();
        nameList = new ArrayList<>();
        telList = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    setAdapter(s.toString());
                } else {
                    codeList.clear();
                    fmList.clear();
                    highList.clear();
                    latList.clear();
                    lngList.clear();
                    locationList.clear();
                    nameList.clear();
                    telList.clear();
                    recyclerView.removeAllViews();
                }
            }
        });


    }

    private void setAdapter(final String searchedString) {
        databaseReference.child("station").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                codeList.clear();
                fmList.clear();
                highList.clear();
                latList.clear();
                lngList.clear();
                locationList.clear();
                nameList.clear();
                telList.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String uid = snapshot.getKey();
                    String code = snapshot.child("code").getValue(String.class);
                    String fm = snapshot.child("fm").getValue(String.class);
                    String high = snapshot.child("high").getValue(String.class);
                    String lat = snapshot.child("lat").getValue(String.class);
                    String lng = snapshot.child("lng").getValue(String.class);
                    String location = snapshot.child("location").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    tel = snapshot.child("tel").getValue(String.class);

                    if (name.toLowerCase().contains(searchedString.toLowerCase())) {
                        codeList.add("รหัสสถานี: "+code);
                        fmList.add("ความถี่: "+fm);
                        highList.add("เสาสูง: "+high);
                        latList.add("ละติจูด: "+lat);
                        lngList.add("ลองติจูด: "+lng);
                        locationList.add("ที่อยู่: "+location);
                        nameList.add(name);
                        telList.add(""+tel);
                    } else  if (location.toLowerCase().contains(searchedString.toLowerCase())) {
                        codeList.add("รหัสสถานี: "+code);
                        fmList.add("ความถี่: "+fm);
                        highList.add("เสาสูง: "+high);
                        latList.add("ละติจูด: "+lat);
                        lngList.add("ลองติจูด: "+lng);
                        locationList.add("ที่อยู่: "+location);
                        nameList.add(name);
                        telList.add(""+tel);
                        counter++;


                    }
                    if (counter == 15)
                        break;
                }

                Intent intent = new Intent(SearchActivity.this,popUp.class);
                intent.putExtra("tel",tel);

                searchAdapter = new SearchAdapter(SearchActivity.this, codeList,
                        fmList,highList,latList,lngList,locationList,nameList,telList);
                recyclerView.setAdapter(searchAdapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(SearchActivity.this, Main2Activity.class);
                String email = "";
                String name = "";
                intent.putExtra("email", email);
                intent.putExtra("name",name);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
