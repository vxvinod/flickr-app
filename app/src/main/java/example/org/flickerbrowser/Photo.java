package example.org.flickerbrowser;

/**
 * Created by 60010743 on 7/2/2017.
 */
public class Photo {
    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mLink;
    private String mImage;
    private String mTags;

    public Photo(String mTitle, String mAuthor, String mAuthorId, String mLink, String mImage, String mTags) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mAuthorId = mAuthorId;
        this.mLink = mLink;
        this.mImage = mImage;
        this.mTags = mTags;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmAuthorId() {
        return mAuthorId;
    }

    public String getmLink() {
        return mLink;
    }

    public String getmImage() {
        return mImage;
    }

    public String getmTags() {
        return mTags;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mTags='" + mTags + '\'' +
                '}';
    }
}
