package by.htp.store.bean;

import java.io.Serializable;

public class ProductSize implements Serializable {

	private static final long serialVersionUID = 7678402838971199139L;
	
	private int id;
	private int quantity;
	private String size;

	public ProductSize() {

	}

	public ProductSize(int id,int quantity, String size) {
		this.id = id;
		this.quantity = quantity;
		this.size = size;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + quantity;
		result = prime * result + ((size == null) ? 0 : size.hashCode());
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
		ProductSize other = (ProductSize) obj;
		if (id != other.id)
			return false;
		if (quantity != other.quantity)
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductSize [id=" + id + ", quantity=" + quantity + ", size=" + size + "]";
	}
	
}
