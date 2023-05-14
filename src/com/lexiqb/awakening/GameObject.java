package com.lexiqb.awakening;

import com.rubynaxela.kyanite.game.entities.HitBoxObject;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.math.FloatRect;
import org.jetbrains.annotations.Nullable;

public abstract class GameObject extends RectangleShape implements HitBoxObject {

    private FloatRect hitBox = null;

    @Override
    public @Nullable FloatRect getHitBox() {
        final var bounds = getGlobalBounds();
        return new FloatRect(hitBox.left + bounds.left, hitBox.top + bounds.top, hitBox.width, hitBox.height);
    }

    public void setHitBox(@Nullable FloatRect hitBox) {
        this.hitBox = hitBox;
    }
}
