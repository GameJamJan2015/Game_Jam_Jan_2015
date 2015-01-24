package wdwdn.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;

import wdwdn.Assets;
import wdwdn.LevelLoader;
import wdwdn.World;
import wdwdn.WorldRenderer;
import wdwdn.entity.Dialog;

/**
 * Created by Simon on 1/23/2015.
 */
public class GameScreen extends Screen {
    private World world;
    private WorldRenderer renderer;
    private int level;
    Dialog dialog;
    OrthographicCamera uiCam;

    public GameScreen(int level) {
        this.level = level;
    }

    @Override
    public void show() {
        this.world = new World(LevelLoader.LoadMap(level));
        this.renderer = new WorldRenderer(world);
        this.dialog = new Dialog("hej", "bajs"); 
        this.uiCam = new OrthographicCamera(1280, 720);
        uiCam.position.x = 1280 / 2;
        uiCam.position.y = 720/2;
    }

    @Override
    public void update(float delta) {
        world.update(delta);
        
        if(dialog != null)  
        	dialog.update(delta);

        if (world.isNextLevel) {
            setScreen(new NextLevelScreen(level + 1));
        }
        
        if(dialog != null && dialog.removeDialog == true){
        	
        	dialog = null;
        }
    }

    @Override
    public void present(float delta) {
        //getBatch().setProjectionMatrix(renderer.getCamera().combined);
    	 renderer.render(getBatch());
    	 
    	 uiCam.update();
    	 getBatch().setProjectionMatrix(uiCam.combined  );
        getBatch().begin();
        //getBatch().draw(Assets.region, 0,0,  10,10);
        
        if(dialog != null)
        	dialog.draw(getBatch());
        
        getBatch().end();

       
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
