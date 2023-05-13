package com.lexiqb.awakening;

import com.lexiqb.awakening.world.Portal;
import com.lexiqb.awakening.world.World;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.graphics.Colors;

public class Lobby extends World {

    private final Portal testPortal = new Portal(Colors.ORANGE);

    protected Lobby() {
        super(2048, 2048, GameContext.getInstance().getAssetsBundle().get("texture.world.lobby.background"));
    }

    @Override
    protected void init() {
        testPortal.setPosition(400, 400);
        add(testPortal);
    }

//    @Override
//    protected void loop() {
//        super.loop();
//    }
}
