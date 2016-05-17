package navneet.kwickie.com.kwickiefeed.Kwickie;

import android.graphics.Bitmap;
import java.io.Serializable;

// POJO Kwickie
public class Kwickie implements Serializable {
    private String thumbnailUrl;
    private String videoUrl;
    private Bitmap bitmap;
    private String firstPersonName;
    private String secondPersonName;

    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setVideoUrl(String _videoUrl) {
        this.videoUrl = _videoUrl;
    }
    public String getVideoUrl() {
        return videoUrl;
    }

    public String getFirstPersonName() {
        return firstPersonName;
    }
    public void setFirstPersonName(String firstPersonName) {
        this.firstPersonName = firstPersonName;
    }

    public String getSecondPersonName() {
        return secondPersonName;
    }
    public void setSecondPersonName(String secondPersonName) {
        this.secondPersonName = secondPersonName;
    }
}