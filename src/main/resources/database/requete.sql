-- Créer la séquence pour l'auto-incrémentation
CREATE SEQUENCE client_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE chambre_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE reservation_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE facture_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE service_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE reservation_service_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE employe_seq START WITH 1 INCREMENT BY 1;

-- Table Client
CREATE TABLE Client (
                        id NUMBER PRIMARY KEY,
                        nom VARCHAR2(100),
                        prenom VARCHAR2(100),
                        adresse VARCHAR2(100),
                        tel VARCHAR2(20),
                        email VARCHAR2(100),
                        nationalite VARCHAR2(50)
);

-- Trigger pour l'auto-incrémentation de l'ID dans Client
CREATE OR REPLACE TRIGGER client_trigger
BEFORE INSERT ON Client
FOR EACH ROW
BEGIN
    :new.id := client_seq.NEXTVAL;
END;
/

-- Table Chambre
CREATE TABLE Chambre (
                         id NUMBER PRIMARY KEY,
                         numero VARCHAR2(10) UNIQUE,
                         type VARCHAR2(50),
                         prix DECIMAL(10,2),
                         disponibilite NUMBER(1)
);

-- Trigger pour l'auto-incrémentation de l'ID dans Chambre
CREATE OR REPLACE TRIGGER chambre_trigger
BEFORE INSERT ON Chambre
FOR EACH ROW
BEGIN
    :new.id := chambre_seq.NEXTVAL;
END;
/

-- Table Reservation
CREATE TABLE Reservation (
                             id NUMBER PRIMARY KEY,
                             date_debut DATE,
                             date_fin DATE,
                             statut VARCHAR2(50),
                             id_client NUMBER,
                             id_chambre NUMBER,
                             FOREIGN KEY (id_client) REFERENCES Client(id),
                             FOREIGN KEY (id_chambre) REFERENCES Chambre(id)
);

-- Trigger pour l'auto-incrémentation de l'ID dans Reservation
CREATE OR REPLACE TRIGGER reservation_trigger
BEFORE INSERT ON Reservation
FOR EACH ROW
BEGIN
    :new.id := reservation_seq.NEXTVAL;
END;
/

-- Table Facture
CREATE TABLE Facture (
                         id NUMBER PRIMARY KEY,
                         montant_total DECIMAL(10,2),
                         date_paiement DATE,
                         mode_paiement VARCHAR2(50),
                         file_name VARCHAR2(255),
                         id_reservation NUMBER,
                         FOREIGN KEY (id_reservation) REFERENCES Reservation(id)
);

-- Trigger pour l'auto-incrémentation de l'ID dans Facture
CREATE OR REPLACE TRIGGER facture_trigger
BEFORE INSERT ON Facture
FOR EACH ROW
BEGIN
    :new.id := facture_seq.NEXTVAL;
END;
/

-- Table Service
CREATE TABLE Service (
                         id NUMBER PRIMARY KEY,
                         nom VARCHAR2(100),
                         description CLOB,
                         prix DECIMAL(10,2)
);

-- Trigger pour l'auto-incrémentation de l'ID dans Service
CREATE OR REPLACE TRIGGER service_trigger
BEFORE INSERT ON Service
FOR EACH ROW
BEGIN
    :new.id := service_seq.NEXTVAL;
END;
/

-- Table Reservation_Service
CREATE TABLE Reservation_Service (
                                     id_reservation NUMBER,
                                     id_service NUMBER,
                                     PRIMARY KEY (id_reservation, id_service),
                                     FOREIGN KEY (id_reservation) REFERENCES Reservation(id),
                                     FOREIGN KEY (id_service) REFERENCES Service(id)
);

-- Table Employe
CREATE TABLE Employe (
                         id NUMBER PRIMARY KEY,
                         nom VARCHAR2(100),
                         poste VARCHAR2(100),
                         prenom VARCHAR2(100),
                         email VARCHAR2(100),
                         salaire DECIMAL(10,2),
                         adresse CLOB,
                         telephone VARCHAR2(20),
                         id_service NUMBER,
                         FOREIGN KEY (id_service) REFERENCES Service(id)
);

-- Trigger pour l'auto-incrémentation de l'ID dans Employe
CREATE OR REPLACE TRIGGER employe_trigger
BEFORE INSERT ON Employe
FOR EACH ROW
BEGIN
    :new.id := employe_seq.NEXTVAL;
END;
/
