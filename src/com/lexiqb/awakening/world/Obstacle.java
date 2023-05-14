package com.lexiqb.awakening.world;

import com.lexiqb.awakening.GameObject;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.MathUtils;
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
            case BUSH_1 -> "texture.world.bush.bush1";
            case BUSH_2 -> "texture.world.bush.bush2";
            case TREE_1 -> "texture.world.bush.tree1";
            case TREE_2 -> "texture.world.bush.tree2";
            case TREE_3 -> "texture.world.bush.tree3";
        }));
        setSize(Vec2.multiply(Objects.requireNonNull(getTexture()).getSize(),
                              type.name().toLowerCase().contains("tree") ? 4 : 2));
        setOrigin(getSize().x / 2, getSize().y);
        if (MathUtils.probability(0.5f)) setScale(-1, 1);
        if (type.name().toLowerCase().contains("boulder"))
            setHitBox(new FloatRect(0, getSize().y * 0.67f, getSize().x, getSize().y * 0.33f));
        if (type.name().toLowerCase().contains("tree"))
            setHitBox(new FloatRect(getSize().x * 0.33f, 0.99f, getSize().x * 0.33f, 0.01f));
    }

    public enum Type {
        BOULDER_1("texture.world.boulders.boulder1"),
        BOULDER_2("texture.world.boulders.boulder2"),
        BOULDER_3("texture.world.boulders.boulder3"),
        BUSH_1("texture.world.bush.bush1"),
        BUSH_2("texture.world.bush.bush2"),
        TREE_1("texture.world.bush.tree1"),
        TREE_2("texture.world.bush.tree2"),
        TREE_3("texture.world.bush.bush3");

        final @AssetId String texture;

        Type(@NotNull @AssetId String texture) {
            this.texture = texture;
        }
    }
}
