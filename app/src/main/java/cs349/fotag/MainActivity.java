package cs349.fotag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ImageAdapter adapter = new ImageAdapter();
        recyclerView.setAdapter(adapter);

        adapter.addImage(new ImageModel(this, R.drawable.sample1));
        adapter.addImage(new ImageModel(this, R.drawable.sample2));
        adapter.addImage(new ImageModel(this, R.drawable.sample3));
        adapter.addImage(new ImageModel(this, R.drawable.sample4));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
