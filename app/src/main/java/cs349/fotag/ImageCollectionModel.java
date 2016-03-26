package cs349.fotag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ImageCollectionModel extends SimpleObservable {
    private List<ImageModel> images;
    private List<ImageModel> visibleImages;
    private Context context;

    public ImageCollectionModel(Context context) {
        images = new ArrayList<>();
        visibleImages = new ArrayList<>();
        this.context = context;
    }




    private class LoadResourceAsync extends AsyncTask<Integer, Void, ImageModel> {
        private int id;

        public LoadResourceAsync(int id) {
            this.id = id;
        }

        @Override
        protected ImageModel doInBackground(Integer... params) {
            Resources res = context.getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res, id);
            Drawable image = new BitmapDrawable(res, bmp);

            Bitmap thumbnailBmp = ThumbnailUtils.extractThumbnail(bmp, 1280, 720);
            Drawable thumbnail = new BitmapDrawable(res, thumbnailBmp);

        }

        @Override
        protected void onPostExecute(ImageModel imageModel) {

        }
    }



}
