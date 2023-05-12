package com.lexiqb.awakening;

import com.lexiqb.awakening.world.Portal;
import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.graphics.*;
import com.rubynaxela.kyanite.math.Vec2;

public class Lobby extends Scene {

    @Override
    protected void init() {
        final var testPortal = new Portal(Colors.ORANGE);
        testPortal.setPosition(100, 100);
        add(testPortal);
        final var bg = new RectangleShape(2048, 2048);
        bg.setTexture(new Texture("assets/touch_sum_grass.png"));
        add(bg);//yes
    }

    @Override
    protected void loop() {

    }
}
