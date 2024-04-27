package me.fedox.skcord.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Role by ID")
@Description("Get a pingable role.")
@Examples({
        "command pingrole:\n" +
                "\ttrigger:\n" +
                "\t\tset {_role} to role with id \"123456789\"\n" +
                "\t\tsend \"{_role}\" as webhook to \"https://discord.com/api/webhooks/?????/??????\""
})
@Since("2.2")
public class ExprRoleId extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprRoleId.class, String.class, ExpressionType.PROPERTY,  "role with id %string%");
    }

    private Expression<String> roleId;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        roleId = (Expression<String>) expressions[0];
        return true;
    }

    @Override
    protected String[] get(Event event) {
        return new String[]{"<@&" + roleId.getSingle(event) + ">"};
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
        return "role with id " + roleId.toString(event, b);
    }
}
