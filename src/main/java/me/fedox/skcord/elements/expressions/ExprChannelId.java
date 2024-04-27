package me.fedox.skcord.elements.expressions;

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
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Channel by ID")
@Description("Get a pingable channel.")
@Examples({
        "command pingchannel:\n" +
                "\ttrigger:\n" +
                "\t\tset {_channel} to channel with id \"123456789\"\n" +
                "\t\tsend \"Look the %{_channel}% Channel is great!\" as webhook to \"https://discord.com/api/webhooks/?????/??????\""
})
@Since("2.3-RELEASE")
public class ExprChannelId extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprChannelId.class, String.class, ExpressionType.PROPERTY,  "channel with id %string%");
    }

    private Expression<String> channelId;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        channelId = (Expression<String>) expressions[0];
        return true;
    }

    @Override
    protected String[] get(Event event) {
        return new String[]{"<#" + channelId.getSingle(event) + ">"};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "channel with id " + channelId.toString(event, b);
    }

}
