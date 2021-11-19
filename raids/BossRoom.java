package me.remie.xeros.raids;

import simple.api.filters.SimplePrayers;
import simple.api.wrappers.SimpleNpc;

import java.util.function.Predicate;

/**
 * Created by Seth on October 10/25/2021, 2021 at 1:14 PM
 *
 * @author Seth Davis <sethdavis321@gmail.com>
 * @Discord Reminisce#1707
 */
public abstract class BossRoom extends RaidRoom {

    /**
     * Creates a new instance with the specified context.
     *
     * @param roomType
     */
    public BossRoom(RoomTypes roomType) {
        super(roomType);
    }

    public SimplePrayers.Prayers protectionPrayer() {
        return roomType.protectionPrayer;
    }

    public SimpleNpc nextNpc() {
        return ctx.npcs.populate().filter(roomType.npcIds).filter(NPC_FILTER).next();
    }

    public SimpleNpc nextNearestNpc() {
        return ctx.npcs.populate().filter(roomType.npcIds).filter(NPC_FILTER).nearest().next();
    }

    private static final Predicate<SimpleNpc> NPC_FILTER = (n) -> {
        return !n.isDead();
    };

}
