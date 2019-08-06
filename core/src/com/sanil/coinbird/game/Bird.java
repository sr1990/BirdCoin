package com.sanil.coinbird.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

//Singleton bird class
public class Bird {
    Texture[] birdTextures; // set textures in constructor
    int birdState = 0;

    float velocity = 0; // this changes as per gravity and jump
    final float gravity = 0.2f;

    int manY; // this changes as per velocity
    int manX = Gdx.graphics.getWidth() / 2 ;

    Rectangle birdRectangle;

    private Bird(){
        //set bird textures
        birdTextures = new Texture[4];
        birdTextures[0] = new Texture("frame-1.png");
        birdTextures[1] = new Texture("frame-2.png");
        birdTextures[2] = new Texture("frame-3.png");
        birdTextures[3] = new Texture("frame-4.png");

        //set birdState
        birdState = 0;

        manY = Gdx.graphics.getHeight() /2;
        manX = Gdx.graphics.getWidth()/2 - birdTextures[birdState].getWidth()/2;
    }

    private static Bird bird;

    public static Bird getInstance() {
        if (bird == null) {
            synchronized (Bird.class) {
                if (bird == null) {
                    bird = new Bird();
                }
            }
        }

        return bird;
    }

    void setVelocity(float i) {
        velocity = i;
    }


    void setManY(float i) {
        manY = (int)i;
        //check if manY is less than 0;
        if (manY<=0) {
            manY = 0;
        } else if (manY > Gdx.graphics.getHeight() - birdTextures[birdState].getHeight()) {
            manY = Gdx.graphics.getHeight() - birdTextures[birdState].getHeight();
        }
    }

    Rectangle getBirdRectangle() {
        return birdRectangle;
    }

    void changeVelocityPerGravity(){
        setVelocity(velocity+gravity);
        setManY(this.manY - this.velocity);
    }

    void setBirdRectangle(){
        birdRectangle = new Rectangle(Gdx.graphics.getWidth()/2 - birdTextures[birdState].getWidth()/2,
                manY, birdTextures[birdState].getWidth(), birdTextures[birdState].getHeight());
    }

    void drawBird(SpriteBatch batch) {
        batch.draw(birdTextures[birdState],manX,manY);
    }

    void changeBirdState(){
        if (birdState < 3) {
            birdState++;
        } else {
            birdState = 0;
        }
    }

    void clear() {
        manY = Gdx.graphics.getHeight() /2;
        velocity = 0;
    }

}
