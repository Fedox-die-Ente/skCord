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

import java.util.Objects;

@Name("Emoji by ID")
@Description("Get a emoji.")
@Examples({
        "command emoji:\n" +
                "\ttrigger:\n" +
                "\t\tset {_emoji} to emoji named \"wave\" with id \"12345\"\n" +
                "\t\tsend \"Look at this emoji: %{_emoji}%\" as webhook to \"https://discord.com/api/webhooks/?????/??????\""
})
@Since("2.3-RELEASE")
public class ExprEmojiId extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprEmojiId.class, String.class, ExpressionType.PROPERTY,  "emoji named %string% with id %string%");
    }

    private Expression<String> emojiId;
    private Expression<String> emojiName;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        emojiName = (Expression<String>) expressions[0];
        emojiId = (Expression<String>) expressions[1];
        return true;
    }

    @Override
    protected String[] get(Event event) {
        String builder = "<:" + emojiName.getSingle(event) + ":" + emojiId.getSingle(event) + ">";
        return new String[]{builder};
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
        return "emoji with id " + emojiId.toString(event, b) + " and name " + emojiName.toString(event, b);
    }

}
