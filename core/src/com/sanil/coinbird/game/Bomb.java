package com.sanil.coinbird.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

//Create factory design pattern - ScreenElementFactory
public class Bomb {

    Texture bomb;

    ArrayList<Integer> bombXs;
    ArrayList<Integer> bombYs;

    ArrayList<Rectangle> bombRetangles;
    Random random;

    public Bomb() {
        bomb = new Texture("bomb.png");
        bombXs = new ArrayList<>();
        bombYs = new ArrayList<>();
        bombRetangles = new ArrayList<Rectangle>();
        random = new Random();
    }

    public void makeBomb() {
        float height = random.nextFloat() * Gdx.graphics.getHeight();
        bombYs.add((int) height);
        bombXs.add(Gdx.graphics.getWidth());
    }

    public void setupBombs(SpriteBatch batch) {
        bombRetangles.clear();
        //draw each bomb rectangle again and add each coin to coinRectangle
        for (int i=0;i<bombXs.size();i++) {
            batch.draw(bomb,bombXs.get(i),bombYs.get(i));
            bombXs.set(i,bombXs.get(i) - 8); // velocity of coin is 8
            bombRetangles.add(new Rectangle(bombXs.get(i), bombYs.get(i),bomb.getWidth(),bomb.getHeight()));
        }
    }

    public CoinBird.PlayState detectCollision(Rectangle rectangle) {
        for (int j=0;j<bombRetangles.size();j++) {
            if (Intersector.overlaps(rectangle,bombRetangles.get(j))) {
                Gdx.app.log("Bomb!","Collision! ");
                Gdx.input.vibrate(1000);
                return CoinBird.PlayState.END;
            }
        }
        return CoinBird.PlayState.IN_PLAY;
    }
    public void clear() {
        bombXs.clear();
        bombYs.clear();
        bombRetangles.clear();
    }
}
