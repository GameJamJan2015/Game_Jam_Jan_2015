package wdwdn.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import wdwdn.Assets;
import wdwdn.LevelLoader;
import wdwdn.World;
import wdwdn.WorldRenderer;

/**
 * Created by Simon on 1/23/2015.
 */
public class GameScreen extends Screen {
    private World world;
    private WorldRenderer renderer;

    @Override
    public void show() {
        this.world = new World(LevelLoader.LoadMap());
        this.renderer = new WorldRenderer(world);
    }

    @Override
    public void update(float delta) {
        world.update(delta);
    }

    @Override
    public void present(float delta) {
        getBatch().setProjectionMatrix(renderer.getCamera().combined);
        getBatch().begin();
        getBatch().draw(Assets.region, 0,0,  10,10);
        getBatch().end();

        renderer.render(getBatch());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
