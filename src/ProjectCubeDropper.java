/*
M2ngu m6te on kukutada kuubikuid yksteise peale, aga kuubik algul liigub, seega peab 6igel ajal seda kukutama
stack kuubikuid peab kokku saama, et edasi levelites j6uda, kui kukub m66da, on m2ng l2bi.

pean lisama:
    nupu tekstuurid
*/

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ProjectCubeDropper extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    boolean cubeLeftRightMode = false;
    boolean cubeDropMode = false;
    boolean firstCubeMode = false;

    int gameScoreMultiplier = 1;
    int speedAdjuster = 0;

    int gameCanvasW = 448; //448 default
    int gameCanvasH = 640; //640 default

    //BACKGROUND COLORS
    int bgR = 100;
    int bgG = 100;
    int bgB = 100;

    @Override
    public void start( Stage gameStage ) throws Exception {

        gameStage.setTitle("Cube Dropper by Egert Kõll");

        Group gameGroup = new Group();

        Scene gameScene = new Scene( gameGroup );
        gameScene.setFill( Color.rgb( bgR, bgG, bgB ) );
        gameStage.setScene( gameScene );

        Image background = new Image("background.png");

        Canvas gameCanvas = new Canvas( gameCanvasW, gameCanvasH );
        gameGroup.getChildren().add( gameCanvas );

        GraphicsContext gameGC = gameCanvas.getGraphicsContext2D();

        Font gameFont = Font.font("Impact", FontWeight.BOLD, 28 );
        gameGC.setFill( Color.rgb( 46, 160, 242 ) );
        gameGC.setStroke( Color.BLACK );
        gameGC.setTextBaseline( VPos.CENTER );

        GameCube gameCube = new GameCube();
        GamePlatform gamePlatform = new GamePlatform();
        GameScoreAndLevel gameScoreAndLevel = new GameScoreAndLevel();
        GameMainMenuScreen gameMainMenuScreen = new GameMainMenuScreen();
        GameOverScreen gameOverScreen = new GameOverScreen();

        gameMainMenuScreen.gameMainMenu( gameGC, gameCanvasW );
        gameGroup.getChildren().addAll( gameMainMenuScreen.menuButton1, gameMainMenuScreen.menuButton2 );

        //GAME LOOP
        AnimationTimer gameAnimationTimer = new AnimationTimer() {
            @Override
            public void handle( long now ) {

                //CUBE LEFT/RIGHT MOTION MODE
                if ( cubeLeftRightMode ) {

                    gameCube.moveCubeLeftRightDirection( gameCanvasW );
                    gameCube.moveCubeLeftRight();

                    //CUBE DROP MODE
                } else if ( cubeDropMode ) {

                    gameCube.moveCubeDown();

                    //IF THE CUBE LANDS ON THE PLATFORM
                    if (
                            ( gameCube.posY >= gameCanvasH - gameCube.cubeHeight - gamePlatform.platformHeight &&
                                    gamePlatform.platformCollisionBoolean( gameCube.cubeCollision1, gameCanvasW ) ) &&
                                    firstCubeMode ) {

                        gameCube.moveCubeOnPlatform( gamePlatform.platformHeight, gameCanvasH );
                        gameGC.drawImage( gameCube.cube, gameCube.posX, gameCube.posY );
                        gameCube.spawnCube( gameCanvasW, speedAdjuster );

                        gameScoreAndLevel.cubeStackingScore += 100 * gameScoreMultiplier;
                        gameScoreAndLevel.addScore( gameGC, gameCanvasW );

                        cubeDropMode = false;
                        cubeLeftRightMode = true;
                        firstCubeMode = false;

                        //IF THE CUBE FALLS INTO VOID = GAME OVER
                    } else if ( gameCube.posY >= gameCanvasH + gameCube.cubeHeight) {

                        gameGroup.getChildren().removeAll( gamePlatform.gamePlatform, gameCube.gameCube );

                        gameOverScreen.gameOver(

                                gameGC,
                                gameCanvasW,
                                gameCanvasH,
                                gameScoreAndLevel.cubeStackingScore,
                                gameScoreAndLevel.levelNumber );

                        gameGroup.getChildren().addAll( gameOverScreen.menuButton1, gameOverScreen.menuButton2 );

                        cubeDropMode = false;
                        this.stop();

                        //IF THE CUBES STACK
                    } else if ( gameCube.cubeCollisionBoolean() ) {

                        gameCube.moveCubeDownStacking();
                        gameGC.drawImage( gameCube.cube, gameCube.posX, gameCube.posY );
                        gameCube.spawnCube( gameCanvasW, speedAdjuster );

                        gameScoreAndLevel.cubeStackingScore += 100 * gameScoreMultiplier;
                        gameScoreAndLevel.addScore( gameGC, gameCanvasW );

                        cubeDropMode = false;
                        cubeLeftRightMode = true;

                    }
                }

                //IF THE CUBES STACK 6 TIMES, IT INCREASES LEVEL, SCORE MULTIPLIER, SPEED
                if ( gameCube.cubeStackingCounter == 6 ) {

                    gameGC.clearRect( 0, 56, gameCanvasW, gameCanvasH - 64 );
                    gameGC.drawImage( background, 0, 56 );

                    gameCube.cubeStackingCounter = 0;
                    gameCube.cubeCollision2.setTranslateX( 0 );
                    gameCube.cubeCollision2.setTranslateY( 0 );

                    gameScoreMultiplier += 1;

                    gameScoreAndLevel.levelNumber += 1;
                    gameScoreAndLevel.addLevel( gameGC, gameCanvasW );

                    //IF THE LEVEL NUMBER IS EVEN, THE SPEED INCREASES
                    if ( gameScoreAndLevel.levelNumber % 2 == 0 ) {

                        speedAdjuster += 1;

                    }

                    gamePlatform.spawnPlatform( gameCanvasW, gameCanvasH, gameCube.cubeWidth );

                    firstCubeMode = true;

                }
            }
        };

        //WHEN SPACE KEY IS PRESSED, THE CUBE DROPS
        gameScene.setOnKeyPressed( event -> {

            if ( event.getCode() == KeyCode.SPACE ) {

                cubeLeftRightMode = false;
                cubeDropMode = true;

            }
        });

        EventHandler< MouseEvent > gameStartHandler = new EventHandler< MouseEvent >() {
            @Override
            public void handle( MouseEvent event ) {

                gameGC.drawImage( background, 0, 56 );
                gameGC.setFont( gameFont );
                gameGC.setLineWidth( 1 );
                gameGC.setTextAlign( TextAlignment.LEFT );

                gameGroup.getChildren().removeAll( gameMainMenuScreen.menuButton1, gameMainMenuScreen.menuButton2 );
                gameGroup.getChildren().removeAll( gameOverScreen.menuButton1, gameOverScreen.menuButton2 );
                gameGroup.getChildren().addAll( gameCube.gameCube, gamePlatform.gamePlatform );

                gameScoreAndLevel.cubeStackingScore = 0;
                gameScoreAndLevel.levelNumber = 1;
                gameScoreAndLevel.addScore( gameGC, gameCanvasW );
                gameScoreAndLevel.addLevel( gameGC, gameCanvasW );
                gameScoreMultiplier = 1;
                speedAdjuster = 0;

                gameCube.cubeStackingCounter = 0;
                gameCube.cubeCollision2.setTranslateX( 0 );
                gameCube.cubeCollision2.setTranslateY( 0 );
                gameCube.spawnCube( gameCanvasW, speedAdjuster );
                gamePlatform.spawnPlatform( gameCanvasW, gameCanvasH, gameCube.cubeWidth );

                firstCubeMode = true;
                cubeLeftRightMode = true;
                gameAnimationTimer.start();

            }
        };

        //MAIN MENU 'PLAY' BUTTON
        gameMainMenuScreen.menuButton1.setOnMousePressed( gameStartHandler );

        //GAME OVER MENU 'REPLAY' BUTTON
        gameOverScreen.menuButton1.setOnMousePressed( gameStartHandler );

        //MAIN MENU 'EXIT' BUTTON
        gameMainMenuScreen.menuButton2.setOnMousePressed( event -> System.exit( 0 ) );

        //GAME OVER MENU 'EXIT' BUTTON
        gameOverScreen.menuButton2.setOnMousePressed( event -> System.exit( 0 ) );

        gameStage.show();

    }
}