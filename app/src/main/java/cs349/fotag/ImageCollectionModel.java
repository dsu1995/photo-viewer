package cs349.fotag;

import android.content.res.Resources;
import android.os.AsyncTask;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ImageCollectionModel extends SimpleObservable implements Observer {
    public static ImageModel MAILBOX = null;

    private Resources res;

    private Set<Integer> addedLocalDrawables;
    private Set<String> addedUrls;

    private List<LoadResourceAsync> pendingLoadResources;
    private List<DownloadAsync> pendingDownloads;

    private List<ImageModel> images;
    private List<ImageModel> visibleImages;
    private int ratingFilter;
    private ErrorHandler errorHandler;

    public ImageCollectionModel(Resources res, ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;

        images = new ArrayList<>();
        visibleImages = new ArrayList<>();

        addedLocalDrawables = new HashSet<>();
        addedUrls = new HashSet<>();

        pendingLoadResources = new ArrayList<>();
        pendingDownloads = new ArrayList<>();

        this.res = res;
        ratingFilter = 0;
    }

    public int getRatingFilter() {
        return ratingFilter;
    }

    public void setRatingFilter(int ratingFilter) {
        ratingFilter = max(min(ratingFilter, 5), 0);
        if (this.ratingFilter != ratingFilter) {
            this.ratingFilter = ratingFilter;
            refilterAll();
        }
    }

    private void refilterAll() {
        visibleImages.clear();
        for (ImageModel image: images) {
            if (image.getRating() >= ratingFilter) {
                visibleImages.add(image);
            }
        }
        notifyObservers(new ChangeEvent(ChangeEvent.Event.REFILTER_ALL));
    }

    private void addImage(ImageModel image) {
        image.addObserver(this);
        images.add(image);
        if (image.getRating() >= ratingFilter) {
            visibleImages.add(image);
            notifyObservers(new ChangeEvent(ChangeEvent.Event.ADD, visibleImages.size() - 1));
        }
    }

    public boolean addImageResource(int id) {
        if (!addedLocalDrawables.contains(id)) {
            addedLocalDrawables.add(id);

            LoadResourceAsync newTask = new LoadResourceAsync();
            pendingLoadResources.add(newTask);
            newTask.execute(id);

            return true;
        }
        else {
            return false;
        }
    }

    public boolean addImageFromUrl(String urlStr) throws MalformedURLException {
        if (!addedUrls.contains(urlStr)) {
            addedUrls.add(urlStr);

            URL url = new URL(urlStr);

            DownloadAsync newTask = new DownloadAsync();
            pendingDownloads.add(newTask);
            newTask.execute(url);

            return true;
        }
        else {
            return false;
        }
    }

    public ImageModel getVisibleImage(int index) {
        return visibleImages.get(index);
    }

    @Override
    public void update(Observable observable, Object data) {
        ChangeEvent event = (ChangeEvent) data;
        if (event.event == ChangeEvent.Event.RATING_CHANGED) {
            ImageModel imageModel = (ImageModel) observable;
            if (imageModel.getRating() < ratingFilter) {
                int index = visibleImages.indexOf(imageModel);
                if (index != -1) {
                    visibleImages.remove(index);
                    notifyObservers(new ChangeEvent(ChangeEvent.Event.REMOVE, index));
                }
                else {
                    throw new RuntimeException("Images can only disappear!");
                }
            }
        }
    }


    private class LoadResourceAsync extends AsyncTask<Integer, Void, ImageModel> {
        @Override
        protected ImageModel doInBackground(Integer... ints) {
            return new ImageModel(res, ints[0]);
        }

        @Override
        protected void onPostExecute(ImageModel imageModel) {
            pendingLoadResources.remove(this);
            addImage(imageModel);
        }
    }


    private class DownloadAsync extends AsyncTask<URL, Void, ImageModel> {
        private Exception e = null;

        @Override
        protected ImageModel doInBackground(URL... urls) {
            try {
                return new ImageModel(res, urls[0]);
            }
            catch (FileNotFoundException e) {
                this.e = new RuntimeException("URL does not contain a valid image!", e);
                return null;
            }
            catch (Exception e) {
                this.e = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(ImageModel imageModel) {
            if (e != null) {
                errorHandler.handle(e);
            }
            else {
                pendingDownloads.remove(this);
                addImage(imageModel);
            }
        }
    }

    public int getNumVisibleImages() {
        return visibleImages.size();
    }

    public void clearImages() {
        for (LoadResourceAsync pendingTask: pendingLoadResources) {
            pendingTask.cancel(true);
        }
        pendingLoadResources.clear();
        for (DownloadAsync pendingTask: pendingDownloads) {
            pendingTask.cancel(true);
        }
        pendingDownloads.clear();

        addedLocalDrawables.clear();
        addedUrls.clear();
        images.clear();
        visibleImages.clear();
        notifyObservers(new ChangeEvent(ChangeEvent.Event.REFILTER_ALL));
    }

    public interface ErrorHandler {
        void handle(Exception e);
    }
}
