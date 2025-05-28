INSERT INTO users VALUES
('admin','123');

INSERT INTO rooms (room_number, capacity, occupied, status, room_type) VALUES 
-- Tầng 1 (Male)
('101', 4, 0, 'Available', 'Male'),
('102', 6, 0, 'Available', 'Male'),
('103', 8, 0, 'Available', 'Male'),
('104', 4, 0, 'Available', 'Male'),
('105', 6, 0, 'Available', 'Male'),
('106', 8, 0, 'Available', 'Male'),
('107', 4, 0, 'Available', 'Male'),
('108', 6, 0, 'Available', 'Male'),
('109', 8, 0, 'Available', 'Male'),
('110', 4, 0, 'Available', 'Male'),

-- Tầng 2 (Female)
('201', 6, 0, 'Available', 'Female'),
('202', 8, 0, 'Available', 'Female'),
('203', 4, 0, 'Available', 'Female'),
('204', 6, 0, 'Available', 'Female'),
('205', 8, 0, 'Available', 'Female'),
('206', 4, 0, 'Available', 'Female'),
('207', 6, 0, 'Available', 'Female'),
('208', 8, 0, 'Available', 'Female'),
('209', 4, 0, 'Available', 'Female'),
('210', 6, 0, 'Available', 'Female'),

-- Tầng 3 (Male)
('301', 8, 0, 'Available', 'Male'),
('302', 4, 0, 'Available', 'Male'),
('303', 6, 0, 'Available', 'Male'),
('304', 8, 0, 'Available', 'Male'),
('305', 4, 0, 'Available', 'Male'),
('306', 6, 0, 'Available', 'Male'),
('307', 8, 0, 'Available', 'Male'),
('308', 4, 0, 'Available', 'Male'),
('309', 6, 0, 'Available', 'Male'),
('310', 8, 0, 'Available', 'Male'),

-- Tầng 4 (Female)
('401', 4, 0, 'Available', 'Female'),
('402', 6, 0, 'Available', 'Female'),
('403', 8, 0, 'Available', 'Female'),
('404', 4, 0, 'Available', 'Female'),
('405', 6, 0, 'Available', 'Female'),
('406', 8, 0, 'Available', 'Female'),
('407', 4, 0, 'Available', 'Female'),
('408', 6, 0, 'Available', 'Female'),
('409', 8, 0, 'Available', 'Female'),
('410', 4, 0, 'Available', 'Female'),

-- Tầng 5 (Male)
('501', 6, 0, 'Available', 'Male'),
('502', 8, 0, 'Available', 'Male'),
('503', 4, 0, 'Available', 'Male'),
('504', 6, 0, 'Available', 'Male'),
('505', 8, 0, 'Available', 'Male'),
('506', 4, 0, 'Available', 'Male'),
('507', 6, 0, 'Available', 'Male'),
('508', 8, 0, 'Available', 'Male'),
('509', 4, 0, 'Available', 'Male'),
('510', 6, 0, 'Available', 'Male');

INSERT INTO students (id, name, dob, gender, phone, email, room_id) VALUES 
-- Sinh viên nam (tầng 1, 3, 5)
('24it001', 'Nguyễn Văn Hùng', '2006-03-15', 'Male', '0332145876', 'hungnv.it@vku.udn.vn', '101'),
('24it002', 'Trần Quốc Anh', '2006-05-22', 'Male', '0332145877', 'anhtq.it@vku.udn.vn', '101'),
('24da003', 'Lê Minh Tuấn', '2006-07-10', 'Male', '0332145878', 'tuanlm.da@vku.udn.vn', '101'),
('24ai004', 'Phạm Hoàng Long', '2006-09-18', 'Male', '0332145879', 'longph.ai@vku.udn.vn', '101'), -- Phòng 101 đầy (4/4)

('23it001', 'Nguyễn Thanh Phong', '2005-01-12', 'Male', '0332145880', 'phongnt.it@vku.udn.vn', '102'),
('23da002', 'Trần Văn Nam', '2005-03-20', 'Male', '0332145881', 'namtv.da@vku.udn.vn', '102'),
('23ai003', 'Lê Quốc Huy', '2005-05-25', 'Male', '0332145882', 'huylq.ai@vku.udn.vn', '102'),
('23it004', 'Phạm Minh Khang', '2005-07-30', 'Male', '0332145883', 'khangpm.it@vku.udn.vn', '102'), -- Phòng 102: 4/6

