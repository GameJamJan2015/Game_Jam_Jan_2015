package wdwdn.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import wdwdn.Assets;

import java.util.HashMap;

/**
 * Created by Simon on 1/23/2015.
 */
public class GameEntity {
    protected Vector2 position;
    protected Rectangle bounds;
    private HashMap<String, Animation> anims;
    private Animation currentAnim;
    protected Sprite sprite;
    private boolean isDead;

    private float stateTime;

    public GameEntity(float x, float y, float width, float height) {
        this.anims = new HashMap<String, Animation>();
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x - width/2, y - height/2, width, height);
        this.sprite = new Sprite();
        this.sprite.setPosition(x, y);
        this.sprite.setSize(width, height);
        this.isDead = false;
    }

    public void update(float delta) {
        this.stateTime += delta;

        this.bounds.x = position.x - bounds.width/2;
        this.bounds.y = position.y - bounds.height /2;

        this.sprite.setPosition(position.x - sprite.getWidth()/2, position.y - sprite.getHeight()/2);

        if (currentAnim != null) {
            sprite.setRegion(currentAnim.getKeyFrame(stateTime, true));

        }

        //sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight() / 2);
    }

    public void draw(Batch batch) {
       // batch.draw(Assets.pixel, bounds.x, bounds.y, bounds.width, bounds.height);

        if (sprite.getTexture() != null)
            sprite.draw(batch);
    }

    public void addAnimation(String name, Animation anim) {
        anims.put(name, anim);
    }

    public void setAnimation(String name) {
        currentAnim = anims.get(name);
    }
    
    public void remove() {
    	isDead = true;
	}
    
    public boolean isRemoved() {
    	return isDead;
    }

    // GETTTERS
    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getAngle() {
        return 0;
    }

    // SETTERS
    public void setX(float x) {
        position.x = x;
    }

    public void setY(float y) {
        position.y = y;
    }
}
