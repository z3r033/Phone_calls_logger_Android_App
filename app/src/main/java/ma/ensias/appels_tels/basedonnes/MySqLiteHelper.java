package ma.ensias.appels_tels.basedonnes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqLiteHelper extends SQLiteOpenHelper {
    // Database Info
    Context context;
    public static String nomTable="history";
    public static String COLUMN_ID="_id";
    public static String COLUMN_IDLOG= "idlog";
    public static String COLUMN_NOM= "nom";
    public static String COLUMN_NUMERO="numero";
    public static String COLUMN_DATE="date";
    public static String COLUMN_TIME="time";
    public static String COLUMN_DURATION="duration";
    public static String COLUMN_TYPE="type";
    public static String COLUMN_LOCATION="location";
    public static String COLUMN_FULL_DATE="callfulldate";
    private static final String nomDb = "appelle.db";
    private static final int versionDb = 4;

    private static final String SQL_CREATE_HISTORY_TABLE =  "CREATE TABLE history (" +
            COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_IDLOG +" TEXT NOT NULL, "+
            COLUMN_NOM+  " TEXT NOT NULL, "+
            COLUMN_NUMERO+ " TEXT NOT NULL, "+
            COLUMN_DATE+" TEXT NOT NULL, "+
            COLUMN_TIME+ " TEXT NOT NULL, "+
            COLUMN_DURATION+ " INTEGER NOT NULL, "+
            COLUMN_TYPE+ " TEXT NOT NULL, "+
            COLUMN_LOCATION+ " TEXT NOT NULL, "+
            COLUMN_FULL_DATE +  " TEXT NOT NULL);";

    public MySqLiteHelper(@Nullable Context context) {
        super(context, nomDb, null, versionDb);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS history");
        onCreate(db);
    }
}
