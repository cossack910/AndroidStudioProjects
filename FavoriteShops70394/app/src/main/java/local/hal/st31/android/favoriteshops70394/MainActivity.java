package local.hal.st31.android.favoriteshops70394;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.view.Menu;

import java.util.Map;

/**
 * ST31 評価課題
 *
 * 第１画面用のアクテビティクラス
 * 店情報のリストを表示する
 *
 * @autor 70394
 */
public class MainActivity extends AppCompatActivity {
    /**
     *新規登録モードを表す定数フィールド
     */
    static final int MODE_INSERT = 1;
    /**
     * 更新モードを表す定数フィールド
     */
    static final int MODE_EDIT = 2;
    /**
     * ショップリスト用ListView
     */
    private ListView _lvShopList;
    /**
     * データベースヘルパーオブジェクト
     */
    private DatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _lvShopList = findViewById(R.id.lvShopList);
        _lvShopList.setOnItemClickListener(new ListItemClickListener());

        _helper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        SQLiteDatabase db = _helper.getWritableDatabase();
        Cursor cursor = DataAccess.findAll(db);
        String[] from = {"name"};
        int[] to = {android.R.id.text1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, cursor, from, to,0);
        _lvShopList.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        _helper.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.menuNewbutton:
                Intent intent = new Intent(getApplicationContext(), ShopEditActivity.class);
                intent.putExtra("mode", MODE_INSERT);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     *リストが選択されたときのリスナクラス
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            Cursor item = (Cursor) parent.getItemAtPosition(position);
            int idxId = item.getColumnIndex("_id");
            long idNo = item.getLong(idxId);

            Intent intent = new Intent(getApplicationContext(), ShopEditActivity.class);
            intent.putExtra("mode", MODE_EDIT);
            intent.putExtra("idNo", idNo);
            startActivity(intent);
        }
    }

}
