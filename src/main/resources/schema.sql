CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role ENUM('Patient', 'Doctor', 'Administrator','Staff') NOT NULL,
    ContactInfo VARCHAR(255),
    YearOfBirth INT,
    MonthOfBirth INT,
    DayOfBirth INT,
    BloodGroup VARCHAR(3),
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    Age INT,
    Email VARCHAR(255),
    Address TEXT,
    Gender ENUM('Male', 'Female', 'Other')
);

CREATE TABLE Patients (
    PatientID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    Diseases TEXT,
    EmergencyContactAddress TEXT,
    EmergencyContactPhone VARCHAR(20),
    EmergencyContactName VARCHAR(255),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);



CREATE TABLE Departments (
    DepartmentID INT PRIMARY KEY AUTO_INCREMENT,
    DepartmentName VARCHAR(255) NOT NULL,
    Description TEXT
);

CREATE TABLE Doctors (
    DoctorID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    Specialization VARCHAR(255),
    DepartmentID INT,
    Fees DECIMAL(10, 2),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID),
    Rating INT
);

CREATE TABLE Appointments (
    AppointmentID INT PRIMARY KEY AUTO_INCREMENT,
    PatientID INT,
    DoctorID INT,
    AppointmentDate DATE,
    Time TIME,
    Status ENUM('Pending', 'Approved', 'Completed'),
    FOREIGN KEY (PatientID) REFERENCES Patients(PatientID),
    FOREIGN KEY (DoctorID) REFERENCES Doctors(DoctorID)
);

CREATE TABLE Bills (
    BillID INT PRIMARY KEY AUTO_INCREMENT,
    PatientID INT,
    AppointmentID INT,
    Amount DECIMAL(10, 2),
    DateIssued DATE,
    TransactionID VARCHAR(255),
    FOREIGN KEY (PatientID) REFERENCES Patients(PatientID),
    FOREIGN KEY (AppointmentID) REFERENCES Appointments(AppointmentID)
);

CREATE TABLE Prescriptions (
    PrescriptionID INT PRIMARY KEY AUTO_INCREMENT,
    AppointmentID INT,
    Dosage TEXT,
    FOREIGN KEY (AppointmentID) REFERENCES Appointments(AppointmentID)
);

CREATE TABLE Feedbacks (
    FeedbackID INT PRIMARY KEY AUTO_INCREMENT,
    AppointmentID INT,
    Rating INT CHECK (Rating BETWEEN 1 AND 5),
    Comments TEXT,
    FeedbackDate DATE,
    FOREIGN KEY (AppointmentID) REFERENCES Appointments(AppointmentID)
);

CREATE TABLE Staff (
    StaffID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    Designation VARCHAR(255),
    Salary DECIMAL(10, 2),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE Notifications (
    NotificationID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    AppointmentID INT,
    Message TEXT,
    DateSent DATE,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (AppointmentID) REFERENCES Appointments(AppointmentID)
);

CREATE TABLE Weekly_Statistics (
    StatID INT PRIMARY KEY AUTO_INCREMENT,
    Week VARCHAR(10),
    TotalAppointments INT,
    TotalIncome DECIMAL(10, 2),
    TotalUsersRegistered INT
);

CREATE TABLE Monthly_Statistics (
    StatID INT PRIMARY KEY AUTO_INCREMENT,
    MonthName VARCHAR(50),
    TotalAppointments INT,
    TotalIncome DECIMAL(10, 2),
    TotalUsersRegistered INT
);


CREATE TABLE qualification (
    DoctorID INT,
    Degree VARCHAR(255),
    PRIMARY KEY (DoctorID, Degree),
    FOREIGN KEY (DoctorID) REFERENCES doctors(DoctorID)
);

CREATE TABLE medication_detail (
    PrescriptionID INT,
    Advice Varchar(50),
    PRIMARY KEY (PrescriptionID, Advice),
    FOREIGN KEY (PrescriptionID) REFERENCES prescriptions(PrescriptionID)
);

CREATE TABLE timeslots (
    DoctorID INT,
    Timeslot TIME,
    workday varchar(
    10),
    PRIMARY KEY (DoctorID, Timeslot,workday),
    FOREIGN KEY (DoctorID) REFERENCES Doctors(DoctorID)
);

CREATE TABLE descriptions (
    AppointmentID INT,
    Problem varchar(50),
    Primary key(AppointmentID,Problem),
    FOREIGN KEY (AppointmentID) REFERENCES appointments(AppointmentID)
);