package at.ac.univie.hci.MyA2App;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;


import android.content.Context;
import android.view.inputmethod
        .InputMethodManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        //aus irgendeinem grund wird wenn man enter drueckt, dashier 2x ausgefuehrt
        //ist scheinbar ein bug?
        //sollte aber nicht wichtig sein, da es nur passiert wenn man enter auf der tastatur drueckt
        //https://stackoverflow.com/questions/4513888/enter-key-on-edittext-hitting-onkey-twice
        EditText general_search_field = findViewById(R.id.general_search);
        general_search_field.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event)
            {
                if (event != null && event.getAction() != KeyEvent.ACTION_DOWN)
                {
                    return false;
                }


                general_search = view.getText().toString();

                Snackbar.make(view, "Searching generally...", Snackbar.LENGTH_SHORT).show();
                //hides keybaord so that the snackbar can be visible
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                search(true);

                return true;

            }
        });




    }

    //only for search
    public String general_search = "";

    //for filter
    public String search_artist_name = "";
    public String search_time_period_from = "";
    public String search_time_period_to = "";
    public String search_title = "";
    public String search_country = "";
    public String search_medium = "";



    //for display
    public ArrayList<Artwork> artwork_list = new ArrayList<>();
    public String artist_name = "";
    public String title = "";
    public String year = "";
    public String medium = "";
    public String alt_text = "";
    public String date = "";
    public String description = "";
    public String country = "";




    Response res;



    private JSONArray first_response_success(String res)
    {
        try
        {
            JSONObject json = new JSONObject(res);
            JSONArray data = json.getJSONArray("data");
//            Log.d("data", data.toString());
            return data;
        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }

    }

    private String get_artwork_id(JSONObject elem)
    {
        String id = elem.optString("id", "ERROR_NO_ID");
        return id;
    }

    private ArrayList<String> get_artwork_ids(JSONArray data)
    {
        ArrayList<String> artwork_ids = new ArrayList<String>();
        String artwork_id = "";

        for (int i = 0; i < data.length(); i = i + 1)
        {
            try
            {
                artwork_id = get_artwork_id(data.getJSONObject(i));
                artwork_ids.add(artwork_id);
            }
            catch (JSONException e)
            {
                throw new RuntimeException(e);
            }

            String id_url = "https://api.artic.edu/api/v1/artworks/" + artwork_id;
            //Log.d(id_url, id_url);

        }

        /*
        int i = 1;
        for (String elem : artwork_ids)
        {
            Log.d("artwork_id number " + i + ":", elem);
            i = i + 1;
        }
        */

        return artwork_ids;

    }


    private void call_api_for_specific_id(String artwork_id)
    {
        String url = "https://api.artic.edu/api/v1/artworks/" + artwork_id;

        OkHttpClient client2 = new OkHttpClient();
        Request request2 = new Request.Builder().url(url).build();

        client2.newCall(request2).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.d("fail2", "fail2");
            }


            @Override
            public void onResponse(Call call, Response response)
            {

                if (response.isSuccessful())
                {


                    Log.d("succ2", "succ2");

                    try
                    {
                        Thread.sleep(100);

                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }


    public void call_api(String url)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();



        client.newCall(request).enqueue(new Callback()
        {

            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.d("fail1", "fail1");
            }


            @Override
            public void onResponse(Call call, Response response)
            {

                if(response.isSuccessful())
                {

                    Log.d("succ", "succ");

                    res = response;
                    try
                    {



                        JSONArray data = first_response_success(res.body().string());
                        Log.d("data", data.toString()); //WORKS
                        Log.d("ammount of artworks", Integer.toString(data.length())); //WORKS

                        ArrayList<String> artwork_ids = get_artwork_ids(data);
                        ArrayList<Artwork> artworks = new ArrayList<Artwork>();
                        for(String id : artwork_ids)
                        {
                            call_api_for_specific_id(id);
                        }



                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }
        });





    }

    public void search(boolean from_general)
    {
        String url = "https://api.artic.edu/api/v1/artworks";
//        Log.d("url", url);

        if(from_general == true)
        {
            url = url + "/search?q=" + general_search;

        }
        else //from_general == false
        {
            url = url + "/search";

            if(!search_artist_name.isEmpty())
            {
                url = url + "?[term][artist_title]=" + search_artist_name + "&";
            }
            if(!search_title.isEmpty())
            {
                url = url + "?[term][title]=" + search_title + "&";
            }
            if(!search_country.isEmpty())
            {
                url = url + "?[term][title]=" + search_country + "&";
            }
            if(!search_time_period_from.isEmpty())
            {
                url = url + "?[term][title]=" + search_time_period_from + "&";
            }
            if(!search_time_period_to.isEmpty())
            {
                url = url + "?[term][title]=" + search_time_period_to + "&";
            }
            if(!search_medium.isEmpty())
            {
                url = url + "?[term][title]=" + search_medium + "&";
            }

            //removes last "&"
            if(url.substring(url.length() - 1).equals("&"))
            {
                url = url.substring(0, url.length() - 1);

            }


        }

        url = url + "&limit=100";
//        Log.d("url", url);

        call_api(url);
        

    }







    public void filter_button_click(View view)
    {
        FilterPopup popup = new FilterPopup(this);
        popup.show(view);
    }

}