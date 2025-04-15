package at.ac.univie.hci.MyA2App;

import android.os.Bundle;
import android.view.Gravity;
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
    }

    public String general_search = "";
    public String artist_name  = "";
    public String art_name = "";
    public String country = "";
    public String time_period_from = "";
    public String time_period_to = "";
    public String medium = "";

    private void call_api()
    {

    }

    public void filter_button_click(View view)
    {

        ImageButton filter_button = findViewById(R.id.filter_button);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popup = inflater.inflate(R.layout.filter_popup, null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popup, width, height, focusable);
        filter_button.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(filter_button, Gravity.TOP, 0, 0);
            }
        });


        //GO BUTTON
        TextView go_button;
        go_button = popup.findViewById(R.id.go_button);
        go_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText artist_search = popup.findViewById(R.id.artist_search);
                artist_name = artist_search.getText().toString();
                EditText art_search = popup.findViewById(R.id.art_search);
                art_name = art_search.getText().toString();
                EditText country_search = popup.findViewById(R.id.country_search);
                country = country_search.getText().toString();
                EditText time_from_serach = popup.findViewById(R.id.time_period_from_search);
                time_period_from = time_from_serach.getText().toString();
                EditText time_to_search = popup.findViewById(R.id.time_period_to_search);
                time_period_to = time_to_search.getText().toString();
                EditText medium_search = popup.findViewById(R.id.medium_search);
                medium = medium_search.getText().toString();

                Snackbar.make(view, "Searching...", Snackbar.LENGTH_SHORT).show();
                call_api();
                popupWindow.dismiss();
            }
        });







        //CLEAR BUTTON
        Button clear_button;
        clear_button = popup.findViewById(R.id.clear_button);
        clear_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText artist_search = popup.findViewById(R.id.artist_search);
                artist_search.setText("");
                artist_name  = "";

                EditText art_search = popup.findViewById(R.id.art_search);
                art_search.setText("");
                art_name = "";

                EditText country_search = popup.findViewById(R.id.country_search);
                country_search.setText("");
                country = "";

                EditText time_from_search = popup.findViewById(R.id.time_period_from_search);
                time_from_search.setText("");
                time_period_from = "";

                EditText time_period_to_search = popup.findViewById(R.id.time_period_to_search);
                time_period_to_search.setText("");
                time_period_to = "";

                EditText medium_search = popup.findViewById(R.id.medium_search);
                medium_search.setText("");
                medium = "";

                //hides keybaord so that the snackbar can be visible
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(popup.getWindowToken(), 0);

                Snackbar.make(view, "Filters have been cleared.", Snackbar.LENGTH_SHORT).show();

            }
        });


    }



}