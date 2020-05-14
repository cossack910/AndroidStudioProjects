package local.hal.st31.android.forresultsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingEvaluateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_evaluate);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String msg = name + "さんの評価をつけてください。";
        TextView tvEvaluate = findViewById(R.id.tvEvaluate);
        tvEvaluate.setText(msg);

        Button btPrevious = findViewById(R.id.btPrevious);
        btPrevious.setOnClickListener(new ButtonClickListener());
    }
    /**
     * ボタンが押されたときの処理が記述されたメンバクラス
     */
    private class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            RatingBar rtbValue = findViewById(R.id.rtbValue);
            int rate = (int) rtbValue.getRating();
            Intent intent = getIntent();
            intent.putExtra("rate",rate);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
