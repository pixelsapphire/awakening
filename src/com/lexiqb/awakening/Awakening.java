package com.lexiqb.awakening;

import com.rubynaxela.kyanite.game.Game;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.graphics.AnimatedTexture;
import com.rubynaxela.kyanite.graphics.ConstTexture;
import com.rubynaxela.kyanite.graphics.Texture;

import java.util.stream.Stream;

public class Awakening extends Game {

    public static void main(String[] args) {
        Game.run(Awakening.class, args);
    }

    @Override
    protected void preInit() {

        final AssetsBundle assets = getContext().getAssetsBundle();
        assets.register("portal", new AnimatedTexture(Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8)
                                                            .map(i -> new Texture("assets/portal/stage" + i + ".png"))
                                                            .toArray(ConstTexture[]::new), 1 / 9f));
    }

    @Override
    protected void init() {
        getContext().setupWindow(1280, 720, "Awakening of the Rarely Observed Big Unidentified Sus Thing");
        getContext().getWindow().setScene(new Lobby());
    }
}