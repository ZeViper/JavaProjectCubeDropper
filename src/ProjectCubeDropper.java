/*Kood on praegu taiesti Alpha versioonis :D

M2ngu m6te on kukutada kuubikuid yksteise peale, aga kuubik algul liigub, seega peab 6igel ajal seda kukutama
stack kuubikuid peab kokku saama, et edasi levelites j6uda, kui kukub m66da, on m2ng l2bi.

pean lisama:
    levelid
    raskusastmed levelitel
    highscore
    tsykklid
    collisioni
    korraliku main menu ja exit screeni
*/
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ProjectCubeDropper extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage gameStage) throws Exception {

        gameStage.setTitle("Cube Dropper by Egert K6ll");
        //gameStage.initStyle(StageStyle.UTILITY);
        gameStage.setResizable(false);

        Group gameGroup = new Group();

        Scene gameScene = new Scene( gameGroup );
        gameScene.setFill(Color.rgb(100, 100, 255));
        gameStage.setScene( gameScene );

        Canvas gameCanvas = new Canvas(490, 680);

        Image cube = new Image("cube.png");
        double spriteWidth = cube.getWidth();
        double spriteHeight = cube.getHeight();
        ImageView gameSprite = new ImageView( cube );

        //collision tuleks panna kuubikuga kaasa liikuma, nagu tema kylge
        Rectangle2D spriteColl = new Rectangle2D(0, 0, spriteWidth, spriteHeight);

        gameGroup.getChildren().addAll( gameCanvas, gameSprite );

        TranslateTransition gameTT = new TranslateTransition(Duration.millis(1000), gameSprite);

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        double posX = 490 - spriteWidth * 2;
        double posY = 680 - spriteHeight;

        //see osa siin on praegu selleks, et testida 2d kuubiku liikumist ja 2ra peitmist
        //space'iga panen liikuma
        //nool alla klahviga liigub kuubik alla
        //s klahv peatab liikumise
        //h klahv peidab 2ra

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    gameTT.setByX(posX);
                    gameTT.setCycleCount(Animation.INDEFINITE);
                    gameTT.setAutoReverse(true);
                    gameTT.play();
                } else if (event.getCode() == KeyCode.DOWN) {
                    gameTT.stop();
                    gameTT.setByY(posY);
                    gameTT.setByX(0);
                    gameTT.setCycleCount(1);
                    gameTT.play();
                } else if (event.getCode() == KeyCode.H) {
                    gameSprite.setOpacity(0.5);
                } else if (event.getCode() == KeyCode.S) {
                    gameTT.stop();
                    gc.drawImage(cube, gameTT.getToX(), gameTT.getToY());
                }
            }
        });

        gameSprite.setTranslateX(spriteWidth / 2);

        gameStage.show();
    }
}