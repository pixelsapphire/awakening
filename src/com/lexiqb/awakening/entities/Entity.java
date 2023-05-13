package com.lexiqb.awakening.entities;

import com.lexiqb.awakening.GameObject;
import com.rubynaxela.kyanite.game.entities.MovingEntity;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

public abstract class Entity extends GameObject implements MovingEntity {

    protected int currentHp, maxHp = 1;
    protected float invincibilityTime = 0;
    protected boolean dead = false, recentlyDamaged = false, canBeDamaged = true;
    private Vector2f velocity = Vector2f.zero();

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
}
