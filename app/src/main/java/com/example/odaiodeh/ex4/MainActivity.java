package com.example.odaiodeh.ex4;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.RetrofitError;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.concurrent.ExecutionException;



public class MainActivity extends AppCompatActivity implements Callback<JsonElement>{

    static final String ID = "dac070446963382";
    static final String Base = "https://api.imgur.com/3/";
    static final String AlbumId = "aTmq6mB";
    static final String AlbumPath = "/album/{albumHash}";
    static final String auth = "Authorization: Client-ID ";
    static final String AlbumHash = "albumHash";
    static final String DATA = "data";
    static final String IMAGES = "images";
    static final String LINK = "link";
    private int size = 15;
    MyAdapter adap;
    GridView Grid;
    Button button;


    public interface serviceImages {
        @GET(AlbumPath)
        @Headers(auth + ID)
        void get(@Path(AlbumHash) String Id, Callback<JsonElement> jsonContext);
    }

    
    private void init(){
        Grid = findViewById(R.id.GridView);
        adap = new MyAdapter(this);
        Grid.setAdapter(adap);
        button = findViewById(R.id.button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestAdapter JsonAdapter = new RestAdapter.Builder().setEndpoint(Base).build();
                serviceImages service = JsonAdapter.create(serviceImages.class);
                service.get(AlbumId,MainActivity.this);
            }
        });
    }

    @Override
    public void success(JsonElement jsonElement, Response response) {
        JsonObject json = jsonElement.getAsJsonObject();
        JsonArray arr = json.get(DATA).getAsJsonObject().get(IMAGES).getAsJsonArray();
        String path[] = new String[size];
        for (int i = 0; i < arr.size();i++ ){
            path[i] = arr.get(i).getAsJsonObject().get(LINK).getAsString();
        }
        new work(this).execute(path);

    }

    @Override
    public void failure(RetrofitError error) {}

    class work extends AsyncTask<String, Void, Integer> {
        Context mcontext;

        public work(Context context) {
            this.mcontext = context;
        }

        @Override
        protected Integer doInBackground(String... urls) {
            for(int i = 0; i < urls.length; i++){
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(MainActivity.this).load(urls[i]).asBitmap().into(400, 400).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                adap.setter(i, bitmap);
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            adap.notifyDataSetChanged();
        }
    }
}
