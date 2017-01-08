import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class GamePlatform {

    double platformPosX = 0;
    double platformPosY = 0;

    Image platform = new Image("platform.png");
    double platformWidth = platform.getWidth();
    double platformHeight = platform.getHeight();
    ImageView gamePlatform = new ImageView( platform );

    Rectangle platformCollision = new Rectangle( 0, 0, platformWidth, 64 );

    public void spawnPlatform( double canvasWidth, double canvasHeight, double spriteWidth ) {

        platformPosX = ( spriteWidth + Math.floor( Math.random() * ( canvasWidth - 2 * spriteWidth - platformWidth ) ) );
        platformPosY = canvasHeight - platformHeight;

        gamePlatform.setTranslateX( platformPosX );
        gamePlatform.setTranslateY( platformPosY );
        platformCollision.setTranslateX( platformPosX );
        platformCollision.setTranslateY( canvasHeight - 64 );

    }

    public boolean platformCollisionBoolean( Rectangle cubeCollision, double cubeWidth ) {

        return ( platformCollision.getBoundsInParent().intersects( cubeCollision.getBoundsInParent() ) &&
                cubeCollision.getTranslateX() < platformCollision.getTranslateX() + platformWidth &&
                cubeCollision.getTranslateX() > platformCollision.getTranslateX() - cubeWidth );

    }
}