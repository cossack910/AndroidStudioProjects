package local.hal.st31.android.memopad;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    static final int MODE_INSERT = 1;

    static final int MODE_EDIT = 2;

    private ListView _lvMemoList;

    private DatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _lvMemoList = findViewById(R.id.lvMemoList);
        _lvMemoList.setOnItemClickListener(new ListItemClickListener());

        _helper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    protected void onResume(){
        super.onResume();
        SQLiteDatabase db = _helper.getWritableDatabase();
        Cursor cursor = DataAccess.findAll(db);
        String[] from = { "title" };
        int[] to = { android.R.id.text1 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, cursor, from, to,0);
        _lvMemoList.setAdapter(adapter);
    }

    @Override
    protected void onDestroy(){
        _helper.close();
        super.onDestroy();
    }

    public void onNewButtonClick(View view){
        Intent intent = new Intent(getApplicationContext(), MemoEditActivity.class);
        intent.putExtra("mode", MODE_INSERT);
        startActivity(intent);
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick (AdapterView<?> parent,View view,int position,long id){
            Cursor item = (Cursor) parent.getItemAtPosition(position);
            int idxId = item.getColumnIndex("_id");
            long idNo = item.getLong(idxId);

            Intent intent = new Intent(getApplicationContext(),MemoEditActivity.class);
            intent.putExtra("mode", MODE_EDIT);
            intent.putExtra("idNo", idNo);
            //intent.putExtra("idNo", id);
            startActivity(intent);
        }
    }
}
