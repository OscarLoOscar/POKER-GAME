# Big Two Poker Game

A multi-player Big Two poker game implemented using Spring Boot and WebSocket, allowing players to join a game, play cards, and interact in real-time.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Game Rules](#game-rules)
- [API Endpoints](#api-endpoints)
- [WebSocket Communication](#websocket-communication)
- [Development](#development)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## Introduction

Big Two is a card game popular in East Asia. The objective is to be the first to play all your cards by forming valid combinations according to the rules.

This project implements a real-time Big Two game server using Spring Boot. Players can connect to the server, join a game, and play cards using a web-based interface.

## Features

- Real-time multiplayer gameplay with WebSocket
- Player management and game state tracking
- Support for card sorting and combination validation
- Automated game state broadcasting
- Robust exception handling and input validation

## Technologies Used

- **Spring Boot**: Application framework
- **WebSocket**: Real-time communication
- **Java**: Programming language
- **Maven**: Dependency management

## Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven** for dependency management

### Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/big-two-poker-game.git
    cd big-two-poker-game
    ```

2. **Build the project**:
    ```bash
    mvn clean install
    ```

3. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

### Access the Game

Open your browser and navigate to `http://localhost:8080` to access the game interface.

## Usage

### Joining a Game

1. Open the game interface.
2. Enter a unique player ID and name.
3. Click "Join Game" to participate.

### Playing Cards

1. Select the cards you want to play.
2. Click "Play Cards" to submit your move.
3. Alternatively, click "Pass" if you cannot or do not want to play cards.

### Sorting Cards

- Use the "Sort by Rank" or "Sort by Suit" buttons to organize your hand.

## Game Rules

- Players take turns to play combinations of cards (e.g., single, pair, triple).
- The played combination must be higher in rank than the previous one.
- Players can pass if they cannot or choose not to play.
- The goal is to be the first player to empty your hand.

For detailed rules, refer to [Big Two Rules](https://en.wikipedia.org/wiki/Big_Two).

## API Endpoints

### WebSocket Endpoints

- **`/join`**: Join a game with a player ID and name.
- **`/play`**: Play selected cards.
- **`/pass`**: Pass your turn.
- **`/sort-by-rank`**: Sort cards in hand by rank.
- **`/sort-by-suit`**: Sort cards in hand by suit.

### REST Endpoints

(Not implemented in this version, can be added for future extension)

## WebSocket Communication

WebSocket provides real-time interaction between the client and server. Here are the key topics and messages:

- **`/topic/game-state`**: Broadcasts the current game state to all players.
- **`/app/join`**: Sends a join request to the server.
- **`/app/play`**: Sends a play cards request to the server.
- **`/app/pass`**: Sends a pass turn request to the server.

## Development

### Project Structure

- **`/src/main/java/com/bc2405p/poker_game`**: Main codebase.
  - **Controller**: Handles WebSocket messages and REST endpoints.
  - **Service**: Contains business logic for game management.
  - **Model**: Defines data structures such as Player, Card, and GameState.

### Running Tests

To run the test suite, execute:
```bash
mvn test
