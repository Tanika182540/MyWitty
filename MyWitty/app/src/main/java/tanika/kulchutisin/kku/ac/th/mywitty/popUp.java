package tanika.kulchutisin.kku.ac.th.mywitty;

        import android.content.Intent;
        import android.net.Uri;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.view.View;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.shashank.sony.fancytoastlib.FancyToast;
        import com.varunest.sparkbutton.SparkButton;
        import com.varunest.sparkbutton.SparkEventListener;

        import java.util.ArrayList;

public class popUp extends AppCompatActivity {
    private String tel,uid;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner ;
    private SparkButton heartButton ;
    private TextView textView ;
    private DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);


        Bundle extra = getIntent().getBundleExtra("extra");
        ArrayList<String> tellist = (ArrayList<String>) extra.getSerializable("telList");
        ArrayList<String> latList = (ArrayList<String>) extra.getSerializable("latList");
        ArrayList<String> lngList = (ArrayList<String>) extra.getSerializable("lngList");
        ArrayList<String> nameList = (ArrayList<String>) extra.getSerializable("nameList");
        ArrayList<String> fmList = (ArrayList<String>) extra.getSerializable("fmList");
        ArrayList<String> highList = (ArrayList<String>) extra.getSerializable("highList");
        ArrayList<String> locationList = (ArrayList<String>) extra.getSerializable("locationList");
        int position = getIntent().getExtras().getInt("position");

        final String tel = tellist.get(position);
        final String lat = latList.get(position);
        final String lng = lngList.get(position);
        final String name = nameList.get(position);
        final String fm = fmList.get(position);
        final String high = highList.get(position);
        final String location = locationList.get(position);
        heartButton = (SparkButton) findViewById(R.id.heart_button);
        textView = (TextView)findViewById(R.id.tv_fav);

        mAuth = FirebaseAuth.getInstance();

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    heartButton.removeAllViews();
                    textView.setVisibility(View.GONE);
                }else{
                     uid = mAuth.getCurrentUser().getUid();
                }

            }
        } ;

        heartButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState){
                    FancyToast.makeText(getApplicationContext(),"Add " + name + " to favorite.",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

                }else  {
                    FancyToast.makeText(getApplicationContext(),"Delete " + name + " from favorite.",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });



        ((TextView)findViewById(R.id.tv_phonumber)).setText(tel);



        ((ImageButton)findViewById(R.id.ibtn_callPhone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+tel));
                startActivity(intent);

            }
        });

        ((ImageButton)findViewById(R.id.ibtn_Map)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(popUp.this,MapsFormSearch.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.putExtra("name",name);
                intent.putExtra("fm",fm);
                intent.putExtra("high", high);
                intent.putExtra("tel",tel);
                intent.putExtra("location",location);
                startActivity(intent);

            }
        });



    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }
}
