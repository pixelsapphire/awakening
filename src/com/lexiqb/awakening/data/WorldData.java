package com.lexiqb.awakening.data;

import com.lexiqb.awakening.GameObject;
import com.lexiqb.awakening.world.Obstacle;
import com.lexiqb.awakening.world.Portal;
import com.lexiqb.awakening.world.World;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.graphics.Color;
import com.rubynaxela.kyanite.math.MathUtils;
import com.rubynaxela.kyanite.util.AssetId;
import com.rubynaxela.kyanite.util.Utils;

import java.util.List;

public class WorldData {

    public @AssetId String background;
    public int width, height;

    public List<PortalData> portals;
    public List<DecorData> decor;

    public World build() {
        final var world = new World(width, height, GameContext.getInstance().getAssetsBundle().get(background));
        for (final var portalData : portals) {
            final var portal = portalData.build();
            world.add(portal);
        }
        for (final var decorData : decor) {
            final var decor = decorData.build();
            world.add(decor);
        }
        return world;
    }

    public static class PortalData {

        public int x, y;
        public String color, facing, destination;

        Portal build() {
            final boolean altTexture = facing.equalsIgnoreCase("right");
            final var portal = new Portal(Color.parse(color), altTexture);
            portal.setPosition(x, y);
            return portal;
        }
    }

    public static class DecorData {

        public int x, y;
        public String type;

        GameObject build() {
            if (type.equalsIgnoreCase("boulder")) {
                return Utils.lambdaInit(new Obstacle(MathUtils.randomOf(
                        Obstacle.Type.BOULDER_1, Obstacle.Type.BOULDER_2, Obstacle.Type.BOULDER_3)), o -> o.setPosition(x, y));
            } else {
                throw new RuntimeException("Unknown decor type: " + type);
            }
        }
    }
}
