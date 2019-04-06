package tanika.kulchutisin.kku.ac.th.mywitty;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class GuessPopUp extends AppCompatActivity {
    private String tel;

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
                Intent intent = new Intent(GuessPopUp.this,MapsFormSearch.class);
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
}