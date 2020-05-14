package local.hal.st31.android.saigoku3370394;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

public class DataAccess {
    /**
     * 主キーによるメモ内容検索メソッド
     * @return 主キーに対応する honzon,shushi,address,url,noteのカラム値の配列
     */
    public static String[] findContentByPK(SQLiteDatabase db, int id){
        String sql = "SELECT honzon,shushi,address,url,note FROM temples WHERE _id =" + id;
        Cursor cursor = db.rawQuery(sql,null);
        String[] result = new String[5];
        while(cursor.moveToNext()){
            int idxhonzon = cursor.getColumnIndex("honzon");
            int idxshushi = cursor.getColumnIndex("shushi");
            int idxaddress = cursor.getColumnIndex("address");
            int idxurl = cursor.getColumnIndex("url");
            int idxnote = cursor.getColumnIndex("note");
            result[0] = cursor.getString(idxhonzon);
            result[1] = cursor.getString(idxshushi);
            result[2] = cursor.getString(idxaddress);
            result[3] = cursor.getString(idxurl);
            result[4] = cursor.getString(idxnote);
        }
        return result;
    }

    /**
     * 寺院のメモが存在するかどうかをチェックするメソッド
     * @param db
     * @param id
     * @return
     */
    public static boolean findRowByPK(SQLiteDatabase db, int id){
        String sql = "SELECT COUNT(*) AS count FROM temples WHERE _id = " + id;
        Cursor cursor = db.rawQuery(sql,null);
        boolean result = false;
        if(cursor.moveToFirst()){
            int idxConut = cursor.getColumnIndex("count");
            int count = cursor.getInt(idxConut);
            if(count >= 1){
                result = true;
            }
        }
        return result;
    }

    /**
     * 寺院のメモを上書きするメソッド
     * @param db
     * @param id
     * @param name
     * @param honzon
     * @param shushi
     * @param address
     * @param url
     * @param note
     * @return
     */
    public static int update(SQLiteDatabase db, int id,String name,String honzon,String shushi,String address,String url,String note){
        String sql = "UPDATE temples SET name = ?, honzon = ?, shushi = ?, address = ?, url = ?, note = ? WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, name);
        stmt.bindString(2, honzon);
        stmt.bindString(3,shushi);
        stmt.bindString(4,address);
        stmt.bindString(5, url);
        stmt.bindString(6, note);
        stmt.bindLong(7, id);
        int result = stmt.executeUpdateDelete();
        return result;
    }

    /**
     * 寺院のメモを追加するメソッド
     * @param db
     * @param id
     * @param name
     * @param honzon
     * @param shushi
     * @param address
     * @param url
     * @param note
     * @return
     */
    public static long insert(SQLiteDatabase db, int id,String name,String honzon,String shushi,String address,String url,String note){
        String sql = "INSERT INTO temples (_id, name, honzon, shushi, address, url, note) VALUES(?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindLong(1, id);
        stmt.bindString(2, name);
        stmt.bindString(3, honzon);
        stmt.bindString(4, shushi);
        stmt.bindString(5, address);
        stmt.bindString(6, url);
        stmt.bindString(7, note);
        long insertedId = stmt.executeInsert();
        return insertedId;
    }
}
