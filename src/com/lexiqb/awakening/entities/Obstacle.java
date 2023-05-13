package com.lexiqb.awakening.entities;

import com.lexiqb.awakening.GameObject;

public class Obstacle extends GameObject {

    public Obstacle() {
//        setSize();
//        setOrigin(getSize().x / 2, getSize().y);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public void makeSquareHitBox() {
//        setHitBox(new FloatRect(getPositionOnMap().x - getSize().x / 2, getPositionOnMap().y - getSize().x,
//                getSize().x, getSize().x));  // TODO probably wrong
    }
}
