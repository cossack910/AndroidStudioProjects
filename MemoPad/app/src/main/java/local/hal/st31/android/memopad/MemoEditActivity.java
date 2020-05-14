package local.hal.st31.android.memopad;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MemoEditActivity extends AppCompatActivity {

    private int _mode = MainActivity.MODE_INSERT;

    private long _idNo = 0;

    private DatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        _helper = new DatabaseHelper(getApplicationContext());

        Intent intent = getIntent();
        _mode = intent.getIntExtra("mode",MainActivity.MODE_INSERT);

        if(_mode == MainActivity.MODE_INSERT){
            TextView tvTitleEdit = findViewById(R.id.tvTitleEdit);
            tvTitleEdit.setText(R.string.tv_title_insert);

            Button btnSave = findViewById(R.id.btnSave);
            btnSave.setText(R.string.btn_insert);

            Button btnDelete = findViewById(R.id.btnDelete);
            btnDelete.setVisibility(View.INVISIBLE);
        }else{
            _idNo = intent.getLongExtra("idNo",0);
            SQLiteDatabase db = _helper.getWritableDatabase();
            Memo memoData = DataAccess.findByPK(db, _idNo);

            EditText etInputTitle = findViewById(R.id.etInputTitle);
            etInputTitle.setText(memoData.getTitle());

            EditText etInputContent = findViewById(R.id.etInputContent);
            etInputContent.setText(memoData.getContent());
        }
    }

    @Override
    protected void onDestroy(){
        _helper.close();
        super.onDestroy();
    }

    public void onSaveButtonClick(View view){
        EditText etInputTitle = findViewById(R.id.etInputTitle);
        String inputTitle = etInputTitle.getText().toString();
        if(inputTitle.equals("")){
            Toast.makeText(MemoEditActivity.this, R.string.msg_input_title, Toast.LENGTH_SHORT).show();
        }else {
            EditText etInputContent = findViewById(R.id.etInputContent);
            String inputContent = etInputContent.getText().toString();
            SQLiteDatabase db = _helper.getWritableDatabase();
            if(_mode == MainActivity.MODE_INSERT){
                DataAccess.insert(db, inputTitle,inputContent);
            }else{
                DataAccess.update(db, _idNo, inputTitle,inputContent);
            }
            finish();
        }
    }

    public void onBackButtonClick(View view){
        finish();
    }

    public void onDeleteButtonClick(View view){
        SQLiteDatabase db = _helper.getWritableDatabase();
        DataAccess.delete(db, _idNo);
        finish();
    }
}
