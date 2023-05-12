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

        // Moving objects
//        for (final Drawable obj : getContext().getWindow().getScene()) {
//            if (obj instanceof final MapObject gameObject) {
//                gameObject.setPosition(Vec2.subtract(gameObject.getPositionOnMap(), mapOffset));
//            }
//            if (obj instanceof final GameText text) {
//                text.setPosition(Vec2.subtract(text.getPositionOnMap(), mapOffset));
//            }
//            if (obj instanceof final MapBackground back) {
//                float backgroundOffsetX, backgroundOffsetY;
//                if (getMapSize().x > window.getSize().x) {
//                    backgroundOffsetX = (back.mainBody.getSize().x - window.getSize().x) / (getMapSize().x - window.getSize().x);
//                } else backgroundOffsetX = (back.mainBody.getSize().x - window.getSize().x) / getMapSize().x;
//                if (getMapSize().y > window.getSize().y) {
//                    backgroundOffsetY = (back.mainBody.getSize().y - window.getSize().y) / (getMapSize().y - window.getSize().y);
//                } else backgroundOffsetY = (back.mainBody.getSize().y - window.getSize().y) / getMapSize().y;
//                back.setPosition(Vec2.multiply(Vec2.subtract(back.getPositionOnMap(), Vec2.f(backgroundOffsetX, backgroundOffsetY)), getMapOffset()));
//            }
//        }
    }
}
