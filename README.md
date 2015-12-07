# 2dGameEngine-v2
This is a simple Java 2d api based game engine that I started in 2007, based on this original
version shared here:

https://github.com/kevinhooke/2dGameEngine2007

The base classes provide a structure that you extend to implement your own game.

The main points to extend and the lifecycle of the engine looks like this:

1. Subclass GameCanvas, provide implementation for the following:
init() 

    - create your sprites and add to the GameCanvas.spriteArray

2. Your main app extends GameFrame. In your main() instantiate your subclass
of GameFrame and call startUp(), passing your subclass of GameCanvas

3. startUp() calls GameCanvas.runGame()

    - starts a SwingUtilities Runnable for the intro screen

    - override GameCanvas.showIntroScreen() for your game intro screen

    - adds ClickToStartMouseListener and waits for a mouse click to start the game

    - ClickToStartMouseListener.mouseClicked() creates new GameLoopWorkerThread
(which is a SwingWorker thread) and then calls execute(), which calls
GameCanvas.gameLoop() which is the main game loop

4. gameLoop() has a while loop that iterates for the duration of game play,
calling a number of GameCanvas lifecycle methods which is where most
of you custom game logic needs to be added:

    - implement GameCanvas.calculatePlayerPosition() to calculate current position
of your player sprite

    - implement GameCanvas.calculateSpritePositions() to calculate position of
other spites currently displayed

    - implement GameCanvas.checkPlayerPositionOutOfBoundsConditions() to check
if last player inputs would move the player outside of the game canvas
(if so, do not move the player sprite)

    - implement GameCanvas.showScore() to show player's current score

    - implement GameCanvas.showLives() to show current remaining lives