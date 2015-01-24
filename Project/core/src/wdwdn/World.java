package wdwdn;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import wdwdn.entity.GameEntity;

import java.util.ArrayList;

/**
 * Created by Simon on 1/23/2015.
 */
public class World {
    private ArrayList<GameEntity> entities = new ArrayList<GameEntity>(128);
    private TiledMap map;

    /**
     * BOX2D LIGHT STUFF
     */
    private RayHandler rayHandler;
    private ArrayList<Light> lights = new ArrayList<Light>(128);

    public World(TiledMap map) {
        this.map = map;
        // Init
        for (int i = 0; i < 128; i++) {
            entities.add(new GameEntity());
        }
        InitLights();
    }

    private void InitLights() {
        /** BOX2D LIGHT STUFF BEGIN */
        RayHandler.setGammaCorrection(true);
        //RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(this);
        rayHandler.setAmbientLight(0f, 0f, 0f, 0.01f);
        rayHandler.setShadows(true);
        rayHandler.setCulling(true);
        rayHandler.setBlurNum(10);

        initPointLights();
        /** BOX2D LIGHT STUFF END */}

    public void update(float delta) {
        rayHandler.update();
    }

    // Box2d lights
    void initPointLights() {
        clearLights();
        for (int i = 0; i < 128; i++) {
            Light light = new ConeLight(rayHandler, 128, Color.BLUE, 64, 0, 0, 0, MathUtils.random(15f, 40f));

            light.setSoft(true);
            light.setSoftnessLength(10);
            light.attachToBody(entities.get(i));//, 0, 0);
            lights.add(light);
        }
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
}
