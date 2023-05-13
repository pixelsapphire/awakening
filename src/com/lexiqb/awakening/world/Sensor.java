package com.lexiqb.awakening.world;

import com.lexiqb.awakening.entities.Entity;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

public class Sensor extends Entity {
    private float disturbedTime = 0.0f;
    private boolean disturbed = false;
    public Sensor() {

    }

    public void disturb(float force) {
        // calculate force into time
        disturbed = true;
        resumeAnimatedTexture();
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
