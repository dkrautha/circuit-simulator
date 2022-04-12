package edu.stevens.circuit.simulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class CircuitSimulator extends ApplicationAdapter {
  ShapeRenderer shape;
  //Ball ball;
  BitmapFont font;
  SpriteBatch batch;

  @Override
  public void create() {
    //shape = new ShapeRenderer();
    //ball = new Ball(150, 200, 70, 12, 5);
    font = new BitmapFont();
    batch = new SpriteBatch();
  }

  @Override
  public void render() {
    // Clearing the screen to all black
    ScreenUtils.clear(Color.BLACK);

    // Update the balls position
    //ball.update();
    //shape.begin(ShapeRenderer.ShapeType.Filled);
    //ball.draw(shape);
    shape.end();

    // Begin drawing text on the screen
    batch.begin();
    font.draw(this.batch, "Hello World!", 50, 50);
    batch.end();
  }

  @Override
  public void dispose() {
    font.dispose();
    batch.dispose();
  }
}
