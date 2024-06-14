# Learning Birds

Learning Birds is a Java-based implementation of the Flappy Bird game, where the birds are controlled by neural networks.

## Table of Contents

- [Technologies](#technologies)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Configuration](#configuration)

## Technologies

- Spring
- JavaFX
- Gson
- JUnit
- Mockito

## Features

- **Neural Networks**
    - **Inputs**: 4
        - Distance from the bird to the pipe horizontally
        - Distance from the bird to the bottom pipe vertically
        - Distance from the bird to the top pipe vertically
        - Bird's speed
    - **Hidden Layer**: 5 neurons
    - **Output**: 1 neuron (decision to jump)
    - **Activation Function**: Sigmoid
    - **Population Size**: 50
- **Graphics**: JavaFX for game rendering
- **Configuration**: JSON-based configuration for easy adjustments
- **Testing**: Unit tests for game logic and neural network components

## Installation

1. **Clone the repository**:
    ```sh
    git clone https://github.com/Apolones/BirdNNs.git
    cd BirdNNs
    ```

2. **Build the project**:
    ```sh
    ./mvnw clean install
    ```

3. **Run the game**:
    ```sh
    ./mvnw javafx:run
    ```

## Usage

- The game will start automatically after running the above command.
- Birds are controlled by neural networks.
- You can change settings while the game is running.
- More configuration options are available in `config.json`.

## Project Structure

- **src/main/java/com/birds/nn/**:
    - **gameCore**: Core game logic
        - **gameLogic**: Main game logic and services
        - **gameLoop**: Main game loop and controllers
        - **gameObjects**: Game objects like Bird, Pipe, Background, etc.
        - **main**: Main application configuration and entry point
    - **graphics**: Rendering components
    - **neuralNetwork**: Neural network implementation
        - **neuralNetworkCore**: Core neural network components like Layer, NeuralNetwork, Neuron
    - **utils**: Utility classes and configurations
- **src/main/resources**: Game resources (images, configurations, etc.)
    - **logging.properties**: Logging configuration
    - **config.json**: Game and neural network configuration
- **src/test/java/com/birds/nn/**: Unit tests

## Configuration

Configuration is managed via `src/main/resources/com/birds/nn/config.json`. You can adjust game settings, neural network parameters, and other configurations.
