package com.lexiqb.awakening.entities;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.graphics.AnimatedTexture;
import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.graphics.TextureAtlas;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

public class Leech extends Entity {

    private static final AnimatedTexture animation = new AnimatedTexture(GameContext.getInstance().getAssetsBundle().<TextureAtlas>get("texture.entity.leech")
                                                                                    .getRow(110, 110, 8), 0.2f);
    private boolean awakened = false;
    private Time eatingTime = Time.ZERO;

    public Leech() {
        setSize(animation.getFrame(0).getSize());
        setOrigin(getSize().x / 2, getSize().y / 2);
        setFillColor(Colors.TRANSPARENT);
    }

    public void spawn(@NotNull Vector2f position) {
        setPosition(position);
        setTexture(animation);
        setFillColor(Colors.WHITE);
        awakened = true;
    }

    @Override
    public void update(@NotNull Time deltaTime) {
        if (!awakened && getTexture() instanceof AnimatedTexture) freezeAnimatedTexture();
        else {
            if (getTexture() instanceof AnimatedTexture) resumeAnimatedTexture();
            eatingTime = Time.add(eatingTime, deltaTime);
            if (eatingTime.asSeconds() >= animation.getFrameDuration() * animation.getFramesCount() * 0.9f) {
                setTexture(animation.getFrame(animation.getFramesCount() - 1));
            }
        }
    }
}
