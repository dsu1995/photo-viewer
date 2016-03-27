package cs349.fotag;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ImageModel extends SimpleObservable {
    private BitmapDrawable image;
    private BitmapDrawable thumbnail;
    private int rating;

    public ImageModel(Resources res, int id) {
        rating = 0;

        Bitmap bmp = BitmapFactory.decodeResource(res, id);
        image = new BitmapDrawable(res, bmp);

        Bitmap thumbnailBmp = ThumbnailUtils.extractThumbnail(bmp, 1280, 720);
        thumbnail = new BitmapDrawable(res, thumbnailBmp);
    }

    public ImageModel(Resources res, URL url) throws IOException {
        rating = 0;

        try (InputStream stream = url.openStream()) {
            Bitmap bmp = BitmapFactory.decodeStream(stream);
            image = new BitmapDrawable(res, bmp);

            Bitmap thumbnailBmp = ThumbnailUtils.extractThumbnail(bmp, 1280, 720);
            thumbnail = new BitmapDrawable(res, thumbnailBmp);
        }
    }

    public Drawable getThumbnail() {
        return thumbnail;
    }

    public Bitmap getImageBmp() {
        return image.getBitmap();
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        rating = max(min(rating, 5), 0);
        if (this.rating != rating) {
            this.rating = rating;
            notifyObservers(new ChangeEvent(ChangeEvent.Event.RATING_CHANGED));
        }
    }

    public Drawable getImage() {
        return image;
    }
}

