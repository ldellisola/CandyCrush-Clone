# Candy Crush

Se eligió realizar las implementaciones de Golden Board y Cherry & Hazelnut

## Funcionalidades Agregadas

En primer lugar, se creó la clase abstracta Level que realiza los métodos comunes a todos los niveles: llenar el tablero con los caramelos, revisar si un movimiento es válido y a su vez le agregamos un método para preguntar si el nivel tiene celdas doradas. Este último método fue agregado, no solo para la implementación del Level 2, sino también en caso de que se tenga un nivel con celdas doradas y otros elementos y una vez ganado el nivel se pueda quitar el efecto dorado de esas celdas en específico.

Luego, se agregaron las clases Level 2 para la implementación de Golden Board y Level 3 para la de Cherry & Hazelnut 

### GOLDEN BOARD
Para esta implementación es necesario tener muy en claro la distinción entre "marcar como dorado" y "pintar de dorado". La primera hace referencia a, en el backend, setear el estado de una GoldenCell (clase que luego será explicada). En cambio, la segunda pone el efecto dorado a una celda en el frontend. 

Se agregó la clase Level 2 que extiende de Level. Su objetivo es marcar las celdas como doradas

#####Class: GoldenCell 

Esta clase permite marcar a las celdas como doradas luego de hacer un intercambio. 

#####Class: GoldenGameListener
Se decidió hacer esta clase a fin de mejorar la eficiencia del Level 2. Su objetivo es que aquellas celdas que ya están pintadas de dorado no vuelvan a pintarse. 

###CHERRY & HAZELNUT

Se agregó la clase Level 3 que extiende de Level

#####Class: Cherry - Class: Hazelnut

Dentro del package "element" agregamos las clases Cherry y Hazelnut que extienden de la clase abtracta Fruit. Estos elementos se marcan como indestructibles. 


###Otras funcionalidades
Se agregó el Package Alert cuyo objetivo es mostrar en pantalla si se ganó o perdió el nivel. 
La clase GameInfoListener es la encargada de mostrar los paneles de Movimientos restantes, Score y GoldenCells pintadas o Cantidad de frutas restantes dependiendo de cada nivel.

## Modificaciones al proyecto inicial

### CandyGame.java

El cambio mas grande fue en la inicializacion de la clase. Ahora en vez de tomar como variable a un nivel, recibe una lista con los niveles que tiene que ejecutar, luego dentro de la clase hay un control interno sobre que nivel tiene que ejecutar:

```java
private int levelIndex = 0;
private List<Class<?>> levels = new ArrayList<>();

public CandyGame(List<Class<?>> levels) {
	if(!levels.isEmpty()) {
		this.levels = levels;
		this.levelClass = levels.get(0);
	}
	else
		throw new IllegalArgumentException("At least one level must be passed");
}
```

Para el control de los niveles, y asegurarnos que solo esta clase se encargue de manejarlos, creamos dos funciones `nextLevel()` y `hasNextLevel()` que se encargan de poner el proximo nivel:

```java 
public boolean hasNextLevel(){
	return levels.size() > levelIndex+1;
}

public void nextLevel() {
	if(hasNextLevel()) {
		grid.wasUpdated();
		this.levelClass = levels.get(++levelIndex);
		try {
			grid = (Grid) levelClass.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			System.out.println("ERROR AL INICIAR");
		}
		state = grid.createState();
		grid.initialize();
		addGameListener(this);
	}
}
```

Por ultimo, al agregar mas funcionalidad a `GameState` tuvimos que agregar un par de getters. Ademas creamos una funcion para poder actualizar a los listeners:

```java
public int getGoal(){return state.getGoal();}

public int getCurrentGoal(){return state.getCurrentGoal();}

public String getGoalDescription() {return state.getGoalDescription();}

public void updateListeners() {grid.wasUpdated();}

public int maxMovements() {return state.getMaxMoves();}

public int currMovements() {return state.getMoves();}
```

### GameState.java

La clase `GameState` fue modificada para poder acomodar a los nuevos objetivos de los niveles. Como todos los niveles pueden terminar de dos formas distintas, si el jugador gana o si el jugador hace mas movimientos del maximo, por lo que ahora `gameOver()` se define en base a funciones que tienen que definir los estados de los niveles que implementen la clase. De forma similar, como hay varias formas de ganar segun el nivel, se implementaron funciones para poder ver que tan lejos esta de ganar el jugador y cual es el objetivo.

