package me.fedox.skcord.elements.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.fedox.skcord.elements.events.bukkit.WebhookSentEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Webhook Message")
@Description("Get the message of a webhook.")
@Examples({
        "on webhook sent:\n" +
                "\tbroadcast event-message"
})
@Since("2.3-RELEASE")
public class ExprEventText extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprEventText.class, String.class, ExpressionType.SIMPLE, "event-message");
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult pr) {
        if (!ScriptLoader.isCurrentEvent(WebhookSentEvent.class)) {
            Skript.error("You can not use event-message expression in any event but on webhook sent.");
            return false;
        }
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "the event webhook message";
    }

    @Override
    @Nullable
    protected String[] get(Event e) {
        return new String[]{((WebhookSentEvent) e).getMessage()};
    }

}
