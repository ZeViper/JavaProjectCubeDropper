import javafx.scene.canvas.GraphicsContext;

public class GameScoreAndLevel {

    int cubeStackingScore = 0;
    int levelNumber = 1;

    public void addScore( GraphicsContext gc, double canvasWidth ) {

        gc.clearRect( 0, 0, canvasWidth / 2 , 56 );
        String gameScoreText = "Score: " + cubeStackingScore;
        gc.fillText( gameScoreText, 32, 28 );
        gc.strokeText( gameScoreText, 32, 28 );

    }

    public void addLevel( GraphicsContext gc, double canvasWidth ) {

        gc.clearRect( canvasWidth / 2, 0, canvasWidth / 2 , 56 );
        String gameScoreText = "Level: " + levelNumber;
        gc.fillText( gameScoreText, 328, 28 );
        gc.strokeText( gameScoreText, 328, 28 );

    }
}