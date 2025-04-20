package at.ac.univie.hci.MyA2App;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BigPicture extends AppCompatActivity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//EdgeToEdge.enable(this);


		setContentView(R.layout.big_picture);


		ImageView imageView = findViewById(R.id.bp_artwork_image);
		TextView titleView = findViewById(R.id.bp_title_text);
		TextView artistView = findViewById(R.id.bp_artist_text);
		TextView countryView = findViewById(R.id.bp_country_text);
		TextView yearView = findViewById(R.id.bp_year_text);
		TextView mediumView = findViewById(R.id.bp_medium_text);
		TextView descView = findViewById(R.id.bp_description_text);

		Intent intent = getIntent();
		String imageUrl = intent.getStringExtra("imageUrl");
		String title = intent.getStringExtra("title");
		String artist = intent.getStringExtra("artist");
		String country = intent.getStringExtra("country");
		String year = intent.getStringExtra("year");
		String medium = intent.getStringExtra("medium");
		String description = intent.getStringExtra("description");



		Intent intent = getIntent();

	}
}