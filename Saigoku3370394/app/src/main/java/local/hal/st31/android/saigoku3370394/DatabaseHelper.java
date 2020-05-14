package local.hal.st31.android.saigoku3370394;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * データべースファイル名の定数フィールド
     */
    private static final String DATABASE_NAME = "templememo.db";
    /**
     * バージョン情報の定数フィールド。
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * コンストラクタ
     */
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE temples (");
        sb.append("_id INTEGER,");
        sb.append("name TEXT NOT NULL,");
        sb.append("honzon TEXT,");
        sb.append("shushi TEXT,");
        sb.append("address TEXT,");
        sb.append("url TEXT,");
        sb.append("note TEXT,");
        sb.append("PRIMARY KEY(_id)");
        sb.append(");");
        String sql = sb.toString();

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
