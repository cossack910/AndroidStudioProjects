package local.hal.st31.android.memopad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "memopad.db";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE memos (");
        sb.append(" _id INTEGER PRIMARY KEY AUTOINCREMENT,");
        sb.append("title TEXT NOT NULL,");
        sb.append("content TEXT,");
        sb.append("update_at TIMESTAMP NOT NULL");
        sb.append(");");
        String sql = sb.toString();

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){

    }
}
