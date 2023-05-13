package com.lexiqb.awakening;

import com.rubynaxela.kyanite.game.entities.HitBoxObject;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.math.FloatRect;
import org.jetbrains.annotations.Nullable;

public abstract class GameObject extends RectangleShape implements HitBoxObject {

    private FloatRect hitBox;

    @Override
    public @Nullable FloatRect getHitBox() {
        return hitBox;
    }

    @Override
    public void setHitBox(@Nullable FloatRect hitBox) {
        this.hitBox = hitBox;
    }
}
