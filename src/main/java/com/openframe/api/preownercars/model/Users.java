package com.openframe.api.preownercars.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
	private String firstName;
    private String lastName;
    private String email;
    
    
	public Long getId() {
		return user_id;
	}
	public void setId(Long id) {
		this.user_id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
