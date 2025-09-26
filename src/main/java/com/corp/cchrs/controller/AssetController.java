package com.corp.cchrs.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.corp.cchrs.model.Asset;
import com.corp.cchrs.model.AssetHistory;
import com.corp.cchrs.model.BorrowHistory;
import com.corp.cchrs.model.EHardwareGroup;
import com.corp.cchrs.model.Person;
import com.corp.cchrs.model.Room;
import com.corp.cchrs.service.AssetHistoryService;
import com.corp.cchrs.service.AssetService;
import com.corp.cchrs.service.BorrowHistoryService;
import com.corp.cchrs.service.HardwareService;
import com.corp.cchrs.service.PersonService;
import com.corp.cchrs.service.RoomService;
import com.corp.cchrs.utils.Utils;

@Controller
public class AssetController {
	
	final public static int PAGE_SIZE = 3;

	@Autowired
	AssetService service;

	@Autowired
	HardwareService hService;
	
	@Autowired
	AssetHistoryService aHService;
	
	@Autowired
	PersonService pService;
	
	@Autowired
	RoomService rService;
	
	@Autowired
	BorrowHistoryService bHService;
	
	@GetMapping("/")
	public String redirectFromRoot() {
		return "redirect:/assets";
	}
	
	//attributes to filter assets using group and type droplist in frontend.
	private void provideFilterOptions(Model model) {
		EHardwareGroup[] groupsOfAssets = EHardwareGroup.values();
		int numberOfGroups = groupsOfAssets.length;
		
		for(int i = 1; i < numberOfGroups + 1; i++) {
			model.addAttribute("hardwareType" + i, Utils.getFormattedNames(hService.getTypesByGroup(i)));
		}
		model.addAttribute("hardwareGroups", Utils.getFormattedNames(groupsOfAssets));
		model.addAttribute("hardwareTypes", Utils.getFormattedNames(hService.getAllTypes()));
	}

	@GetMapping("/assets")
	public String getAssets(Model model, @RequestParam(required = false) Integer group, @RequestParam(required = false) Integer type, 
			@RequestParam(defaultValue = "0") int page) throws Exception {  
	    
		provideFilterOptions(model);
		Pageable paging = PageRequest.of(page, PAGE_SIZE);
		
		model.addAttribute("group", group);
		model.addAttribute("type", type);
		model.addAttribute("currentPage", page + 1);
		model.addAttribute("totalPages", service.findByGroupAndType(type, group, paging).getTotalPages());
		model.addAttribute("assets", service.findByGroupAndType(type, group, paging));
		model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		
		return "showassets";
	}
	
	@GetMapping("/my/assets")
	public String getLoggedInPersonsAssets(Model model, @RequestParam(required = false) Integer group,
			@RequestParam(required = false) Integer type) throws Exception {
		
		provideFilterOptions(model);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		final List<Asset> assetsByPerson = bHService.getAssetsByPerson(pService.getPersonByEmail(auth.getName()).getId());
		model.addAttribute("assets", service.getAssets(assetsByPerson, group, type));
		model.addAttribute("role", auth.getAuthorities().toString());
		
		return "showassets";
	}

	@GetMapping("/assets/person/{id}")
	public String getPersonsAssets(Model model, @Valid @PathVariable Integer id,
			@RequestParam(required = false) Integer group, @RequestParam(required = false) Integer type, 
			@RequestParam(defaultValue = "0") int page) throws Exception {
		
		provideFilterOptions(model);
		Pageable paging = PageRequest.of(page, PAGE_SIZE);
		
		model.addAttribute("group", group);
		model.addAttribute("type", type);
		model.addAttribute("currentPage", page + 1);
		model.addAttribute("totalPages", service.findByGroupAndType(type, group, paging).getTotalPages());
		model.addAttribute("assets", bHService.getAssets(id, group, type, paging)); //in progress
		model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		
		return "showassets";
	}
	
	@GetMapping("/deleted/assets")
	public String getDeletedAssets(Model model, @RequestParam(required = false) Integer group,
			@RequestParam(required = false) Integer type) throws Exception {
		
		provideFilterOptions(model);
		model.addAttribute("assets", service.getAssets(group, type, true));
		model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		
		return "showdeletedassets";
	}
	
	@GetMapping("/asset/add")
	public String createAsset(Model model) {
		model.addAttribute("asset", new Asset());
		model.addAttribute("hardwareTypes", Utils.getFormattedNames(hService.getAllTypes()));
		model.addAttribute("people", pService.getPeople());
		model.addAttribute("rooms", rService.getRooms());
		model.addAttribute("assetHistory", new AssetHistory());
		model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		return "addasset";
	}

	@PostMapping("/assets")
	public String saveAsset(@Valid Asset asset,
			@RequestParam("assetHistory.purchaseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime purchaseDate,
			@RequestParam(value = "assetHistory.warrantyDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime warrantyDate,
			@RequestParam @Valid String type,
			@RequestParam @Valid Person person,
			@RequestParam @Valid Room room) {
		AssetHistory assetHistory = new AssetHistory();
		assetHistory.setPurchaseDate(purchaseDate);
		assetHistory.setWarrantyDate(warrantyDate);
		aHService.saveAssetHistory(assetHistory);
		
		asset.setId(null);
		asset.setHardware(hService.getHardwareByType(Utils.getOriginalName(type)));
		asset.setAssetHistory(assetHistory);
		service.saveAsset(asset);
		
		BorrowHistory borrowHistory = new BorrowHistory();
		borrowHistory.setPerson(person);
		borrowHistory.setRoom(room);
		borrowHistory.setAsset(asset);
		borrowHistory.setBorrowDate(LocalDateTime.now());
		bHService.saveBorrowHistory(borrowHistory);
		
		return "redirect:/assets";
	}

	//Asset won't be deleted physically, method only change boolean 'deleted' to true.
	@GetMapping("asset/delete/{id}")
	public String deleteAsset(@Valid @PathVariable Integer id) {
		service.deleteAsset(id);
		return "redirect:/assets";
	}

	@GetMapping("asset/edit/{id}")
	public String editAsset(@Valid @PathVariable Integer id, Model model) {
		Asset asset = service.getAsset(id);
		model.addAttribute("asset", asset);
		model.addAttribute("hardwareTypes", Utils.getFormattedNames(hService.getAllTypes()));
		model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		return "updateasset";
	}

	@PostMapping("asset/edit/{id}")
	public String updateAsset(@Valid @PathVariable Integer id, @Valid Asset asset, String hardwareType,
			@RequestParam("assetHistory.purchaseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime purchaseDate,
			@RequestParam(value = "assetHistory.warrantyDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime warrantyDate) {
		asset.setId(id);
		asset.setHardware(hService.getHardwareByType(Utils.getOriginalName(hardwareType)));
		AssetHistory assetHistory = asset.getAssetHistory();
		assetHistory.setPurchaseDate(purchaseDate);
		assetHistory.setWarrantyDate(warrantyDate);
		aHService.saveAssetHistory(assetHistory);
		
		service.saveAsset(asset);
		return "redirect:/assets";
	}
}
