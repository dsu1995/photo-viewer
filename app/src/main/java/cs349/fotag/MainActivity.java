package cs349.fotag;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    private ImageCollectionModel imageCollectionModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

//        setupActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


//        LinearLayout layout = new LinearLayout(this);

//        ImageModel imageModel = new ImageModel(this, R.drawable.sample1);
//
//        AsyncImageView imageView = new AsyncImageView(this, imageModel);
//
//        layout.addView(imageView);


//        try {
//            ImageModel imageModel2 = new ImageModel(getResources(), new URL("http://www.serebii.net/anime/pokemon/251.gif"));
//            AsyncImageView imageView2 = new AsyncImageView(this, imageModel2);
//            layout.addView(imageView2);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        setContentView(layout);
        imageCollectionModel = new ImageCollectionModel(getResources(), new ImageCollectionModel.ErrorHandler() {
            @Override
            public void handle(Exception e) {
                errorHandler(e);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ImageAdapter adapter = new ImageAdapter(imageCollectionModel, this);
        recyclerView.setAdapter(adapter);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            findViewById(R.id.title_text_view).setVisibility(View.VISIBLE);
        }

        RatingBar ratingFilter = (RatingBar) toolbar.findViewById(R.id.rating_filter);
        ratingFilter.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                imageCollectionModel.setRatingFilter((int) rating);
            }
        });
    }

    public void errorHandler(Exception e) {
        Snackbar.make(findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (recyclerView != null) {
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
                findViewById(R.id.title_text_view).setVisibility(View.GONE);
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                findViewById(R.id.title_text_view).setVisibility(View.VISIBLE);
            }
        }
    }

    public void loadImages(View view) {
        imageCollectionModel.addImageResource(R.drawable.sample1);
        imageCollectionModel.addImageResource(R.drawable.sample2);
        imageCollectionModel.addImageResource(R.drawable.sample3);
        imageCollectionModel.addImageResource(R.drawable.sample4);
        try {
            imageCollectionModel.addImageFromUrl("http://www.serebii.net/sunmoon2.jpg");
        } catch (MalformedURLException e) {
            errorHandler(e);
        }
    }

    public void clearImages(View view) {
        imageCollectionModel.clearImages();
    }

    public void loadUrl(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Load from URL");

        final EditText input = new EditText(this);
        alert.setView(input);
        alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String result = input.getText().toString();

                if (!result.isEmpty()) {
                    try {
                        boolean success = imageCollectionModel.addImageFromUrl(result);
                        if (!success) {
                            errorHandler(new Exception("Image has already been added!"));
                        }
                    } catch (MalformedURLException e) {
                        errorHandler(e);
                    }
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }

//    private void setupActionBar(Toolbar toolbar) {
//        toolbar.setDisplayShowCustomEnabled(true);
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.action_bar_layout, null);
//
//        actionBar.setCustomView(view);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
