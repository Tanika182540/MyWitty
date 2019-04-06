package tanika.kulchutisin.kku.ac.th.mywitty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GuessPopUpDetail extends AppCompatActivity {

    String name, location, tel, fm, high;
    TextView stationName, locationName, telName, fmName, highName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_detail);

        fm = getIntent().getExtras().getString("fm");
        high = getIntent().getExtras().getString("high");
        tel = getIntent().getExtras().getString("tel");
        location = getIntent().getExtras().getString("location");
        name = getIntent().getExtras().getString("name");

        stationName = (TextView) findViewById(R.id.stationName);
        locationName = (TextView) findViewById(R.id.location);
        telName = (TextView) findViewById(R.id.phoneNumber);
        fmName = (TextView) findViewById(R.id.frequency);
        highName = (TextView) findViewById(R.id.high);

        stationName.setText(name);
        locationName.setText(location);
        telName.setText(tel);
        fmName.setText(fm);
        highName.setText(high);
    }
}
