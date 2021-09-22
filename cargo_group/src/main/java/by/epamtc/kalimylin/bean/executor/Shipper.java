package by.epamtc.kalimylin.bean.executor;

import java.io.Serializable;

/**
 * Entity class Shipper
 */
public class Shipper implements Serializable {

	private static final long serialVersionUID = 7425920393083399415L;
	
	private int id;
	private String name;
	private String email;
	private String contactPersonName;
	private String contactPersonSurname;
	private String contactPhone;
	private String note;
	
	public Shipper() {}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getContactPersonName() {
		return contactPersonName;
	}
	
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	
	public String getContactPersonSurname() {
		return contactPersonSurname;
	}
	
	public void setContactPersonSurname(String contactPersonSurname) {
		this.contactPersonSurname = contactPersonSurname;
	}
	
	public String getContactPhone() {
		return contactPhone;
	}
	
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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
		result = prime * result + ((contactPersonName == null)
				? 0 : contactPersonName.hashCode());
		result = prime * result + ((contactPersonSurname == null)
				? 0 : contactPersonSurname.hashCode());
		result = prime * result + ((contactPhone == null)
				? 0 : contactPhone.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
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
		Shipper other = (Shipper) obj;
		if (contactPersonName == null) {
			if (other.contactPersonName != null)
				return false;
		} else if (!contactPersonName.equals(other.contactPersonName))
			return false;
		if (contactPersonSurname == null) {
			if (other.contactPersonSurname != null)
				return false;
		} else if (!contactPersonSurname.equals(other.contactPersonSurname))
			return false;
		if (contactPhone == null) {
			if (other.contactPhone != null)
				return false;
		} else if (!contactPhone.equals(other.contactPhone))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Shipper [id=" + id + ", name=" + name + ", email=" + email 
				+ ", contactPersonName=" + contactPersonName 
				+ ", contactPersonSurname=" + contactPersonSurname 
				+ ", contactPhone=" + contactPhone + ", note=" + note + "]";
	}
}
