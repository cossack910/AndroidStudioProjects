package local.hal.st31.android.calcbmi70394;

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
     * ボタンが押されたときの処理が記述されたメンバクラス
     */
    private class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            EditText etHeight = findViewById(R.id.etHeight);
            EditText etWeight = findViewById(R.id.etWeight);
            TextView tvAnswer = findViewById(R.id.tvAnswer);
            Context context = getApplicationContext();

            int id = view.getId();
            switch(id){
                case R.id.btCalc:
                    String strHeight = etHeight.getText().toString();
                    String strWeight = etWeight.getText().toString();
                    if(strHeight.equals("")||strWeight.equals("")){
                        String msg = "値が入っていません。";
                        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                    }else{
                        double height = Double.valueOf(strHeight);
                        double weight = Double.valueOf(strWeight);
                        //新生児の身長が44cm,体重が2kgぐらい
                        if(height < 44 || height >300 || weight < 2 ||weight > 300){
                            String msg = "正しく値を入力してください。";
                            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                        }else{
                            BigDecimal bigAns = new BigDecimal(weight / (height/100 * height/100) );
                            bigAns = bigAns.setScale(1, RoundingMode.HALF_UP);
                            String strAns = bigAns.toString();
                            double reAns = Double.valueOf(strAns);
                            tvAnswer.setText("あなたのBMIは" + strAns + "です。");
                            if(reAns >= 18.5 && reAns < 25){
                                String msg = "ちょうどいいです。現状を維持しましょう。";
                                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                            }else if(reAns >= 25){
                                double doubleWeight = height/100 * height/100 * 22;
                                //四捨五入する
                                doubleWeight = Math.ceil(doubleWeight);
                                int bestWeight = (int)doubleWeight;
                                String msg = "肥満です。体重" + bestWeight + "kgを目指しましょう。";
                                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                            }else if(reAns < 18.5){
                                double doubleWeight = height/100 * height/100 * 22;
                                //四捨五入する
                                doubleWeight = Math.ceil(doubleWeight);
                                int bestWeight = (int)doubleWeight;
                                String msg = "やせています。体重" + bestWeight + "kgを目指しましょう。";
                                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    break;
                case R.id.btClear:
                    etHeight.setText("");
                    etWeight.setText("");
                    tvAnswer.setText("");
                    break;
            }

        }
    }

}