('22it001', 'Nguyễn Đức Thắng', '2004-02-14', 'Male', '0332145884', 'thangnd.it@vku.udn.vn', '103'),
('22da002', 'Trần Văn Đạt', '2004-04-18', 'Male', '0332145885', 'dattv.da@vku.udn.vn', '103'),
('22ai003', 'Lê Hoàng Phúc', '2004-06-22', 'Male', '0332145886', 'phuclh.ai@vku.udn.vn', '103'),
('22it004', 'Phạm Quốc Bảo', '2004-08-26', 'Male', '0332145887', 'baopq.it@vku.udn.vn', '103'),
('22da005', 'Nguyễn Minh Trí', '2004-10-30', 'Male', '0332145888', 'trimm.da@vku.udn.vn', '103'), -- Phòng 103: 5/8

('24it005', 'Trần Văn Khôi', '2006-01-05', 'Male', '0332145889', 'khoitv.it@vku.udn.vn', '301'),
('24da006', 'Lê Minh Đức', '2006-03-10', 'Male', '0332145890', 'duclm.da@vku.udn.vn', '301'),
('24ai007', 'Phạm Văn Tâm', '2006-05-15', 'Male', '0332145891', 'tampv.ai@vku.udn.vn', '301'),
('24it008', 'Nguyễn Quốc Hùng', '2006-07-20', 'Male', '0332145892', 'hungnq.it@vku.udn.vn', '301'),
('24da009', 'Trần Minh Quang', '2006-09-25', 'Male', '0332145893', 'quangtm.da@vku.udn.vn', '301'), -- Phòng 301: 5/8

('23it005', 'Lê Văn Bình', '2005-02-10', 'Male', '0332145894', 'binhlv.it@vku.udn.vn', '302'),
('23da006', 'Phạm Quốc Dũng', '2005-04-15', 'Male', '0332145895', 'dungpq.da@vku.udn.vn', '302'),
('23ai007', 'Nguyễn Văn Lâm', '2005-06-20', 'Male', '0332145896', 'lamnv.ai@vku.udn.vn', '302'), -- Phòng 302: 3/4

('22it006', 'Trần Quốc Việt', '2004-01-05', 'Male', '0332145897', 'vietnq.it@vku.udn.vn', '501'),
('22da007', 'Lê Minh Hoàng', '2004-03-10', 'Male', '0332145898', 'hoanglm.da@vku.udn.vn', '501'),
('22ai008', 'Phạm Văn Thắng', '2004-05-15', 'Male', '0332145899', 'thangpv.ai@vku.udn.vn', '501'),
('22it009', 'Nguyễn Quốc Anh', '2004-07-20', 'Male', '0332145900', 'anhnq.it@vku.udn.vn', '501'), -- Phòng 501: 4/6

-- Sinh viên nữ (tầng 2, 4)
('24it010', 'Nguyễn Thị Mai', '2006-02-12', 'Female', '0332145901', 'main.it@vku.udn.vn', '201'),
('24da011', 'Trần Thị Lan', '2006-04-18', 'Female', '0332145902', 'lantt.da@vku.udn.vn', '201'),
('24ai012', 'Lê Thị Hương', '2006-06-22', 'Female', '0332145903', 'huonglt.ai@vku.udn.vn', '201'),
('24it013', 'Phạm Thị Ngọc', '2006-08-26', 'Female', '0332145904', 'ngocpt.it@vku.udn.vn', '201'), -- Phòng 201: 4/6

('23it008', 'Nguyễn Thị Thảo', '2005-01-15', 'Female', '0332145905', 'thaont.it@vku.udn.vn', '202'),
('23da009', 'Trần Thị Hồng', '2005-03-20', 'Female', '0332145906', 'hongtt.da@vku.udn.vn', '202'),
('23ai010', 'Lê Thị Duyên', '2005-05-25', 'Female', '0332145907', 'duyenlt.ai@vku.udn.vn', '202'),
('23it011', 'Phạm Thị Linh', '2005-07-30', 'Female', '0332145908', 'linhpt.it@vku.udn.vn', '202'),
('23da012', 'Nguyễn Thị Ánh', '2005-09-05', 'Female', '0332145909', 'anhnt.da@vku.udn.vn', '202'), -- Phòng 202: 5/8

('22it010', 'Trần Thị Bích', '2004-02-10', 'Female', '0332145910', 'bicht.it@vku.udn.vn', '203'),
('22da011', 'Lê Thị Cẩm', '2004-04-15', 'Female', '0332145911', 'camlt.da@vku.udn.vn', '203'),
('22ai012', 'Phạm Thị Đào', '2004-06-20', 'Female', '0332145912', 'daopt.ai@vku.udn.vn', '203'), -- Phòng 203: 3/4

