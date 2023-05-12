package com.lexiqb.awakening.entities;

import com.lexiqb.awakening.MapObject;
import com.rubynaxela.kyanite.game.entities.MovingEntity;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

public class Entity extends MapObject implements MovingEntity {

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

    protected void preventBlockCollision(Time deltaTime) {
        float X = gGB().left + getOrigin().x, Y = gGB().top + getOrigin().y,
                movementX = getVelocity().x, movementY = getVelocity().y;
        // Horizontal map restraints
        if (movementX > 0 && X + gGB().width + movementX * deltaTime.asSeconds() >= mapSize.x) {
            movementX = (mapSize.x - (X + gGB().width)) / deltaTime.asSeconds();
        }
        if (movementX < 0 && X + movementX * deltaTime.asSeconds() <= 0) {
            movementX = -X / deltaTime.asSeconds();
        }
        // Vertical map restraints
        if (movementY > 0 && Y + movementY * deltaTime.asSeconds() >= mapSize.y) {
            movementY = (mapSize.y - Y) / deltaTime.asSeconds();
        }
        if (movementY < 0 && Y - gGB().height + movementY * deltaTime.asSeconds() <= 0) {
            movementY = (-Y + gGB().height) / deltaTime.asSeconds();
        }
        setVelocity(Vec2.f(movementX, movementY));
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
}
