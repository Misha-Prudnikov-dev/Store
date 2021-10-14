package by.htp.store.bean;

import java.io.Serializable;
import java.util.Date;

public class Delivery implements Serializable {


	private static final long serialVersionUID = 1763245031719424001L;
	
	private int id;
	private Date dateDelivery;
	private String typeDelivery;
	private String statusDelivery;

	public Delivery() {

	}

	public Delivery(int id, Date dateDelivery, String typeDelivery, String statusDelivery) {

		this.id = id;
		this.dateDelivery = dateDelivery;
		this.typeDelivery = typeDelivery;
		this.statusDelivery = statusDelivery;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateDelivery() {
		return dateDelivery;
	}

	public void setDateDelivery(Date dateDelivery) {
		this.dateDelivery = dateDelivery;
	}

	public String getTypeDelivery() {
		return typeDelivery;
	}

	public void setTypeDelivery(String typeDelivery) {
		this.typeDelivery = typeDelivery;
	}

	public String getStatusDelivery() {
		return statusDelivery;
	}

	public void setStatusDelivery(String statusDelivery) {
		this.statusDelivery = statusDelivery;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateDelivery == null) ? 0 : dateDelivery.hashCode());
		result = prime * result + id;
		result = prime * result + ((statusDelivery == null) ? 0 : statusDelivery.hashCode());
		result = prime * result + ((typeDelivery == null) ? 0 : typeDelivery.hashCode());
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
		Delivery other = (Delivery) obj;
		if (dateDelivery == null) {
			if (other.dateDelivery != null)
				return false;
		} else if (!dateDelivery.equals(other.dateDelivery))
			return false;
		if (id != other.id)
			return false;
		if (statusDelivery == null) {
			if (other.statusDelivery != null)
				return false;
		} else if (!statusDelivery.equals(other.statusDelivery))
			return false;
		if (typeDelivery == null) {
			if (other.typeDelivery != null)
				return false;
		} else if (!typeDelivery.equals(other.typeDelivery))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Delivery [id=" + id + ", dateDelivery=" + dateDelivery + ", typeDelivery=" + typeDelivery
				+ ", statusDelivery=" + statusDelivery + "]";
	}
	

}
