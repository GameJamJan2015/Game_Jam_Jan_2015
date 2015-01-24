package wdwdn;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
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
    private TiledMap map;

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
        for (int i = 0; i < 1; i++) {
            entities.add(new GameEntity(0, 0, 1, 1));
        }

        player = new Player(0, 0);

        InitLights();
    }

    private void InitLights() {
        /** BOX2D LIGHT STUFF BEGIN */
        RayHandler.setGammaCorrection(true);
        //RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(this);
        rayHandler.setAmbientLight(0f, 0f, 0f, 0.01f);
        rayHandler.setCulling(true);
        rayHandler.setBlurNum(10);

        initPointLights();
        /** BOX2D LIGHT STUFF END */}

    public void update(float delta) {
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
        player.update(delta);


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            player.setX(player.getPosition().x - .1f);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            player.setX(player.getPosition().x + .1f);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            player.setY(player.getPosition().y + .1f);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.setY(player.getPosition().y - .1f);
    }

    private void updateLights() {
        rayHandler.update();
    }

    private void updateCollision(float delta) {
        MapLayers layers = map.getLayers();

        getTiles((int) player.getBounds().x, (int) player.getBounds().y, (int) player.getBounds().width, (int) player.getBounds().height, tiles);
        for (Rectangle tile : tiles) {
            if (player.getBounds().overlaps(tile)) {
                // koala.velocity.x = 0;
                //break;
                Gdx.app.log("", "colli");
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
                if (layer.getProperties().get("Collision").equals("false"))
                    continue;
            }
            //TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

            for (int y = startY; y <= endY; y++) {
                for (int x = startX; x <= endX; x++) {
                    TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) layer).getCell(x, ((TiledMapTileLayer) layer).getHeight() - y);
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
            Light light = new PointLight(rayHandler, 32, Color.BLUE, 3, 0, 0);

            light.setSoft(true);
            light.setSoftnessLength(10);
            light.attachToBody(player);
            lights.add(light);
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
