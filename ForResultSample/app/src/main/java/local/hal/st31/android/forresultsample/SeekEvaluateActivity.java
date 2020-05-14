package local.hal.st31.android.forresultsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekEvaluateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_evaluate);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String msg = name + "   さんの評価をつけてください。";
        TextView tvEvaluate = findViewById(R.id.tvEvaluate);
        tvEvaluate.setOnClickListener(new ButtonClickListener());

        Button btPrevious = findViewById(R.id.btPrevious);
        btPrevious.setOnClickListener(new ButtonClickListener());
    }
    /**
     * ボタンが押されたときの処理が記述されたメンバクラス
     */
    private class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            SeekBar skbValue = findViewById(R.id.skbValue);
            int rate = skbValue.getProgress();

            Intent intent = getIntent();
            intent.putExtra("rate",rate);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
