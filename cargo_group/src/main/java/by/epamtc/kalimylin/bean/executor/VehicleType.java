package by.epamtc.kalimylin.bean.executor;

import java.io.Serializable;

/**
 * Entity class VehicleType
 */
public class VehicleType implements Serializable {
	
	private static final long serialVersionUID = 7456328811579091754L;
	
	private int id;
	private int requirementId;
	private String type;
	private boolean isAvailable;
	
	public VehicleType() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(int requirementId) {
		this.requirementId = requirementId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + (isAvailable ? 1231 : 1237);
		result = prime * result + requirementId;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		VehicleType other = (VehicleType) obj;
		if (id != other.id)
			return false;
		if (isAvailable != other.isAvailable)
			return false;
		if (requirementId != other.requirementId)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VehicleType [id=" + id + ", requirementId=" + requirementId 
				+ ", type=" + type + ", isAvailable=" + isAvailable + "]";
	}
}
