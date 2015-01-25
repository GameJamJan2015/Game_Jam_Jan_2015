package wdwdn;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by Simon on 1/23/2015.
 */
public class LevelLoader {
    public static TiledMap LoadMap(int level) {
        TmxMapLoader.Parameters parms = new TmxMapLoader.Parameters();
        parms.textureMagFilter = Texture.TextureFilter.Nearest;
        parms.textureMinFilter = Texture.TextureFilter.Nearest;

        return new TmxMapLoader().load("graphics/world" + level + ".tmx", parms);
    }
}
