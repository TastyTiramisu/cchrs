INSERT INTO hardware_group(hw_group) VALUES 
("PERIPHERAL_DEVICES"),
("NETWORK_DEVICES"),
("COMPUTERS"),
("UPS"),
("FURNITURE");

INSERT INTO hardware(type, hardware_group_id) VALUES 
("PC", 3),
("LAPTOP", 3),
("PRINTER", 1),
("NETWORK_PRINTER", 2),
("MOUSE", 1),
("KEYBOARD", 1),
("SCANNER", 1),
("MONITOR", 1),
("WEBCAM", 1),
("SPEAKERS", 1),
("MICROPHONE", 1),
("COMPUTER_CHAIR", 5),
("DESK", 5),
("UPS", 4),
("NETWORK_CARD", 2),
("ROUTER", 2),
("SWITCH", 2),
("ACCESS_POINT", 2);

INSERT INTO asset_history(purchase_date, warranty_date, disposal_date) VALUES 
("2021-06-10", "2022-05-30", NULL),
("2021-06-10", NULL, NULL),
("2021-06-10", "2022-05-30", NULL);

INSERT INTO asset(asset_name, note, uuid, serial_number, deleted, asset_history_id, hardware_id) VALUES 
("PC1", "Komputer OptiPlex Dell OptiPlex 5060 Intel Core i5-8500T 8 GB 240 GB SSD Windows 10 Pro", UUID(), "TKMU7PM241477230058925", 0, 1, 1),
("PC2", "Komputer Dell OptiPlex 790 DT Intel Core i5-2500 8 GB 240 GB SSD Windows 10 Home", UUID(), "TEDB-8-LF-818387054947576", 0, 2, 1),
("myszka1", "Mysz komputerowa MICROSOFT Bluetooth Mobile Mouse 3600 - PN7-00023", UUID(), "UGLL-8-YX-108463001059070", 0, 3, 5);

#Using BCrypt hashes.
INSERT INTO person(name, surname, email, role, enabled, password) VALUES 
("Ernest", "Zakrzewski", "ezakrzewski@wp.pl", "USER", 1, "$2a$10$BT.lPDAGwaE.zsgj6EIuYOsCG9DCD0CCdfJGiL.xPPyfdg1CWQOQW"),
("Igor", "Kaźmierczak", "ikazimierczak@wp.pl", "USER", 1, "$2a$10$LlGltfd55YLRwN5.cvsjSONa7q0GbxJDE1tqUJZBNSRJfvw8FWbea"),
("Roman", "Wojciechowski", "rwojciechowski@wp.pl", "USER", 1, "$2a$10$8RBxXIJgBq6dqonAgOdlDOMv8CleXZDUieNc5k14yoXSps0fEPN6i"),
("Alicja", "Kaczmarczyk", "akaczmarczyk@wp.pl", "USER", 1, "$2a$10$aXSqGbMJ5f2eW5OB.hWfkudQUTEyY3aVijtarnMTpmmYAd/ridmVG"),
("Stanisława", "Wójcik", "swojcik@wp.pl", "USER", 1, "$2a$10$tUXPpOVW91wrt2US6bmJ..9XAgfBbsG0Y2jUfSKBhB.dxfs5SRDTq"),
("Ada", "Mróz", "amroz@gmail.com", "ADMIN", 1, "$2a$10$4AV8j1m2QbM8k0iVPOsV3OmgeLJR7FdYsAAY3/9.SRHhBibIIVHt6");

INSERT INTO room(number, type, deleted) VALUES 
(1, "OFFICE", 0),
(2, "OFFICE", 0),
(3, "OFFICE", 0),
(4, "OFFICE", 0),
(5, "OFFICE", 0),
(11, "OFFICE", 0),
(12, "OFFICE", 0),
(20, "OFFICE", 0);

INSERT INTO borrow_history(borrow_date, borrow_until, return_back_date, note, asset_id, person_id, room_id) VALUES 
("2023-01-13", NULL, "2023-09-01", "Tymczasowy komputer Ady", 1, 6, 1),
("2023-01-13", NULL, NULL, NULL, 2, 4, 2),
("2023-01-13", NULL, NULL, NULL, 3, 4, 2);




