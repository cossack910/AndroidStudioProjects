package local.hal.st31.android.optionmenusample;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Typeface _fontType = Typeface.DEFAULT;

    int _fontStyle = Typeface.NORMAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_menu_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        TextView tvSpeech = findViewById(R.id.tvSpeech);

        int itemId = item.getItemId();
        switch (itemId){
            case R.id.menuFonttypeSerif:
                _fontType = Typeface.SERIF;
                break;
            case R.id.menuFonttypeSansserif:
                _fontType = Typeface.SANS_SERIF;
                break;
            case R.id.menuFonttypeMonospace:
                _fontType = Typeface.MONOSPACE;
                break;
            case R.id.menuFontstyleNormal:
                _fontStyle = Typeface.NORMAL;
                break;
            case R.id.menuFontstyleItalic:
                _fontStyle = Typeface.ITALIC;
                break;
            case R.id.menuFontstyleBold:
                _fontStyle = Typeface.BOLD;
                break;
            case R.id.menuFontstyleBolditalic:
                _fontStyle = Typeface.BOLD_ITALIC;
                break;
            case R.id.menuReset:
                _fontType = Typeface.DEFAULT;
                _fontStyle = Typeface.NORMAL;
                break;
        }
        tvSpeech.setTypeface(_fontType, _fontStyle);
        return super.onOptionsItemSelected(item);
    }
}
