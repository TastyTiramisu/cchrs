INSERT INTO hardware_group(id, hw_group) VALUES 
(1, "PERIPHERAL_DEVICES"),
(2, "NETWORK_DEVICES"),
(3, "COMPUTERS"),
(4, "UPS"),
(5, "FURNITURE");

INSERT INTO hardware(id, type, hardware_group_id) VALUES 
(1, "PC", 3),
(2, "LAPTOP", 3),
(3, "PRINTER", 1),
(4, "NETWORK_PRINTER", 2),
(5, "MOUSE", 1),
(6, "KEYBOARD", 1),
(7, "SCANNER", 1),
(8, "MONITOR", 1),
(9, "WEBCAM", 1),
(10, "SPEAKERS", 1),
(11, "MICROPHONE", 1),
(12, "COMPUTER_CHAIR", 5),
(13, "DESK", 5),
(14, "UPS", 4),
(15, "NETWORK_CARD", 2),
(16, "ROUTER", 2),
(17, "SWITCH", 2),
(18, "ACCESS_POINT", 2);

INSERT INTO asset_history(id, purchase_date, warranty_date, disposal_date) VALUES 
(1, "2021-06-10", "2022-05-30", NULL),
(2, "2021-06-10", NULL, NULL),
(3, "2021-06-10", "2022-05-30", NULL);

INSERT INTO asset(id, asset_name, note, uuid, serial_number, deleted, asset_history_id, hardware_id) VALUES 
(1, "PC1", "Komputer OptiPlex Dell OptiPlex 5060 Intel Core i5-8500T 8 GB 240 GB SSD Windows 10 Pro", UUID(), "TKMU7PM241477230058925", 0, 1, 1),
(2, "PC2", "Komputer Dell OptiPlex 790 DT Intel Core i5-2500 8 GB 240 GB SSD Windows 10 Home", UUID(), "TEDB-8-LF-818387054947576", 0, 2, 1),
(3, "myszka1", "Mysz komputerowa MICROSOFT Bluetooth Mobile Mouse 3600 - PN7-00023", UUID(), "UGLL-8-YX-108463001059070", 0, 3, 5),
(4, "myszka2", "Mysz komputerowa MICROSOFT Bluetooth Mobile Mouse 3600 - PN7-00023", UUID(), "UGLL-8-YX-108463001059071", 0, 3, 5),
(5, "myszka3", "Mysz komputerowa MICROSOFT Bluetooth Mobile Mouse 3600 - PN7-00023", UUID(), "UGLL-8-YX-108463001059072", 0, 3, 5),
(6, "myszka4", "Mysz komputerowa MICROSOFT Bluetooth Mobile Mouse 3600 - PN7-00023", UUID(), "UGLL-8-YX-108463001059073", 0, 3, 5),
(7, "myszka5", "Mysz komputerowa MICROSOFT Bluetooth Mobile Mouse 3600 - PN7-00023", UUID(), "UGLL-8-YX-108463001059074", 0, 3, 5);

-- Using BCrypt hashes.
INSERT INTO person(id, name, surname, email, role, enabled, password) VALUES 
(1, "Ernest", "Zakrzewski", "ezakrzewski@wp.pl", "USER", 1, "$2a$10$BT.lPDAGwaE.zsgj6EIuYOsCG9DCD0CCdfJGiL.xPPyfdg1CWQOQW"),
(2, "Igor", "Kaźmierczak", "ikazimierczak@wp.pl", "USER", 1, "$2a$10$LlGltfd55YLRwN5.cvsjSONa7q0GbxJDE1tqUJZBNSRJfvw8FWbea"),
(3, "Roman", "Wojciechowski", "rwojciechowski@wp.pl", "USER", 1, "$2a$10$8RBxXIJgBq6dqonAgOdlDOMv8CleXZDUieNc5k14yoXSps0fEPN6i"),
(4, "Alicja", "Kaczmarczyk", "akaczmarczyk@wp.pl", "USER", 1, "$2a$10$aXSqGbMJ5f2eW5OB.hWfkudQUTEyY3aVijtarnMTpmmYAd/ridmVG"),
(5, "Stanisława", "Wójcik", "swojcik@wp.pl", "USER", 1, "$2a$10$tUXPpOVW91wrt2US6bmJ..9XAgfBbsG0Y2jUfSKBhB.dxfs5SRDTq"),
(6, "Ada", "Mróz", "amroz@gmail.com", "ADMIN", 1, "$2a$10$4AV8j1m2QbM8k0iVPOsV3OmgeLJR7FdYsAAY3/9.SRHhBibIIVHt6");

INSERT INTO room(id, number, type, deleted) VALUES 
(1, 1, "OFFICE", 0),
(2, 2, "OFFICE", 0),
(3, 3, "OFFICE", 0),
(4, 4, "OFFICE", 0),
(5, 5, "OFFICE", 0),
(6, 11, "OFFICE", 0),
(7, 12, "OFFICE", 0),
(8, 20, "OFFICE", 0);

INSERT INTO borrow_history(borrow_date, borrow_until, return_back_date, note, asset_id, person_id, room_id) VALUES 
("2023-01-13", "2025-10-12", "2023-09-01", "Tymczasowy komputer Ady", 1, 6, 1),
("2023-01-13", NULL, NULL, NULL, 2, 4, 2),
("2023-01-13", NULL, NULL, NULL, 3, 4, 2);




