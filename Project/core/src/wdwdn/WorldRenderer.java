package wdwdn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import wdwdn.entity.GameEntity;

/**
 * Created by Simon on 1/23/2015.
 */
public class WorldRenderer {
    public static float WIDTH = 10;
    public static float HEIGHT = 6;

    private World world;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;

    public WorldRenderer(World world) {
        this.world = world;
        this.camera = new OrthographicCamera(WIDTH, HEIGHT);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(world.getMap(), 1 / 64f);
        // this.camera.position.x = -WIDTH/2;
        //this.camera.position.y = -HEIGHT/2;
    }

    public void render(Batch batch) {
        // debug

        camera.position.x = world.getPlayer().getPosition().x;
        camera.position.y = world.getPlayer().getPosition().y;

        //end debug

        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.getPlayer().draw(batch);
        batch.end();

        world.getRayHandler().setCombinedMatrix(camera);
        world.getRayHandler().render();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
