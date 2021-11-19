package me.remie.xeros.raids;

import simple.api.coords.WorldArea;
import simple.api.coords.WorldPoint;
import simple.api.filters.SimplePrayers;

/**
 * Created by Seth on October 10/25/2021, 2021 at 3:48 PM
 *
 * @author Seth Davis <sethdavis321@gmail.com>
 * @Discord Reminisce#1707
 */
public enum RoomTypes {

    OLM_ROOM("Great Olm", new WorldArea(new WorldPoint(3212, 5757, 0), new WorldPoint(3248, 5710, 0)), WorldPoint.NIL, SimplePrayers.Prayers.PROTECT_FROM_MISSILES, 7553, 7555, 7554),
    MAIN_ROOM("Main Room", new WorldArea(new WorldPoint(3292, 5204, 0), new WorldPoint(3323, 5183, 0)), WorldPoint.NIL, null),
    PUZZLE_ROOM("Puzzle Room", new WorldArea(new WorldPoint(3326, 5410, 0), new WorldPoint(3297, 5373, 0)), WorldPoint.NIL, null),
    OLM_STAIRS("Olm Dungeon", new WorldArea(new WorldPoint(3264, 5179, 0), new WorldPoint(3290, 5151, 0)), WorldPoint.NIL, null),
    ICE_DEMON("Ice Demon", new WorldArea(new WorldPoint(3301, 5342, 0), new WorldPoint(3325, 5372, 0)), new WorldPoint(3311, 5360, 0), SimplePrayers.Prayers.PROTECT_FROM_MELEE, 7585),
    MUTTADILE("Muttadile", new WorldArea(new WorldPoint(3296, 5338, 1), new WorldPoint(3326, 5309, 1)), new WorldPoint(3310, 5323, 1), SimplePrayers.Prayers.PROTECT_FROM_MELEE, 7563),
    SHAMAN("Lizardman shaman", new WorldArea(new WorldPoint(3296, 5277, 0), new WorldPoint(3327, 5207, 0)), new WorldPoint(3316, 5257, 0), SimplePrayers.Prayers.PROTECT_FROM_MELEE, 7573),
    SKELETAL_MYSTIC("Skeletal Mystic", new WorldArea(new WorldPoint(3297, 5275, 1), new WorldPoint(3324, 5214, 1)), new WorldPoint(3312, 5252, 1), SimplePrayers.Prayers.PROTECT_FROM_MAGIC, 7604, 7605, 7606),
    TEKTON("Tekton (enraged)", new WorldArea(new WorldPoint(3296, 5305, 1), new WorldPoint(3326, 5277, 1)), new WorldPoint(3311, 5287, 1), SimplePrayers.Prayers.PROTECT_FROM_MELEE, 7544),
    VANGUARD("Vanguard", new WorldArea(new WorldPoint(3297, 5342, 0), new WorldPoint(3325, 5310, 0)), new WorldPoint(3315, 5321, 0), SimplePrayers.Prayers.PROTECT_FROM_MELEE, 7527, 7528, 7529),
    VASA_NISTIRIO("Vasa Nistirio", new WorldArea(new WorldPoint(3297, 5309, 0), new WorldPoint(3326, 5278, 0)), new WorldPoint(3311, 5287, 0), SimplePrayers.Prayers.PROTECT_FROM_MAGIC, 7566);

    public final String roomName;
    public final WorldArea roomArea;
    public final WorldPoint stepPoint;

    public final SimplePrayers.Prayers protectionPrayer;

    public final int[] npcIds;

    RoomTypes(final String roomName, final WorldArea roomArea, final WorldPoint stepPoint, final SimplePrayers.Prayers protectionPrayer, final int... npcIds) {
        this.roomName = roomName;
        this.roomArea = roomArea;
        this.stepPoint = stepPoint;
        this.protectionPrayer = protectionPrayer;
        this.npcIds = npcIds;
    }
}
