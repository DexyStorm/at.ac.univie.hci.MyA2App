package at.ac.univie.hci.MyA2App;

import android.content.Intent;
import android.os.Bundle;

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
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
		{
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});



		Intent intent = getIntent();

	}
}