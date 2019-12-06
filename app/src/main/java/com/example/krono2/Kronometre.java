package com.example.krono2;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.LinkedList;

class duraklama implements Parcelable {
    int sıra;
    long ana_sure;
    long fark;


    public duraklama(int count, long main_time, long diff){
        this.ana_sure=main_time;
        this.fark=diff;
        this.sıra=count;
    }

    @Override
    public String toString() {
        String item_format;
        if(Kronometre.getMilli()){
            item_format=sıra+"       +"+fark+"    "+Kronometre.format_duzenleme_milli(ana_sure);
        }else{
            item_format=sıra+"       +"+(int)fark/1000+"    "+Kronometre.format_duzenleme_second(ana_sure);
        }

        return item_format;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.sıra);
        dest.writeLong(this.ana_sure);
        dest.writeLong(this.fark);
    }

    protected duraklama(Parcel in) {
        this.sıra = in.readInt();
        this.ana_sure = in.readLong();
        this.fark = in.readLong();
    }

    public static final Parcelable.Creator<duraklama> CREATOR = new Parcelable.Creator<duraklama>() {
        @Override
        public duraklama createFromParcel(Parcel source) {
            return new duraklama(source);
        }

        @Override
        public duraklama[] newArray(int size) {
            return new duraklama[size];
        }
    };
}
public class Kronometre implements Parcelable {
    static Kronometre asıl;
    static Boolean milli=true;

    public static Boolean getMilli() {
        return milli;
    }

    public static void setMilli(Boolean milli) {
        Kronometre.milli = milli;
    }

    public static Kronometre getAsıl() {
        return asıl;
    }

    public static void setAsıl(Kronometre asıl) {
        Kronometre.asıl = asıl;
    }

    long toplam_sayilan;
    long son_baslama;
    Boolean stop;
    Boolean isStarted;
    long ilk_oynatma;
    int durma;
    long toplam_durma;
    LinkedList<duraklama> durmalar;

    public Kronometre(){
        toplam_sayilan =0;
        son_baslama=0;
        stop=true;
        isStarted=false;
        ilk_oynatma=0;
        durma=0;
        toplam_durma=0;
        durmalar=new LinkedList<duraklama>();
    }

    public void Start(){
        isStarted=true;
        stop=false;
        ilk_oynatma=System.currentTimeMillis();
        son_baslama=ilk_oynatma;
    }

    public void Stop(){
        stop=true;
        durma++;
        duraklama son;
        if(durma==1){
            son=new duraklama(durma, toplam_sayilan,System.currentTimeMillis()-ilk_oynatma);
        }else{
            son=new duraklama(durma, toplam_sayilan,System.currentTimeMillis()-son_baslama);
        }

        durmalar.add(son);
    }

    public void CountUp() {
        if(isStarted) {
            if (stop == false) {
                toplam_sayilan = System.currentTimeMillis() - ilk_oynatma - toplam_durma;
            } else {
                toplam_durma = System.currentTimeMillis() - ilk_oynatma - toplam_sayilan;
            }
        }
    }

    public void Reset(){
        toplam_sayilan =0;
        toplam_durma=0;
        stop=true;
        isStarted=false;
        while(durmalar.size()!=0){
            durmalar.remove(0);
        }
        ilk_oynatma=0;
        durma=0;
        son_baslama=0;
    }

    public static String format_duzenleme_second(long current){
        long hour=current/3600000;
        long minute=(current%3600000)/60000;
        long second=(current-(hour*3600000)-(minute*60000))/1000;
        String formatt=hour+" : "+minute+" : "+second;
        return  formatt;
    }
    public static String ekleme_milli(long current){
        long minute=current/60000;
        long second=(current%60000)/1000;
        long milisecond=current-(minute*60000)-(second*1000);
        String formatt=". "+String.format("%03d", milisecond);
        return  formatt;
    }
    public static String format_duzenleme_milli(long current){
        long minute=current/60000;
        long second=(current%60000)/1000;
        String formatt=minute+" : "+second;
        return  formatt;
    }
    public void Continue(){
        stop=false;
        son_baslama=System.currentTimeMillis();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.toplam_sayilan);
        dest.writeLong(this.son_baslama);
        dest.writeValue(this.stop);
        dest.writeValue(this.isStarted);
        dest.writeLong(this.ilk_oynatma);
        dest.writeInt(this.durma);
        dest.writeLong(this.toplam_durma);
        dest.writeList(this.durmalar);
    }

    protected Kronometre(Parcel in) {
        this.toplam_sayilan = in.readLong();
        this.son_baslama = in.readLong();
        this.stop = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isStarted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.ilk_oynatma = in.readLong();
        this.durma = in.readInt();
        this.toplam_durma = in.readLong();
        this.durmalar = new LinkedList<duraklama>();
        in.readList(this.durmalar, duraklama.class.getClassLoader());
    }

    public static final Parcelable.Creator<Kronometre> CREATOR = new Parcelable.Creator<Kronometre>() {
        @Override
        public Kronometre createFromParcel(Parcel source) {
            return new Kronometre(source);
        }

        @Override
        public Kronometre[] newArray(int size) {
            return new Kronometre[size];
        }
    };
}


