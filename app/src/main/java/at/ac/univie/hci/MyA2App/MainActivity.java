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
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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
//        Log.d("url", url);
        call_api(url);
        

    }


    public String get_artwork_id(JSONObject elem)
    {
        String id = elem.optString("id", "Untitled");
//        Log.d("id", id);
        return id;
    }



    private void save_artworks_in_list_view()
    {
        ListView l;
        String artwork_title = "";

        for(Artwork elem : artwork_list)
        {
            artwork_title = elem.title;
            Log.d("artwork_title", artwork_title);
        }





    }



    private void get_info_about_artwork(String id_url)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(id_url).build();

        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.d("fail3", "fail3");
            }

            @Override
            public void onResponse(Call call, Response response)
            {
                if(response.isSuccessful())
                {
                    Log.d("succ2", "response is succ2");
                    try
                    {
                        String res = response.body().string();
                        try
                        {

                            JSONObject json = new JSONObject(res);
                            JSONObject artwork_data = json.getJSONObject("data");
//                            Log.d("artwork_data", artwork_data.toString());

                            title = artwork_data.optString("title", "Untitled");
                            if(title.equals("null"))
                            {
                                title = "Untitled";
                            }
                            artist_name = artwork_data.optString("artist_title", "Unknown Artist");
                            if(artist_name.equals("null"))
                            {
                                artist_name = "Unknown Artist";
                            }
                            date = artwork_data.optString("date_display", "Unknown date");
                            if(date.equals("null"))
                            {
                                date = "Unknown date";
                            }
                            medium = artwork_data.optString("medium_display", "Unknown medium");
                            if(medium.equals("null"))
                            {
                                medium = "Unknown medium";
                            }
                            country = artwork_data.optString("place_of_origin", "Unknown origin");
                            if(search_country.equals("null"))
                            {
                                country = "Unknown origin";
                            }
                            description = artwork_data.optString("description", "No Description");
                            if(description.equals("null"))
                            {
                                description = "No Description";
                            }
                            year = artwork_data.optString("date_display", "Unknown year");
                            if(year.equals("null"))
                            {
                                year = "Unknown year";
                            }
//                            Log.d("title", title);
//                            Log.d("artist_name", artist_name);
//                            Log.d("date", date);
//                            Log.d("medium", medium);
//                            Log.d("country", country);
//                            Log.d("description", description);
//                            Log.d("year", year);
                            save_artwork_in_array();

                            save_artworks_in_list_view();


                        }
                        catch (Exception e)
                        {
                            Log.d("json error2", "failed to make json object or smthing like that idk");
                        }

                    }
                    catch (IOException e)
                    {
                        Log.d("API error2", e.getMessage());
                    }


                }
                else
                {
                    Log.d("fail3", response.message());
                }
            }
        });


    }



    public void save_artwork_in_array()
    {
        //	public Artwork
        Artwork a = new Artwork(artist_name, title, country, year, medium, alt_text, description);
        artwork_list.add(a);

//        Log.d("ammount of artworks in artwork_list:", Integer.toString(artwork_list.size())); //WORKS

//        Log.d("artwork info from save_artwork_in_array", a.toString());
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
                    Log.d("succ", "response is succ");
                    try
                    {
                        String res = response.body().string();
//                        Log.d("res", res);
                        try
                        {

                            JSONObject json = new JSONObject(res);
                            JSONArray data = json.getJSONArray("data");
//                            Log.d("data", data.toString());

//                            Log.d("ammount of artworks", Integer.toString(data.length()));

                            for (int i = 0; i < data.length(); i = i + 1)
                            {
                                String artwork_id = get_artwork_id(data.getJSONObject(i));

                                String id_url = "https://api.artic.edu/api/v1/artworks/" + artwork_id;
//                                Log.d(id_url, id_url);
                                get_info_about_artwork(id_url);




                            }






                        }
                        catch (Exception e)
                        {
                            Log.d("json error", "failed to make json object or smthing like that idk");
                        }

                    }
                    catch (IOException e)
                    {
                        Log.d("API error", e.getMessage());
                    }


                }
                else
                {
                    Log.d("fail2", response.message());
                }
            }
        });




    }







    public void filter_button_click(View view)
    {
        FilterPopup popup = new FilterPopup(this);
        popup.show(view);
    }

}