package by.htp.store.bean;

import java.io.Serializable;

public class Manufacturer implements Serializable {

	private static final long serialVersionUID = 2909108818951840837L;

	private int id;
	private String title;
	private String description;
	private String country;

	public Manufacturer() {

	}

	public Manufacturer(int id, String title, String description, String country) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.country = country;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + id;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Manufacturer other = (Manufacturer) obj;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title == null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (description == null) {
			if (other.description == null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (country == null) {
			if (other.country == null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		return true;

	}

	@Override
	public String toString() {
		return "Manufacturer [ id=" + id + ", title=" + title + ", description=" + description + ", country=" + country
				+ " ] ";
	}
}
