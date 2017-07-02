package example.org.flickerbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 60010743 on 7/2/2017.
 */

enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK }
public class GetRawData {
    private String LOG_TAG = GetRawData.class.getSimpleName(); // Google to know what it returns
    private String urlPath;
    private String downloadData;
    private DownloadStatus downloadStatus;

    public GetRawData(String urlPath) {
        this.urlPath = urlPath;
        this.downloadStatus = DownloadStatus.IDLE;
    }

    public void reset(){
        this.urlPath = null;
        this.downloadData = null;
        this.downloadStatus = DownloadStatus.IDLE;
    }

    public DownloadStatus getDownloadStatus() {
        return downloadStatus;
    }

    public String getDownloadData() {
        return downloadData;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public void execute(){
        this.downloadStatus = DownloadStatus.PROCESSING;
        DownloadData downloadData = new DownloadData();
        downloadData.execute(urlPath);

    }


    public class DownloadData extends AsyncTask<String, Void, String>{

        protected void onPostExecute(String result){
            downloadData = result;
           // Log.v(LOG_TAG, "downloaded data: "+ downloadData);
            if(downloadData == null){
                if(urlPath == null){
                    downloadStatus = DownloadStatus.NOT_INITIALIZED;
                }else{
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }
            }else{
                downloadStatus = DownloadStatus.OK;
            }
        }

        protected String doInBackground(String... params){
            StringBuffer buffer = new StringBuffer();
            HttpURLConnection connection = null;
            BufferedReader bReader = null;

            if(params == null){
                return null;
            }

            try{
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();

                bReader = new BufferedReader(new InputStreamReader(is));

                String line;
                while((line = bReader.readLine()) != null ){
                    buffer.append(line + "\n");
                }

                return buffer.toString();
            }catch(IOException e){
                Log.v(LOG_TAG, "Exception in downloading"+ e.getMessage());
                return null;
            }finally {
                if(connection != null){
                    connection.disconnect();
                }
                if(bReader != null) {
                    try {
                        bReader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
        }
    }
}



















