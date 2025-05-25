# H-CLUS: Agglomerative Hierarchical Clustering

H-CLUS is a Java application designed to perform agglomerative hierarchical clustering on datasets. It allows users to discover dendrograms from data stored in a MySQL database, visualize them, and save/load them from files. The project supports both single-link and average-link distance metrics for cluster merging and offers both a text-based console interface (Base Version) and a JavaFX-based graphical user interface (Extended Version).

## Table of Contents

1.  [Overview](#overview)
2.  [Features](#features)
3.  [Technology Stack](#technology-stack)
4.  [Prerequisites](#prerequisites)
5.  [Setup and Installation](#setup-and-installation)
    *   [Database Setup](#database-setup)
    *   [IDE Configuration (for development)](#ide-configuration-for-development)
6.  [Running the Application](#running-the-application)
    *   [1. Server (MultiServer)](#1-server-multiserver)
    *   [2. Client Applications](#2-client-applications)
        *   [Base Version (Text-based)](#base-version-text-based)
        *   [Extended Version (GUI)](#extended-version-gui)
7.  [Usage](#usage)
    *   [Base Version](#base-version)
    *   [Extended Version (GUI)](#extended-version-gui-1)
8.  [Error Handling](#error-handling)
9.  [Authors](#authors)

## Overview

The H-CLUS project implements an agglomerative hierarchical clustering algorithm. It starts by treating each data point (transaction) as an individual cluster. It then iteratively merges the closest pair of clusters until all data points belong to a single cluster. The process generates a dendrogram, which is a tree-like diagram illustrating the sequence of merges and the similarity levels at which they occur. This dendrogram can then be "cut" at a desired level to obtain a specific number of clusters (k).

**Key Concepts:**
*   **Data:** A collection of transactions, where each transaction is a vector of numerical attribute values.
*   **Distance Calculation:** Euclidean distance is used between individual data points.
*   **Cluster Linkage:**
    *   **Single-link:** The distance between two clusters is the minimum distance between any two points in the different clusters.
    *   **Average-link:** The distance between two clusters is the average distance between all pairs of points in the different clusters.

## Features

*   Implementation of Agglomerative Hierarchical Clustering.
*   Supports Euclidean distance for point-to-point comparison.
*   Offers Single-link and Average-link methods for inter-cluster distance.
*   Dendrogram generation and textual visualization.
*   Ability to specify the desired depth of the dendrogram to be displayed/saved.
*   Data loading from a MySQL database.
*   Saving discovered dendrograms to files for later retrieval.
*   Loading pre-computed dendrograms from files.
*   Client-Server architecture with a multithreaded server capable of handling multiple client connections.
*   Two distinct client interfaces:
    *   **Base Version:** A text-based console application.
    *   **Extended Version:** A user-friendly JavaFX graphical user interface.
*   Comprehensive error handling for various scenarios.

## Technology Stack

*   **Programming Language:** Java (developed with JDK 21, compatible with JRE 1.8+)
*   **Database:** MySQL (utilizes MySQL Workbench 8.0 CE for interaction)
*   **GUI Framework (Extended Version):** JavaFX, SceneBuilder
*   **Networking:** Java Sockets for client-server communication.
*   **Concurrency:** Java Threads for the multithreaded server.

## Prerequisites

*   **Java Development Kit (JDK) or Java Runtime Environment (JRE):**
    *   JRE version 1.8 or higher is required to run the application.
    *   JDK 21 was used for development.
    *   Download Java: [https://www.java.com/download/](https://www.java.com/download/)
*   **MySQL Server:** An instance of MySQL server must be running.
*   **MySQL Connector/J:** The JDBC driver for MySQL (e.g., `mysql-connector-java-8.0.17.jar`). This needs to be in the classpath or configured in your IDE.
*   **JavaFX SDK (for Extended Version only):** If running or developing the Extended (GUI) version, the JavaFX SDK is required.

## Setup and Installation

### Database Setup
1.  Ensure your MySQL server is running.
2.  Execute the `createDB.sql` script (mentioned in the documentation, typically provided with the project source) on your MySQL server. This script will create the necessary database and/or tables for the application to store and retrieve data.

### IDE Configuration (for development)

If you are setting up the project in an IDE like IntelliJ IDEA:

1.  **MySQL Driver:**
    *   Add the `mysql-connector-java-X.X.XX.jar` to your project's dependencies.
    *   In IntelliJ: `File` -> `Project Structure` -> `Modules` -> select your module -> `Dependencies` tab -> `+` -> `JARs or directories...` -> navigate to and select the MySQL connector JAR.

2.  **JavaFX SDK (for Extended Version development):**
    *   **Add JavaFX Library:**
        *   `File` -> `Project Structure` -> `Libraries` -> `+` -> `Java` -> navigate to the `lib` folder of your JavaFX SDK.
    *   **Add VM Options:** When running the Extended version's main class, you need to add VM options:
        *   Go to `Run` -> `Edit Configurations...`.
        *   Select your run configuration for the Extended version.
        *   In `VM options`, add:
          ```
          --module-path /path/to/your/javafx-sdk-VERSION/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base
          ```
          Replace `/path/to/your/javafx-sdk-VERSION/lib` with the actual path to your JavaFX SDK's lib folder.

## Running the Application

The application consists of a server component and two possible client components (Base or Extended). **The server must be started before any client can connect.**

### 1. Server (MultiServer)

*   **From IDE:**
    1.  Open the `MultiServer.java` file (or equivalent main server class).
    2.  Configure its run configuration to accept a port number as a program argument (e.g., `8080`). Default range: 1025-65535.
    3.  Run the `MultiServer` configuration.
*   **Using pre-built JAR (if available):**
    1.  Navigate to the output directory (e.g., `ServerH-CLUS/out/artifacts/ServerH-CLUS_jar/`).
    2.  Execute `ServerH-CLUS.bat`. This script likely starts the server on a pre-configured port (e.g., 8080).

    You should see a confirmation message like "Server attivato." (Server activated).

### 2. Client Applications

#### Base Version (Text-based)

*   **From IDE:**
    1.  Open the `MainTest.java` file (or equivalent main client class for the base version).
    2.  Configure its run configuration to accept the server's IP address and port number as program arguments (e.g., `127.0.0.1 8080`).
    3.  Run the `MainTest` configuration.
*   **Using pre-built JAR (if available):**
    1.  Navigate to the output directory (e.g., `ClientH-CLUS/out/artifacts/ClientH-CLUS_jar/`).
    2.  Execute `ClientH-CLUS.bat`. This script might prompt for IP/Port or use defaults.

#### Extended Version (GUI)

*   **From IDE:**
    1.  Ensure JavaFX VM options are correctly set in the run configuration for the main class of the Extended version (e.g., `Extension.java` or similar).
    2.  Run this configuration.
*   **Using pre-built JAR (if available):**
    1.  Navigate to the output directory (e.g., `Extension/out/artifacts/Extension_jar/`).
    2.  Execute `Extension.bat`.

## Usage

### Base Version
Once the client is connected to the server:
1.  **Enter Table Name:** You'll be prompted to enter the name of the database table to work with (e.g., `exampleTab`).
2.  **Choose Option:**
    *   `(1) Carica Dendrogramma da File` (Load Dendrogram from File)
    *   `(2) Apprendi Dendrogramma da Database` (Learn Dendrogram from Database)
3.  **If Learning from Database (Option 2):**
    *   **Enter Depth:** Input the desired depth for the dendrogram.
    *   **Choose Distance Metric:**
        *   `(1)` for Single-link
        *   `(2)` for Average-link
    *   The dendrogram will be displayed level by level.
    *   You will then be prompted to enter a filename (including extension, e.g., `prova.txt`) to save the discovered dendrogram.
4.  **If Loading from File (Option 1):**
    *   **Enter Filename:** Input the name of the file (including extension, e.g., `prova.txt`) containing the previously saved dendrogram.
    *   The dendrogram from the file will be displayed.
5.  Press any key to continue/exit after an operation.

### Extended Version (GUI)

1.  **Connection Screen:**
    *   Enter the `IP Address` of the server (e.g., `127.0.0.1`).
    *   Enter the `Port Number` the server is listening on (e.g., `8080`).
    *   Click `Connetti` (Connect).
2.  **Main Screen (Schermata Principale):**
    *   Choose an option:
        *   `Lettura` (Read/Load from file)
        *   `Scoperta` (Discover from database)
3.  **If "Scoperta" (Discover):**
    *   **Enter Table Name:** Input the database table name (e.g., `exampleTab`) and click `Inserisci` (Insert).
    *   **Enter Dendrogram Depth:** Input the desired depth for the dendrogram.
    *   **Choose Distance Metric:** Click `Single-Link` or `Average-Link`.
    *   A new window will appear displaying the discovered dendrogram.
    *   **Save Dendrogram:** In this new window, enter a `File name` (e.g., `prova.txt`) and click `Salva` (Save).
    *   Close this window to return to the depth/distance selection, or click the "home" icon to return to the main `Lettura`/`Scoperta` screen.
4.  **If "Lettura" (Read/Load):**
    *   **Enter Filename:** Input the name of the file (e.g., `prova.txt`) containing the dendrogram and click `Esegui` (Execute).
    *   A new window will display the dendrogram from the file.
    *   Click the "home" icon to return to the main `Lettura`/`Scoperta` screen.

## Error Handling

The application includes various checks and provides informative error messages for common issues, including:
*   Server not active or connection refused.
*   Incorrect database table name (table not found).
*   Invalid dendrogram depth (out of range, non-numeric).
*   File not found when trying to load a dendrogram.
*   Invalid IP address or port number format (GUI version).
*   Other I/O or database connectivity problems.

## Authors

*   **Emanuele Fornaro** (mat.: 738721, e.fornaro4@studenti.uniba.it)
*   **Alessandro Boffolo Aldo** (mat.: 735963, a.boffolo@studenti.uniba.it)
*   **Vincenzo Pio Eraclea** (mat.: 775561, v.eraclea@studenti.uniba.it)
