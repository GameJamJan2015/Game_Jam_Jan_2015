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
    public static Texture player;
    public static Texture menu;
    public static Texture gameover;
    public static Texture shadow;
    public static TextureRegion region;
    public static TextureRegion pixel;

    public static BitmapFont font;

    public static Music music;

    public static Sound thunder;
    public static Sound rain;
    public static Sound noise;

    public static Preferences prefs;


    public static void load() {
        manager = new AssetManager();

        // Init Preferences
        prefs = Gdx.app.getPreferences("wdwdn.prefs");

        // === ASSETS LOADING === //

        // Load item
        TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.Nearest;
        param.magFilter = Texture.TextureFilter.Nearest;
        manager.load("graphics/lamp.png", Texture.class, param);

        // Load item
        param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.Nearest;
        param.magFilter = Texture.TextureFilter.Nearest;
        manager.load("graphics/walking_player_sheet.png", Texture.class, param);

        param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.Nearest;
        param.magFilter = Texture.TextureFilter.Nearest;
        manager.load("graphics/menu.png", Texture.class, param);

        param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.Nearest;
        param.magFilter = Texture.TextureFilter.Nearest;
        manager.load("graphics/gameover.png", Texture.class, param);

        param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.Nearest;
        param.magFilter = Texture.TextureFilter.Nearest;
        manager.load("graphics/shadow.png", Texture.class, param);

        // Load Font
        BitmapFontLoader.BitmapFontParameter fontParam = new BitmapFontLoader.BitmapFontParameter();
        fontParam.genMipMaps = true;
        fontParam.minFilter = Texture.TextureFilter.Nearest;
        fontParam.magFilter = Texture.TextureFilter.Nearest;
        manager.load("font/bit.fnt", BitmapFont.class, fontParam);

        // Music
        manager.load("sound/music.mp3", Music.class);

        manager.load("sound/thunder_fade.ogg", Sound.class);
        manager.load("sound/rain.ogg", Sound.class);
        manager.load("sound/noise.ogg", Sound.class);
    }

    public static void loadFinished() {

        Texture items = manager.get("graphics/lamp.png", Texture.class);
        player = manager.get("graphics/walking_player_sheet.png", Texture.class);
        menu = manager.get("graphics/menu.png", Texture.class);
        gameover = manager.get("graphics/gameover.png", Texture.class);
        shadow = manager.get("graphics/shadow.png", Texture.class);

        loadRegions(items);

        // Load Font
        font = manager.get("font/bit.fnt", BitmapFont.class);
        font.setUseIntegerPositions(false);

        music = manager.get("sound/music.mp3");
        music.setLooping(true);
        playMusic();

        thunder = manager.get("sound/thunder_fade.ogg");
        rain = manager.get("sound/rain.ogg");
        noise = manager.get("sound/noise.ogg");
    }

    private static void loadRegions(Texture item) {
        region = new TextureRegion(item, 0, 0, 400, 100);
        pixel = new TextureRegion(item, 30, 30, 4, 4);
    }

    public static void playSound(Sound sound) {
        playSound(sound, .3f);
    }

    public static void playSound(Sound sound, float vol) {
        sound.play(vol);
    }

    public static void playMusic() {
        if (music != null) if (prefs.getBoolean("music", true)) music.play();
    }

    public static void PauseMusic() {
        if (music != null) music.pause();
    }

    public static boolean ToggleMusic() {
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

    public static boolean isMusicEnabled() {
        return prefs.getBoolean("music", true);
    }

    public static void dispose() {
        manager.dispose();
    }
}
