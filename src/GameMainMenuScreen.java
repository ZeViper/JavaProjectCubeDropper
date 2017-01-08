import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class GameMainMenuScreen {

    double textPosX;
    double textPosY;

    Font menuFont1 = Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 100 );
    Font menuFont2 = Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 40 );

    Rectangle menuButton1 = new Rectangle( 80, 440, 128, 64 );
    Rectangle menuButton2 = new Rectangle( 240, 440, 128, 64 );

    public void gameMainMenu( GraphicsContext gc, double canvasWidth ) {

        menuButton1.setFill(Color.rgb(0,0,255,0.5));
        menuButton2.setFill(Color.rgb(255,0,0,0.5));

        gc.setFont( menuFont1 );
        gc.setLineWidth( 2 );
        gc.setTextAlign( TextAlignment.CENTER );
        textPosX = Math.round( canvasWidth / 2 );

        gc.fillText("Cube Dropper", textPosX, 128 );
        gc.strokeText("Cube Dropper", textPosX, 128 );

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