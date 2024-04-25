package play_store;

import java.sql.Date;

public class App {
    private String name;
    private String description;
    private Date releaseDate;
    private String version;
    private double ratings;
    private String category; // New attribute for category ID
    private boolean visible;

    public App( String name, String description, Date releaseDate, String version, double ratings, String category, boolean visible ) {
     
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.version = version;
        this.ratings = ratings;
        this.category = category; // Assign category ID
        this.visible = visible;
        
    }


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public double getRatings() {
		return ratings;
	}

	public void setRatings(double ratings) {
		this.ratings = ratings;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}


    // Getter and setter methods for all attributes


}
