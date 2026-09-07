
# Tikito - Event Ticket Booking System

## Table of Contents

| Sr. No. | Content                          |
| --------|----------------------------------|
| 1       | Introduction                     |
| 2       | Tech Stack                       |
| 3       | Key Features                     |
| 4       | iOS Project Structure            |
| 5       | iOS Application Modules          |
| 6       | iOS Application Flow             |
| 7       | Future Enhancements & Conclusion |

## Introduction

Tikito is an end-to-end ticketing platform for events like movies, concerts, and stage plays, streamlining the reservation process for end-users. The system implements multi-venue browsing capabilities, allowing users to dynamically filter and select events based on schedule, and seating preferences. It optimizes system architecture to handle concurrent seat selection, preventing double-booking and ensuring data consistency.

## Tech Stack

- **Backend:** Java Programming Language, Spring-Boot (RESTful API)
- **Database:** MySQL
- **Frontend:** iOS Mobile Application, UIKit for user interface
- **Tools:** Postman for API testing

## Key Features

- **User Registration & Authentication**
  - User registration and login using **JWT-based authentication**.
  - Secure authentication and authorization for protected APIs.

- **Event Discovery**
  - Users can browse and select events based on different event types like movies, concerts, shows etc.

- **Show & Venue Selection**
  - Users can select their preferred **show timing**.
  - Users can choose a **venue/location** according to their preference.

- **Interactive Seat Selection**
  - User-friendly and interactive **seat layout**.
  - Users can select available seats before booking.
  - Prevents **double booking** of the same seat for the same show.

- **Password Management**
  - Users can **update their password**.
  - **Forgot Password** functionality is available for password recovery.

- **Persistent Data Storage**
  - Application data is persistently stored using **MySQL**.
  - Stores user, event, show, venue, seat, and booking-related information.

- **RESTful API**
  - Backend services are developed using **Spring Boot**.
  - Communication between the client and backend is handled through **RESTful APIs**.

- **User-Friendly Interface**
  - Visually pleasing and intuitive user interface.
  - Designed to provide a smooth event discovery and ticket-booking experience.

## iOS Project Structure

```text
Tikito/
├── Controllers/     → Handles UI screens, user interactions, and navigation
├── Models/          → Defines data models and api response and request models
├── Networking/      → Handles API communication through APIClient for GET, POST, PUT, DELETE, etc.
├── Services/        → Contains the service layer that uses URLSession and APIClient to perform application-specific API calls
└── Resources/       → Contains Storyboards, Assets, and other application resources
```

## iOS Application Modules

### Authentication
Handles user authentication and account-related functionality.

- Login
- JWT handling
- Keychain storage
- Profile API

### Events
Allows users to discover and filter events based on event categories.

- Event categories
- Events by category

### Shows
Allows users to select a suitable show based on date, venue, and timing.

- Event shows
- Date selection
- Venue filtering
- Show timings

### Booking
Handles the complete seat selection and ticket booking process.

- Venue seats
- Available seats
- Seat selection
- Ticket booking
- Booking confirmation

## iOS Application Flow

### Positive Flow — Login to Booking

```text
User
  ↓
Login Screen
  ↓
Enter Email & Password
  ↓
Login API
  ↓
JWT Authentication
  ↓
Credentials Valid?
  ├── No → Authentication Failed
  │
  └── Yes
        ↓
    JWT Token Generated
        ↓
    Token Stored on Client
        ↓
    Home Screen
        ↓
    Select Event Type
        ↓
    Select Event
        ↓
    Select Show Timing
        ↓
    Select Venue
        ↓
    View Seat Layout
        ↓
    Select Available Seat(s)
        ↓
    Booking API
        ↓
    JWT Token Sent with Request
        ↓
    Backend Validates Token
        ↓
    Seat Availability Checked
        ↓
    Booking Created
        ↓
    Booking Confirmation
```

### iOS Negative Flow — Authentication Failure
```text
User
  ↓
Login Screen
  ↓
Enter Invalid Email / Password
  ↓
Login API
  ↓
JWT Authentication
  ↓
Credentials Invalid
  ↓
Authentication Failed
  ↓
No JWT Token Generated
  ↓
User Remains Unauthenticated
  ↓
Protected API Request
  ↓
JWT Token Missing / Invalid
  ↓
Request Rejected
  ↓
Protected API Not Accessible
```

## Future Enhancements

- **Food & Beverage Ordering**
  - Allow users to order food and beverages such as **popcorn, snacks, and drinks** along with their tickets.

- **Payment Gateway Integration**
  - Integrate a payment gateway such as **Razorpay** for secure online ticket payments.
  - Add a mock payment flow for testing the booking and payment process.

- **My Tickets**
  - Add a **My Tickets** section where users can view all their previously booked tickets.
  - Display booking details such as event, show timing, venue, selected seats, and booking status.

## Conclusion

Tikito provides a complete event ticket-booking experience with **JWT-based authentication, event and show selection, venue selection, interactive seat booking, and double-booking prevention**. The application uses a **Spring Boot RESTful backend with MySQL** for persistent data storage and an **iOS UIKit frontend** to provide a user-friendly and visually pleasing experience.

Future enhancements such as **food and beverage ordering, Razorpay payment integration, and a My Tickets section** can further extend Tikito into a more complete event-booking platform.    