package com.corp.cchrs.model;

import java.time.LocalDate;
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
	private LocalDate purchaseDate;
	private LocalDate warrantyDate;
	private LocalDate disposalDate;
	
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
	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(LocalDate localDate) {
		this.purchaseDate = localDate;
	}
	public void setPurchaseDate(String localDate) {
		this.purchaseDate = Utils.dateConventer(localDate);
	}
	public LocalDate getWarrantyDate() {
		return warrantyDate;
	}
	public void setWarrantyDate(LocalDate warrantyDate) {
		this.warrantyDate = warrantyDate;
	}
	public void setWarrantyDate(String warrantyDate) {
		this.warrantyDate = Utils.dateConventer(warrantyDate);
	}
	public LocalDate getDisposalDate() {
		return disposalDate;
	}
	public void setDisposalDate(LocalDate disposalDate) {
		this.disposalDate = disposalDate;
	}
	public void setDisposalDate(String disposalDate) {
		this.disposalDate = Utils.dateConventer(disposalDate);
	}
}
