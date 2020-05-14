package local.hal.st31.android.itarticlecollection70394;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

public class ArticleDetailActivity extends AppCompatActivity {

    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        // アクションバーに前画面に戻る機能をつける
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String id = intent.getStringExtra("Id");
        String title = intent.getStringExtra("Title");
        url = intent.getStringExtra("Url");
        String comment = intent.getStringExtra("Comment");
        String student_id = intent.getStringExtra("Student_id");
        String seat_no = intent.getStringExtra("Seat_no");
        String name = intent.getStringExtra("Name");
        String created_at = intent.getStringExtra("Created_at");

        TextView tv_id = findViewById(R.id.list_id);
        TextView tv_title = findViewById(R.id.list_title);
        Button tv_url = findViewById(R.id.list_url);
        TextView tv_comment = findViewById(R.id.list_comment);
        TextView tv_student_id = findViewById(R.id.list_student_id);
        TextView tv_seat_no = findViewById(R.id.list_seat_no);
        TextView tv_name = findViewById(R.id.list_name);
        TextView tv_created_at = findViewById(R.id.list_created_at);

        tv_id.setText("ID:"+id);
        tv_title.setText("タイトル:"+title);
        tv_url.setText(url);
        tv_comment.setText("コメント:"+comment);
        tv_student_id.setText("学籍番号:"+student_id);
        tv_seat_no.setText("出席番号:"+seat_no);
        tv_name.setText("学生氏名:"+name);
        tv_created_at.setText("登録日時:"+created_at);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void urlJump(View view){
        Uri uri = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }

}
