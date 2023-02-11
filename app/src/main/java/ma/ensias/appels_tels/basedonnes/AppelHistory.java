package ma.ensias.appels_tels.basedonnes;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telecom.Call;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppelHistory {
    Context context;
    DataSource dbGest;
    public  AppelHistory (Context context,DataSource dbGest) {

        this.context=context;
        this.dbGest=dbGest;

    }
    public  AppelHistory (Context context) {

        this.context=context;

    }

    public void getAppelsInfo() {
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");
        int idlog= cursor.getColumnIndex(CallLog.Calls._ID);
        int nom=cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int numero = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int location = cursor.getColumnIndex(CallLog.Calls.LOCATION);
        while (cursor.moveToNext()) {

            String nomstr=cursor.getString(nom);
            String numerostr = cursor.getString(numero);
            String typestr = cursor.getString(type);
            String datestr = cursor.getString(date);
            Date ddate = new Date(Long.valueOf(datestr));
            String durationstr = cursor.getString(duration);
            String typee = null;
            String locationstr;
            String idlogStr = cursor.getString(idlog);
            int dircode = Integer.parseInt(typestr);


            if(location == -1) {

                locationstr ="Unknown";
            }else{
                locationstr = cursor.getString(location);
            }
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    typee = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    typee = "INCOMING";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    typee = "MISSED";
                    break;
                case CallLog.Calls.REJECTED_TYPE:
                    typee = "REJECTED";
                    break;
            }

            if(nomstr==null)

            {
                String nomc=getContactNameFromNumber(numerostr);

                if(nomc!=null) {

                    nomstr=nomc;
                }

                else {
                    nomstr = "Unknown";
                }
            }
            SimpleDateFormat formaterDate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formaterTime = new SimpleDateFormat("kk:mm:ss");
            SimpleDateFormat formaterTemp=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            String formatDate = formaterDate.format(ddate);
            String formatTime = formaterTime.format(ddate);
            String formatTemps= formaterTemp.format(ddate);

            if(! dbGest.Exists(formatTime,formatDate)) {

                ContentValues values = new ContentValues();
                values.put(DataSource.COLUMN_IDLOG,idlogStr);
                values.put(DataSource.COLUMN_NOM, nomstr);
                values.put(DataSource.COLUMN_NUMERO, numerostr);
                values.put(DataSource.COLUMN_DURATION, durationstr);
                values.put(DataSource.COLUMN_TYPE, typee);
                values.put(DataSource.COLUMN_DATE, formatDate);
                values.put(DataSource.COLUMN_TIME, formatTime);
                values.put(DataSource.COLUMN_FULL_DATE,formatTemps);
                values.put(DataSource.COLUMN_LOCATION, locationstr);

                dbGest.Insert(values);

            }

        }
        cursor.close();

    }


    public String getContactNameFromNumber(String number)
    {


        String contactName = null;
        ContentResolver contentResolver = context.getContentResolver();

        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] projection = new String[] {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.DISPLAY_NAME};
        String selection = "REPLACE (" + ContactsContract.CommonDataKinds.Phone.NUMBER + ", \" \" , \"\" ) = ?";
        String[] selectionArgs = { number };
        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);

        if (cursor.moveToFirst())
        {

            contactName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME));
            cursor.close();
            return contactName;
        }
        else
        {
            cursor.close();
            return null;
        }
    }
    public void deleteAppelleNumber(String idlog,int id){
        String queryString= CallLog.Calls._ID+"="+Integer.valueOf(idlog);
    //    context.getContentResolver().delete(CallLog.Calls.CONTENT_URI,CallLog.Calls._ID + " =? ",
        context.getContentResolver().delete(CallLog.Calls.CONTENT_URI,queryString,null);
        //        new String[] { idlog });

    boolean d= dbGest.deleteNumberById(id);
        Toast.makeText(context,"fuck"+d,Toast.LENGTH_SHORT).show();

    }


}
