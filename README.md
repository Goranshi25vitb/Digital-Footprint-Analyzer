# Digital-Footprint-Analyzer
Digital Footprint Analyzer is a Java-based privacy risk assessment system that analyzes users’ online exposure, social media activity, location sharing, account security, and app permissions. It calculates privacy risk scores and provides personalized recommendations to help users improve their digital privacy and security.

#  Digital Footprint Analyzer & Privacy Risk Assessment System

A Java Swing-based desktop application that analyzes a user's digital footprint and privacy habits, calculates privacy risk, and provides personalized recommendations to improve online privacy and security.

##  Project Overview

In today's digital world, people share a large amount of personal information through social media, applications, online accounts, and location services. This information can create privacy and security risks when it is unnecessarily exposed.

The **Digital Footprint Analyzer** helps users understand their level of digital exposure through a structured privacy assessment. It evaluates different privacy and security practices and generates an easy-to-understand risk analysis.

The project uses an **explainable rule-based risk assessment system** to calculate a Privacy Risk Score and provide practical recommendations.

##  Objectives

- Analyze common digital privacy risks
- Identify areas of excessive personal information exposure
- Evaluate account security practices
- Assess location-sharing habits
- Analyze social media exposure
- Evaluate application permissions and account hygiene
- Generate personalized privacy recommendations
- Improve users' awareness of online privacy and security

##  Key Features

-  Privacy Risk Assessment
-  Privacy Risk Score (0–100)
-  Privacy Score
-  Category-wise Risk Analysis
-  Social Media Exposure Analysis
-  Location Privacy Analysis
-  Password & Account Security Assessment
-  Application Permission Analysis
-  Unused Account Analysis
-  Personalized Privacy Recommendations
-  Detailed Risk Report
-  CSV-based Assessment History
-  Risk Improvement Simulator
-  Privacy Education Section
-  Java Swing Graphical User Interface
-  Local file-based data storage
-  Single-file Java implementation
-  No external libraries required

##  Risk Categories

The application evaluates six major privacy categories:

| Category | Weight |
|---|---:|
| Social Media Exposure | 20% |
| Account Security | 25% |
| Location Privacy | 20% |
| Personal Information | 15% |
| Account Hygiene | 10% |
| Application Privacy | 10% |

The system combines these category scores to calculate the overall **Risk Score**.

### Risk Levels

| Risk Score | Risk Level |
|---:|---|
| 0–20 | LOW |
| 21–40 | MODERATE |
| 41–60 | MEDIUM |
| 61–80 | HIGH |
| 81–100 | CRITICAL |

The **Privacy Score** is calculated based on the overall risk score.

##  How the Risk Assessment Works

The project uses a **rule-based decision system** instead of automatically accessing users' online accounts.

The user answers questions related to their privacy and security habits. Each response contributes a certain amount of risk based on its potential privacy impact.

```text
User Assessment
       ↓
Response Processing
       ↓
Category Risk Calculation
       ↓
Overall Risk Score
       ↓
Risk Level
       ↓
Personalized Recommendations

### Privacy & Security

The application is designed as a privacy-awareness tool.

It does not require or store:

-  Real passwords
-  OTPs
-  Login credentials
-  Social media passwords
-  API keys
-  Banking information

Users should use fictional or general information when demonstrating the project.
