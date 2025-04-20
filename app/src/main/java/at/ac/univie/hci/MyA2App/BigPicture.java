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


//		ImageView imageView = findViewById(R.id.pb_image_big);
//		TextView titleView = findViewById(R.id.detail_title);
//		TextView artistView = findViewById(R.id.detail_artist);
//		TextView countryView = findViewById(R.id.detail_country);
//		TextView yearView = findViewById(R.id.detail_year);
//		TextView mediumView = findViewById(R.id.detail_medium);
//		TextView descView = findViewById(R.id.detail_description);
//
//		Intent intent = getIntent();
//		String imageUrl = intent.getStringExtra("imageUrl");
//		String title = intent.getStringExtra("title");
//		String artist = intent.getStringExtra("artist");
//		String country = intent.getStringExtra("country");
//		String year = intent.getStringExtra("year");
//		String medium = intent.getStringExtra("medium");
//		String description = intent.getStringExtra("description");



		Intent intent = getIntent();

	}
}