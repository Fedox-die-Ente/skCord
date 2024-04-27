package me.fedox.skcord.elements.events.bukkit;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class WebhookSentEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private String webhook;
    private String message;

    public WebhookSentEvent(String webhook, String message) {
        this.webhook = webhook;
        this.message = message;

        System.out.println("Webhook sent: " + message);
    }

    public String getMessage() {
        return message;
    }

    public String getWebhook() {
        return webhook;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
