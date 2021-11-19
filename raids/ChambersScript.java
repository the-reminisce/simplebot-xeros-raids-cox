package me.remie.xeros.raids;

import me.remie.xeros.raids.rooms.*;
import simple.api.events.ChatMessageEvent;
import simple.api.listeners.SimpleMessageListener;
import simple.api.script.Category;
import simple.api.script.Script;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleItem;
import simple.api.wrappers.SimpleNpc;

import java.awt.*;

/**
 * Created by Seth on October 10/25/2021, 2021 at 12:25 AM
 *
 * @author Seth Davis <sethdavis321@gmail.com>
 * @Discord Reminisce#1707
 */
@ScriptManifest(author = "Reminisce", category = Category.MINIGAMES,
        description = "Mindlessly does Chambers of Xerics", name = "SChambers of Xerics", version = "1.3", discord = "Reminisce#1707", servers = {"Xeros"})
public class ChambersScript extends Script implements SimplePaintable, SimpleMessageListener {

    public String status;
    public long startTime;

    private final RaidRoom[] RAID_ROOMS = {
            new MainRoom(), new OlmRoom(), new IceDemonRoom(), new MuttadileRoom(), new ShamanRoom(), new SkeletalMysticRoom(), new TektonRoom(),
            new VanguardRoom(), new VasaNistirioRoom(), new OlmStairs()
    };

    @Override
    public boolean onExecute() {
        startTime = System.currentTimeMillis();
        status = "Waiting to start...";
        ctx.log("Thanks for using %s!", getName());
        return true;
    }

    @Override
    public void onProcess() {
        for (RaidRoom room : RAID_ROOMS) {
            if (room.inRoom()) {
                status = "Within " + room.roomType.roomName;

                handleFood();
                handlePrayer();

                if (room instanceof BossRoom) {
                    BossRoom bossRoom = (BossRoom) room;

                    if (ctx.players.getLocal().getLocation().getY() < bossRoom.roomType.stepPoint.getY()) {
                        ctx.pathing.step(bossRoom.roomType.stepPoint);
                        ctx.sleep(650);
                    } else {
                        SimpleNpc npc = bossRoom.nextNpc();
                        if (npc != null) {
                            ctx.prayers.prayer(bossRoom.protectionPrayer(), true, false);
                            if (ctx.players.getLocal().getInteracting() == null) {
                                npc.interact("attack");
                                ctx.onCondition(() -> ctx.players.getLocal().getInteracting() != null, 250, 12);
                            }
                        } else {
                            status = "Finding passage";
                            ctx.prayers.disableAll();
                            //walk to passage
                            bossRoom.handlePassage(true);
                        }
                    }

                } else { // End of Room is of type Boss Room
                    //TODO: add logic here to make script process certain rooms (not sure if olm stairs can be skipped by backwards passaging)
                    status = "Finding passage";
                    ctx.prayers.disableAll();
                    //walk to passage
                    room.handlePassage(false);
                }


                break;
            }
        }
    }

    private void handlePrayer() {
        if (ctx.prayers.points() <= 20) {
            final SimpleItem potion = ctx.inventory.populate().filterContains("Prayer potion", "Super restore").next();
            final int cached = ctx.prayers.points();
            status = "Drinking prayer";
            if (potion != null && potion.interact("drink")) {
                ctx.onCondition(() -> ctx.prayers.points() > cached, 250, 12);
            }
        }
    }

    private void handleFood() {
        if (ctx.combat.health() < 52) {
            final SimpleItem food = ctx.inventory.populate().filter("Shark", "Monkfish", "Anglerfish").next();
            if (food != null) {
                final int cached = ctx.inventory.getFreeSlots();
                status = "Eating " + food.getName();
                food.interact("eat");
                ctx.onCondition(() -> ctx.inventory.getFreeSlots() > cached, 250, 9);
            }
        }
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onPaint(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(5, 2, 192, 72);
        g.setColor(Color.decode("#adab2b"));
        g.drawRect(5, 2, 192, 72);
        g.drawLine(8, 24, 194, 24);

        g.setColor(Color.decode("#1d7515"));
        g.drawString("RChambers of Xerics               v. " + "0.1", 12, 20);
        g.drawString("Time: " + ctx.paint.formatTime(System.currentTimeMillis() - startTime), 14, 42);
        g.drawString("Status: " + status, 14, 56);
    }

    @Override
    public void onChatMessage(ChatMessageEvent event) {
        //The room has been cleared and you are free to pass.
    }
}


/**
 * Main room: 13137
 * Muttidile: 13138-13139 (3308, 5329, 1) npc: 7563: muttadile
 * Vasa: 13138 (3311, 5288, 0) npc: 7566: Vasa Nistirio
 * Vanguard: (3312, 5311, 0) -> (3313, 5321, 0) npc: 7527, 7528, 7529: Vanguard
 * Ice demon: (3311, 5360, 0) npc: 7585: Ice demon
 * Puzzel room: (3311, 5374, 0) step to: (3318, 5399, 0)
 * Puzeel room 2 (Skeletal Mystic): (3312, 5218, 1) step to: (3312, 5252, 1)
 * kill 7604 first, 7605 second, 7606 last
 * stay on tile: (3317, 5258, 1) while killing the second
 * <p>
 * Tekton: (3310, 5277, 1) step to: (3311, 5287, 1) npc Tekton (enraged): 7544
 * Shaman room: (3308, 5208, 0) step to (3316, 5257, 0) npc: lizardman shaman (7573)
 * Attack them one by one
 * Olm room: (3275, 5159, 0) Enter hole: object id: 29734 action id: 502
 * Portal room: (3232, 5721, 0) region id: 12889 Mystical barrier id: (29879) Action id: 502
 * <p>
 * Great Olm (Right claw): 7553
 * Great Olm (Left claw): 7555
 * Great Olm : 7554
 */