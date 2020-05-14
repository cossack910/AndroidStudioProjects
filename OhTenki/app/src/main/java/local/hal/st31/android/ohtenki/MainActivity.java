package local.hal.st31.android.ohtenki;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String WEATHEREINFO_URL = "http://weather.livedoor.com/forecast/webservice/json/v1";

    private List<Map<String, String>> _list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _list = createList();

        ListView  lvCityList = findViewById(R.id.lvCityList);
        String[] from = {"name"};
        int[] to = {android.R.id.text1};
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), _list,android.R.layout.simple_expandable_list_item_1, from, to);
        lvCityList.setAdapter(adapter);
        lvCityList.setOnItemClickListener(new ListItemClickListener());
    }

    private List<Map<String, String>> createList() {
        List<Map<String, String>> list = new ArrayList<>();

        Map<String, String> map = new HashMap<>();
        map.put("name", "大阪");
        map.put("id", "270000");
        list.add(map);
        map = new HashMap<>();
        map.put("name", "神戸");
        map.put("id", "280010");
        list.add(map);
        map = new HashMap<>();
        map.put("name", "豊岡");
        map.put("id", "280020");
        list.add(map);
        map = new HashMap<>();
        map.put("name", "京都");
        map.put("id", "260010");
        list.add(map);
        map = new HashMap<>();
        map.put("name", "舞鶴");
        map.put("id", "260020");
        list.add(map);
        map = new HashMap<>();
        map.put("name", "奈良");
        map.put("id", "290010");
        list.add(map);
        map = new HashMap<>();
        map.put("name", "風屋");
        map.put("id", "290020");
        list.add(map);
        map = new HashMap<>();
        map.put("name", "和歌山");
        map.put("id", "300010");
        list.add(map);
        map = new HashMap<>();
        map.put("name", "潮岬");
        map.put("id", "300020");
        list.add(map);

        return list;
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            Map<String, String> item = _list.get(position);
            String idNo = item.get("id");

            WeatherInfoReceiver receiver = new WeatherInfoReceiver();
            receiver.execute(WEATHEREINFO_URL, idNo);
        }
    }

    private class WeatherInfoReceiver extends AsyncTask<String, Void, String> {

        private static final String DEBUG_TAG = "WeatherInfoReceiver";

        @Override
        public String doInBackground(String... params) {
            String urlStr = params[0];
            String id = params[1];

            HttpURLConnection con = null;
            InputStream is = null;
            String result = "";

            try {
                URL url = new URL(urlStr + "?city=" + id);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                is = con.getInputStream();

                result = is2String(is);
            }
            catch(MalformedURLException ex){
                Log.e(DEBUG_TAG, "URL変換失敗", ex);
            }
            catch(IOException ex) {
                Log.e(DEBUG_TAG, "通信失敗", ex);
            }
            finally {
                if(con != null) {
                    con.disconnect();
                }

                if(is != null) {
                    try {
                        is.close();
                    }
                    catch (IOException ex){
                        Log.e(DEBUG_TAG, "InputStream解放失敗", ex);
                    }
                }
            }

            return result;
        }

        @Override
        public void onPostExecute(String result) {
            String title = "";
            String text = "";
            String dateLabel = "";
            String telop = "";
            try {
                JSONObject rootJSON = new JSONObject(result);
                title = rootJSON.getString("title");
                JSONObject descriptionJSON = rootJSON.getJSONObject("description");
                text = descriptionJSON.getString("text");
                JSONArray forecasts = rootJSON.getJSONArray("forecasts");
                JSONObject forecastsNow = forecasts.getJSONObject(0);
                dateLabel = forecastsNow.getString("dateLabel");
                telop = forecastsNow.getString("telop");
            }
            catch (JSONException ex){
                Log.e(DEBUG_TAG, "JSON解放失敗", ex);
            }

            String msg = dateLabel + "の天気:" + telop + "\n" + text;

            WeatherInfoDialog dialog = new WeatherInfoDialog();
            Bundle extras = new Bundle();
            extras.putString("title", title);
            extras.putString("msg", msg);
            dialog.setArguments(extras);
            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "WeatherInfoDialog");
        }

        private String is2String(InputStream is) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            char[] b = new char[1024];
            int line;
            while(0 <= (line = reader.read(b))) {
                sb.append(b, 0, line);
            }
            return sb.toString();
        }
    }
}
