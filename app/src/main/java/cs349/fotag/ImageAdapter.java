package cs349.fotag;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * TODO: start new asynctask when new image is added, either from local resource or from web
 * TODO: Async generate thumbnails and keep original image
 * TODO: Add to images list when done
 *
 * TODO: 2 lists: images, and visible images
 * TODO: when individual rating changes, see if it needs to be added or removed
 * TODO: when rating filter changes, check all images to see if it needs to be added or removed
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> implements Observer {
    private ImageCollectionModel images;

    @Override
    public void update(Observable observable, Object data) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public RatingBar ratingBar;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        }
    }

    public ImageAdapter(ImageCollectionModel images) {
        this.images = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {

    }
}
