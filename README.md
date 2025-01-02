## **Game Demo**

- This project is a complete Ping Pong Game with physics-based ball movement, player controls, AI opponent, and scoring system.
- It was part of **ECSE 202** - Intro to Software Development Course at **McGill University**.
- Final Grade: **A**


https://github.com/user-attachments/assets/a965585a-049a-45a0-aba8-27b695997db8

---
# Game Flow

### **Initialization**
- The game components and environment are created.

### **Gameplay**
- Players and the AI interact with the ball.
- Tracks ball and paddle movements.
- Handles misses and collisions.

### **Scoring**
- Updates scores based on gameplay events.

---

# Key Features

### **Physics Simulation**
- Realistic ball movement with gravity.
- Terminal velocity calculations.
- Energy loss on collisions.
- Velocity gains on paddle hits.

### **User Interface**
- Mouse control for the player paddle.
- Button controls for game functions.
- Score display.
- Optional ball trajectory tracing.

### **AI Opponent**
- Automatically tracks ball movement.
- Provides a consistent challenge.
- Uses the same physics as the player paddle.

### **Visual Elements**
- Colored paddles:
  - Green for the player.
  - Blue for the AI.
- Red ball.
- Black trace points (optional).
- Score display.
- Ground plane/table surface.

---

# Implementation

### **`ppSimPaddleAgent` (Main Class)**
- Acts as the main controller/driver class.
- Initializes the game window and components.
- Handles button controls:
  - New Serve
  - Clear
  - Trace
  - Quit
- Manages the scoring system.
- Controls mouse input for player paddle movement.

### **`ppBall`**
- Manages ball physics and movement.
- Handles collision detection with:
  - Ground
  - Ceiling
  - Player paddle
  - Agent paddle
- Updates ball position on the screen.
- Manages ball trajectory tracing.

### **`ppPaddle` (Base Class)**
- Base class for paddle functionality.
- Handles basic paddle movement and position.
- Contains collision detection logic.
- Manages paddle velocity calculations.

### `ppPaddleAgent` 
- Controls the AI opponent paddle.
- Automatically follows the ball's Y position.
- Uses the same basic functionality as `ppPaddle` but with autonomous movement.

### **`ppTable`**
- Manages the game display area.
- Handles coordinate conversions between the game world and screen.
- Draws trace points when enabled.
- Creates the ground plane/table surface.

### **`ppSimParams`**
- Contains all game constants and parameters.
- Defines physical properties (gravity, velocities, dimensions).
- Sets up colors, dimensions, and game settings.
