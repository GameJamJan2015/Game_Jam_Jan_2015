package wdwdn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Simon on 1/23/2015.
 */
public class Assets {
    public static AssetManager manager;

    public static TextureRegion region;

    public static BitmapFont font;

    public static Music music;

    public static Preferences prefs;


    public static void load() {
        manager = new AssetManager();

        // Init Preferences
        prefs = Gdx.app.getPreferences("wdwdn.prefs");

        // === ASSETS LOADING === //

        // Load item
        TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.Linear;
        param.magFilter = Texture.TextureFilter.Linear;
        manager.load("graphics/libgdx.png", Texture.class, param);


        // Load Font
        BitmapFontLoader.BitmapFontParameter fontParam = new BitmapFontLoader.BitmapFontParameter();
        fontParam.genMipMaps = true;
        fontParam.minFilter = Texture.TextureFilter.MipMapLinearLinear;
        fontParam.magFilter = Texture.TextureFilter.Linear;
        // TODO manager.load("", BitmapFont.class, fontParam);

        // Music
        //manager.load("sound/music.mp3", Music.class);
    }

    public static void loadFinished() {

        Texture items = manager.get("graphics/libgdx.png", Texture.class);

        loadRegions(items);

        // Load Font
        //font = manager.get("font/", BitmapFont.class);
        //font.setUseIntegerPositions(false);

       // music = manager.get("sound/music.mp3");
        //music.setLooping(true);
        playMusic();
    }

    private static void loadRegions(Texture item) {
        region = new TextureRegion(item, 576, 128, 128, 128);

    }

    public static void playSound (Sound sound) {
        if (sound != null) if (prefs.getBoolean("sound", true)) sound.play(1);
    }

    public static void playMusic () {
        if (music != null) if (prefs.getBoolean("music", false)) music.play();
    }

    public static void PauseMusic () {
        if (music != null) music.pause();
    }

    public static boolean ToggleMusic () {
        prefs.putBoolean("music", !prefs.getBoolean("music", false));
        prefs.putBoolean("sound", !prefs.getBoolean("sound", true));
        prefs.flush();

        if (prefs.getBoolean("music", false)) {
            playMusic();
            return true;
        }

        PauseMusic();
        return false;
    }

    public static boolean isMusicEnabled () {
        return prefs.getBoolean("music", false);
    }

    public static void dispose() {
        manager.dispose();
    }
}
