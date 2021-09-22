package by.epamtc.kalimylin.bean.document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity class Order
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 4605216051839368848L;
	
	private int id;
	private int userId;
	private int shipperId;
	private int requirementId;
	private int tripId;
	private LocalDate date;
	private BigDecimal price;
	private String routeStartPoint;
	private String routeEndPoint;
	private int distance;
	private String note;
	
	public Order() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getShipperId() {
		return shipperId;
	}

	public void setShipperId(int shipperId) {
		this.shipperId = shipperId;
	}

	public int getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(int requirementId) {
		this.requirementId = requirementId;
	}

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getRouteStartPoint() {
		return routeStartPoint;
	}

	public void setRouteStartPoint(String routeStartPoint) {
		this.routeStartPoint = routeStartPoint;
	}

	public String getRouteEndPoint() {
		return routeEndPoint;
	}

	public void setRouteEndPoint(String routeEndPoint) {
		this.routeEndPoint = routeEndPoint;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + distance;
		result = prime * result + id;
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + requirementId;
		result = prime * result + 
				((routeEndPoint == null) ? 0 : routeEndPoint.hashCode());
		result = prime * result + 
				((routeStartPoint == null) ? 0 : routeStartPoint.hashCode());
		result = prime * result + shipperId;
		result = prime * result + tripId;
		result = prime * result + userId;
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
		Order other = (Order) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (distance != other.distance)
			return false;
		if (id != other.id)
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (requirementId != other.requirementId)
			return false;
		if (routeEndPoint == null) {
			if (other.routeEndPoint != null)
				return false;
		} else if (!routeEndPoint.equals(other.routeEndPoint))
			return false;
		if (routeStartPoint == null) {
			if (other.routeStartPoint != null)
				return false;
		} else if (!routeStartPoint.equals(other.routeStartPoint))
			return false;
		if (shipperId != other.shipperId)
			return false;
		if (tripId != other.tripId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", shipperId=" 
				+ shipperId + ", requirementId=" + requirementId + ", tripId=" 
				+ tripId + ", date=" + date + ", price=" + price 
				+ ", routeStartPoint=" + routeStartPoint + ", routeEndPoint=" 
				+ routeEndPoint + ", distance=" + distance + ", note=" + note 
				+ "]";
	}	
}
