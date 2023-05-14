package com.lexiqb.awakening.world;

import com.lexiqb.awakening.GameObject;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.util.AssetId;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Obstacle extends GameObject {

    public Obstacle(@NotNull Type type) {
        setTexture(GameContext.getInstance().getAssetsBundle().<Texture>get(switch (type) {
            case BOULDER_1 -> "texture.world.boulders.boulder1";
            case BOULDER_2 -> "texture.world.boulders.boulder2";
            case BOULDER_3 -> "texture.world.boulders.boulder3";
        }));
        setSize(Vec2.multiply(Objects.requireNonNull(getTexture()).getSize(), 2));
        setOrigin(getSize().x / 2, getSize().y);
        setHitBox(new FloatRect(0, getSize().y * 0.67f, getSize().x, getSize().y * 0.33f));
    }

    public enum Type {
        BOULDER_1("texture.world.boulders.boulder1"),
        BOULDER_2("texture.world.boulders.boulder2"),
        BOULDER_3("texture.world.boulders.boulder3");

        final @AssetId String texture;

        Type(@NotNull @AssetId String texture) {
            this.texture = texture;
        }
    }
}
