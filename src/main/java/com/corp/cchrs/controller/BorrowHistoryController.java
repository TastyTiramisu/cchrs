package com.corp.cchrs.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.corp.cchrs.model.BorrowHistory;
import com.corp.cchrs.model.Person;
import com.corp.cchrs.model.Room;
import com.corp.cchrs.service.AssetService;
import com.corp.cchrs.service.BorrowHistoryService;
import com.corp.cchrs.service.PersonService;
import com.corp.cchrs.service.RoomService;

@Controller
public class BorrowHistoryController extends FilterOptions {
	
	final public static int PAGE_SIZE = 3;
	
	@Autowired
	BorrowHistoryService service;
	
	@Autowired
	AssetService aService;
	
	@Autowired
	PersonService pService;
	
	@Autowired
	RoomService rService;
	
	@GetMapping(path = "/asset/{id}")
	public String markTheItemAsReturned(@Valid @PathVariable Integer id, @RequestParam(required = false) boolean returned, Model model) throws MethodArgumentNotValidException {
		final String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		final String username = SecurityContextHolder.getContext().getAuthentication().getName();
		BorrowHistory borrowHistory = service.getLastRecordInHistoryOfAsset(id);
		LocalDateTime currentReturnBackDate = borrowHistory.getReturnBackDate();

		if(role.equals("[ADMIN]") || username.equals(borrowHistory.getPerson().getEmail()))
			if(returned == true && currentReturnBackDate == null) {
				if(aService.getAsset(id).isDeleted()) {
					model.addAttribute("status", "400");
					model.addAttribute("error", "Bad Request");
					model.addAttribute("message", "Asset was deleted");
					return "error";
				}
				borrowHistory.setReturnBackDate(LocalDateTime.now());
				service.saveBorrowHistory(borrowHistory);
			}
		model.addAttribute("assetDetails", borrowHistory);
		model.addAttribute("role", role);
		model.addAttribute("username", username);
		return "assetdetails";
	}
	
	@GetMapping("/asset/lend/{id}")
	public String lendSomeoneAssetForm(@Valid @PathVariable Integer id, Model model) {
		if(aService.getAsset(id).isDeleted()) {
			model.addAttribute("status", "400");
			model.addAttribute("error", "Bad Request");
			model.addAttribute("message", "Asset was deleted");
			return "error";
			}
		
		model.addAttribute("borrowHistory", new BorrowHistory());
		model.addAttribute("asset", aService.getAsset(id));
		model.addAttribute("people", pService.getPeople());
		model.addAttribute("rooms", rService.getRooms());
		model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		return "addborrowhistory";
	}
	
	@PostMapping("/asset/{id}")
	public String lendSomeoneAsset(@Valid @PathVariable Integer id, Model model,
			BorrowHistory borrowHistory,
			@RequestParam @Valid Person person,
			@RequestParam @Valid Room room) {
		borrowHistory.setId(null);
		borrowHistory.setAsset(aService.getAsset(id));
		borrowHistory.setPerson(person);
		borrowHistory.setRoom(room);

		service.saveBorrowHistory(borrowHistory);
		model.addAttribute("assetDetails", service.getLastRecordInHistoryOfAsset(id));
		model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		return "assetdetails";
	}
	
	@GetMapping("/asset/{id}/history")
	public String showHistory(@Valid @PathVariable Integer id, Model model) {
		model.addAttribute("historyOfAsset", service.getBorrowHistoryOfAsset(id));
		model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		return "borrowhistoryofasset";
	}
	
	@GetMapping("/my/assets")
	public String getLoggedInPersonsAssets(Model model, @RequestParam(required = false) Integer group,
			@RequestParam(required = false) Integer type, 
			@RequestParam(defaultValue = "0") int page) throws Exception {
		
		provideFilterOptions(model);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer personId = pService.getPersonByEmail(auth.getName()).getId();
		
		Pageable paging = PageRequest.of(page, PAGE_SIZE);
		Page<BorrowHistory> borrowHistory = service.getBorrowHistory(personId, type, group, paging);
		model.addAttribute("urlPath", "/my/assets");
		model.addAttribute("group", group);
		model.addAttribute("type", type);
		model.addAttribute("currentPage", page + 1);
		model.addAttribute("totalPages", borrowHistory.getTotalPages());
		
		model.addAttribute("borrowHistory", borrowHistory);
		model.addAttribute("role", auth.getAuthorities().toString());
		
		return "showpersonsassets";
	}
}
