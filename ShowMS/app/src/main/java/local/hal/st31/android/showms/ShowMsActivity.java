package local.hal.st31.android.showms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowMsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ms);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        int selectedPictNo = 0;
        String selectedPictName = "";

        if(extras != null){
            selectedPictNo = extras.getInt("selectedPictNo");
            selectedPictName = ((Bundle) extras).getString("selectedPictName");
        }

        TextView tvName = findViewById(R.id.tvName);
        tvName.setText(selectedPictName);

        ImageView ivSelected = findViewById(R.id.ivSelected);

        switch(selectedPictNo){
            case 0:
                ivSelected.setImageResource(R.drawable.firstgundam);
                break;
            case 1:
                ivSelected.setImageResource(R.drawable.zgundam);
                break;
            case 2:
                ivSelected.setImageResource(R.drawable.zzgundam);
                break;
            case 3:
                ivSelected.setImageResource(R.drawable.nugundam);
                break;
            case 4:
                ivSelected.setImageResource(R.drawable.ucgundam);
                break;
        }

        Button btBack = findViewById(R.id.btBack);
        btBack.setOnClickListener(new ButtonClickListener());
    }
    /**
     *
     */
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v){
            finish();
        }
    }
}
