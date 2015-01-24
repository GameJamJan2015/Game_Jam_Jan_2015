package wdwdn;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import wdwdn.entity.GameEntity;
import wdwdn.entity.Player;

import java.util.ArrayList;

/**
 * Created by Simon on 1/23/2015.
 */
public class World {
    private ArrayList<GameEntity> entities = new ArrayList<GameEntity>(128);
    private Player player;
    private Light playerLight;
    private Light ambLight;
    private TiledMap map;

    private float stateTime;
    /**
     * BOX2D LIGHT STUFF
     */
    private RayHandler rayHandler;
    private ArrayList<Light> lights = new ArrayList<Light>(128);

    // Collisions
    private Array<Rectangle> tiles = new Array<Rectangle>();
    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject() {
            return new Rectangle();
        }
    };

    public World(TiledMap map) {
        this.map = map;
        // Init
        player = new Player(51, 50);
        player.addAnimation("idle", new Animation(1, Assets.region));
        player.setAnimation("idle");

        InitLights();
    }

    private void InitLights() {
        /** BOX2D LIGHT STUFF BEGIN */
        RayHandler.setGammaCorrection(true);
        //RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(this);
        rayHandler.setAmbientLight(0f, 0f, 0f, 0.00f);
        rayHandler.setCulling(true);
        rayHandler.setBlurNum(10);

        initPointLights();
        /** BOX2D LIGHT STUFF END */}

    Rectangle prevRect;
    public void update(float delta) {
        stateTime += delta;
        prevRect = new Rectangle(player.getBounds());
        updatePlayer(delta);
        updateEntities(delta);
        updateLights();
        updateCollision(delta);
    }

    private void updateEntities(float delta) {
        for (GameEntity entity : entities) {
            entity.update(delta);
        }
    }

    public void updatePlayer(float delta) {
        Vector2 vel = new Vector2();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            vel.x = -2;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            vel.x = 2;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            vel.y = 2;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            vel.y = -2;

        player.setVelocity(vel.x, vel.y);

        player.update(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            playerLight.setDistance(3);
        } else {
            playerLight.setDistance(0);
        }
    }

    private boolean isLightning = false;
    private void updateLights() {
        rayHandler.update();

        if (!isLightning) {
            if (MathUtils.random(0, 1000) <= 1) {
                isLightning = true;
                stateTime = 0;
            }
        }

        if (isLightning) {
            float a = (float) Math.sin(stateTime * 5.5f)*.35f;
            ambLight.setColor(1, 1, 0, a);
            //rayHandler.setAmbientLight(1, 1, 0, a);
            if (a < -.01f) {
                isLightning = false;
                ambLight.setColor(1, 1, 1, .06f);
            }
        }
    }

    private void updateCollision(float delta) {
        MapLayers layers = map.getLayers();

        getTiles((int) player.getBounds().x, (int) (player.getBounds().y),
                (int) (player.getBounds().x + player.getBounds().width), (int) (player.getBounds().y + player.getBounds().height), tiles);

        for (Rectangle tile : tiles) {
            if (player.getBounds().overlaps(tile)) {

                if (Math.abs(player.getPosition().x - (tile.getX() + tile.getWidth()/2)) > .1f) {
                    player.setX(prevRect.x + player.getBounds().width / 2);
                    prevRect.x = player.getBounds().x;
                }

                if (Math.abs(player.getPosition().y - (tile.getY() + tile.getHeight()/2)) > .1f) {
                    player.setY(prevRect.y + player.getBounds().height / 2);
                    prevRect.y = player.getBounds().y;
                }

                player.update(0);
               // break;
            }
        }
    }

    private void getTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
        MapLayers layers = map.getLayers();

        rectPool.freeAll(tiles);
        tiles.clear();

        for (MapLayer layer : layers) {
            // Return if collision is off
            if (layer.getProperties().containsKey("Collision")) {
                if (layer.getProperties().get("Collision").toString().toLowerCase().equals("false"))
                    continue;
            }
            for (int y = startY; y <= endY; y++) {
                for (int x = startX; x <= endX; x++) {
                    TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) layer).getCell(x, y);
                    if (cell != null) {
                        Rectangle rect = rectPool.obtain();
                        rect.set(x, y, 1, 1);
                        tiles.add(rect);
                    }
                }
            }
        }
    }

    // Box2d lights
    void initPointLights() {
        clearLights();
        Light light = new PointLight(rayHandler, 32, new Color(1,1,1,.5f), 0, 0, 0);

        light.setSoft(true);
        light.setSoftnessLength(2);
        light.attachToBody(player);
        lights.add(light);
        playerLight = light;


        // ambient
        Light amb = new PointLight(rayHandler, 128, new Color(1,1,1,.04f), 8, 0, 0);

        amb.setSoft(true);
        amb.setSoftnessLength(10);
        amb.attachToBody(player);
        lights.add(amb);
        ambLight = amb;
    }

    void clearLights() {
        if (lights.size() > 0) {
            for (Light light : lights) {
                light.remove();
                light.dispose();
            }
            lights.clear();
        }
    }

    // GETTERS AND SETTER //

    public RayHandler getRayHandler() {
        return rayHandler;
    }

    public TiledMap getMap() {
        return map;
    }

    public GameEntity getPlayer() {
        return player;
    }

    public ArrayList<GameEntity> getEntities() {
        return entities;
    }
}
