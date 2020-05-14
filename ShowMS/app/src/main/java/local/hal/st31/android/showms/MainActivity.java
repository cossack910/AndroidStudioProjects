package local.hal.st31.android.showms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvMS = findViewById(R.id.lvMS);
        lvMS.setOnItemClickListener(new ListItemClickListener());
    }
    /**
     *
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent,View view,int position,long id){
            String name = (String) parent.getItemAtPosition(position);

            Intent intent = new Intent(getApplicationContext(),ShowMsActivity.class);
            intent.putExtra("selectedPictNo",position);
            intent.putExtra("selectedPictName",name);
            startActivity(intent);
        }
    }
}
