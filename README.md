# Car Dealership Inventory Manager

A console-based Java application for managing a car dealership's vehicle inventory. Inventory data is persisted to a local CSV file.

## Features

- Search vehicles by price range, make/model, year range, color, mileage, or type
- View the full inventory
- Add and remove vehicles
- All changes are automatically saved to the inventory file

## Project Structure

| File | Description |
|------|-------------|
| `Vehicle.java` | Data model representing a single vehicle |
| `Dealership.java` | Holds the inventory and provides search/filter methods |
| `DealershipFileManager.java` | Reads and writes inventory data to `inventory.csv` |
| `UserInterface.java` | Handles all console input/output and user interaction |
| `Program.java` | Entry point — launches the UI |

## Data File

Inventory is stored at `src/main/resources/inventory.csv` using a pipe-delimited (`|`) format:

```
Dealership Name|Address|Phone
VIN|Year|Make|Model|Type|Color|Odometer|Price
```

## Getting Started

1. Clone or download the repository
2. Ensure `src/main/resources/inventory.csv` exists with at least a dealership header line
3. Compile all `.java` files:
   ```bash
   javac *.java
   ```
4. Run the application:
   ```bash
   java Program
   ```

## Usage

On launch, a menu is displayed with the following options:

```
1  - Search by price range
2  - Search by make and model
3  - Search by year range
4  - Search by color
5  - Search by mileage range
6  - Search by vehicle type
7  - View all vehicles
8  - Add a vehicle
9  - Remove a vehicle
99 - Exit
```

Supported vehicle types: `car`, `truck`, `SUV`, `van`
