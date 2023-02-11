package ma.ensias.appels_tels.basedonnes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ma.ensias.appels_tels.models.AppelModel;

public class DataSource {
    private SQLiteDatabase db;
    private MySqLiteHelper dbHelper;
    public static String nomTable="history";
    public static String COLUMN_ID="_id";
    public static String COLUMN_IDLOG= "idlog";
    public static String COLUMN_NOM= "nom";;
    public static String COLUMN_NUMERO="numero";
    public static String COLUMN_DATE="date";
    public static String COLUMN_TIME="time";
    public static String COLUMN_DURATION="duration";
    public static String COLUMN_TYPE="type";
    public static String COLUMN_LOCATION="location";
    public static String COLUMN_FULL_DATE="callfulldate";
    Context context;
    public DataSource(Context context) {
        dbHelper = new MySqLiteHelper(context);
     //   db=dbHelper.getWritableDatabase();
        open();
        this.context=context;
    }

    public void open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    //inserer les donnes dans la base de donnes
    public long Insert (ContentValues values) {
        long ID= db.insert(nomTable,null,values);
        return ID;
    }
    //Vérifie si l'enregistrement existe
    public boolean Exists(String formatHeure, String formatDate) {

        String[] columns = { COLUMN_TIME,COLUMN_DATE };
        String selection = COLUMN_TIME + " =?" + " AND "+ COLUMN_DATE  + " =?" ;
        String[] selectionArgs = { formatHeure,formatDate };
        String limit = "1";
        Cursor cursor = db.query(nomTable, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
   //méthode où nous pouvons faire une requête conditionnelle
   public Cursor query  (String[] projection,String selection,String[] selectionArgs,String sortOrder) {

       SQLiteQueryBuilder qb= new SQLiteQueryBuilder();
       qb.setTables(nomTable);

       Cursor cursor=qb.query(db,projection,selection,selectionArgs,null,null,sortOrder);

       return cursor;

   }



    //extrait les données de la base de données
    @SuppressLint("Range")
    public ArrayList<AppelModel> getData() {

        ArrayList<AppelModel> mDataList= new ArrayList<>();
        Cursor cursor=null;

            cursor=query(null,null,null,COLUMN_DATE+" DESC");
        if(cursor.moveToFirst()){

            do {

                AppelModel appelregister= new AppelModel();

                appelregister.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                appelregister.setIdlog(cursor.getString(cursor.getColumnIndex(COLUMN_IDLOG)));
                appelregister.setNom(cursor.getString(cursor.getColumnIndex(COLUMN_NOM)));
                appelregister.setNumero(cursor.getString(cursor.getColumnIndex(COLUMN_NUMERO)));
                appelregister.setDuration(cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION)));
                appelregister.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
                appelregister.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                appelregister.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
                appelregister.setTemps_appelle(cursor.getString(cursor.getColumnIndex(COLUMN_FULL_DATE)));
                appelregister.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));

                mDataList.add(appelregister);

            }

