package com.claro.WSLigaCampeones.dto;

public class ChampionsLeagueDto {
	
	 private String  exist;
	 private String   name;
	 private String   lastName;
	 private String   cell;
	 private String   departament;
	 private String   address;
	 private String   email;
	 private String   code;
	 private String   description;
	 private String city;
	public String getExist() {
		return exist;
	}
	public void setExist(String exist) {
		this.exist = exist;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getDepartament() {
		return departament;
	}
	public void setDepartament(String departament) {
		this.departament = departament;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "ChampionsLeagueDto [exist=" + exist + ", name=" + name + ", lastName=" + lastName + ", cell=" + cell
				+ ", departament=" + departament + ", address=" + address + ", email=" + email + ", code=" + code
				+ ", description=" + description + ", city=" + city + "]";
	}

	 

}
