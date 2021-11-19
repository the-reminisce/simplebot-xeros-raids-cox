package me.remie.xeros.raids;

import simple.api.ClientAccessor;
import simple.api.ClientContext;
import simple.api.wrappers.SimpleGameObject;

/**
 * Created by Seth on October 10/25/2021, 2021 at 1:11 PM
 *
 * @author Seth Davis <sethdavis321@gmail.com>
 * @Discord Reminisce#1707
 */
public abstract class RaidRoom extends ClientAccessor<ClientContext> {

    public final RoomTypes roomType;

    /**
     * Creates a new instance with the specified context.
     */
    public RaidRoom(final RoomTypes roomType) {
        super(ClientContext.instance());
        this.roomType = roomType;
    }

    public abstract void processRoom();

    public boolean inRoom() {
        return ctx.pathing.inArea(roomType.roomArea);
    }

    public void handlePassage() {
        handlePassage(true);
    }

    public void handlePassage(boolean forward) {
        SimpleGameObject passage;
        if (forward) {
            passage = (SimpleGameObject) ctx.objects.populate().filter("passage").filter((o) -> o.getLocation().getY() >= roomType.stepPoint.getY()).nearest().next();
        } else {
            passage = (SimpleGameObject) ctx.objects.populate().filter("passage").nearest().next();
        }
        if (passage != null) {
            if (ctx.pathing.distanceTo(passage) <= 8) {
                passage.interact(502);
            } else {
                ctx.pathing.step(passage);
            }
        }
    }

}
