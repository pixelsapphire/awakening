package com.lexiqb.awakening.entities;

import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

public class Player extends Slime {

    @Override
    public void update(@NotNull Time deltaTime) {
        super.update(deltaTime);
        keepInWorldBounds(deltaTime);
    }

    private void keepInWorldBounds(@NotNull Time deltaTime) {
        final var gGB = getGlobalBounds();
        assert getWorld() != null;
        final var worldBounds = getWorld().getBounds();
        float vX = getVelocity().x, vY = getVelocity().y;
        // Horizontal map constraints
        if (vX < 0 && gGB.left + vX * deltaTime.asSeconds() < 0)
            vX = -gGB.left / deltaTime.asSeconds();
        else if (vX > 0 && gGB.right + vX * deltaTime.asSeconds() > worldBounds.width)
            vX = (worldBounds.width - gGB.right) / deltaTime.asSeconds();
        // Vertical map constraints
        if (vY < 0 && gGB.top + vY * deltaTime.asSeconds() < 0)
            vY = -gGB.top / deltaTime.asSeconds();
        else if (vY > 0 && gGB.bottom + vY * deltaTime.asSeconds() > worldBounds.height)
            vY = (worldBounds.height - gGB.bottom) / deltaTime.asSeconds();
        setVelocity(Vec2.f(vX, vY));
    }
}
