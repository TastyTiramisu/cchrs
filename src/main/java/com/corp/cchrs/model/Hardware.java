package com.corp.cchrs.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Hardware {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@OneToMany(mappedBy="hardware", fetch = FetchType.LAZY)
	private Set<Asset> assets;
	@NotNull
	@Enumerated(EnumType.STRING)
	private EHardwareType type;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hardware_group_id")
	private HardwareGroup hardwareGroup;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type.name();
	}
	public void setType(String type) {
		this.type = EHardwareType.valueOf(type);
	}
	public Set<Asset> getAssets() {
		return assets;
	}
	public void setAssets(Set<Asset> assets) {
		this.assets = assets;
	}
	public HardwareGroup getHardwareGroup() {
		return hardwareGroup;
	}
	public void setHardwareGroup(HardwareGroup hardwareGroup) {
		this.hardwareGroup = hardwareGroup;
	}
}
