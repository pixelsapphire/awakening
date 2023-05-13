package com.lexiqb.awakening.world;

import com.lexiqb.awakening.MapObject;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.graphics.AnimatedTexture;
import com.rubynaxela.kyanite.graphics.Color;
import com.rubynaxela.kyanite.math.Vec2;

public class Portal extends MapObject {

    public Portal(Color color) {
        setSize(64, 104);
        setOrigin(Vec2.divide(getSize(), 2));
        setFillColor(color);
        setTexture(GameContext.getInstance().getAssetsBundle().<AnimatedTexture>get("texture.world.portal"));
    }
}
