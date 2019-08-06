package com.sanil.coinbird.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Coin {

    Texture coin;

    ArrayList<Integer> coinXs;
    ArrayList<Integer> coinYs;

    ArrayList<Rectangle> coinRetangles;
    Random random;

    public Coin() {
        coin = new Texture("coin.png");
        coinXs = new ArrayList<>();
        coinYs = new ArrayList<>();
        coinRetangles = new ArrayList<Rectangle>();
        random = new Random();
    }

    public void makeCoin() {
        float height = random.nextFloat() * Gdx.graphics.getHeight();
        coinYs.add((int) height);
        coinXs.add(Gdx.graphics.getWidth());
    }

    public void setupCoins(SpriteBatch batch) {
        coinRetangles.clear();
        //draw each coin rectangle again and add each coin to coinRectangle
        for (int i=0;i<coinXs.size();i++){
            batch.draw(coin,coinXs.get(i),coinYs.get(i));
            coinXs.set(i,coinXs.get(i) - 8); // velocity of coin is 8
            coinRetangles.add(new Rectangle(coinXs.get(i), coinYs.get(i),coin.getWidth(),coin.getHeight()));
        }
    }

    public int detectCollision(Rectangle birdRectangle,int score) {
        for (int i=0;i<coinRetangles.size();i++) {
            if (Intersector.overlaps(birdRectangle,coinRetangles.get(i))) {
                Gdx.app.log("Coin!","Collision!");
                //remove coin
                //remove position entry
                score++;
                coinXs.remove(i);
                coinYs.remove(i);
                //remove rectangle entry
                coinRetangles.remove(i);
                break;
            }
        }
        return score;
    }

    public void clear() {
        coinXs.clear();
        coinYs.clear();
        coinRetangles.clear();
    }
}
