package wdwdn;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Simon on 1/23/2015.
 */
public class WorldRenderer {
    public static float WIDTH = 320;
    public static float HEIGHT = 240;

    private World world;
    private OrthographicCamera camera;

    public WorldRenderer(World world) {
        this.world = world;
        this.camera = new OrthographicCamera(WIDTH, HEIGHT);
       // this.camera.position.x = -WIDTH/2;
        //this.camera.position.y = -HEIGHT/2;
    }

    public void render(Batch batch) {
        camera.update();

        world.getRayHandler().setCombinedMatrix(camera);
        world.getRayHandler().render();
    }
}
