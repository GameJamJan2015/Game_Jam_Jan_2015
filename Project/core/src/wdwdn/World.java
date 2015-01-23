package main;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import wdwdn.entity.GameEntity;

import java.util.ArrayList;

/**
 * Created by Simon on 1/23/2015.
 */
public class World {
    /** our boxes **/
    ArrayList<GameEntity> balls = new ArrayList<GameEntity>(128);

    /** BOX2D LIGHT STUFF */
    RayHandler rayHandler;

    ArrayList<Light> lights = new ArrayList<Light>(128);

    float sunDirection = -90f;

    public World() {
        /** BOX2D LIGHT STUFF BEGIN */
        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);

        rayHandler = new RayHandler(this);
        rayHandler.setAmbientLight(0f, 0f, 0f, 0.2f);
        rayHandler.setBlurNum(3);

        initPointLights();
        /** BOX2D LIGHT STUFF END */
    }

    void initPointLights() {
        clearLights();
        for (int i = 0; i < 128; i++) {
            PointLight light = new PointLight(
                    rayHandler, 0, null, 64, 0f, 0f);

            light.attachToBody(balls.get(i), 32 / 2f, 32 / 2f);
            light.setColor(
                    MathUtils.random(),
                    MathUtils.random(),
                    MathUtils.random(),
                    1f);
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
}
