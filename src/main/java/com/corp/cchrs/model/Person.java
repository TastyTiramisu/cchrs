package com.corp.cchrs.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Person {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@OneToMany(mappedBy="person", fetch = FetchType.LAZY)
	private Set<BorrowHistory> borrowHistories;
	@NotBlank
	private String name;
	@NotBlank
	private String surname;
	@NotBlank
	@Email
	@Column(unique=true)
    private String email;
	@NotNull
	@Enumerated(EnumType.STRING)
	private ERole role;
	@NotBlank
	private String password;
	@Column(nullable = true)
	private Boolean enabled = true;
	
	public Person() {}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role.name();
	}
	public void setRole(String role) {
		this.role = ERole.valueOf(role);
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<BorrowHistory> getBorrowHistories() {
		return borrowHistories;
	}
	public void setBorrowHistories(Set<BorrowHistory> borrowHistories) {
		this.borrowHistories = borrowHistories;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		String msg = name + " " + surname + " with role " + role;
		msg += enabled ? " is enabled" : " is disabled";
		return msg;
	}
}
