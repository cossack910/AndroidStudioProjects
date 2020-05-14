package local.hal.st31.android.itarticlecollection70394;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import androidx.fragment.app.FragmentManager;


public class ArticleAddActivity extends AppCompatActivity {

    private static final String ACCESS_URL = "http://hal.architshin.com/st31/insertItArticle.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_add);
        // アクションバーに前画面に戻る機能をつける
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.second_display, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.menuRegistbutton:
                EditText et_title = findViewById(R.id.et_title);
                EditText et_url = findViewById(R.id.et_url);
                EditText et_comment = findViewById(R.id.et_comment);

                String title = et_title.getText().toString();
                String url = et_url.getText().toString();
                String comment = et_comment.getText().toString();
                String last_name = "高木";
                String first_name = "利晃";
                String student_id = "70394";
                String seat_no = "7";

                TextView tvProcess = findViewById(R.id.tvProcess);
                if (title.equals("")||url.equals("")||comment.equals("")||last_name.equals("")||first_name.equals("")||student_id.equals("")||seat_no.equals("")) {
                    DispDialogFragment dialog = new DispDialogFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    dialog.show(manager, "DispDialogFragment");
                }else{
                    tvProcess.setText("");
                    PostAccess access = new PostAccess(tvProcess);
                    access.execute(ACCESS_URL, title, url, comment, last_name, first_name, student_id, seat_no);
                }
                break;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class PostAccess extends AsyncTask<String, String, String> {
        /**
         * ログに記載するタグ用の文字列
         */
        private  static final String DEBUG_TAG = "PostAccess";
        /**
         * サーバー通信のフラグ
         */
        private  boolean _success = false;

        /**
         * 通信結果メッセージを表示するための文字列部品
         */
        private TextView _tvProcess;
        /**
         * コンストラクタ
         *
         */
        public PostAccess(TextView tvProcess){
            _tvProcess = tvProcess;
        }

        @Override
        public String doInBackground(String... params){
            String urlStr = params[0];
            String title = params[1];
            String url = params[2];
            String comment = params[3];
            String last_name = params[4];
            String first_name = params[5];
            String student_id = params[6];
            String seat_no = params[7];

            String postData = "title=" + title + "&url=" + url + "&comment=" + comment + "&lastname=" + last_name + "&firstname=" + first_name + "&studentid=" + student_id + "&seatno=" + seat_no;
            HttpURLConnection con = null;
            InputStream is = null;
            String result ="";

            try{
                publishProgress(getString(R.string.msg_send_before));
                URL Url = new URL(urlStr);
                con = (HttpURLConnection) Url.openConnection();
                con.setRequestMethod("POST");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();
                int status = con.getResponseCode();
                if(status != 200){
                    throw new IOException("ステータスコード：" + status);
                }
                publishProgress(getString(R.string.msg_send_after));
                is = con.getInputStream();

                result = is2String(is);
                _success = true;
            }
            catch(SocketTimeoutException ex) {
                publishProgress(getString(R.string.msg_err_timeout));
                Log.e(DEBUG_TAG, "タイムアウト", ex);
            }
            catch(MalformedURLException ex){
                publishProgress(getString(R.string.msg_err_send));
                Log.e(DEBUG_TAG,"URL変換失敗", ex);
            }
            catch (IOException ex) {
                publishProgress(getString(R.string.msg_err_send));
                Log.e(DEBUG_TAG,"通信失敗", ex);
            }
            finally {
                if(con != null){
                    con.disconnect();
                }
                try{
                    if(is != null){
                        is.close();
                    }
                }
                catch(IOException ex){
                    publishProgress(getString(R.string.msg_err_parse));
                    Log.e(DEBUG_TAG, "InputStream解放失敗", ex);
                }
            }
            return result;
        }

        @Override
        public void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String message = _tvProcess.getText().toString();
            if(!message.equals("")){
                message += "\n";
            }
            message += values[0];
        }

        @Override
        public void onPostExecute(String result) {
            int status = 2;
            try{
                JSONObject rootJSON = new JSONObject(result);
                status = rootJSON.getInt("status");
            }
            catch(JSONException ex) {
                onProgressUpdate(getString(R.string.msg_err_parse));
                Log.e(DEBUG_TAG, "JSON解析失敗", ex);
            }
            if(status == 0){
                DegistFalseDialogFragment dialog = new DegistFalseDialogFragment();
                FragmentManager manager = getSupportFragmentManager();
                dialog.show(manager, "DegistFalseDialogFragment");
            }

            if(status == 1 && _success == true){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }else if(status == 2){
                FalseDispDialogFragment dialog = new FalseDispDialogFragment();
                FragmentManager manager = getSupportFragmentManager();
                dialog.show(manager, "FalseDispDialogFragment");
            }
        }

        private String is2String(InputStream is) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuffer sb = new StringBuffer();
            char[] b = new char[1024];
            int line;
            while(0 <= (line = reader.read(b))){
                sb.append(b, 0, line);
            }
            return sb.toString();
        }
    }
}
