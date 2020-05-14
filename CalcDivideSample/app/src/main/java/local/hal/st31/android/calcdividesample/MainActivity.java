package local.hal.st31.android.calcdividesample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * ST31 Androidサンプル０４
 *
 * メインアクテビィティクラス
 *
 * @author ohs70394
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btCalc = findViewById(R.id.btCalc);
        btCalc.setOnClickListener(new ButtonClickListener());
        Button btClear = findViewById(R.id.btClear);
        btClear.setOnClickListener(new ButtonClickListener());
    }
    /**
     * ボタンが押された時の処理が記述されたメンバクラス
     */
    private class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            EditText etDenomi = findViewById(R.id.etDenomi);
            EditText etNume = findViewById(R.id.etNume);
            TextView tvAnswer = findViewById(R.id.tvAnswer);
            Context context = getApplicationContext();

            int id = view.getId();
            switch (id){
                case R.id.btCalc:
                    String strDenomi = etDenomi.getText().toString();
                    String strNume = etNume.getText().toString();
                    if(strDenomi.equals("")||strNume.equals("")){
                        String msg = "何か数字を入力せなあきまへんがな";
                        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                    }else{
                        double denomi = Double.valueOf(strDenomi);
                        if(denomi == 0){
                            String msg = "分母に０はあきまへんがな";
                            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                        }else{
                            double nume = Double.valueOf(strNume);
                            BigDecimal bigAns = new BigDecimal(nume / denomi);
                            bigAns = bigAns.setScale(3, RoundingMode.HALF_UP);
                            String strAns = bigAns.toString();
                            tvAnswer.setText(strAns);
                        }
                    }
                    break;
                    case R.id.btClear:
                        etNume.setText("");
                        etDenomi.setText("");
                        tvAnswer.setText("");
                        break;
            }
        }
    }
}
