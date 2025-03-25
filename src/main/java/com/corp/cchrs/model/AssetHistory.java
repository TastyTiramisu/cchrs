package com.corp.cchrs.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.corp.cchrs.utils.Utils;

@Entity
public class AssetHistory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@OneToMany(mappedBy="assetHistory", fetch = FetchType.LAZY)
	private Set<Asset> assets;
	@NotNull
	private LocalDateTime purchaseDate;
	private LocalDateTime warrantyDate;
	private LocalDateTime disposalDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Set<Asset> getAssets() {
		return assets;
	}
	public void setAssets(Set<Asset> assets) {
		this.assets = assets;
	}
	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(LocalDateTime localDate) {
		this.purchaseDate = localDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = Utils.dateConventer(purchaseDate);
	}
	public LocalDateTime getWarrantyDate() {
		return warrantyDate;
	}
	public void setWarrantyDate(LocalDateTime warrantyDate) {
		this.warrantyDate = warrantyDate;
	}
	public void setWarrantyDate(String warrantyDate) {
		this.warrantyDate = Utils.dateConventer(warrantyDate);
	}
	public LocalDateTime getDisposalDate() {
		return disposalDate;
	}
	public void setDisposalDate(LocalDateTime disposalDate) {
		this.disposalDate = disposalDate;
	}
	public void setDisposalDate(String disposalDate) {
		this.disposalDate = Utils.dateConventer(disposalDate);
	}
}
