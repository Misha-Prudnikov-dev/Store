package by.htp.store.bean;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {

	private static final long serialVersionUID = -739781258745088138L;

	private int userId;
	private String userName;
	private String text;
	private String type;
	private byte rating;
	private Date dateAddReview;

	public Review() {

	}

	public Review(int userId, String userName, String text, String type, byte rating, Date dateAddReview) {
		this.userId = userId;
		this.userName = userName;
		this.text = text;
		this.type = type;
		this.rating = rating;
		this.dateAddReview = dateAddReview;

	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte getRating() {
		return rating;
	}

	public void setRating(byte rating) {
		this.rating = rating;
	}

	public Date getDateAddReview() {
		return dateAddReview;
	}

	public void setDateAddReview(Date dateAddReview) {
		this.dateAddReview = dateAddReview;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateAddReview == null) ? 0 : dateAddReview.hashCode());
		result = prime * result + rating;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + userId;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		Review other = (Review) obj;
		if (dateAddReview == null) {
			if (other.dateAddReview != null)
				return false;
		} else if (!dateAddReview.equals(other.dateAddReview))
			return false;
		if (rating != other.rating)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Review [userId=" + userId + ", userName=" + userName + ", text=" + text + ", type=" + type + ", rating="
				+ rating + ", dateAddReview=" + dateAddReview + "]";
	}

}
