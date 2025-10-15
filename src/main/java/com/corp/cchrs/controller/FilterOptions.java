package com.corp.cchrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.corp.cchrs.model.EHardwareGroup;
import com.corp.cchrs.service.HardwareService;
import com.corp.cchrs.utils.Utils;

public class FilterOptions {
	
	@Autowired
	HardwareService hService;

	//attributes to filter assets using group and type droplist in frontend.
	protected void provideFilterOptions(Model model) {
		EHardwareGroup[] groupsOfAssets = EHardwareGroup.values();
		int numberOfGroups = groupsOfAssets.length;
		
		for(int i = 1; i < numberOfGroups + 1; i++) {
			model.addAttribute("hardwareType" + i, Utils.getFormattedNames(hService.getTypesByGroup(i)));
		}
		model.addAttribute("hardwareGroups", Utils.getFormattedNames(groupsOfAssets));
		model.addAttribute("hardwareTypes", Utils.getFormattedNames(hService.getAllTypes()));
	}
}