```java
public abstract int getMaxMoves();

public abstract int getGoal();

public abstract int getCurrentGoal();

public abstract String getGoalDescription();

public boolean gameOver(){
	return playerWon() || getMoves() > getMaxMoves()-1;
}
```

### Grid.java

La clase abstracta `Grid` es la base del juego, por lo que se encarga solo de las cosas mas fundamentales como crear la grilla, cargarla de elementos y hacer que estos caigan cuando se hagan combinaciones validas.  En esta clase separamos la accion de crear la grilla con la de llenarla de elementos y hacer que caigan, y los ubicamos en la funcion que inicializa al objeto. Esto permite que si un nivel requiere otro tipo de celda, pueden sobrecargar solo la funcion `fillCells()` para que se creen celdas que hereden de `Cell`.

```java
public void initialize() {
	moveMaker = new MoveMaker(this);
	figureDetector = new FigureDetector(this);
	createGrid();
	fillCells();
	fallElements();
}

protected void createGrid(){
	for (int i = 0; i < SIZE; i++) {
		for (int j = 0; j < SIZE; j++) {
			g[i][j] = new Cell(this);
			gMap.put(g[i][j], new Point(i,j));
		}
	}
}
```

### Cell.java

El unico cambio en esta clase es que solo se pueden combinar elementos si el elemento no es destruible.

```java
public void clearContent() {
	if (content.isMovable() && content.isDestoyable()) {
		Direction[] explosionCascade = content.explode();
		grid.cellExplosion(content);
		this.content = new Nothing();
		if (explosionCascade != null) {
			expandExplosion(explosionCascade); 
		}
		this.content = new Nothing();
	}
}
```

### Element.java

Como implementamos la funcionalidad de las Cherries, tuvimos que implementar un metodo `isDestroyable()` que indique si el elemento puede ser destruido. Si un elemento no puede ser destruido, entonces tiene que sobrecargar a esta funcion para indicarlo. Tambien sobrecargamos el metodo `equals()` para verificar si dos elementos son iguales en base a la clave que se les asigna.

```java
public boolean isDestoyable() {return true;}

@Override
public boolean equals(Object obj){
	if(this == obj)
		return  true;
	if(!(obj instanceof Element))
		return  false;
	Element el = (Element) obj;
	return this.getFullKey().equals(el.getFullKey());
}
```

### Nothing.java

Sobrecargamos el metodo `isDestroyable()` para indicar que no es posible destruir al vacio.

```java
@Override
public boolean isDestoyable() {return false;}
```

### Wall.java

Sobrecargamos el metodo `isDestroyable()` para indicar que no es posible destruir a las paredes.

```java
@Override
public boolean isDestoyable() {return false;}
```

### Level1.java

Probablemente la clase a la que mas modificamos, debido a estructura del programa, consideramos conveniente crear una clase intermedia `Level` que tenga toda la funcionalidad general de un nivel con una configuracion de celdas paredes (`Wall`) y celdas generadoras de caramelos (`CandyGeneratorCell`). Y como este nivel es muy basico no requeria cambiar nada mas, salvo crear la clase que impemente `GameState` para el nivel.

```java
public class Level1 extends Level {
	
	private static int REQUIRED_SCORE = 1000;
	private static int MAX_MOVES = 20; 

	@Override
	protected GameState newState() {
		return new Level1State(REQUIRED_SCORE, MAX_MOVES);
	}
    
    private class Level1State extends GameState {
		private long requiredScore;
		private int maxMoves;
		
		public Level1State(long requiredScore, int maxMoves) {
			this.requiredScore = requiredScore;
			this.maxMoves = maxMoves;
		}

		@Override
		public int getMaxMoves() {return maxMoves;}

		@Override
		public int getGoal() {return REQUIRED_SCORE;}

		@Override
		public int getCurrentGoal() {return (int)getScore();}

		@Override
		public String getGoalDescription() {return "Score";}

        public boolean playerWon() {return getScore() > requiredScore;}
	}
}
```

### BombMove.java

