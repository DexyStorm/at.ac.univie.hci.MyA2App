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

    public String general_search = "";
    public String artist_name = "";
    public String title = "";
    public String country = "";
    public String time_period_from = "";
    public String time_period_to = "";
    public String medium = "";
    public String alt_text = "";
    public String date = "";
    public String description = "";


    public void search(boolean from_general)
    {
        String url = "https://api.artic.edu/api/v1/artworks";

        if(from_general == true)
        {
            url = url + "/search?q=" + general_search;

        }
        else //from_general == false
        {
            url = url + "/search";

            if(!artist_name.isEmpty())
            {
                url = url + "?[term][artist_title]=" + artist_name + "&";
            }
            if(!title.isEmpty())
            {
                url = url + "?[term][title]=" + title + "&";
            }
            if(!country.isEmpty())
            {
                url = url + "?[term][title]=" + country + "&";
            }
            if(!time_period_from.isEmpty())
            {
                url = url + "?[term][title]=" + time_period_from + "&";
            }
            if(!time_period_to.isEmpty())
            {
                url = url + "?[term][title]=" + time_period_to + "&";
            }
            if(!medium.isEmpty())
            {
                url = url + "?[term][title]=" + medium + "&";
            }

            //removes last "&"
            if(url.substring(url.length() - 1).equals("&"))
            {
                url = url.substring(0, url.length() - 1);

            }

        }
//        Log.d(url, url);
        call_api(url);
        

    }


    public String get_artwork_id(JSONObject elem)
    {
        String id = elem.optString("id", "Untitled");
        return id;
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
                            Log.d("artwork_data", artwork_data.toString());

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
                            if(country.equals("null"))
                            {
                                country = "Unknown origin";
                            }
                            description = artwork_data.optString("description", "No Description");
                            if(description.equals("null"))
                            {
                                description = "No Description";
                            }
                            Log.d("title", title);
                            Log.d("artist_name", artist_name);
                            Log.d("date", date);
                            Log.d("medium", medium);
                            Log.d("country", country);
                            Log.d("description", description);



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
                        try
                        {

                            JSONObject json = new JSONObject(res);
                            JSONArray data = json.getJSONArray("data");
//                            Log.d("data", data.toString());


                            for (int i = 0; i < data.length(); i = i + 1)
                            {
                                String artwork_id = get_artwork_id(data.getJSONObject(i));

                                String id_url = "https://api.artic.edu/api/v1/artworks/" + artwork_id;
//                                Log.d(id_url, id_url);
                                get_info_about_artwork(id_url);



                                //STOPPED HERE
                                //SAVE RESPONSE IN LIST

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