package local.hal.st31.android.favoriteshops70394;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.support.v7.app.ActionBar;
import android.widget.Toast;
/**
 * ST31 評価課題
 *
 * 第二画面表示用アクテビティクラス
 * メモ情報編集画面を表示する
 *
 * @autor 70394
 */
public class ShopEditActivity extends AppCompatActivity {
    /**
     *新規登録モードか更新モードかを表すフィールド
     */
    private int _mode = MainActivity.MODE_INSERT;
    /**
     * 更新モード時、現在表示しているお店情報のデータベース上の主キー値。
     */
    private long _idNo = 0;
    /**
     * データベースヘルパーオブジェクト
     */
    private DatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_edit);
        // アクションバーに前画面に戻る機能をつける
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        _helper = new DatabaseHelper(getApplicationContext());

        Intent intent = getIntent();
        _mode = intent.getIntExtra("mode",MainActivity.MODE_INSERT);

        if(_mode == MainActivity.MODE_INSERT){
            TextView tvTitleEdit = findViewById(R.id.tvTitleEdit);
            tvTitleEdit.setText(R.string.tv_title_insert);

        } else {
            _idNo = intent.getLongExtra("idNo", 0);
            SQLiteDatabase db = _helper.getWritableDatabase();
            Shops shopData = DataAccess.findByPK(db, _idNo);

            EditText etInputTitle = findViewById(R.id.etInputTitle);
            etInputTitle.setText(shopData.getName());

            EditText etInputTel = findViewById(R.id.etInputTell);
            etInputTel.setText(shopData.getTel());

            EditText etInputUrl = findViewById(R.id.etInputUrl);
            etInputUrl.setText(shopData.getUrl());

            EditText etInputMemo = findViewById(R.id.etInputMemo);
            etInputMemo.setText(shopData.getNote());
        }
    }
    /* アクションバー*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        if(_mode == MainActivity.MODE_INSERT) {
            inflater.inflate(R.menu.new_shop_edit_menu, menu);
        } else {
            inflater.inflate(R.menu.shop_edit_menu, menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Context context = getApplicationContext();
        switch(itemId) {
            case R.id.menuSavebutton:
                EditText etInputTitle = findViewById(R.id.etInputTitle);
                String inputTitle = etInputTitle.getText().toString();
                if (inputTitle.equals("")) {
                    Toast.makeText(ShopEditActivity.this, R.string.tv_input_title, Toast.LENGTH_SHORT).show();
                } else {
                    EditText etInputTell = findViewById(R.id.etInputTell);
                    String inputTell = etInputTell.getText().toString();

                    EditText etInputUrl = findViewById(R.id.etInputUrl);
                    String inputUrl = etInputUrl.getText().toString();

                    EditText etInputMemo = findViewById(R.id.etInputMemo);
                    String inputMemo = etInputMemo.getText().toString();

                    SQLiteDatabase db = _helper.getWritableDatabase();
                    if (_mode == MainActivity.MODE_INSERT) {
                        DataAccess.insert(db, inputTitle, inputTell, inputUrl, inputMemo);
                    } else {
                        DataAccess.update(db, _idNo, inputTitle, inputTell, inputUrl, inputMemo);
                    }
                    finish();
                }
                break;
            case R.id.menuDeletebutton:
                FUllDialogFragment dialog = new FUllDialogFragment();
                FragmentManager manager = getSupportFragmentManager();
                dialog.show(manager, "FullDialogFragment");
                break;
            case R.id.menuUrlbutton:
                EditText etInputUrl = findViewById(R.id.etInputUrl);
                String inputUrl = etInputUrl.getText().toString();
                if (inputUrl.equals("")) {
                    String msg = "URLが入っていません";
                    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                }else if(inputUrl.startsWith( "http://") || inputUrl.startsWith( "https://" )){
                    Uri uri = Uri.parse(inputUrl);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }else {
                    String msg = "URLではありません";
                    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                }

                break;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        _helper.close();
        super.onDestroy();
    }

    /**
     * 表示されたダイアログの削除ボタンが押されたときに、データ削除を行うメソッド
     */
    public void  listDelete(){
        SQLiteDatabase db = _helper.getWritableDatabase();
        DataAccess.delete(db, _idNo);
        finish();
    }

}
