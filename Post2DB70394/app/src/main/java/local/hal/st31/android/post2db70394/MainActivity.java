package local.hal.st31.android.post2db70394;

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
    private static final String ACCESS_URL = "http://hal.architshin.com/st31/post2DB.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 送信ボタンがクリックしたときの処理メソッド
     */
    public void sendButtonClick(View view){
        EditText et_last_name = findViewById(R.id.et_last_name);
        EditText et_first_name = findViewById(R.id.et_first_name);
        EditText et_student_id = findViewById(R.id.et_student_id);
        EditText et_seat_no = findViewById(R.id.et_seat_no);
        EditText et_message = findViewById(R.id.et_message);
        TextView tvResult = findViewById(R.id.tvResult);

        String last_name = et_last_name.getText().toString() ;
        String first_name = et_first_name.getText().toString();
        String student_id = et_student_id.getText().toString();
        String seat_no = et_seat_no.getText().toString();
        String message = et_message.getText().toString();

        if(!(last_name.equals("")) && !(first_name.equals("")) && !(student_id.equals("")) && !(seat_no.equals("")) ){
            tvResult.setText("");
            PostAccess access = new PostAccess(tvResult);
            access.execute(ACCESS_URL, last_name, first_name, student_id, seat_no, message);
        }
        tvResult.setText("姓,名,学籍番号,出席番号は必須項目です。");

    }
    /**
     * 非同期でサーバと通信するクラス
     */
    private class PostAccess extends AsyncTask<String, String, String>{
        /**
         * ログに記載するタグ用の文字列
         */
        private  static final String DEBUG_TAG = "PostAccess";
        /**
         * 通信結果メッセージを表示するための文字列部品
         */
        private TextView _tvResult;
        /**
         * サーバー通信が成功したかどうかのフラグ
         */
        private  boolean _success = false;
        /**
         * コンストラクタ
         *
         * @param tvResult 通信結果メッセージを表示するための文字列部品
         */
        public PostAccess(TextView tvResult) {
            _tvResult = tvResult;
        }

        @Override
        public String doInBackground(String... params){
            String urlStr = params[0];
            String last_name = params[1];
            String first_name = params[2];
            String student_id = params[3];
            String seat_no = params[4];
            String message = params[5];

            String postData = "lastname=" + last_name + "&firstname=" + first_name + "&studentid=" + student_id + "&seatno=" + seat_no + "&message=" + message;
            HttpURLConnection con = null;
            InputStream is = null;
            String result ="";

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
        public void onPostExecute(String result) {
            if(_success == true){
                String name = "";
                String student_id = "";
                String seat_no = "";
                String status = "";
                String msg = "";
                String serialno = "";
                String timestamp = "";

                onProgressUpdate(getString(R.string.msg_parse_before));
                try{
                    JSONObject rootJSON = new JSONObject(result);
                    name = rootJSON.getString("name");
                    student_id = rootJSON.getString("studentid");
                    seat_no = rootJSON.getString("seatno");
                    status = rootJSON.getString("status");
                    msg = rootJSON.getString("msg");
                    serialno = rootJSON.getString("serialno");
                    timestamp = rootJSON.getString("timestamp");
                }
                catch(JSONException ex) {
                    onProgressUpdate(getString(R.string.msg_err_parse));
                    Log.e(DEBUG_TAG, "JSON解析失敗", ex);
                }
                onProgressUpdate(getString(R.string.msg_parse_after));

                String v_message = getString(R.string.dlg_name) + name + "\n" +
                                   getString(R.string.dlg_student_id) + student_id + "\n" +
                                   getString(R.string.dlg_seat_no) + seat_no + "\n" +
                                   getString(R.string.dlg_status) + status + "\n" +
                                   getString(R.string.dlg_message) + "\n" + msg + "\n" +
                                   getString(R.string.dlg_timestamp) + "\n" + timestamp + "\n" +
                                   getString(R.string.dlg_serialno) + serialno;
                _tvResult.setText(v_message);
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
