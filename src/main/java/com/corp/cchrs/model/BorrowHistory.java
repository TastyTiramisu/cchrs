package com.corp.cchrs.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.corp.cchrs.utils.Utils;

@Entity
public class BorrowHistory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	private LocalDateTime borrowDate = LocalDateTime.now();
	private LocalDateTime borrowUntil;
	@Column(name="note")
	private String desc = "";
	private LocalDateTime returnBackDate;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="asset_id", nullable=false)
	private Asset asset;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id", nullable=false)
	private Person person;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id", nullable=false)
	private Room room;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDateTime getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(LocalDateTime borrowDate) {
		this.borrowDate = borrowDate;
	}
	public LocalDateTime getBorrowUntil() {
		return borrowUntil;
	}
	/* borrowUntil must be later than day when it was borrowed
	 * borrowUntil cannot be set as the day that is right now*/
	public void setBorrowUntil(LocalDateTime borrowUntil) {
		if(borrowUntil == null) return;
		if(borrowUntil.isAfter(borrowDate) && borrowUntil.isAfter(LocalDateTime.now()))
			this.borrowUntil = borrowUntil;
	}
	public void setBorrowUntil(String borrowUntil) {
		setBorrowUntil(Utils.dateConventer(borrowUntil));
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Asset getAsset() {
		return asset;
	}
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	public LocalDateTime getReturnBackDate() {
		return returnBackDate;
	}
	/*Person has to return asset by himself physically. If the person returned asset back, 
	 * admin should confirm it in database by changing this date so it's no longer null.*/
	public void setReturnBackDate(LocalDateTime returnBackDate) {
		this.returnBackDate = returnBackDate;
	}
	public void setReturnBackDate(String returnBackDate) {
		this.returnBackDate = Utils.dateConventer(returnBackDate);
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
}
