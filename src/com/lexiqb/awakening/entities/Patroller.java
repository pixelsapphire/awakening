package com.lexiqb.awakening.entities;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.Sound;
import com.rubynaxela.kyanite.graphics.AnimatedTexture;
import com.rubynaxela.kyanite.math.MathUtils;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class Patroller extends Entity {
    private static final float betweenYells = 2;
    private final float detectionRange, movementSpeed;
    private float yellCd = 0;
    private int patrolPoint = 0;
    private ArrayList<Vector2f> patrolPath = new ArrayList<>();

    public Patroller(Creature creature) {
        detectionRange = creature.detectionRange;
        movementSpeed = creature.movementSpeed;
        setSize(creature.size);
        setTexture(creature.texture);
    }

    public void makePatrolPath(Vector2f... points) {
        patrolPath.addAll(Arrays.asList(points));
    }

    @Override
    public void update(@NotNull Time deltaTime) {
        if (yellCd > 0) yellCd -= deltaTime.asSeconds();
        if (yellCd < 0) yellCd = 0;
        if (yellCd == 0) {
            assert getWorld() != null;
            if (MathUtils.distance(getPosition(), getWorld().getPlayer().getPosition()) < detectionRange) {
                yellCd = betweenYells;
                // TODO make some other NOOOOISE
                getWorld().makeNoise(getPosition(), 80);
            }
        }

        // Patrolling
        if (patrolPath.size() > 0) {
            if (getGlobalBounds().contains(patrolPath.get(patrolPoint))) patrolPoint = (patrolPoint + 1) % patrolPath.size();
            setVelocity(Vec2.multiply(Vec2.subtract(patrolPath.get(patrolPoint), getPosition()).normalized(), movementSpeed));
        }
        if (getVelocity().x * getScale().x < 0) setScale(-getScale().x, getScale().y);
        if (getVelocity().y * getScale().y < 0) setScale(getScale().x, -getScale().y);
    }

    public enum Creature {
        BIRB(250, 80.0f, new Vector2f(80, 60),
                GameContext.getInstance().getAssetsBundle().get("texture.birb"),
                GameContext.getInstance().getAssetsBundle().get("sound.birb"));

        final float detectionRange, movementSpeed;
        final Vector2f size;
        final AnimatedTexture texture;
        final Sound spotSound;

        Creature(float detectionRange, float movementSpeed, Vector2f size, AnimatedTexture texture, Sound sound) {
            this.detectionRange = detectionRange;
            this.movementSpeed = movementSpeed;
            this.size = size;
            this.texture = texture;
            this.spotSound = sound;
        }
    }
}
