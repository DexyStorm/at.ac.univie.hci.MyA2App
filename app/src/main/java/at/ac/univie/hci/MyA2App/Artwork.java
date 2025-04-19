package at.ac.univie.hci.MyA2App;

public class Artwork
{
	public String artist_name = "";
	public String title = "";
	public String country = "";
	public String medium = "";
	public String alt_text = "";
	public String description = "";
	public String year = "";


	public Artwork(String artist_name, String title, String country, String year, String medium, String alt_text, String description)
	{

		this.artist_name = artist_name;
		this.title = title;
		this.country = country;
		this.year = year;
		this.medium = medium;
		this.alt_text = alt_text;
		this.description = description;

	}

	@Override
	public String toString()
	{
		return "NAME:" + this.artist_name + " TITLE: " + this.title;
	}
}
