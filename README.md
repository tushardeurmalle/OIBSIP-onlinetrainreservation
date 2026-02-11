# 🚆 Online Train Reservation System

A Java-based desktop application developed using **Java Swing** and **MySQL** for managing online train reservations.

---

## 📌 Project Description

The Online Train Reservation System allows users to:

- Login securely
- Book train tickets
- Cancel reservations using PNR
- Store and manage reservation data in MySQL database

This project was developed as part of the **Oasis Infobyte Internship (OIBSIP)**.

---

## 🛠 Technologies Used

- Java (Core Java)
- Java Swing (GUI)
- MySQL Database
- JDBC Connectivity
- Git & GitHub

---

## 💻 Features

✔ User Login Authentication  
✔ Train Ticket Reservation  
✔ Ticket Cancellation using PNR  
✔ Database Integration  
✔ Professional Modern UI  
✔ PreparedStatement for Secure Queries  

---

## 🗄 Database Structure

### 🔹 users table

| Column     | Type        |
|------------|------------|
| id         | int (PK)   |
| username   | varchar    |
| password   | varchar    |

### 🔹 reservations table

| Column        | Type        |
|--------------|------------|
| pnr          | int (PK)   |
| name         | varchar    |
| train_no     | varchar    |
| train_name   | varchar    |
| class_type   | varchar    |
| journey_date | date       |
| source       | varchar    |
| destination  | varchar    |

---

## ⚙ How to Run the Project

1. Install MySQL and create database:
   ```sql
   CREATE DATABASE online_reservation;