('24it014', 'Nguyễn Thị Hạnh', '2006-01-05', 'Female', '0332145913', 'hanhnt.it@vku.udn.vn', '401'),
('24da015', 'Trần Thị Kim', '2006-03-10', 'Female', '0332145914', 'kimtt.da@vku.udn.vn', '401'),
('24ai016', 'Lê Thị Loan', '2006-05-15', 'Female', '0332145915', 'loanlt.ai@vku.udn.vn', '401'), -- Phòng 401: 3/4

('23it013', 'Phạm Thị Minh', '2005-02-10', 'Female', '0332145916', 'minhpt.it@vku.udn.vn', '402'),
('23da014', 'Nguyễn Thị Nhung', '2005-04-15', 'Female', '0332145917', 'nhungnt.da@vku.udn.vn', '402'),
('23ai015', 'Trần Thị Oanh', '2005-06-20', 'Female', '0332145918', 'oanhtt.ai@vku.udn.vn', '402'),
('23it016', 'Lê Thị Phượng', '2005-08-25', 'Female', '0332145919', 'phuonglt.it@vku.udn.vn', '402'), -- Phòng 402: 4/6

('22it013', 'Phạm Thị Quỳnh', '2004-01-05', 'Female', '0332145920', 'quynhpt.it@vku.udn.vn', '403'),
('22da014', 'Nguyễn Thị Sương', '2004-03-10', 'Female', '0332145921', 'suongnt.da@vku.udn.vn', '403'),
('22ai015', 'Trần Thị Thắm', '2004-05-15', 'Female', '0332145922', 'thamtt.ai@vku.udn.vn', '403'),
('22it016', 'Lê Thị Uyên', '2004-07-20', 'Female', '0332145923', 'uyenlt.it@vku.udn.vn', '403'),
('22da017', 'Phạm Thị Vân', '2004-09-25', 'Female', '0332145924', 'vanpt.da@vku.udn.vn', '403'); -- Phòng 403: 5/8

UPDATE rooms SET occupied = 4, status = 'Full' WHERE room_number = '101'; -- 4/4
UPDATE rooms SET occupied = 4, status = 'Available' WHERE room_number = '102'; -- 4/6
UPDATE rooms SET occupied = 5, status = 'Available' WHERE room_number = '103'; -- 5/8
UPDATE rooms SET occupied = 0, status = 'Available' WHERE room_number IN ('104', '105', '106', '107', '108', '109', '110'); -- Trống

UPDATE rooms SET occupied = 4, status = 'Available' WHERE room_number = '201'; -- 4/6
UPDATE rooms SET occupied = 5, status = 'Available' WHERE room_number = '202'; -- 5/8
UPDATE rooms SET occupied = 3, status = 'Available' WHERE room_number = '203'; -- 3/4
UPDATE rooms SET occupied = 0, status = 'Available' WHERE room_number IN ('204', '205', '206', '207', '208', '209', '210'); -- Trống

UPDATE rooms SET occupied = 5, status = 'Available' WHERE room_number = '301'; -- 5/8
UPDATE rooms SET occupied = 3, status = 'Available' WHERE room_number = '302'; -- 3/4
UPDATE rooms SET occupied = 0, status = 'Available' WHERE room_number IN ('303', '304', '305', '306', '307', '308', '309', '310'); -- Trống

UPDATE rooms SET occupied = 3, status = 'Available' WHERE room_number = '401'; -- 3/4
UPDATE rooms SET occupied = 4, status = 'Available' WHERE room_number = '402'; -- 4/6
UPDATE rooms SET occupied = 5, status = 'Available' WHERE room_number = '403'; -- 5/8
UPDATE rooms SET occupied = 0, status = 'Available' WHERE room_number IN ('404', '405', '406', '407', '408', '409', '410'); -- Trống

UPDATE rooms SET occupied = 4, status = 'Available' WHERE room_number = '501'; -- 4/6
UPDATE rooms SET occupied = 0, status = 'Available' WHERE room_number IN ('502', '503', '504', '505', '506', '507', '508', '509', '510'); -- Trống

INSERT INTO contracts (id, room_number, student_id, start_date, end_date, monthly_fee) VALUES 
-- Sinh viên nam (tầng 1, 3, 5)
('CON-20240901-001', '101', '24it001', '2024-09-01', '2025-09-01', 1200000.00), 
('CON-20240901-002', '101', '24it002', '2024-09-01', '2025-09-01', 1200000.00),
('CON-20240901-003', '101', '24da003', '2024-09-01', '2025-09-01', 1200000.00),
('CON-20240901-004', '101', '24ai004', '2024-09-01', '2025-09-01', 1200000.00),

