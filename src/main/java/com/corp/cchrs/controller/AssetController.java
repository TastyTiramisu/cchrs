package com.corp.cchrs.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.corp.cchrs.model.Person;
import com.corp.cchrs.model.Room;
import com.corp.cchrs.service.AssetHistoryService;
import com.corp.cchrs.service.AssetService;
import com.corp.cchrs.service.BorrowHistoryService;
import com.corp.cchrs.service.PersonService;
import com.corp.cchrs.service.RoomService;
import com.corp.cchrs.utils.Utils;

@Controller
public class AssetController extends FilterOptions {
	
	final public static int PAGE_SIZE = 3;

	@Autowired
	AssetService service;
	
	@Autowired
	AssetHistoryService aHService;
	
	@Autowired
	PersonService pService;
	
	@Autowired
	RoomService rService;
	
	@Autowired
	BorrowHistoryService bHService;
	
	@GetMapping("/ser2")
	public String genSerNumWithDashes() {
		return Utils.generateSerialNumberWithDashes();
	}

	@GetMapping("/assets")
	public String getAssets(Model model, @RequestParam(required = false) Integer group, @RequestParam(required = false) Integer type, 
			@RequestParam(defaultValue = "0") int page) throws Exception {  
	    
		provideFilterOptions(model);
		
		Pageable paging = PageRequest.of(page, PAGE_SIZE);
		model.addAttribute("urlPath", "/assets");
		model.addAttribute("group", group);
		model.addAttribute("type", type);
		model.addAttribute("currentPage", page + 1);
		model.addAttribute("totalPages", service.getAssets(type, group, paging).getTotalPages());
		
		model.addAttribute("assets", service.getAssets(type, group, paging));
		model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		
		return "showassets";
	}

	@GetMapping("/assets/person/{id}")
	public String getPersonsAssets(Model model, @Valid @PathVariable Integer id,
			@RequestParam(required = false) Integer group, @RequestParam(required = false) Integer type, 
			@RequestParam(defaultValue = "0") int page) throws Exception {
		
		provideFilterOptions(model);
		
		Pageable paging = PageRequest.of(page, PAGE_SIZE);
		model.addAttribute("urlPath", "/assets/person/" + id);
		model.addAttribute("group", group);
		model.addAttribute("type", type);
		model.addAttribute("currentPage", page + 1);
		model.addAttribute("totalPages", service.getAssets(type, group, paging).getTotalPages());
		
		model.addAttribute("assets", service.getAssets(id, type, group, paging));
		model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		
		return "showassets";
	}
	
	@GetMapping("/deleted/assets")
	public String getDeletedAssets(Model model, @RequestParam(required = false) Integer group,
			@RequestParam(required = false) Integer type, @RequestParam(defaultValue = "0") int page) throws Exception {
		
		provideFilterOptions(model);
		Pageable paging = PageRequest.of(page, PAGE_SIZE);
		model.addAttribute("urlPath", "/deleted/assets");
		model.addAttribute("group", group);
		model.addAttribute("type", type);
		model.addAttribute("currentPage", page + 1);
		model.addAttribute("totalPages", service.getDeletedAssets(type, group, paging).getTotalPages());
		
		model.addAttribute("assets", service.getDeletedAssets(type, group, paging));
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
		model.addAttribute("currentType", Utils.getFormattedName(asset.getHardware().getType()));
		model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		return "updateasset";
	}

	@PostMapping("asset/edit/{id}")
	public String updateAsset(@Valid @PathVariable Integer id, @Valid Asset asset, String type,
			@RequestParam("assetHistory.purchaseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime purchaseDate,
			@RequestParam(value = "assetHistory.warrantyDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime warrantyDate) {
		asset.setId(id);
		System.out.println("hardwareType: " + type);
		asset.setHardware(hService.getHardwareByType(Utils.getOriginalName(type)));
		AssetHistory assetHistory = asset.getAssetHistory();
		assetHistory.setPurchaseDate(purchaseDate);
		assetHistory.setWarrantyDate(warrantyDate);
		aHService.saveAssetHistory(assetHistory);
		
		service.saveAsset(asset);
		return "redirect:/assets";
	}
}
