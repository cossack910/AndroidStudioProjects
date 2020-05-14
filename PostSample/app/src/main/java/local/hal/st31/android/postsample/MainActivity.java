package local.hal.st31.android.postsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    /**
     * POST先のURL
     */
    private static final String ACCESS_URL = "http://hal.architshin.com/st31/post2Json.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 送信ボタンがクリックしたときの処理メソッド
     */
    public void sendButtonClick(View view) {
        EditText etName = findViewById(R.id.etName);
        EditText etComment = findViewById(R.id.etComment);
        TextView tvProcess = findViewById(R.id.tvProcess);
        TextView tvResult = findViewById(R.id.tvResult);

        tvProcess.setText("");
        tvResult.setText("");

        String name = etName.getText().toString();
        String comment = etComment.getText().toString();

        PostAccess access = new PostAccess(tvProcess, tvResult);
        access.execute(ACCESS_URL, name,comment);
    }
    /**
     * 非同期でサーバーと通信するクラス
     */
    private class PostAccess extends AsyncTask<String, String, String>{
        /**
         * ログに記載するタグ用の文字列
         */
        private static final String DEBUG_TAG = "PostAccess";
        private TextView _tvProcess;
        private TextView _tvResult;
        private boolean _success = false;

        public PostAccess(TextView tvProcess, TextView tvResult){
            _tvProcess = tvProcess;
            _tvResult = tvResult;
        }

        @Override
        public String doInBackground(String... params){
            String urlStr = params[0];
            String name = params[1];
            String comment = params[2];

            String postData = "name=" + name + "&comment=" + comment;
            HttpURLConnection con = null;
            InputStream is = null;
            String result = "";

            try{
                publishProgress(getString(R.string.msg_send_before));
                URL url = new URL(urlStr);
                con = (HttpURLConnection) url.openConnection();
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
            _tvProcess.setText(message);
        }

        @Override
        public void onPostExecute(String result) {
            if(_success){
                String name = "";
                String comment = "";
                onProgressUpdate(getString(R.string.msg_parse_before));
                try{
                    JSONObject rootJSON = new JSONObject(result);
                    name = rootJSON.getString("name");
                    comment = rootJSON.getString("comment");
                }
                catch(JSONException ex) {
                    onProgressUpdate(getString(R.string.msg_err_parse));
                    Log.e(DEBUG_TAG, "JSON解析失敗", ex);
                }
                onProgressUpdate(getString(R.string.msg_parse_after));

                String message = getString(R.string.dlg_msg_name) + name + "\n" + getString(R.string.dlg_msg_comment) + comment;
                _tvResult.setText(message);
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
