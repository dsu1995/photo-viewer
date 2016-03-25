package cs349.fotag;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ImageModel extends Observable {
    private Drawable image;
    private int rating;

    public enum Signals {
        IMAGE_LOADED,
        RATING_CHANGED
    }

    public ImageModel(Context context, int id) {
        rating = 0;
        image = ContextCompat.getDrawable(context, id);
        notifyObservers(Signals.IMAGE_LOADED);
    }

    public ImageModel(Resources resources, URL url) throws IOException {
        rating = 0;
        image = null;
        new DownloadImageAsync(resources).execute(url);
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        rating = max(min(rating, 5), 0);
        if (this.rating != rating) {
            this.rating = rating;
            notifyObservers();
        }
    }

    public Drawable getImage() {
        return image;
    }

    private class DownloadImageAsync extends AsyncTask<URL, Void, Drawable> {
        private Resources resources;

        public DownloadImageAsync(Resources resources) {
            this.resources = resources;
        }

        @Override
        protected Drawable doInBackground(URL... urls) {
            URL url = urls[0];
            try (InputStream stream = url.openStream()){
                Bitmap bmp = BitmapFactory.decodeStream(stream);
                return new BitmapDrawable(resources, bmp);
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            image = drawable;
            notifyObservers();
        }
    }
}

