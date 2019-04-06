package tanika.kulchutisin.kku.ac.th.mywitty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Saranya on 5/2/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> codeList;
    ArrayList<String> fmList;
    ArrayList<String> highList;
    ArrayList<String> latList;
    ArrayList<String> lngList;
    ArrayList<String> locationList;
    ArrayList<String> nameList;
    ArrayList<String> telList;


    class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView code_list,fm_list,high_list,lat_list,lng_list,location_list,
        name_list,tel_list;

        public SearchViewHolder(View itemView) {
            super(itemView);
            code_list = (TextView) itemView.findViewById(R.id.code_station);
            fm_list = (TextView) itemView.findViewById(R.id.fm_station);
            high_list = (TextView) itemView.findViewById(R.id.high_station);
            lat_list = (TextView) itemView.findViewById(R.id.lat_station);
            lng_list = (TextView) itemView.findViewById(R.id.lng_station);
            location_list = (TextView) itemView.findViewById(R.id.location_station);
            name_list = (TextView) itemView.findViewById(R.id.name_station);
            tel_list = (TextView) itemView.findViewById(R.id.tel_station);
        }
    }

    public SearchAdapter(Context context, ArrayList<String> codeList, ArrayList fmList,
                         ArrayList highList, ArrayList latList, ArrayList lngList,
                         ArrayList locationList, ArrayList nameList, ArrayList telList) {
        this.context = context;
        this.codeList = codeList;
        this.fmList = fmList;
        this.highList = highList;
        this.latList = latList;
        this.lngList = lngList;
        this.locationList = locationList;
        this.nameList = nameList;
        this.telList = telList;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false);
        return new SearchAdapter.SearchViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        holder.code_list.setText(codeList.get(position));
        holder.fm_list.setText(fmList.get(position));
        holder.high_list.setText(highList.get(position));
        holder.lat_list.setText(latList.get(position));
        holder.lng_list.setText(lngList.get(position));
        holder.location_list.setText(locationList.get(position));
        holder.name_list.setText(nameList.get(position));
        holder.tel_list.setText(telList.get(position));

        final Bundle extra = new Bundle();
        extra.putSerializable("latList",latList);
        extra.putSerializable("lngList",lngList);
        extra.putSerializable("telList",telList);
        extra.putSerializable("nameList",nameList);
        extra.putSerializable("fmList",fmList);
        extra.putSerializable("highList",highList);
        extra.putSerializable("locationList",locationList);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context , popUp.class);
                intent.putExtra("extra",extra);
                intent.putExtra("position", position);
                context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return nameList.size();
    }
}
