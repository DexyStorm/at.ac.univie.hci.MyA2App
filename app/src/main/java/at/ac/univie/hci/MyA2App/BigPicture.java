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

import com.bumptech.glide.Glide;

public class BigPicture extends AppCompatActivity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//EdgeToEdge.enable(this);


		setContentView(R.layout.big_picture);


		ImageView image_view = findViewById(R.id.bp_artwork_image);
		TextView title_view = findViewById(R.id.bp_title_text);
		TextView artist_view = findViewById(R.id.bp_artist_text);
		TextView country_view = findViewById(R.id.bp_country_text);
		TextView year_view = findViewById(R.id.bp_year_text);
		TextView medium_view = findViewById(R.id.bp_medium_text);
		TextView description_view = findViewById(R.id.bp_description_text);
		TextView dimension_view = findViewById(R.id.pb_dimension_text);

		Intent intent = getIntent();
		String imageUrl = intent.getStringExtra("imageUrl");
		String title = intent.getStringExtra("title");
		String artist = intent.getStringExtra("artist");
		String country = intent.getStringExtra("country");
		String year = intent.getStringExtra("year");
		String medium = intent.getStringExtra("medium");
		String description = intent.getStringExtra("description");
		String dimensions = intent.getStringExtra("dimensions");



		Glide.with(this).load(imageUrl).into(image_view);

		title_view.setText(title);
		artist_view.setText(artist);
		country_view.setText(country);
		year_view.setText(year);
		medium_view.setText(medium);
		description_view.setText(description);
		dimension_view.setText(dimensions);




	}
}