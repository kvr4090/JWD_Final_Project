package by.epamtc.kalimylin.bean.executor.user;

import java.io.Serializable;

/**
 * Entity class User
 */
public class User implements Serializable {

	private static final long serialVersionUID = 5858185998531867228L;
	
	private int id;
	private int vehicleId;
	private String login;
	private String correctHash;
	private String email;
	private String phone;
	private Role role;
	private boolean isActive;
	
	public User() {}

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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCorrectHash() {
		return correctHash;
	}

	public void setCorrectHash(String correctHash) {
		this.correctHash = correctHash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + 
				((correctHash == null) ? 0 : correctHash.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		User other = (User) obj;
		if (correctHash == null) {
			if (other.correctHash != null)
				return false;
		} else if (!correctHash.equals(other.correctHash))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (isActive != other.isActive)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (role != other.role)
			return false;
		if (vehicleId != other.vehicleId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", vehicleId=" + vehicleId + ", login=" 
				+ login + ", correctHash=" + correctHash + ", email=" + email 
				+ ", phone=" + phone + ", role=" + role + ", isActive=" 
				+ isActive + "]";
	}
}
