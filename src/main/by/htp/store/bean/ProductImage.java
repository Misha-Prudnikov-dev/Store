package by.htp.store.bean;

import java.io.Serializable;

public class ProductImage implements Serializable {

	private static final long serialVersionUID = -5823807828056492660L;

	private int id;
	private String image;

	public ProductImage() {

	}

	public ProductImage(int id, String image) {
		this.id = id;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductImage other = (ProductImage) obj;
		if (id != other.id)
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "ProductImage [id=" + id + ", image=" + image + "]";
	}

}
