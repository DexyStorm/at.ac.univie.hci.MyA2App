package at.ac.univie.hci.MyA2App;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;


public class FilterPopup
{
	private final MainActivity activity;
	public PopupWindow filter_popup;


	public FilterPopup(Activity activity)
	{
		this.activity = (MainActivity) activity;
	}




	public void show(View location)
	{

		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
		View popup = inflater.inflate(R.layout.filter_popup, null);
		filter_popup = new PopupWindow(popup, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		filter_popup.showAtLocation(location, Gravity.TOP, 0, 0);


		//to retain the information from previous filtering
		EditText artist_search = popup.findViewById(R.id.artist_search);
		artist_search.setText(activity.search_artist_name);

		EditText country_search = popup.findViewById(R.id.country_search);
		country_search.setText(activity.search_country);

		EditText time_from_search = popup.findViewById(R.id.time_period_from_search);
		time_from_search.setText(activity.search_time_period_from);

		EditText time_period_to_search = popup.findViewById(R.id.time_period_to_search);
		time_period_to_search.setText(activity.search_time_period_to);

		EditText medium_search = popup.findViewById(R.id.medium_search);
		medium_search.setText(activity.search_medium);





		clear_button_clicked(popup, location);
		go_button_clicked(popup, location);
		back_button_clicked(popup, location);


	}


	private void go_button_clicked(View popup, View location)
	{
		TextView go_button;
		go_button = popup.findViewById(R.id.go_button);
		go_button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				EditText artist_search = popup.findViewById(R.id.artist_search);
				activity.search_artist_name = artist_search.getText().toString();
				EditText country_search = popup.findViewById(R.id.country_search);
				activity.search_country = country_search.getText().toString();
				EditText time_from_search = popup.findViewById(R.id.time_period_from_search);
				activity.search_time_period_from = time_from_search.getText().toString();
				EditText time_to_search = popup.findViewById(R.id.time_period_to_search);
				activity.search_time_period_to = time_to_search.getText().toString();
				EditText medium_search = popup.findViewById(R.id.medium_search);
				activity.search_medium = medium_search.getText().toString();


				InputMethodManager imm = (InputMethodManager)activity.getSystemService(activity.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(popup.getWindowToken(), 0);
				Snackbar.make(location, "Searching with Filters...", Snackbar.LENGTH_SHORT).show();
				activity.search(false);
				filter_popup.dismiss();
			}
		});
	}


	private void clear_button_clicked(View popup, View location)
	{

		Button clear_button = popup.findViewById(R.id.clear_button);
		clear_button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				EditText artist_search = popup.findViewById(R.id.artist_search);
				artist_search.setText("");
				activity.artist_name = "";

				EditText country_search = popup.findViewById(R.id.country_search);
				country_search.setText("");
				activity.country = "";

				EditText time_from_search = popup.findViewById(R.id.time_period_from_search);
				time_from_search.setText("");
				activity.search_time_period_from = "";

				EditText time_period_to_search = popup.findViewById(R.id.time_period_to_search);
				time_period_to_search.setText("");
				activity.search_time_period_to = "";

				EditText medium_search = popup.findViewById(R.id.medium_search);
				medium_search.setText("");
				activity.medium = "";

				//hides keybaord so that the snackbar can be visible
				InputMethodManager imm = (InputMethodManager)activity.getSystemService(activity.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(popup.getWindowToken(), 0);

				Snackbar.make(location, "Filters have been cleared.", Snackbar.LENGTH_SHORT).show();

			}
		});
	}



	private void back_button_clicked(View popup, View location)
	{
		ImageButton back_button = popup.findViewById(R.id.popup_back_button);
		back_button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				InputMethodManager imm = (InputMethodManager)activity.getSystemService(activity.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(popup.getWindowToken(), 0);
				filter_popup.dismiss();
			}
		});

	}



}