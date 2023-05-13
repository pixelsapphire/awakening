package com.lexiqb.awakening.entities;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.graphics.AnimatedTexture;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

public class Sensor extends Entity {

    private float disturbedTime = 0.0f;
    private boolean disturbed = false;

    public Sensor() {
        final var texture = GameContext.getInstance().getAssetsBundle().<AnimatedTexture>get("texture.world.sensor");

        setSize(Vec2.multiply(texture.getSize(), 4));
        setTexture(texture);
//        setFillColor(Colors.PURPLE);
        setOrigin(getSize().x / 2f, getSize().y);
        freezeAnimatedTexture();
    }

    public void disturb(float volume) {
        if (volume > 65) {
            var converted = volume / 20f - 2f;
            if (!disturbed) {
                disturbed = true;
                resumeAnimatedTexture();
            }
            if (disturbedTime < converted)
                disturbedTime = converted;
            if (getWorld() != null) getWorld().increaseDangerLevel(converted + 0.5f);
        }
    }

    @Override
    public void update(@NotNull Time deltaTime) {
        if (disturbedTime > 0) {
            disturbedTime -= deltaTime.asSeconds();
        }
        if (disturbedTime <= 0 && disturbed) {
            freezeAnimatedTexture();
            disturbedTime = 0;
            disturbed = false;
        }
    }
}
