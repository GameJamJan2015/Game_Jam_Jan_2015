package wdwdn;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import wdwdn.entity.Dialog;
import wdwdn.entity.Enemy;
import wdwdn.entity.GameEntity;
import wdwdn.entity.Player;
import wdwdn.screen.GameScreen;

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

    public boolean isNextLevel = false;

    private float stateTime;

    private boolean isFlashing = false;
    private float flashTimer;
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
        player = new Player(0, 0);

        Array<TextureRegion> walk = new Array<TextureRegion>();
        for (int i = 0; i < 8; i++) {
            walk.add(new TextureRegion(Assets.player, i * 64, 0, 64, 64));
        }
        player.addAnimation("walk", new Animation(.13f, walk));


        Array<TextureRegion> idleup = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            idleup.add(new TextureRegion(Assets.player, i * 64, 64, 64, 64));
        }
        player.addAnimation("idleup", new Animation(.5f, idleup));

        Array<TextureRegion> idledown = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            idledown.add(new TextureRegion(Assets.player, 128 + i * 64, 64, 64, 64));
        }
        player.addAnimation("idledown", new Animation(.5f, idledown));


        Array<TextureRegion> walkdown = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            walkdown.add(new TextureRegion(Assets.player, 256 + i * 64, 64, 64, 64));
        }
        player.addAnimation("walkdown", new Animation(.2f, walkdown));


        Array<TextureRegion> walkup = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            walkup.add(new TextureRegion(Assets.player, i * 64, 128, 64, 64));
        }
        player.addAnimation("walkup", new Animation(.2f, walkup));

        player.setAnimation("idledown");

        InitLights();
        LoadWorld();
    }

    private void LoadWorld() {
        MapObject pl = ((MapLayer) map.getLayers().get("spawn")).getObjects().get("player");

        player.setX(pl.getProperties().get("x", Float.class) / 64f);
        player.setY(pl.getProperties().get("y", Float.class) / 64f);
        player.update(0);


        // load mah girl

        entities.add(new Enemy(this, player.getPosition().x, player.getPosition().y - 3, 1, 1, 8));
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
        updateLights(delta);
        updateCollision(delta);
    }

    private void updateEntities(float delta) {
        for (int i = 0; i < entities.size(); i++) {
            GameEntity entity = entities.get(i);

            entity.update(delta);

            if (entity.isRemoved())
                entities.remove(entity);
        }
    }

    float speed = 1.5f;

    public void updatePlayer(float delta) {
        Vector2 vel = new Vector2();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            vel.x = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            vel.x = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            vel.y = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            vel.y = -1;

        vel.nor().scl(speed);

        player.setVelocity(vel.x, vel.y);

        player.update(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || GameScreen.dialog != null) {
            playerLight.setDistance(3);
        } else {
            playerLight.setDistance(0);
        }
    }

    private boolean isLightning = false;

    private void updateLights(float delta) {
        rayHandler.update();

        if (!isLightning) {
            if (MathUtils.random(0, 1000) <= 1) {
                isLightning = true;
                stateTime = 0;
            }
        }

        if (isLightning) {
            float a = (float) Math.sin(stateTime * 5.5f) * .35f;
            ambLight.setColor(1, 1, 0, a);
            //rayHandler.setAmbientLight(1, 1, 0, a);
            if (a < -.01f) {
                isLightning = false;
                ambLight.setColor(1, 1, 1, .06f);
            }
        }

        if (isFlashing) {
            flashTimer += delta;

            if (MathUtils.random(0, 5) < 1)
                ambLight.setColor(1, 1, 1, MathUtils.randomBoolean() ? 0 : .5f);

            if (flashTimer > 12) {
                isFlashing = false;
                ambLight.setColor(1, 1, 1, .06f);
            }
        }
    }

    private void startFlashing() {
        isFlashing = true;
        flashTimer = 0;
    }

    private void updateCollision(float delta) {
        MapLayers layers = map.getLayers();


        triggerCollision((int) player.getBounds().x, (int) (player.getBounds().y),
                (int) (player.getBounds().x + player.getBounds().width), (int) (player.getBounds().y + player.getBounds().height));


        getTiles((int) player.getBounds().x, (int) (player.getBounds().y),
                (int) (player.getBounds().x + player.getBounds().width), (int) (player.getBounds().y + player.getBounds().height), tiles);

        for (Rectangle tile : tiles) {
            if (player.getBounds().overlaps(tile)) {

                if (Math.abs(player.getPosition().x - (tile.getX() + tile.getWidth() / 2)) > .1f) {
                    player.setX(prevRect.x + player.getBounds().width / 2);
                    prevRect.x = player.getBounds().x;
                }

                if (Math.abs(player.getPosition().y - (tile.getY() + tile.getHeight() / 2)) > .1f) {
                    player.setY(prevRect.y + player.getBounds().height / 2);
                    prevRect.y = player.getBounds().y;
                }

                player.update(0);
                // break;
            }
        }

    }

    private void triggerCollision(int startX, int startY, int endX, int endY) {
        MapLayers layers = map.getLayers();
        for (MapLayer layer : layers) {
            if (layer.getProperties().containsKey("Collision")) {
                if (layer.getProperties().get("Collision").toString().toLowerCase().equals("false"))
                    continue;
            }

            // obj
            if (layer instanceof TiledMapTileLayer == false) {
                MapObjects objects = layer.getObjects();

                // there are several other types, Rectangle is probably the most common one
                for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

                    Rectangle rectangle = rectangleObject.getRectangle(); // map.getProperties().get("height", Integer.class) -
                    rectangle = new Rectangle(rectangle.x / 64f, rectangle.y / 64f, rectangle.width / 64f, rectangle.height / 64f);

                    if (Intersector.overlaps(rectangle, player.getBounds())) {
                        // collision happened
                        if (rectangleObject.getName().equals("note1")) {

                        }

                        if (rectangleObject.getName().equals("end")) {
                            isNextLevel = true;
                        }

                        if (rectangleObject.getProperties().containsKey("Flash")) {
                            startFlashing();
                            rectangleObject.getProperties().remove("Flash");
                        }

                        if (rectangleObject.getProperties().containsKey("Dialog")) {
                            String d = rectangleObject.getProperties().get("Dialog", String.class).trim();
                            String[] ds = d.split(",");
                            for (int i = 0; i < ds.length; i++) {
                                ds[i] = Dialogs.Texts[Integer.parseInt(ds[i])];
                            }
                            GameScreen.dialog = new Dialog(ds);

                            rectangleObject.getProperties().remove("Dialog");
                        }
                    }
                }
                continue;
            }

            // cell
            for (int y = startY; y <= endY; y++) {
                for (int x = startX; x <= endX; x++) {
                    TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) layer).getCell(x, y);
                    if (cell != null) {
                        if (cell.getTile().getId() == 11 || cell.getTile().getId() == 10) {
                            ((TiledMapTileLayer) layer).setCell(x, y, null);
                        }
                    }
                }
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
            if (layer instanceof TiledMapTileLayer == false) {
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
        Light light = new PointLight(rayHandler, 32, new Color(1, 1, 1, .5f), 0, 0, 0);

        light.setSoft(true);
        light.setSoftnessLength(2);
        light.attachToBody(player);
        lights.add(light);
        playerLight = light;


        // ambient
        Light amb = new PointLight(rayHandler, 128, new Color(1, 1, 1, .033f), 5.5f, 0, 0);

        amb.setSoft(true);
        amb.setSoftnessLength(12);
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
