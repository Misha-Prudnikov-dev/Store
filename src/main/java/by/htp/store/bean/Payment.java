package by.htp.store.bean;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable {

	private static final long serialVersionUID = -5498767556978561828L;

	private int id;
	private String typePayment;
	private Date datePayment;
	private String statusPayment;

	public Payment() {

	}

	public Payment(int id, String typePayment, Date datePayment, String statusPayment) {
		this.id = id;
		this.typePayment = typePayment;
		this.datePayment = datePayment;
		this.statusPayment = statusPayment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypePayment() {
		return typePayment;
	}

	public void setTypePayment(String typePayment) {
		this.typePayment = typePayment;
	}

	public Date getDatePayment() {
		return datePayment;
	}

	public void setDatePayment(Date datePayment) {
		this.datePayment = datePayment;
	}

	public String getStatusPayment() {
		return statusPayment;
	}

	public void setStatusPayment(String statusPayment) {
		this.statusPayment = statusPayment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datePayment == null) ? 0 : datePayment.hashCode());
		result = prime * result + id;
		result = prime * result + ((statusPayment == null) ? 0 : statusPayment.hashCode());
		result = prime * result + ((typePayment == null) ? 0 : typePayment.hashCode());
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
		Payment other = (Payment) obj;
		if (datePayment == null) {
			if (other.datePayment != null)
				return false;
		} else if (!datePayment.equals(other.datePayment))
			return false;
		if (id != other.id)
			return false;
		if (statusPayment == null) {
			if (other.statusPayment != null)
				return false;
		} else if (!statusPayment.equals(other.statusPayment))
			return false;
		if (typePayment == null) {
			if (other.typePayment != null)
				return false;
		} else if (!typePayment.equals(other.typePayment))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", typePayment=" + typePayment + ", datePayment=" + datePayment
				+ ", statusPayment=" + statusPayment + "]";
	}

}
