package com.corp.cchrs;

import java.time.LocalDate;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.corp.cchrs.model.Asset;
import com.corp.cchrs.model.AssetHistory;
import com.corp.cchrs.model.BorrowHistory;
import com.corp.cchrs.model.EHardwareType;
import com.corp.cchrs.model.Hardware;
import com.corp.cchrs.model.HardwareGroup;
import com.corp.cchrs.model.Person;
import com.corp.cchrs.model.Room;
import com.corp.cchrs.model.repository.AssetHistoryRepo;
import com.corp.cchrs.model.repository.AssetRepo;
import com.corp.cchrs.model.repository.BorrowHistoryRepo;
import com.corp.cchrs.model.repository.HardwareGroupRepo;
import com.corp.cchrs.model.repository.HardwareRepo;
import com.corp.cchrs.model.repository.PersonRepo;
import com.corp.cchrs.model.repository.RoomRepo;
import com.corp.cchrs.service.HardwareService;
import com.corp.cchrs.utils.Utils;

@Component
public class Starter implements ApplicationRunner {
	
	@Autowired
	AssetRepo aRepo;
	
	@Autowired
	PersonRepo pRepo;
	
	@Autowired
	RoomRepo rRepo;
	
	@Autowired
	HardwareRepo hRepo;
	
	@Autowired
	HardwareService hService;
	
	@Autowired
	HardwareGroupRepo hGRepo;
	
	@Autowired
	AssetHistoryRepo aHRepo;
	
