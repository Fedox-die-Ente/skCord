package me.fedox.skcord.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.fedox.skcord.elements.events.bukkit.WebhookSentEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Webhook Sent Event")
@Description("This event is getting called, after a message webhook is sent. CURRENTLY ONLY WORKING FOR MESSAGE WEBHOOKS.")
@Examples({
        "on webhook sent:\n" +
                "\tbroadcast event-message # Get the message\n" +
                "\tbroadcast event-webhook #Get the webhook link"
})
@Since("2.3-RELEASE")
public class EvtWebhookSent extends SkriptEvent {

    static {
        Skript.registerEvent("Webhook Sent", EvtWebhookSent.class, WebhookSentEvent.class, "webhook sent");

        EventValues.registerEventValue(WebhookSentEvent.class, String.class, new Getter<String, WebhookSentEvent>() {
            public String get(WebhookSentEvent e) {
                return e.getWebhook();
            }
        }, 0);

        EventValues.registerEventValue(WebhookSentEvent.class, String.class, new Getter<String, WebhookSentEvent>() {
            public String get(WebhookSentEvent e) {
                return e.getMessage();
            }
        }, 0);
    }

    @Nullable
    private String webhook = null;

    @Nullable
    private String message = null;

    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        if (literals.length > 0) {
            webhook = (String) literals[0].getSingle();
        }
        if (literals.length > 1) {
            message = (String) literals[1].getSingle();
        }

        return true;
    }

    @Override
    public boolean check(Event event) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "webhook sent";
    }
}
