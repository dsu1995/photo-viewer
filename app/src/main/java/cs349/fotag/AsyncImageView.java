package cs349.fotag;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Observable;
import java.util.Observer;

public class AsyncImageView extends android.widget.ImageView implements Observer {

    public ImageModel model;

    public AsyncImageView(Context context, ImageModel model) {
        super(context);
        this.model = model;
        model.addObserver(this);
        setImageDrawable(model.getImage());
    }

    public AsyncImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AsyncImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AsyncImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data == ImageModel.Signals.IMAGE_LOADED && model != null) {
            setImageDrawable(model.getImage());
        }
    }
}
