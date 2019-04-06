package tanika.kulchutisin.kku.ac.th.mywitty;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Station{
public String stationName, lat, lng, phone, name, fm, high;

    public Station() {

    }

    public String getStationName(){
        return stationName;
    }

    public void setStationName(String stationName){
        this.stationName = stationName;
    }

    public String getLat(){
        return lat;
    }

    public void setLat(String lat){
        this.lat = lat;
    }

    public String getLng(){
        return lng;
    }

    public void setLng(String lng){
        this.lng = lng;
    }

    public String getPhone(){
        return stationName;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getFm(){
        return fm;
    }

    public void setFm(String fm){
        this.fm = fm;
    }

    public String getHigh(){
        return high;
    }

    public void setHigh(String high){
        this.high = high;
    }

    public Station(String stationName, String lat, String lng, String phone, String name, String fm, String high){
        this.stationName=stationName;
        this.lat=lat;
        this.lng=lng;
        this.phone=phone;
        this.name=name;
        this.fm=fm;
        this.high=high;

        }
}
