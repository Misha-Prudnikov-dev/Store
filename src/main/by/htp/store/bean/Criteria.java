package by.htp.store.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Criteria implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> criteria = new HashMap<String, Object>();

	public Map<String, Object> getCriteria() {
		return criteria;
	}

	public void setCriteria(Map<String, Object> criteria) {
		this.criteria = criteria;
	}

	public void add(String searchCriteria, Object value) {
		criteria.put(searchCriteria, value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((criteria == null) ? 0 : criteria.hashCode());
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
		Criteria other = (Criteria) obj;
		if (criteria == null) {
			if (other.criteria != null)
				return false;
		} else if (!criteria.equals(other.criteria))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Criteria [ criteria=" + criteria + "]";
	}

}
