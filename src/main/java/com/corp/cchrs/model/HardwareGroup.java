package com.corp.cchrs.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class HardwareGroup {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@OneToMany(mappedBy="hardwareGroup", fetch = FetchType.LAZY)
	private Set<Hardware> hardwares;
	@NotNull
	@Enumerated(EnumType.STRING)
	private EHardwareGroup hwGroup;
	
	public HardwareGroup() {}
	public HardwareGroup(String hwGroup) {
		setHwGroup(hwGroup);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Set<Hardware> getHardwares() {
		return hardwares;
	}
	public void setHardwares(Set<Hardware> hardwares) {
		this.hardwares = hardwares;
	}
	public String getHwGroup() {
		return hwGroup.name();
	}
	public void setHwGroup(String hwGroup) {
		this.hwGroup = EHardwareGroup.valueOf(hwGroup);
	}
}
