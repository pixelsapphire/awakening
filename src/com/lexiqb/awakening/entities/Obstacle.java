package com.lexiqb.awakening.entities;

import com.lexiqb.awakening.MapObject;
import com.rubynaxela.kyanite.math.FloatRect;

public class Obstacle extends MapObject {
    public Obstacle() {
//        setSize();
//        setOrigin(getSize().x / 2, getSize().y);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public void makeSquareHitBox() {
        setHitBox(new FloatRect(getPositionOnMap().x - getSize().x / 2, getPositionOnMap().y - getSize().x,
                getSize().x, getSize().x));  // TODO probably wrong // why tho
    }
}