	@Autowired
	BorrowHistoryRepo bHRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		//loadData();
	}
	
	@SuppressWarnings("unused")
	private void loadData(){
		addHardware();
		addAssets();
		System.out.println();
		final Iterable<Asset> findAll = aRepo.findAll();
		for (Asset asset : findAll) {
			System.out.println(asset.getName() + " " + asset.getDesc());
		}
		addPeople();
		addRooms();
		addBorrowHistory();
		//printAllHardwareTypes();
		/* 
		http://localhost:8080/h2-console/
		SELECT * FROM HARDWARE_GROUP;
		SELECT * FROM HARDWARE;
		SELECT * FROM ASSET_HISTORY;
		SELECT * FROM ASSET;
		SELECT * FROM PERSON;
		SELECT * FROM ROOM;
		SELECT * FROM BORROW_HISTORY;
		*/
	}

	@SuppressWarnings("unused")
	private void printAllHardwareTypes() {
		//th:each="asset : ${assets}"
		for(EHardwareType value : EHardwareType.values())
			System.out.println(value.name().replace('_', ' ').toLowerCase());
	}

	private void addPeople() {
		Person person = new Person();
		person.setName("Ernest");
		person.setSurname("Zakrzewski");
		person.setEmail("ezakrzewski@wp.pl");
		person.setPassword(encoder.encode("c"));
		person.setRole("USER");
		pRepo.save(person);
		
		person = new Person();
		person.setName("Igor");
		person.setSurname("Kaźmierczak");
		person.setEmail("ikazimierczak@wp.pl");
		person.setPassword(encoder.encode("c"));
		person.setRole("USER");
		pRepo.save(person);
		
		person = new Person();
		person.setName("Roman");
		person.setSurname("Wojciechowski");
		person.setEmail("rwojciechowski@wp.pl");
		person.setPassword(encoder.encode("c"));
		person.setRole("USER");
		pRepo.save(person);
		
		person = new Person();
		person.setName("Alicja");
		person.setSurname("Kaczmarczyk");
		person.setEmail("akaczmarczyk@wp.pl");
		person.setPassword(encoder.encode("c"));
		person.setRole("USER");
		pRepo.save(person);
		
		person = new Person();
		person.setName("Stanisława");
		person.setSurname("Wójcik");
		person.setEmail("swojcik@wp.pl");
		person.setPassword(encoder.encode("c"));
		person.setRole("USER");
		pRepo.save(person);
		
		person = new Person();
		person.setName("Ada");
		person.setSurname("Mróz");
		person.setEmail("amroz@gmail.com");
		person.setPassword(encoder.encode("c"));
		person.setRole("ADMIN");
		pRepo.save(person);
	}
	
	private void addRooms() {
		Room room = new Room();
		room.setNumber(1);
		rRepo.save(room);
		
		room = new Room();
		room.setNumber(2);
		rRepo.save(room);
		
		room = new Room();
		room.setNumber(3);
		rRepo.save(room);
		
		room = new Room();
		room.setNumber(4);
		rRepo.save(room);
		
		room = new Room();
		room.setNumber(5);
		rRepo.save(room);
		
		room = new Room();
		room.setNumber(11);
		rRepo.save(room);
		
		room = new Room();
		room.setNumber(12);
		rRepo.save(room);
		
		room = new Room();
		room.setNumber(20);
		room.setType("STOREHOUSE");
		rRepo.save(room);
	}

	/*Each hardware is assigned to it's group and it will NOT change.*/
	private void addHardware() {
		HardwareGroup peripherals = new HardwareGroup("PERIPHERAL_DEVICES");
		HardwareGroup networkDevices = new HardwareGroup("NETWORK_DEVICES");
		HardwareGroup computers = new HardwareGroup("COMPUTERS");
		HardwareGroup ups = new HardwareGroup("UPS");
		HardwareGroup furniture = new HardwareGroup("FURNITURE");
		
		hGRepo.save(peripherals);
		hGRepo.save(networkDevices);
		hGRepo.save(computers);
		hGRepo.save(ups);
		hGRepo.save(furniture);
		
		//1
		Hardware hardware = new Hardware();
		hardware.setType("PC");
		hardware.setHardwareGroup(computers);
		
		hRepo.save(hardware);
		//2
		hardware = new Hardware();
		hardware.setType("LAPTOP");
		hardware.setHardwareGroup(computers);
		
		hRepo.save(hardware);
		//3
		hardware = new Hardware();
		hardware.setType("PRINTER");
		hardware.setHardwareGroup(peripherals);
		
		hRepo.save(hardware);
		//4
		hardware = new Hardware();
		hardware.setType("NETWORK_PRINTER");
		hardware.setHardwareGroup(networkDevices);
		
		hRepo.save(hardware);
		//5
		hardware = new Hardware();
		hardware.setType("MOUSE");
		hardware.setHardwareGroup(peripherals);
		
		hRepo.save(hardware);
		//6
		hardware = new Hardware();
		hardware.setType("KEYBOARD");
		hardware.setHardwareGroup(peripherals);
		
		hRepo.save(hardware);
		//7
		hardware = new Hardware();
		hardware.setType("SCANNER");
		hardware.setHardwareGroup(peripherals);
		
		hRepo.save(hardware);
		//8
		hardware = new Hardware();
		hardware.setType("MONITOR");
		hardware.setHardwareGroup(peripherals);
		
		hRepo.save(hardware);
		//9
		hardware = new Hardware();
		hardware.setType("WEBCAM");
		hardware.setHardwareGroup(peripherals);
		
		hRepo.save(hardware);
		//10
		hardware = new Hardware();
		hardware.setType("SPEAKERS");
		hardware.setHardwareGroup(peripherals);
		
		hRepo.save(hardware);
		//11
		hardware = new Hardware();
		hardware.setType("MICROPHONE");
		hardware.setHardwareGroup(peripherals);
		
		hRepo.save(hardware);
		//12
		hardware = new Hardware();
		hardware.setType("COMPUTER_CHAIR");
		hardware.setHardwareGroup(furniture);
		
		hRepo.save(hardware);
		//13
		hardware = new Hardware();
		hardware.setType("DESK");
		hardware.setHardwareGroup(furniture);
		
		hRepo.save(hardware);
		//14
		hardware = new Hardware();
		hardware.setType("UPS");
		hardware.setHardwareGroup(ups);
		
		hRepo.save(hardware);
		//15
		hardware = new Hardware();
		hardware.setType("NETWORK_CARD");
		hardware.setHardwareGroup(networkDevices);
		
		hRepo.save(hardware);
		//16
		hardware = new Hardware();
		hardware.setType("ROUTER");
		hardware.setHardwareGroup(networkDevices);
		
		hRepo.save(hardware);
		//17
		hardware = new Hardware();
		hardware.setType("SWITCH");
		hardware.setHardwareGroup(networkDevices);
		
		hRepo.save(hardware);
		//18
		hardware = new Hardware();
		hardware.setType("ACCESS_POINT");
		hardware.setHardwareGroup(networkDevices);
		
		hRepo.save(hardware);
	}
	
	private void addAssets() {
		//1
		Asset asset = new Asset();
		Hardware hardware = hService.findTypeOfAsset("PC");
		asset.setHardware(hardware);
		asset.setName("PC1");
		asset.setSerialNumber(Utils.generateSerialNumber());
		asset.setDesc("Komputer OptiPlex Dell OptiPlex 5060 Intel Core i5-8500T 8 GB 240 GB SSD Windows 10 Pro");
		
		AssetHistory assetHistory = new AssetHistory();
		assetHistory.setPurchaseDate(LocalDate.of(2021, 6, 10));
		assetHistory.setWarrantyDate(LocalDate.of(2022, 5, 30));
		asset.setAssetHistory(assetHistory);
		aHRepo.save(assetHistory);
		hRepo.save(hardware);
		aRepo.save(asset);
		//2
		asset = new Asset();
		hardware = hService.findTypeOfAsset("PC");
		asset.setName("PC2");
		asset.setHardware(hardware);
		asset.setSerialNumber(Utils.generateSerialNumberWithDashes());
		asset.setDesc("Komputer Dell OptiPlex 790 DT Intel Core i5-2500 8 GB 240 GB SSD Windows 10 Home");
		
		assetHistory = new AssetHistory();
		assetHistory.setPurchaseDate(LocalDate.of(2021, 6, 10));
		asset.setAssetHistory(assetHistory);
		aHRepo.save(assetHistory);
		hRepo.save(hardware);
		aRepo.save(asset);
		//3
		asset = new Asset();
		hardware = hService.findTypeOfAsset("MOUSE");
		asset.setName("myszka1");
		asset.setHardware(hardware);
		asset.setSerialNumber(Utils.generateSerialNumberWithDashes());
		asset.setDesc("Mysz komputerowa MICROSOFT Bluetooth Mobile Mouse 3600 - PN7-00023");
		
		assetHistory = new AssetHistory();
		assetHistory.setPurchaseDate(LocalDate.of(2021, 6, 10));
		assetHistory.setWarrantyDate(LocalDate.of(2022, 5, 30));
		asset.setAssetHistory(assetHistory);
		aHRepo.save(assetHistory);
		hRepo.save(hardware);
		aRepo.save(asset);
	}

	//cały sprzęt ma przy jego dodawaniu 1 wpis w historii: kto, gdzie, borrowDate ale nie do kiedy.
	private void addBorrowHistory() {
		//1
		BorrowHistory bHistory = new BorrowHistory();
		bHistory.setBorrowDate(LocalDate.of(2023, 1, 13));
		bHistory.setBorrowUntil(LocalDate.of(2023, 9, 1));
		bHistory.setReturnBackDate(LocalDate.of(2023, 9, 1));
		
		Asset asset = StreamSupport.stream(aRepo.findAll().spliterator(), false)
				.filter(a -> "PC1".equals(a.getName()))
				.findAny()
				.orElseThrow();
		bHistory.setAsset(asset);
		
		Person person = StreamSupport.stream(pRepo.findAll().spliterator(), false)
				.filter(p -> "Ada".equals(p.getName()))
				.filter(p -> "Mróz".equals(p.getSurname()))
				.findAny()
				.orElseThrow();
		bHistory.setPerson(person);
		
		Room room = StreamSupport.stream(rRepo.findAll().spliterator(), false)
				.filter(r -> r.getNumber().equals(1))
				.findAny()
				.orElseThrow();
		bHistory.setRoom(room);
		
		bHRepo.save(bHistory);
		//2
		bHistory = new BorrowHistory();
		bHistory.setBorrowDate(LocalDate.of(2023, 1, 13));
		bHistory.setBorrowUntil(LocalDate.of(2023, 9, 1));
		
		asset = StreamSupport.stream(aRepo.findAll().spliterator(), false)
				.filter(a -> "PC2".equals(a.getName()))
				.findAny()
				.orElseThrow();
		bHistory.setAsset(asset);
		
		person = StreamSupport.stream(pRepo.findAll().spliterator(), false)
				.filter(p -> "Alicja".equals(p.getName()))
				.filter(p -> "Kaczmarczyk".equals(p.getSurname()))
				.findAny()
				.orElseThrow();
		bHistory.setPerson(person);
		
		room = StreamSupport.stream(rRepo.findAll().spliterator(), false)
				.filter(r -> r.getNumber().equals(20))
				.findAny()
				.orElseThrow();
		bHistory.setRoom(room);
		
		bHRepo.save(bHistory);
		//3
		bHistory = new BorrowHistory();
		bHistory.setBorrowDate(LocalDate.of(2023, 1, 13));
		
		asset = StreamSupport.stream(aRepo.findAll().spliterator(), false)
				.filter(a -> "myszka1".equals(a.getName()))
				.findAny()
				.orElseThrow();
		bHistory.setAsset(asset);
		
		person = StreamSupport.stream(pRepo.findAll().spliterator(), false)
				.filter(p -> "Alicja".equals(p.getName()))
				.filter(p -> "Kaczmarczyk".equals(p.getSurname()))
				.findAny()
				.orElseThrow();
		bHistory.setPerson(person);
		
		room = StreamSupport.stream(rRepo.findAll().spliterator(), false)
				.filter(r -> r.getNumber().equals(20))
				.findAny()
				.orElseThrow();
		bHistory.setRoom(room);
		
		bHRepo.save(bHistory);
	}
}
