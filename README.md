# online-tours-and-travels-system
# 🌍 Online Tours and Travels System

A Java Servlet-based web application for booking bus and flight tickets online with admin management and Oracle database integration.

---

## 🚀 Overview

This project is a full-stack travel booking system that allows users to:

- Register and login securely  
- Search buses and flights  
- Book tickets  
- Make payments  
- View booking history  

Admins can manage buses, flights, and monitor bookings.

---

## 🏗 Architecture

**3-Tier Architecture**
- Presentation Layer – HTML, CSS  
- Business Logic – Java Servlets  
- Data Layer – Oracle Database  

---

## 🛠 Tech Stack

- Java Servlets  
- JDBC  
- Apache Tomcat  
- Oracle Database  
- HTML & CSS  

---

## 📂 Core Modules

- User Authentication  
- Bus Booking  
- Flight Booking  
- Payment Processing  
- Admin Management  

---

## 🗄 Database

Entities: User, Admin, Bus, Flight, Booking, Payment  

- One User → Multiple Bookings (1:N)  
- Each Booking → One Payment  
- Foreign keys maintain data integrity  

---

## ⚙ How to Run

1. Configure Oracle Database  
2. Deploy project on Apache Tomcat  
3. Access:

```
http://localhost:8080/online-tours-and-travels-system
```

---

## 🔐 Key Features

- Session management  
- Authentication & authorization  
- Structured relational database design  

---

## 👩‍💻 Developer

Sneha Shaw  
Academic Project
