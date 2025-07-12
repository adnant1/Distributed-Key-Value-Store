# 🔐 Distributed Key-Value Store (DynamoDB-Inspired)

A lightweight distributed key-value store built in Java and Spring Boot. This project is inspired by the architecture of **Amazon DynamoDB** and features key distributed systems concepts such as **consistent hashing**, **data replication**, and **inter-node communication**.

> 🧠 Built to learn the fundamentals of distributed storage, fault tolerance, and scalable backend system design.

---

## 🚀 Features

### ✅ Consistent Hashing (Partitioning)

- Data is evenly distributed across nodes using a **consistent hashing ring**.
- When nodes join or leave, only a subset of keys need to be redistributed.

### 🧪 (In Progress) Replication for Fault Tolerance

- Goal: Replicate each key to **N nodes** for durability and availability.
- Lays the groundwork for **quorum-based consistency** and **Dynamo-style replication**.

### 🔁 Inter-Node Forwarding

- Every node acts as both a **coordinator and a storage node**.
- If a node receives a request for a key it doesn't own, it **forwards** the request to the correct node using consistent hashing.

---

## 📦 API Endpoints

### `PUT /db`

Stores a value under a key.

```http
PUT /db
Body: {
  "key": "username",
  "value": "adnant1"
}
```

Stores the key-value pair on the responsible node.

---

### `GET /db/{key}`

Retrieve the value for a key.

```http
GET /db/username
Response:
{
    "Item": {
        "key": {
            "S": "username"
        },
        "value": {
            "S": "adnant1"
        }
    }
}
```

Returns an `ItemResponse` object with key and value.

---

## 🧪 Setup & Run

### 🔧 Prerequisites

- Java 21+
- Maven
- Docker & Docker Compose

---

### ▶️ Running the System with Docker Compose

```bash
docker-compose up --build
```

Each node will:

- Start on its own port (e.g. 8081, 8082, 8083)
- Use `NODE_ID` and `NODE_IDS` for identifying other nodes

---

### 🛠 Setting Up Docker Compose

1. Clone the repository:

```bash
git clone https://github.com/adnant1/Distributed-Key-Value-Store.git
cd distributed-key-value-store
```

2. Review `docker-compose.yaml`:

   - Ensure each node has a unique `NODE_ID`
   - Configure `NODE_IDS` appropriately for the cluster

Example service block in `docker-compose.yaml`:

```yaml
node-a:
  build: .
  container_name: node-a
  environment:
    - NODE_ID=node-a
    - NODE_IDS=node-a,node-b,node-c,node-d,node-e
  ports:
    - "8081:8080"
```

Repeat with adjusted ports and IDs for other nodes.

3. Start the cluster:

```bash
docker-compose up --build
```

---

## 🗺 Architecture Overview

```
+----------+      +----------+      +----------+
|  Node 1  | <--> |  Node 2  | <--> |  Node 3  |
+----------+      +----------+      +----------+

- Nodes are connected via HTTP
- Use consistent hashing to determine key ownership
- Forward requests between nodes as needed
```

---

## 🔧 Configuration

| Variable   | Description                                                    |
| ---------- | -------------------------------------------------------------- |
| `NODE_ID`  | Unique identifier for this node                                |
| `NODE_IDS` | Comma-separated list of other node addresses, including itself |

---

## 🚧 In Progress

- 🔁 **Replication Layer**

  - Implementing multi-node replication of each key
  - Adds durability + fault tolerance
  - Based on concepts from Dynamo (e.g., N, R, W quorum)

---

## 👨‍💻 Author

**Adnan T.** — [@adnant1](https://github.com/adnant1)
