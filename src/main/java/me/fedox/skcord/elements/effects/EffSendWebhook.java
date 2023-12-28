package me.fedox.skcord.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.fedox.skcord.utils.WebhookManager;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Webhook - Send message as webhook")
@Description("Simply send a normal message to a webhook.")
@Examples("send \"Hello World\" as webhook to \"https://discord.com/api/webhooks/?????/??????\"")
@Since("1.0-RELEASE")

public class EffSendWebhook extends Effect {
    private Expression<String> message;
    private Expression<String> webhook;

    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.message = (Expression<String>) exprs[0];
        this.webhook = (Expression<String>) exprs[1];

        return true;
    }


    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "Send webhook message: " + this.message.toString(e, debug) + " to webhook: " + this.webhook.toString(e, debug);
    }

    protected void execute(Event e) {
        if (this.message == null || this.webhook == null) {
            return;
        }
        WebhookManager.sendMessage((String)this.webhook.getSingle(e), (String)this.message.getSingle(e));
    }

    static {
        Skript.registerEffect(EffSendWebhook.class, (String[])new String[]{"send %string% as webhook to %string%"});
    }
}