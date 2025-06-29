# Bill Payment Service API Documentation

## Overview
This is a comprehensive **Bill Payment Service API** that allows users to:

- Discover available bill payment categories and billers
- View products/services offered by each biller
- Make payments for various services (e.g., electricity, cable TV, internet)

The system integrates with a gateway service for routing and includes **robust API key security**.

---

## Authentication

All endpoints require a valid API key.

**Header:**
X-API-KEY: your-secret-key

---

## API Endpoints

### 1. Get All Biller Categories

**Endpoint:**  
GET /api/bills/categories

**Description:**  
Returns all available bill payment categories.

**Headers:**
X-API-KEY: your-secret-key

**Success Response:**
{
  "flag": true,
  "code": "00",
  "message": "Successful",
  "result": [
    "Electricity",
    "Cable",
    "Internet",
    "Exams",
    "Betting",
    "Airtime",
    "Data"
  ]
}

**Unauthorized Response:**
{
  "flag": false,
  "code": "03",
  "message": "Unauthorized"
}

---

### 2. Get Billers by Category

**Endpoint:**  
GET /api/bills/category/{category}

**Description:**  
Returns billers available for a specific category.

**Path Parameter:**  
category: One of ELECTRICITY, CABLE_TV, INTERNET, EXAMS_AND_RESULTS, BETTING, AIRTIME, DATA

**Headers:**  
X-API-KEY: your-secret-key

**Success Response (Example - ELECTRICITY):**
{
  "flag": true,
  "code": "00",
  "result": [
    "IKEDC",
    "EKEDC",
    "AEDC",
    "KEDCO"
  ]
}

**Invalid Category Response:**
{
  "flag": false,
  "code": "04",
  "message": "Invalid category"
}

---

### 3. Get Products by Biller

**Endpoint:**  
GET /api/bills/{biller}/products

**Description:**  
Returns products/services offered by a specific biller.

**Path Parameter:**  
biller: Name of the biller (e.g., "IKEDC", "DSTV")

**Headers:**  
X-API-KEY: your-secret-key

**Success Response (Example - DSTV):**
{
  "flag": true,
  "code": "00",
  "result": [
    "Compact",
    "Premium",
    "Yanga",
    "Padi"
  ]
}

---

### 4. Make Payment

**Endpoint:**  
POST /api/bills/make-payment

**Description:**  
Processes a bill payment transaction.

**Headers:**
X-API-KEY: your-secret-key  
Content-Type: application/json

**Request Body:**
{
  "biller": "IKEDC",
  "product": "Prepaid Token",
  "amount": 5000.00,
  "sourceAccountNo": "1234567890",
  "sourceAccountName": "John Doe",
  "description": "July 2023 electricity payment",
  "channel": "MOBILE"
}

**Success Response:**
{
  "flag": true,
  "code": "00",
  "message": "Payment successful",
  "result": "a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8"
}

**Insufficient Funds Response:**
{
  "flag": false,
  "code": "05",
  "message": "Insufficient account balance"
}

---

## Technical Details

### Transaction Table Schema
The system uses a `Transaction` entity that captures:

- Reference number
- Amount and charges
- Beneficiary and source info
- Transaction type and category
- Status
- Timestamps

---

## Enumerations

**BillerCategory:**  
ELECTRICITY, CABLE_TV, INTERNET, EXAMS_AND_RESULTS, BETTING, AIRTIME, DATA

**TrxCategory:**  
Transaction categories (e.g., Bill Payment, Airtime Top-up)

**TrxType:**  
DEBIT, CREDIT, REVERSAL

**Status:**  
SUCCESS, PENDING, FAILED, REVERSED

**Channel:**  
MOBILE, WEB, USSD

---

## Security

All endpoints are secured using **API Key Authentication**.  
Include the following header in all requests:

X-API-KEY: your-secret-key

Ensure this matches the configured secret key in your application properties.
