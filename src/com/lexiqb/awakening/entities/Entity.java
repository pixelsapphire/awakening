package com.lexiqb.awakening.entities;

import com.lexiqb.awakening.GameObject;
import com.lexiqb.awakening.world.World;
import com.rubynaxela.kyanite.game.entities.MovingEntity;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Entity extends GameObject implements MovingEntity {

    protected int currentHp, maxHp = 1;
    protected float invincibilityTime = 0;
    protected boolean dead = false, recentlyDamaged = false, canBeDamaged = true;
    private Vector2f velocity = Vector2f.zero();
    private World world;

    public void damage(float dmg) {
        if (canBeDamaged) currentHp -= dmg;
        recentlyDamaged = true;
        makeInvincible();
    }

    public void makeInvincible() {
        canBeDamaged = false;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int hp) {
        maxHp = hp;
        currentHp = hp;
    }

    @Override
    public @NotNull Vector2f getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(@NotNull Vector2f velocity) {
        this.velocity = velocity;
    }

    public @Nullable World getWorld() {
        return world;
    }

    public void assignWorld(@Nullable World world) {
        this.world = world;
    }

    public void replenishHp() {
        currentHp = maxHp;
    }

    public boolean isDead() {
        return dead;
    }

    public void kill() {
        dead = true;
    }

    public abstract void update(@NotNull Time deltaTime);

    protected void keepInWorldBounds(@NotNull Time deltaTime) {
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
