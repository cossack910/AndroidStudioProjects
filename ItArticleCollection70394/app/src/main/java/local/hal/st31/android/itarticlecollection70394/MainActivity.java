package local.hal.st31.android.itarticlecollection70394;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String ACCESS_URL = "http://hal.architshin.com/st31/getItArticlesList.php";
    private List<Map<String, String>> _list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lvResult = findViewById(R.id.lvResult);
        InfoReceiver access = new InfoReceiver(lvResult);
        access.execute(ACCESS_URL);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.first_display, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.menuAddbutton:
                Intent intent = new Intent(getApplicationContext(), ArticleAddActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class InfoReceiver extends AsyncTask<String, String, String> {

        private static final String DEBUG_TAG = "InfoReceiver";

        private ListView _lvResult;

        private boolean _success = false;

        public InfoReceiver(ListView lvResult) {
            _lvResult = lvResult;
        }
        @Override
        public String doInBackground(String... params) {
            String urlStr = params[0];

            HttpURLConnection con = null;
            InputStream is = null;
            String result = "";

            try {
                URL url = new URL(urlStr);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                is = con.getInputStream();

                _success = true;
                result = is2String(is);
            }
            catch (MalformedURLException ex) {
                //publishProgress(getString(R.string.msg_err_send));
                Log.e(DEBUG_TAG, "URL変換失敗", ex);
            }
            catch (IOException ex) {
                //publishProgress(getString(R.string.msg_err_send));
                Log.e(DEBUG_TAG, "通信失敗", ex);
            }
            finally {
                if (con != null) {
                    con.disconnect();
                }

                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                        //publishProgress(getString(R.string.msg_err_parse));
                        Log.e(DEBUG_TAG, "InputStream解放失敗", ex);
                    }
                }
            }
            return result;
        }

        @Override
        public void onPostExecute(String result) {
            List<String> list_id = new ArrayList<String>();
            List<String> list_title = new ArrayList<String>();
            List<String> list_url = new ArrayList<String>();
            List<String> list_comment = new ArrayList<String>();
            List<String> list_student_id = new ArrayList<String>();
            List<String> list_seat_no = new ArrayList<String>();
            List<String> list_last_name = new ArrayList<String>();
            List<String> list_first_name = new ArrayList<String>();
            List<String> list_created_at = new ArrayList<String>();

            if(_success == true){
                //onProgressUpdate(getString(R.string.msg_parse_before));
                try{
                    JSONObject rootJSON = new JSONObject(result);
                    JSONArray lists = rootJSON.getJSONArray("list");
                    for(int i = 0; i <= lists.length(); i++){
                        list_id.add(lists.getJSONObject(i).getString("id"));
                        list_title.add(lists.getJSONObject(i).getString("title"));
                        list_url.add(lists.getJSONObject(i).getString("url"));
                        list_comment.add(lists.getJSONObject(i).getString("comment"));
                        list_student_id.add(lists.getJSONObject(i).getString("student_id"));
                        list_seat_no.add(lists.getJSONObject(i).getString("seat_no"));
                        list_last_name.add(lists.getJSONObject(i).getString("last_name"));
                        list_first_name.add(lists.getJSONObject(i).getString("first_name"));
                        list_created_at.add(lists.getJSONObject(i).getString("created_at"));

                    }
                }
                catch(JSONException ex) {
                    //onProgressUpdate(getString(R.string.msg_err_parse));
                    Log.e(DEBUG_TAG, "JSON解析失敗", ex);
                }
                //onProgressUpdate(getString(R.string.msg_parse_after));
                ListView lvSiteList = findViewById(R.id.lvResult);
                String[] from = {"title", "url"};
                int[] to = {android.R.id.text1, android.R.id.text2};
                Map<String, String> map = new HashMap<>();
                map.put("id",list_id.get(0));
                map.put("title",list_title.get(0));
                map.put("url",list_url.get(0));
                map.put("comment",list_comment.get(0));
                map.put("student_id",list_student_id.get(0));
                map.put("seat_no",list_seat_no.get(0));
                map.put("last_name",list_last_name.get(0));
                map.put("first_name",list_first_name.get(0));
                map.put("created_at",list_created_at.get(0));
                _list.add(map);
                for(int j = 1; j < list_title.size();j++){
                    map = new HashMap<>();
                    map.put("id",list_id.get(j));
                    map.put("title",list_title.get(j));
                    map.put("url",list_url.get(j));
                    map.put("comment",list_comment.get(j));
                    map.put("student_id",list_student_id.get(j));
                    map.put("seat_no",list_seat_no.get(j));
                    map.put("last_name",list_last_name.get(j));
                    map.put("first_name",list_first_name.get(j));
                    map.put("created_at",list_created_at.get(j));

                    _list.add(map);
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), _list, android.R.layout.simple_list_item_2, from, to);
                lvSiteList.setAdapter(adapter);
                lvSiteList.setOnItemClickListener(new ListItemClickListener());
            }
        }

        private class ListItemClickListener implements AdapterView.OnItemClickListener {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Map<String, String> item = _list.get(position);
                String ID = item.get("id");
                String title = item.get("title");
                String url  = item.get("url");
                String comment = item.get("comment");
                String student_id = item.get("student_id");
                String seat_no = item.get("seat_no");
                String name = item.get("last_name") + item.get("first_name");
                String created_at = item.get("created_at");

                Intent intent = new Intent(getApplicationContext(), ArticleDetailActivity.class);
                intent.putExtra("Id",ID);
                intent.putExtra("Title",title);
                intent.putExtra("Url",url);
                intent.putExtra("Comment",comment);
                intent.putExtra("Student_id",student_id);
                intent.putExtra("Seat_no",seat_no);
                intent.putExtra("Name",name);
                intent.putExtra("Created_at",created_at);

                startActivity(intent);
            }
        }

        private String is2String(InputStream is) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            char[] b = new char[1024];
            int line;
            while (0 <= (line = reader.read(b))) {
                sb.append(b, 0, line);
            }
            return sb.toString();
        }
    }

}