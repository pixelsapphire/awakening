package com.lexiqb.awakening;

import com.lexiqb.awakening.world.Portal;
import com.lexiqb.awakening.world.World;
import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.graphics.Texture;

public class Lobby extends World {

    private final Portal testPortal = new Portal(Colors.ORANGE);

    protected Lobby() {
        super(2048, 2048, new Texture("assets/touch_sum_grass.png"));
    }

    @Override
    protected void init() {
        testPortal.setPosition(100, 100);
        add(testPortal);
    }

//    @Override
//    protected void loop() {
//        super.loop();
//    }
}
