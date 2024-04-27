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

@Name("User by ID")
@Description("Get a pingable user.")
@Examples({
        "command pinguser:\n" +
                "\ttrigger:\n" +
                "\t\tset {_user} to user with id \"123456789\"\n" +
                "\t\tsend \"Hello %{_user}% how are you?\" as webhook to \"https://discord.com/api/webhooks/?????/??????\""
})
@Since("2.3-RELEASE")
public class ExprUserId extends SimpleExpression<String>{

    static {
        Skript.registerExpression(ExprUserId.class, String.class, ExpressionType.PROPERTY,  "user with id %string%");
    }

    private Expression<String> userId;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        userId = (Expression<String>) expressions[0];
        return true;
    }

    @Override
    protected String[] get(Event event) {
        return new String[]{"<@" + userId.getSingle(event) + ">"};
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
        return "user with id " + userId.toString(event, b);
    }
}
