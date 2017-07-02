package example.org.flickerbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 60010743 on 7/2/2017.
 */
public class GetFlickrJsonData extends GetRawData {
    private String LOG_TAG = GetFlickrJsonData.class.getSimpleName();
    private Uri destinationUri;
    private List<Photo> mphotos;

    public GetFlickrJsonData(String searchCriteria, boolean matchAll){
        super(null); //Since we dont have uri to initialize the super class we are passing as null.
        mphotos = new ArrayList<Photo>();
        createAndUpdateUri(searchCriteria, matchAll);
    }

    public void execute(){
        super.setUrlPath(destinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Build uri"+destinationUri.toString());
        downloadJsonData.execute(destinationUri.toString());
    }

    public boolean createAndUpdateUri(String searchCriteria, boolean matchAll){
        final String BASE_API = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAG_PARAM = "tags";
        final String TAGMODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NO_JSON_CALLBACK_PARAM = "nojsoncallback";

        destinationUri = Uri.parse(BASE_API).buildUpon()
                .appendQueryParameter(TAG_PARAM, searchCriteria)
                .appendQueryParameter(TAGMODE_PARAM, matchAll ? "ALL" : "ANY")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NO_JSON_CALLBACK_PARAM, "1")
                .build();

        return (destinationUri != null) ;


    }

    //Why process Result is kept outside subclass
    public void processResult(){
        if(getDownloadStatus() != DownloadStatus.OK){
            Log.e(LOG_TAG, "error in fetching data");
        }

        final String FLICKR_ITEMS = "items";
        final String FLICKR_TITLE = "title";
        final String FLICKR_MEDIA = "media";
        final String FLICKR_PHOTO_URL  = "m";
        final String FLICK_AUTHOR = "author";
        final String FLICKR_AUTHOR_ID = "author_id";
        final String FLICKR_LINK = "link";
        final String FLICKR_TAGS = "tags";

        try{
            JSONObject jsonPhoto = new JSONObject(getDownloadData());
            JSONArray jsonItems   = jsonPhoto.getJSONArray(FLICKR_ITEMS);
            for(int i=0; i< jsonItems.length(); i++){
                JSONObject photoData = jsonItems.getJSONObject(i);
                String title = photoData.getString(FLICKR_TITLE);
                String author = photoData.getString(FLICK_AUTHOR);
                String authorId = photoData.getString(FLICKR_AUTHOR_ID);
                String link = photoData.getString(FLICKR_LINK);
                //String image = photoData.getString(FLICKR_PHOTO_URL);
                String tags = photoData.getString(FLICKR_TAGS);

                JSONObject media = photoData.getJSONObject(FLICKR_MEDIA);
                String photoUrl = media.getString(FLICKR_PHOTO_URL);

                Photo photo = new Photo(title, author, authorId, link, photoUrl, tags);
                this.mphotos.add(photo);
            }

            for (Photo singlePhoto: mphotos) {
                Log.v(LOG_TAG,"result:"+ singlePhoto.toString());

            }

        }catch(JSONException e){
            e.printStackTrace();
            Log.e(LOG_TAG, "Error processing json data"+e.getMessage());
        }














    }

    public class DownloadJsonData extends DownloadData{
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            processResult();
        }

        protected String doInBackground(String...params){
            return super.doInBackground(params);
        }
    }

}
