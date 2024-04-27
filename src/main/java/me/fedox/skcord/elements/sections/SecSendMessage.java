package me.fedox.skcord.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.EffectSection;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import me.fedox.skcord.utils.WebhookManager;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.lang.entry.EntryContainer;
import org.skriptlang.skript.lang.entry.EntryValidator;
import org.skriptlang.skript.lang.entry.util.ExpressionEntryData;

import java.util.List;

@Name("Webhook - Send message in section")
@Description({"This section allows you to send a message for your discord webhook.",
        "\n`webhook` = The url from the webhook where you want to send the embed.",
        "\n`message` = The message.",
})
@Examples({"on load:",
        "\tsend a message:",
        "\t\twebhook: \"https://discord.com/api/webhooks/?????/??????\"",
        "\t\tmessage: \"The server is loaded\""})
@Since("2.3-RELEASE")
public class SecSendMessage extends EffectSection {

    private static final EntryValidator.EntryValidatorBuilder ENTRY_VALIDATOR = EntryValidator.builder();

    private static void addEntryData(ExpressionEntryData<?> data) {
        ENTRY_VALIDATOR.addEntryData(data);
    }

    static {
        Skript.registerSection(SecSendMessage.class, "send [a[n]] [new] message");
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("webhook", null, false, String.class));
        addEntryData(new ExpressionEntryData<>("message", null, false, String.class));
    }

    private Expression<String> webhook;
    private Expression<String> message;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult, SectionNode sectionNode, List<TriggerItem> list) {
        if (getParser().isCurrentSection(SecSendMessage.class)) {
            Skript.error("The send message creation section is not meant to be put inside of another message creation section..");
            return false;
        }
        EntryContainer container = ENTRY_VALIDATOR.build().validate(sectionNode);
        if (container == null) return false;

        this.webhook = (Expression<String>) container.getOptional("webhook", false);
        if (this.webhook == null) return false;


        this.message = (Expression<String>) container.getOptional("message", false);
        if (this.message == null) return false;

        return true;
    }

    @Override
    protected @Nullable TriggerItem walk(Event event) {
        execute(event);
        return super.walk(event, false);
    }


    private void execute(Event event) {
        String webhook = this.webhook.getSingle(event);

        String message = this.message.getSingle(event);

        assert webhook != null;
        WebhookManager.sendMessage(webhook, message);
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean b) {
        return "create a new message";
    }
}
