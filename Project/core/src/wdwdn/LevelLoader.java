package wdwdn;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by Simon on 1/23/2015.
 */
public class LevelLoader {
    public static TiledMap LoadMap() {
        return new TmxMapLoader().load("maps/level.tmx");
    }
}
