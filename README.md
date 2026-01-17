# FinOr AI Invoice Reconciliation Platform

FinOr is a full-stack finance automation platform that streamlines invoice ingestion, OCR extraction, GL mapping, bank transaction matching, reconciliation audit trails, and reporting APIs. The backend is implemented in Spring Boot with MySQL; a React dashboard UI is planned as the frontend layer.

## Problem Statement

Finance teams frequently spend significant time on:
- Extracting data from invoice PDFs and images
- Classifying expenses into GL categories
- Matching invoice payments against bank transactions
- Generating reconciliation evidence for audit and reporting

FinOr automates these tasks through a structured pipeline and exposes APIs suitable for integration into a dashboard or ERP workflows.

## Core Features

### Invoice Processing Pipeline
- Upload invoice files (PDF/Image)
- OCR extraction using Tesseract
- Structured parsing:
  - vendor name
  - invoice number
  - total amount
- GL mapping:
  - category
  - GL code and GL name
  - confidence score
- Reconciliation against bank transactions
- Audit API combining invoice and matched transaction evidence

### Reporting APIs
- Reconciliation KPI summary
- Category-wise expense totals
- Vendor-wise totals
- Month-wise totals

## Architecture

The application uses a layered backend architecture:

- Controller layer: REST endpoints
- Service layer: business logic and orchestration
- Repository layer: persistence via Spring Data JPA
- Database layer: MySQL storage

Invoice lifecycle workflow:

`UPLOADED → EXTRACTED → MAPPED → RECONCILED / NOT_RECONCILED`

## Tech Stack

Backend:
- Spring Boot 4.x
- Spring Web (REST)
- Spring Data JPA / Hibernate
- MySQL 8.x
- PDFBox (PDF rendering)
- Tess4J + Tesseract OCR

Frontend (planned):
- React dashboard
- Pipeline UI and finance reports views
