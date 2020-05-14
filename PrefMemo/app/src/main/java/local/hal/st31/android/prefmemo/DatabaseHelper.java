package local.hal.st31.android.prefmemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * データベースファイルの定数フィールド
     */
    private static final String DATABASE_NAME = "prefmemo.db";
    /**
     * バージョン情報の定数フィールド
     */
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE memos (");
        sb.append("_id INTEGER,");
        sb.append("name TEXT,");
        sb.append("content TEXT,");
        sb.append("PRIMARY KEY (_id)");
        sb.append(");");
        String sql = sb.toString();

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}

