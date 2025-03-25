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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Room {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@OneToMany(mappedBy="room", fetch = FetchType.LAZY)
	private Set<BorrowHistory> borrowHistories;
	@NotNull
	@Min(1)
	private Integer number;
	@NotNull
	@Enumerated(EnumType.STRING)
	private ERoomType type = ERoomType.valueOf("OFFICE");
	@Column(nullable = false)
	private Boolean deleted = false;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getType() {
		return type.name();
	}
	public void setType(String type) {
		this.type = ERoomType.valueOf(type);
	}
	public Set<BorrowHistory> getBorrowHistories() {
		return borrowHistories;
	}
	public void setBorrowHistories(Set<BorrowHistory> borrowHistories) {
		this.borrowHistories = borrowHistories;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
}
