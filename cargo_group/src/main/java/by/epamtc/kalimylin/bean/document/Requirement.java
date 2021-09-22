package by.epamtc.kalimylin.bean.document;

import java.io.Serializable;

/**
 * Entity class Requirement
 * This class is used for represent of vehicle data, 
 * also for freight transport requirements.
 */
public class Requirement implements Serializable {

	private static final long serialVersionUID = 1610668894581780307L;
	
	private int id;
	private float weight;
	private float volume;
	private int palletsQuantity;
	private float maxLength;
	private float maxWidth;
	private float maxHeight;
	
	public Requirement() {}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public float getVolume() {
		return volume;
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	public int getPalletsQuantity() {
		return palletsQuantity;
	}
	
	public void setPalletsQuantity(int palletsQuantity) {
		this.palletsQuantity = palletsQuantity;
	}
	
	public float getMaxLength() {
		return maxLength;
	}
	
	public void setMaxLength(float maxLength) {
		this.maxLength = maxLength;
	}
	
	public float getMaxWidth() {
		return maxWidth;
	}
	
	public void setMaxWidth(float maxWidth) {
		this.maxWidth = maxWidth;
	}
	
	public float getMaxHeight() {
		return maxHeight;
	}
	
	public void setMaxHeight(float maxHeight) {
		this.maxHeight = maxHeight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + Float.floatToIntBits(maxHeight);
		result = prime * result + Float.floatToIntBits(maxLength);
		result = prime * result + Float.floatToIntBits(maxWidth);
		result = prime * result + palletsQuantity;
		result = prime * result + Float.floatToIntBits(volume);
		result = prime * result + Float.floatToIntBits(weight);
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
		Requirement other = (Requirement) obj;
		if (id != other.id)
			return false;
		if (Float.floatToIntBits(maxHeight) 
				!= Float.floatToIntBits(other.maxHeight))
			return false;
		if (Float.floatToIntBits(maxLength) 
				!= Float.floatToIntBits(other.maxLength))
			return false;
		if (Float.floatToIntBits(maxWidth) 
				!= Float.floatToIntBits(other.maxWidth))
			return false;
		if (palletsQuantity != other.palletsQuantity)
			return false;
		if (Float.floatToIntBits(volume) != Float.floatToIntBits(other.volume))
			return false;
		if (Float.floatToIntBits(weight) != Float.floatToIntBits(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Requirement [id=" + id + ", weight=" + weight + ", volume=" 
				+ volume + ", palletsQuantity=" + palletsQuantity 
				+ ", maxLength=" + maxLength + ", maxWidth=" + maxWidth 
				+ ", maxHeight=" + maxHeight + "]";
	}
}
