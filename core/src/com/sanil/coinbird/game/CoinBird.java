package com.sanil.coinbird.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CoinBird extends ApplicationAdapter {
    SpriteBatch batch;

    Texture background;

    int pause;


    Coin coin;
    int coinCount = 0;
    final int coinCountMax = 100;

    //Bombs
    Bomb bomb;
    int bombCount = 0;
    final int bombCountMax = 250;

    //Score
    int score;
    BitmapFont font;

    //Game Over
    BitmapFont gameOver;
    BitmapFont gameRestart;
    BitmapFont gameStart;
    private int renderCount;

    public enum PlayState {
        BEFORE_START,
        IN_PLAY,
        END
    }

    PlayState playState;

    @Override
    public void create () {
        batch = new SpriteBatch();
        background = new Texture("bg.png");

        pause = 0;
        renderCount = 0;

        //create Coin
        coin = new Coin();
        //create Bomb
        bomb = new Bomb();

        //Score and message related stuff;
        score = 0;
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(10);

        gameOver = new BitmapFont();
        gameOver.setColor(Color.WHITE);
        gameOver.getData().setScale(15);

        gameRestart = new BitmapFont();
        gameRestart.setColor(Color.WHITE);
        gameRestart.getData().setScale(7);

        gameStart = new BitmapFont();
        gameStart.setColor(Color.WHITE);
        gameStart.getData().setScale(10);


        playState = PlayState.BEFORE_START;

    }


    @Override
    public void render () {
        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //TODO:Render time: https://developer.android.com/studio/profile/android-profiler.html
        if (playState == PlayState.IN_PLAY) {
            //GAME IS LIVE

            //1. Create a Coin and Bomb

            if (renderCount > 0 && renderCount % coinCountMax == 0) {
                coin.makeCoin();
            } else if ( renderCount >0 && renderCount % bombCountMax == 0) {
                renderCount = 0;
                bomb.makeBomb();
            }

            bomb.setupBombs(batch);
            coin.setupCoins(batch);

            renderCount++;


            //2. Handle Touch
            if (Gdx.input.justTouched()) {
                Gdx.input.vibrate(10);
                Bird.getInstance().setVelocity(-10);
            }

            //3. change bird state every 5 iteration
            if (pause<5) {
                pause++;
            }else {
                pause = 0;
                Bird.getInstance().changeBirdState();
            }

            //4. Bird fall
            Bird.getInstance().changeVelocityPerGravity();

            //5. Check collision with coin
            score = coin.detectCollision(Bird.getInstance().birdRectangle,score);

            //6. Check collision with bomb
            playState = bomb.detectCollision(Bird.getInstance().getBirdRectangle());

        } else if (playState == PlayState.BEFORE_START) {
            //Waiting to start
            gameStart.draw(batch,"CLICK TO PLAY", 200,Gdx.graphics.getHeight() - 800);
            if (Gdx.input.justTouched()) {
                playState = PlayState.IN_PLAY;
            }

        } else if (playState == PlayState.END) {
            //GAME OVER
            if (Gdx.input.justTouched()) {

                playState = PlayState.IN_PLAY;
                //Bird clear
                Bird.getInstance().clear();

                score = 0;

                coin.clear();
                coinCount = 0;

                //bomb clear
                bomb.clear();
                bombCount = 0;
            }
        }

        if (playState == PlayState.END) {
            //Draw bird
            Bird.getInstance().setBirdRectangle();
            gameRestart.draw(batch,"CLICK TO PLAY AGAIN",100,Gdx.graphics.getHeight() - 850);
            gameOver.draw(batch,"GAME OVER!",75,Gdx.graphics.getHeight() - 600);
        } else {
            //Draw bird - first instance of bird created here
            Bird.getInstance().drawBird(batch);
        }

        Bird.getInstance().setBirdRectangle();

        font.draw(batch,String.valueOf(score),100,200);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
