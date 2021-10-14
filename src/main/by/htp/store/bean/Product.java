package by.htp.store.bean;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {

	private static final long serialVersionUID = -8605672012523574042L;

	private int id;
	private String title;
	private String description;
	private String color;
	private int year;
	private int quantity;
	private double price;
	private double rating;
	private Date dateAddProduct;
	private ProductImage productImage;

	private Manufacturer manufacturer;
	private Subcategory subcategory;

	public Product() {

	}

	public Product(int id, String title, String description, String color, int year, int quantity, double price,
			double rating, Date dateAddProduct, Manufacturer manufacturer, Subcategory subcategory,
			ProductImage productImage) {

		this.id = id;
		this.title = title;
		this.description = description;
		this.color = color;
		this.price = price;
		this.quantity = quantity;
		this.year = year;
		this.rating = rating;
		this.dateAddProduct = dateAddProduct;
		this.manufacturer = manufacturer;
		this.subcategory = subcategory;
		this.productImage = productImage;

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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Date getDateAddProduct() {
		return dateAddProduct;
	}

	public void setDateAddProduct(Date dateAddProduct) {
		this.dateAddProduct = dateAddProduct;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public ProductImage getProductImage() {
		return productImage;
	}

	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateAddProduct == null) ? 0 : dateAddProduct.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((productImage == null) ? 0 : productImage.hashCode());
		result = prime * result + quantity;
		temp = Double.doubleToLongBits(rating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((subcategory == null) ? 0 : subcategory.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + year;
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
		Product other = (Product) obj;
		if (dateAddProduct == null) {
			if (other.dateAddProduct != null)
				return false;
		} else if (!dateAddProduct.equals(other.dateAddProduct))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.equals(other.manufacturer))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;

		if (productImage == null) {
			if (other.productImage != null)
				return false;
		} else if (!productImage.equals(other.productImage))
			return false;
		if (quantity != other.quantity)
			return false;
		if (Double.doubleToLongBits(rating) != Double.doubleToLongBits(other.rating))
			return false;
		if (subcategory == null) {
			if (other.subcategory != null)
				return false;
		} else if (!subcategory.equals(other.subcategory))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", description=" + description + ", year=" + year
				+ ", quantity=" + quantity + ", price=" + price + ", rating=" + rating + ", dateAddProduct="
				+ dateAddProduct + ", productImage=" + productImage + ", manufacturer=" + manufacturer
				+ ", subcategory=" + subcategory + "]";
	}

}
