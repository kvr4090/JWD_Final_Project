package by.epamtc.kalimylin.bean.executor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entity class Vehicle
 * This class is used for trucks and trailers
 */
public class Vehicle implements Serializable {
	
	private static final long serialVersionUID = -3354763934342963707L;

	private int id;
	private int typeId;
	private int trailerId;
	private String licensePlate;
	private int odometer;
	private boolean isAvailable;
	private String driveLicense;
	private String model;
	private String brand;
	private LocalDate yearOfIssue;
	private String color;
	private String note;
	
	public Vehicle() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getTrailerId() {
		return trailerId;
	}

	public void setTrailerId(int trailerId) {
		this.trailerId = trailerId;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public int getOdometer() {
		return odometer;
	}

	public void setOdometer(int odometer) {
		this.odometer = odometer;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getDriveLicense() {
		return driveLicense;
	}

	public void setDriveLicense(String driveLicense) {
		this.driveLicense = driveLicense;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public LocalDate getYearOfIssue() {
		return yearOfIssue;
	}

	public void setYearOfIssue(LocalDate yearOfIssue) {
		this.yearOfIssue = yearOfIssue;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + 
				((driveLicense == null) ? 0 : driveLicense.hashCode());
		result = prime * result + id;
		result = prime * result + (isAvailable ? 1231 : 1237);
		result = prime * result + 
				((licensePlate == null) ? 0 : licensePlate.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + odometer;
		result = prime * result + trailerId;
		result = prime * result + typeId;
		result = prime * result + 
				((yearOfIssue == null) ? 0 : yearOfIssue.hashCode());
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
		Vehicle other = (Vehicle) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (driveLicense == null) {
			if (other.driveLicense != null)
				return false;
		} else if (!driveLicense.equals(other.driveLicense))
			return false;
		if (id != other.id)
			return false;
		if (isAvailable != other.isAvailable)
			return false;
		if (licensePlate == null) {
			if (other.licensePlate != null)
				return false;
		} else if (!licensePlate.equals(other.licensePlate))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (odometer != other.odometer)
			return false;
		if (trailerId != other.trailerId)
			return false;
		if (typeId != other.typeId)
			return false;
		if (yearOfIssue == null) {
			if (other.yearOfIssue != null)
				return false;
		} else if (!yearOfIssue.equals(other.yearOfIssue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", typeId=" + typeId + ", trailerId=" 
				+ trailerId + ", licensePlate=" + licensePlate + ", odometer=" 
				+ odometer + ", isAvailable=" + isAvailable + ", driveLicense="
				+ driveLicense + ", model=" + model + ", brand=" + brand 
				+ ", yearOfIssue=" + yearOfIssue + ", color=" + color 
				+ ", note=" + note + "]";
	}
}