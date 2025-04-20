package at.ac.univie.hci.MyA2App;

public class Artwork
{
	public String artist_name = "";
	public String title = "";
	public String country = "";
	public String medium = "";
	public String description = "";
	public String year = "";
	public String image_id = "";


	public Artwork(String artist_name, String title, String country, String year, String medium, String description, String image_id)
	{

		this.artist_name = artist_name;
		this.title = title;
		this.country = country;
		this.year = year;
		this.medium = medium;
		this.description = description;
		this.image_id = image_id;

	}

	@Override
	public String toString()
	{
		return "NAME:" + this.artist_name + " TITLE: " + this.title;
	}
}
