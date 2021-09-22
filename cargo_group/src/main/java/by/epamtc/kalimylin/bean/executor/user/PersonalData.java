package by.epamtc.kalimylin.bean.executor.user;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entity class PersonalData
 */
public class PersonalData implements Serializable {

	private static final long serialVersionUID = -8668770082076890440L;
	
	private int userId;
	private String post;
	private String name;
	private String surname;
	private LocalDate birthdate;
	private String passportNumber;
	private String passportIdentificationNumber;
	private LocalDate passportDateOfExpiry;
	private LocalDate passportDateOfIssue;
	private String passportAuthority;	
	private String driveLicense;
	private LocalDate recruitmentDate;
	private LocalDate terminationDate;
	
	public PersonalData() {}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getPassportIdentificationNumber() {
		return passportIdentificationNumber;
	}

	public void setPassportIdentificationNumber(
			String passportIdentificationNumber) {
		this.passportIdentificationNumber = passportIdentificationNumber;
	}

	public LocalDate getPassportDateOfExpiry() {
		return passportDateOfExpiry;
	}

	public void setPassportDateOfExpiry(LocalDate passportDateOfExpiry) {
		this.passportDateOfExpiry = passportDateOfExpiry;
	}

	public LocalDate getPassportDateOfIssue() {
		return passportDateOfIssue;
	}

	public void setPassportDateOfIssue(LocalDate passportDateOfIssue) {
		this.passportDateOfIssue = passportDateOfIssue;
	}

	public String getPassportAuthority() {
		return passportAuthority;
	}

	public void setPassportAuthority(String passportAuthority) {
		this.passportAuthority = passportAuthority;
	}

	public String getDriveLicense() {
		return driveLicense;
	}

	public void setDriveLicense(String driveLicense) {
		this.driveLicense = driveLicense;
	}

	public LocalDate getRecruitmentDate() {
		return recruitmentDate;
	}

	public void setRecruitmentDate(LocalDate recruitmentDate) {
		this.recruitmentDate = recruitmentDate;
	}

	public LocalDate getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(LocalDate terminationDate) {
		this.terminationDate = terminationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + 
				((birthdate == null) ? 0 : birthdate.hashCode());
		result = prime * result + 
				((driveLicense == null) ? 0 : driveLicense.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((passportAuthority == null) 
				? 0 : passportAuthority.hashCode());
		result = prime * result + ((passportDateOfExpiry == null) 
				? 0 : passportDateOfExpiry.hashCode());
		result = prime * result + ((passportDateOfIssue == null) 
				? 0 : passportDateOfIssue.hashCode());
		result = prime * result + ((passportIdentificationNumber == null)
				? 0 : passportIdentificationNumber.hashCode());
		result = prime * result + ((passportNumber == null)
				? 0 : passportNumber.hashCode());
		result = prime * result + ((post == null) ? 0 : post.hashCode());
		result = prime * result + ((recruitmentDate == null)
				? 0 : recruitmentDate.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((terminationDate == null)
				? 0 : terminationDate.hashCode());
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
		PersonalData other = (PersonalData) obj;
		if (birthdate == null) {
			if (other.birthdate != null)
				return false;
		} else if (!birthdate.equals(other.birthdate))
			return false;
		if (driveLicense == null) {
			if (other.driveLicense != null)
				return false;
		} else if (!driveLicense.equals(other.driveLicense))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (passportAuthority == null) {
			if (other.passportAuthority != null)
				return false;
		} else if (!passportAuthority.equals(other.passportAuthority))
			return false;
		if (passportDateOfExpiry == null) {
			if (other.passportDateOfExpiry != null)
				return false;
		} else if (!passportDateOfExpiry.equals(other.passportDateOfExpiry))
			return false;
		if (passportDateOfIssue == null) {
			if (other.passportDateOfIssue != null)
				return false;
		} else if (!passportDateOfIssue.equals(other.passportDateOfIssue))
			return false;
		if (passportIdentificationNumber == null) {
			if (other.passportIdentificationNumber != null)
				return false;
		} else if (!passportIdentificationNumber.equals(
				other.passportIdentificationNumber))
			return false;
		if (passportNumber == null) {
			if (other.passportNumber != null)
				return false;
		} else if (!passportNumber.equals(other.passportNumber))
			return false;
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!post.equals(other.post))
			return false;
		if (recruitmentDate == null) {
			if (other.recruitmentDate != null)
				return false;
		} else if (!recruitmentDate.equals(other.recruitmentDate))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (terminationDate == null) {
			if (other.terminationDate != null)
				return false;
		} else if (!terminationDate.equals(other.terminationDate))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonalData [userId=" + userId + ", post=" + post + ", name=" 
				+ name + ", surname=" + surname + ", birthdate=" + birthdate 
				+ ", passportNumber=" + passportNumber 
				+ ", passportIdentificationNumber=" 
				+ passportIdentificationNumber + ", passportDateOfExpiry=" 
				+ passportDateOfExpiry + ", passportDateOfIssue=" 
				+ passportDateOfIssue + ", passportAuthority=" 
				+ passportAuthority + ", driveLicense=" + driveLicense 
				+ ", recruitmentDate=" + recruitmentDate + ", terminationDate="
				+ terminationDate + "]";
	}	
}