            while(cursor.moveToNext()); }
        return mDataList;

    }

    //extrait les données de la base de données
    @SuppressLint("Range")
    public ArrayList<AppelModel> getDataSortByDuree() {

        ArrayList<AppelModel> mDataList= new ArrayList<>();
        Cursor cursor=null;
        cursor=query(null,null,null, COLUMN_DURATION+" DESC");


        if(cursor.moveToFirst()){

            do {

                AppelModel appelregister= new AppelModel();

                appelregister.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                appelregister.setIdlog(cursor.getString(cursor.getColumnIndex(COLUMN_IDLOG)));
                appelregister.setNom(cursor.getString(cursor.getColumnIndex(COLUMN_NOM)));
                appelregister.setNumero(cursor.getString(cursor.getColumnIndex(COLUMN_NUMERO)));
                appelregister.setDuration(cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION)));
                appelregister.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
                appelregister.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                appelregister.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
                appelregister.setTemps_appelle(cursor.getString(cursor.getColumnIndex(COLUMN_FULL_DATE)));
                appelregister.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
                mDataList.add(appelregister);

            }

            while(cursor.moveToNext()); }
        return mDataList;

    }
    @SuppressLint("Range")
    public ArrayList<AppelModel> getAppelByType(String type) {

        ArrayList<AppelModel> mDataList= new ArrayList<>();
        Cursor cursor=null;
        cursor=query(null,COLUMN_TYPE+" = "+"?",   new String[] { type }, COLUMN_DATE+" DESC");


        if(cursor.moveToFirst()){

            do {

                AppelModel appelregister= new AppelModel();

                appelregister.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                appelregister.setIdlog(cursor.getString(cursor.getColumnIndex(COLUMN_IDLOG)));
                appelregister.setNom(cursor.getString(cursor.getColumnIndex(COLUMN_NOM)));
                appelregister.setNumero(cursor.getString(cursor.getColumnIndex(COLUMN_NUMERO)));
                appelregister.setDuration(cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION)));
                appelregister.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
                appelregister.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                appelregister.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
                appelregister.setTemps_appelle(cursor.getString(cursor.getColumnIndex(COLUMN_FULL_DATE)));
                appelregister.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
                mDataList.add(appelregister);

            }

            while(cursor.moveToNext()); }
        return mDataList;

    }
    @SuppressLint("Range")
    public ArrayList<AppelModel> getAppelByMounth() {
        DateFormat min = new SimpleDateFormat("yyyy-MM-01");
        DateFormat max = new SimpleDateFormat("yyyy-MM-31");
        Date date = new Date();
        Log.d("Month",min.format(date));
        Log.d("Month",max.format(date));
        ArrayList<AppelModel> mDataList= new ArrayList<>();
        Cursor cursor=null;
        cursor=query(null, COLUMN_DATE+ " BETWEEN ? AND ? " ,   new String[] { min.format(date) + " 00:00:00", max.format(date) + " 23:59:59"}, COLUMN_DATE+" DESC");


        if(cursor.moveToFirst()){

            do {

                AppelModel appelregister= new AppelModel();

                appelregister.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                appelregister.setIdlog(cursor.getString(cursor.getColumnIndex(COLUMN_IDLOG)));
                appelregister.setNom(cursor.getString(cursor.getColumnIndex(COLUMN_NOM)));
                appelregister.setNumero(cursor.getString(cursor.getColumnIndex(COLUMN_NUMERO)));
                appelregister.setDuration(cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION)));
                appelregister.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
                appelregister.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                appelregister.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
                appelregister.setTemps_appelle(cursor.getString(cursor.getColumnIndex(COLUMN_FULL_DATE)));
                appelregister.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
                mDataList.add(appelregister);

            }

            while(cursor.moveToNext()); }
        return mDataList;

    }

    @SuppressLint("Range")
    public int getDurationByMounth(String mounth) {
        int somme=0;
        DateFormat min = new SimpleDateFormat("yyyy-"+mounth+"-01");
        DateFormat max = new SimpleDateFormat("yyyy-"+mounth+"-31");
        Date date = new Date();
        Log.d("Month",min.format(date));
        Log.d("Month",max.format(date));
        ArrayList<AppelModel> mDataList= new ArrayList<>();
        ArrayList<String> dataString= new ArrayList<>();
        Cursor cursor=null;
        cursor=query(null, COLUMN_DATE+ " BETWEEN ? AND ? " ,   new String[] { min.format(date) + " 00:00:00", max.format(date) + " 23:59:59"}, COLUMN_DATE+" DESC");


        if(cursor.moveToFirst()){

            do {

                somme+= Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DURATION)));

            }

            while(cursor.moveToNext()); }
        return somme;

    }

    @SuppressLint("Range")
    public ArrayList<String> getAllDates() {

        ArrayList<String> dataString= new ArrayList<>();
        Cursor cursor=null;
        cursor=query(null,null,null,COLUMN_DATE+" ASC");

        if(cursor.moveToFirst()){

            do {

                dataString.add(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));

            }

            while(cursor.moveToNext()); }
        Set<String> set = new HashSet<>(dataString);
        dataString.clear();
        dataString.addAll(set);

        return dataString;

    }

    @SuppressLint("Range")
    public int getDurationyDay (String date) {
        int sum =0;
        String selection;
        String[] selectionArgs;

            selection= COLUMN_DATE + " =? ";
            selectionArgs = new String[] { date };

        Cursor cursor=query(null,selection,selectionArgs,null);

        if(cursor.moveToFirst()){

            do {

                sum+= Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DURATION)));

            }

            while(cursor.moveToNext()); }


        return sum;

    }


    @SuppressLint("Range")
    public int getDurationByWeek(String minDate, String maxDate) throws ParseException {
        int somme=0;
        SimpleDateFormat dateformater = new SimpleDateFormat("yyyy-MM-dd");
        Date minDate2=   dateformater.parse(minDate);
        Date maxDate2=   dateformater.parse(maxDate);
            Log.d("Month",dateformater.format(minDate2));
        ArrayList<AppelModel> mDataList= new ArrayList<>();
        ArrayList<String> dataString= new ArrayList<>();
        Cursor cursor=null;
        cursor=query(null, COLUMN_DATE+ " BETWEEN ? AND ? " ,   new String[] { dateformater.format(minDate2) + " 00:00:00", dateformater.format(maxDate2) + " 23:59:59"}, COLUMN_DATE+" DESC");


        if(cursor.moveToFirst()){

            do {

                somme+= Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DURATION)));

            }

            while(cursor.moveToNext()); }
        return somme;

    }


    public boolean deleteNumberById(int id)
    {
        return db.delete(nomTable, COLUMN_ID + "=" + id, null) > 0;
    }




}
