package local.hal.st31.android.memopad;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.Timestamp;

public class DataAccess {

    public static Cursor findAll(SQLiteDatabase db){
        String sql = "SELECT _id, title, content, update_at FROM memos ORDER BY update_at DESC";

        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

    public static Memo findByPK(SQLiteDatabase db,long id){
        String sql = "SELECT _id, title, content, update_at FROM memos WHERE _id = " + id;

        Cursor cursor = db.rawQuery(sql,null);
        Memo result = null;
        if(cursor.moveToFirst()){
            int idxTitle = cursor.getColumnIndex("title");
            int idxContent = cursor.getColumnIndex("content");
            int idxUpdateAt = cursor.getColumnIndex("update_at");
            String title = cursor.getString(idxTitle);
            String content = cursor.getString(idxContent);
            String updateAtStr = cursor.getString(idxUpdateAt);

            Timestamp updateAt = Timestamp.valueOf(updateAtStr);

            result = new Memo();
            result.setId(id);
            result.setTitle(title);
            result.setContent(content);
            result.setUpdateAt(updateAt);
        }
        return result;
    }

    public static int update(SQLiteDatabase db, long id, String title, String content){
        String sql = "UPDATE memos SET title = ?, content = ?, update_at = datetime('now') WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, title);
        stmt.bindString(2, content);
        stmt.bindLong(3, id);
        int result = stmt.executeUpdateDelete();
        return result;
    }

    public static long insert(SQLiteDatabase db,String title,String content){
        String sql = "INSERT INTO memos (title, content, update_at) VALUES ( ?, ?,datetime('now'))";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1,title);
        stmt.bindString(2,content);
        long id = stmt.executeInsert();
        return id;
    }

    public static int delete(SQLiteDatabase db,long id){
        String sql = "DELETE FROM memos WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindLong(1,id);
        int result = stmt.executeUpdateDelete();
        return result;
    }
}