('CON-20230901-001', '102', '23it001', '2023-09-01', '2024-09-01', 1000000.00),
('CON-20230901-002', '102', '23da002', '2023-09-01', '2024-09-01', 1000000.00),
('CON-20230901-003', '102', '23ai003', '2023-09-01', '2024-09-01', 1000000.00),
('CON-20230901-004', '102', '23it004', '2023-09-01', '2024-09-01', 1000000.00),

('CON-20220901-001', '103', '22it001', '2022-09-01', '2023-09-01', 800000.00), 
('CON-20220901-002', '103', '22da002', '2022-09-01', '2023-09-01', 800000.00),
('CON-20220901-003', '103', '22ai003', '2022-09-01', '2023-09-01', 800000.00),
('CON-20220901-004', '103', '22it004', '2022-09-01', '2023-09-01', 800000.00),
('CON-20220901-005', '103', '22da005', '2022-09-01', '2023-09-01', 800000.00),

('CON-20240901-005', '301', '24it005', '2024-09-01', '2025-09-01', 800000.00), 
('CON-20240901-006', '301', '24da006', '2024-09-01', '2025-09-01', 800000.00),
('CON-20240901-007', '301', '24ai007', '2024-09-01', '2025-09-01', 800000.00),
('CON-20240901-008', '301', '24it008', '2024-09-01', '2025-09-01', 800000.00),
('CON-20240901-009', '301', '24da009', '2024-09-01', '2025-09-01', 800000.00),

('CON-20230901-005', '302', '23it005', '2023-09-01', '2024-09-01', 1200000.00), 
('CON-20230901-006', '302', '23da006', '2023-09-01', '2024-09-01', 1200000.00),
('CON-20230901-007', '302', '23ai007', '2023-09-01', '2024-09-01', 1200000.00),

('CON-20220901-006', '501', '22it006', '2022-09-01', '2023-09-01', 1000000.00), 
('CON-20220901-007', '501', '22da007', '2022-09-01', '2023-09-01', 1000000.00),
('CON-20220901-008', '501', '22ai008', '2022-09-01', '2023-09-01', 1000000.00),
('CON-20220901-009', '501', '22it009', '2022-09-01', '2023-09-01', 1000000.00),

-- Sinh viên nữ (tầng 2, 4)
('CON-20240901-010', '201', '24it010', '2024-09-01', '2025-09-01', 1000000.00), 
('CON-20240901-011', '201', '24da011', '2024-09-01', '2025-09-01', 1000000.00),
('CON-20240901-012', '201', '24ai012', '2024-09-01', '2025-09-01', 1000000.00),
('CON-20240901-013', '201', '24it013', '2024-09-01', '2025-09-01', 1000000.00),

('CON-20230901-008', '202', '23it008', '2023-09-01', '2024-09-01', 800000.00), 
('CON-20230901-009', '202', '23da009', '2023-09-01', '2024-09-01', 800000.00),
('CON-20230901-010', '202', '23ai010', '2023-09-01', '2024-09-01', 800000.00),
('CON-20230901-011', '202', '23it011', '2023-09-01', '2024-09-01', 800000.00),
('CON-20230901-012', '202', '23da012', '2023-09-01', '2024-09-01', 800000.00),

('CON-20220901-010', '203', '22it010', '2022-09-01', '2023-09-01', 1200000.00), 
('CON-20220901-011', '203', '22da011', '2022-09-01', '2023-09-01', 1200000.00),
('CON-20220901-012', '203', '22ai012', '2022-09-01', '2023-09-01', 1200000.00),

('CON-20240901-014', '401', '24it014', '2024-09-01', '2025-09-01', 1200000.00), 
('CON-20240901-015', '401', '24da015', '2024-09-01', '2025-09-01', 1200000.00),
('CON-20240901-016', '401', '24ai016', '2024-09-01', '2025-09-01', 1200000.00),

('CON-20230901-013', '402', '23it013', '2023-09-01', '2024-09-01', 1000000.00),
('CON-20230901-014', '402', '23da014', '2023-09-01', '2024-09-01', 1000000.00),
('CON-20230901-015', '402', '23ai015', '2023-09-01', '2024-09-01', 1000000.00),
('CON-20230901-016', '402', '23it016', '2023-09-01', '2024-09-01', 1000000.00),

('CON-20220901-013', '403', '22it013', '2022-09-01', '2023-09-01', 800000.00),
('CON-20220901-014', '403', '22da014', '2022-09-01', '2023-09-01', 800000.00),
('CON-20220901-015', '403', '22ai015', '2022-09-01', '2023-09-01', 800000.00),
('CON-20220901-016', '403', '22it016', '2022-09-01', '2023-09-01', 800000.00),
('CON-20220901-017', '403', '22da017', '2022-09-01', '2023-09-01', 800000.00);