package tanika.kulchutisin.kku.ac.th.mywitty;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    private EditText search ;
    private Button btnSearch ;
    private RecyclerView result ;
    private DatabaseReference mStationDatebase ;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        search = (EditText) v.findViewById(R.id.editSearch);
        btnSearch = (Button) v.findViewById(R.id.button_search);
        mStationDatebase = FirebaseDatabase.getInstance().getReference("name");
        
        /*btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUserSearch();
            }
        });*/
        return v;
    }

}
