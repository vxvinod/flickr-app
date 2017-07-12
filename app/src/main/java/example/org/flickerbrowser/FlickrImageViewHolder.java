package example.org.flickerbrowser;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 60010743 on 7/4/2017.
 */
public class FlickrImageViewHolder extends RecyclerView.ViewHolder {

    public ImageView thumbnail;
    public TextView title;

    public FlickrImageViewHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.placeholder);
        this.title     = (TextView) view.findViewById(R.id.title);
    }
}
