package at.ac.univie.hci.MyA2App;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import com.bumptech.glide.Glide;


public class ArtworkAdapter extends ArrayAdapter<Artwork>
{


	private final Context context;
	private final List<Artwork> artworks;

	public ArtworkAdapter(Context context, List<Artwork> artworks)
	{
		super(context, 0, artworks);
		this.context = context;
		this.artworks = artworks;

	}


	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.list_entry, parent, false);
		}

		Artwork artwork = artworks.get(position);

		TextView title_view = convertView.findViewById(R.id.list_title_text);
		TextView artist_view = convertView.findViewById(R.id.list_artist_text);
		ImageView image_view = convertView.findViewById(R.id.list_artwork_image);

		title_view.setText(artwork.title);
		artist_view.setText(artwork.artist_name);

		// Image loading (replace this with real image URL logic)
		String imageUrl = "https://www.artic.edu/iiif/2/" + artwork.image_id + "/full/843,/0/default.jpg";
		Glide.with(context).load(imageUrl).into(image_view);

		return convertView;
	}




}
