import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class GameCube {

    double posX = 0;
    double posY = 0;
    int posXSpeed = 0;
    int posYSpeed = 32; //Default on 32

    int cubeStackingCounter = 0;

    Image cube = new Image("cmpncube.png");
    double cubeWidth = cube.getWidth();
    double cubeHeight = cube.getHeight();
    ImageView gameCube = new ImageView( cube );

    Rectangle cubeCollision1 = new Rectangle( 0, 0, cubeWidth, cubeHeight ); //punane kast
    Rectangle cubeCollision2 = new Rectangle( 0, 0, cubeWidth, cubeHeight ); //sinine kast

    public void spawnCube(double canvasWidth, int speedAdjuster ) {

        posX = ( Math.floor( Math.random() * ( canvasWidth - cubeWidth ) ) ); //Kuubiku suvaline stardipositsioon
        posY = 64;

        gameCube.setTranslateX( posX );
        gameCube.setTranslateY( posY );
        cubeCollision1.setTranslateX( posX );
        cubeCollision1.setTranslateY( posY );
        posXSpeed = 4 + speedAdjuster;

        if ( posX > canvasWidth / 2 - cubeWidth / 2 ) {

            posXSpeed = -posXSpeed;

        }
        /*posXSpeed on defaultina positiivne. Kui kuubik spawnib mänguvälja keskkohast paremale, siis hakkab ta
        vasakule liikuma. Samuti, kui spawnib vasakule, liigub paremale*/
    }

    public void moveCubeLeftRight() {

        posX += posXSpeed;
        gameCube.setTranslateX( posX );
        cubeCollision1.setTranslateX( posX );

    }

    public void moveCubeLeftRightDirection( double canvasWidth ) {

        if ( ( posX > canvasWidth - cubeWidth) || ( posX < 0 ) )  {

            posXSpeed = -posXSpeed;

        }
    }

    public void moveCubeDown() {

        posY += posYSpeed;
        gameCube.setTranslateY( posY );
        cubeCollision1.setTranslateY( posY );

    }

    public void moveCubeDownStacking() {

        posY = ( cubeCollision2.getTranslateY() - cubeHeight);

        cubeCollision2.setTranslateX( posX );
        cubeCollision2.setTranslateY( posY );
        gameCube.setTranslateY( posY );

        cubeStackingCounter += 1;

    }

    public void moveCubeOnPlatform( double platformHeight, double canvasHeight ) {

        posY = canvasHeight - platformHeight - cubeHeight;

        gameCube.setTranslateY( posY );
        cubeCollision2.setTranslateX( posX );
        cubeCollision2.setTranslateY( posY );

    }

    public boolean cubeCollisionBoolean() {

        return (
                cubeCollision1.getBoundsInParent().intersects( cubeCollision2.getBoundsInParent() ) &&
                        cubeCollision1.getTranslateX() < cubeCollision2.getTranslateX() + cubeWidth &&
                        cubeCollision1.getTranslateX() > cubeCollision2.getTranslateX() - cubeWidth
        );
    }
}