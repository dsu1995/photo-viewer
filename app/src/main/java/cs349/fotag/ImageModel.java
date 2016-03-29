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

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        Bitmap bmp = BitmapFactory.decodeResource(res, id, options);
        image = new BitmapDrawable(bmp);

        Bitmap thumbnailBmp = ThumbnailUtils.extractThumbnail(bmp, 853, 480);
        thumbnail = new BitmapDrawable(thumbnailBmp);
    }

    public ImageModel(Resources res, URL url) throws IOException {
        rating = 0;

        try (InputStream stream = url.openStream()) {
            Bitmap bmp = BitmapFactory.decodeStream(stream);
            if (bmp == null) {
                throw new IOException("Image cannot be decoded");
            }
            image = new BitmapDrawable(bmp);

            Bitmap thumbnailBmp = ThumbnailUtils.extractThumbnail(bmp, 853, 480);
            thumbnail = new BitmapDrawable(thumbnailBmp);
        }
    }

    public Drawable getThumbnail() {
        return thumbnail;
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

