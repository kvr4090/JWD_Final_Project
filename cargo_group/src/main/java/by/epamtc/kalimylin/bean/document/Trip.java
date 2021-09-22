package by.epamtc.kalimylin.bean.document;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entity class Trip
 */
public class Trip implements Serializable {

	private static final long serialVersionUID = 8032484854847350411L;
	
	private int id;
	private int vehicleId;
	private LocalDate startDate;
	private LocalDate finishDate;
	private String note;
	
	public Trip() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(LocalDate finishDate) {
		this.finishDate = finishDate;
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
		result = prime * result + 
				((finishDate == null) ? 0 : finishDate.hashCode());
		result = prime * result + id;
		result = prime * result + 
				((note == null) ? 0 : note.hashCode());
		result = prime * result + 
				((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + vehicleId;
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
		Trip other = (Trip) obj;
		if (finishDate == null) {
			if (other.finishDate != null)
				return false;
		} else if (!finishDate.equals(other.finishDate))
			return false;
		if (id != other.id)
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (vehicleId != other.vehicleId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trip [id=" + id + ", vehicleId=" + vehicleId + ", startDate=" 
				+ startDate + ", finishDate=" + finishDate + ", note=" + note 
				+ "]";
	}
}
