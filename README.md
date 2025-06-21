# Event-Driven Big Data Pipeline (Local Setup)

🚀 A fully local project to showcase event-driven data pipelines using Kafka, Spark Structured Streaming, and Airflow.

## 📌 Architecture

Kafka (local) → Spark Structured Streaming (local) → Local Folder or MinIO (S3-compatible) → Orchestrated by Airflow (local)

## ⚙️ Tech Stack

- Apache Kafka (local)
- Apache Spark Structured Streaming (local)
- Apache Airflow (local)
- Local Storage or MinIO

## ✅ Steps

1. Kafka producer generates logs
2. Spark streaming job consumes and processes logs
3. Processed data saved to local folder or MinIO bucket
4. Airflow orchestrates end-to-end flow

*No cloud costs. 100% local development.*
