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




        EditText general_search_field = findViewById(R.id.general_search);
        general_search_field.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event)
            {
                general_search = view.getText().toString();

                Snackbar.make(view, "Searching...", Snackbar.LENGTH_SHORT).show();
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


    public void search(boolean from_general)
    {
        String url = "https://api.artic.edu/api/v1/artworks";

        if(from_general == true)
        {
            if(!general_search.isEmpty())
            {
                url = url + "/search?q=" + general_search;

            }
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

    public void call_api(String url) {

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