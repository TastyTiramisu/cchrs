package com.corp.cchrs.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;

@Entity
public class Asset {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@OneToMany(mappedBy="asset", fetch = FetchType.LAZY)
	private Set<BorrowHistory> borrowHistories;
	@NotBlank
	@Column(name="asset_name")
	private String name;
	@Column(name = "uuid", columnDefinition = "BINARY(36)")
	private String UUID;
	private String serialNumber;
	@Column(name="note")
	private String desc;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hardware_id")
    private Hardware hardware;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="asset_history_id")
    private AssetHistory assetHistory;
	@Column(nullable = false)
	private Boolean deleted = false;
	
	public Asset() {}
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
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	@PrePersist
	public void initializeUUID() {
		if (UUID == null) {
			UUID = java.util.UUID.randomUUID().toString();
	    }
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Hardware getHardware() {
		return hardware;
	}
	public void setHardware(Hardware hardware) {
		this.hardware = hardware;
	}
	public AssetHistory getAssetHistory() {
		return assetHistory;
	}
	public void setAssetHistory(AssetHistory assetHistory) {
		this.assetHistory = assetHistory;
	}
	public Boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Set<BorrowHistory> getBorrowHistories() {
		return borrowHistories;
	}
	public void setBorrowHistories(Set<BorrowHistory> borrowHistories) {
		this.borrowHistories = borrowHistories;
	}
	@Override
	public String toString() {
		return "Asset [id=" + id + ", name=" + name + ", desc=" + desc + ", deleted=" + deleted + "]";
	}
}