Aqui hicimos una pequeña modificacion en la funcion `removeElements()` para que las explosiones de las bombas no destruyan a las cherries.

### MoveMaker.java

En esta clase agregamos las interacciones entre las cherries y los demas caramelos, bombas, etc, dentro de la funcion `initMap()`.

### CandyFrame.java

La clase `CandyFrame` es la encargada de manejar la interaccion con el jugador. Modificamos a la clase para poder implementar de forma modular los listeners (clases que heredan a `GameListener`), para lograrlo creamos dos funciones `createGameListeners()` que inicializa los listeners del frontend como lo son `BasicGameListener`, `GoldGameListener` y `GameInfoListener` y luego estos se cargan al Model (`CandyGame`) con la funcion `EnableGameListeners()`. De esta forma logramos compartamentizar el codigo que estaba dentro del eventHandler.

```java
private List<GameListener> listeners = new ArrayList<>();

public CandyFrame(CandyGame game) {
	this.game = game;
	getChildren().add(new AppMenu());
	images = new ImageManager();
	boardPanel = new BoardPanel(game.getSize(), game.getSize(), CELL_SIZE);
	getChildren().add(boardPanel);

	game.initGame();
	createGameListener();
	enableGameListener();

	addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
		if (lastPoint == null) {
			lastPoint = translateCoords(event.getX(), event.getY());
			System.out.println("Get first = " +  lastPoint);
		} else {
			Point2D newPoint = translateCoords(event.getX(), event.getY());
			if (newPoint != null) {
				System.out.println("Get second = " +  newPoint);
				game().tryMove((int)lastPoint.getX(), (int)lastPoint.getY(), (int)newPoint.getX(), (int)newPoint.getY());

				if (game().isFinished()) {
					if(game().playerWon())
						new WonLevelAlert(game());
					else
						new LostLevelAlert(game());
					enableGameListener();
				}
				lastPoint = null;
			}
		}
	});
}

private void createGameListener(){
	listeners.add(new BasicGameListener());
	listeners.add(new GoldenGameListener(game(),boardPanel));
	listeners.add(new GameInfoListener(this,game()));
}

protected void enableGameListener(){
	for(GameListener listener : listeners)
		game().addGameListener(listener);
	game().updateListeners();
}
```

Tambien convertimos al listener anonimo que estaba definido en una clase interna llamada `BasicGameListener` para que sea mas claro.

```java
private class BasicGameListener implements GameListener{
	@Override
	public void gridUpdated() {
		Timeline timeLine = new Timeline();
		Duration frameGap = Duration.millis(100);
		Duration frameTime = Duration.ZERO;
		for (int i = game().getSize() - 1; i >= 0; i--) {
			for (int j = game().getSize() - 1; j >= 0; j--) {
				int finalI = i;
				int finalJ = j;
				Cell cell = CandyFrame.this.game.get(i, j);
				Element element = cell.getContent();
				Image image = images.getImage(element);

				timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, null)));
				timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> boardPanel.setImage(finalI, finalJ, image)));
			}
			frameTime = frameTime.add(frameGap);
		}
		timeLine.play();
	}
	@Override
	public void cellExplosion(Element e) {
		
	}
}
```

### BoardPanel.Java

Modificamos `BoardPanel` para que pueda dibujar de dorado a la celda y tambien quitarle el efecto

```java
public void setGoldenEffect(int row, int column) {
	Light.Distant spotLight = new Light.Distant();
	spotLight.setColor(Color.YELLOW);
	spotLight.setElevation(100);
	Lighting lighting = new Lighting(spotLight);
	cells[row][column].setEffect(lighting);
}

public void stopGoldenEffect(int row, int column){
	cells[row][column].setEffect(null);
}
```

### ScorePanel.java

El unico cambio en la clase `ScorePanel` fue para que tome `long` en vez de `String` y para que el texto indique que es el puntaje.







Cosas para Hacer:

	- CellType.java o implementarla para sacar el isInstance
	- Renombrar isDestoyable a isDestroyable en Element.java
	- Cambiar en BombMove.java `get(i,j).getKey() != "CHERRY"` a `!get(i,j).equals(new Cherry())`
	- Cambiar texto en AppMenu.java a Ingles