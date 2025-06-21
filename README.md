# 🚀 Event-Driven Big Data Pipeline — Real-Time Weather Alerts

## 📌 Project Overview

This project demonstrates a **real-time streaming pipeline** using:
- **Apache Kafka** — for event ingestion
- **Scala Producer** — sends live or fake weather data
- **Apache Spark Structured Streaming** — consumes, analyzes & detects extreme weather conditions
- **Smart Alert Logic** — triggers heatwave, frost, storm, or heavy rain alerts
- **Logs ALL events** (including normal weather) with timestamps

> ✅ **Goal:** Show end-to-end Big Data Engineering + Real-Time Analytics in a clean, production-like way.

---

## ⚙️ Tech Stack

| Tool          | Purpose                           |
| --------------| --------------------------------- |
| Scala         | Data Producer + Consumer Logic   |
| Kafka         | Message Queue / Event Hub        |
| Spark         | Real-Time Processing             |
| Docker        | Easy local Kafka & Zookeeper     |
| Weatherbit API | Free real weather data or fake JSON |

---

## 📂 Project Structure

event-driven-big-data-pipeline/

├── producer/ # Scala producer app
├── spark-consumer/ # Scala Spark streaming consumer
├── docker-compose.yml # Kafka + Zookeeper setup
├── README.md # This file
├── .gitignore # Ignores alerts_output

---

## 🚀 How to Run

### 1️⃣ Start Kafka & Zookeeper

```bash
docker-compose up -
