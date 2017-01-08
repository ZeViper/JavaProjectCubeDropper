import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class GameMainMenuScreen extends GameOverScreen {

    Font menuFont1 = Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 100 );
    Font menuFont2 = Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 40 );

    public void gameMainMenu( GraphicsContext gc, double canvasWidth ) {

        menuButton1.setOpacity( 0 );
        menuButton2.setOpacity( 0 );

        gc.setFont( menuFont1 );
        gc.setLineWidth( 2 );
        gc.setTextAlign( TextAlignment.CENTER );
        textPosX = Math.round( canvasWidth / 2 );

        gc.fillText("Cube Dropper", textPosX, 128 );
        gc.strokeText("Cube Dropper", textPosX, 128 );

        gc.drawImage( button, 80, 440 );
        gc.drawImage( button, 240, 440 );

        gc.setFont( menuFont2 );
        gc.setLineWidth( 1 );
        textPosX = menuButton1.getX() + menuButton1.getWidth() / 2;
        textPosY = menuButton1.getY() + menuButton1.getHeight() / 2;
        gc.fillText("Play", textPosX, textPosY );
        gc.strokeText("Play", textPosX, textPosY );

        textPosX = menuButton2.getX() + menuButton1.getWidth() / 2;
        textPosY = menuButton2.getY() + menuButton1.getHeight() / 2;
        gc.fillText("Exit", textPosX, textPosY );
        gc.strokeText("Exit", textPosX, textPosY );

    }
}