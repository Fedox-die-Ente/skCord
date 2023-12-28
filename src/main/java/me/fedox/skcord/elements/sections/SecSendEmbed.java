package me.fedox.skcord.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;

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
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import java.util.List;


@Name("Webhook - Create a new Embed")
@Description({"This section allows you to create new embeds for your discord webhook.",
        "\n`webhook` = The url from the webhook where you want to send the embed.",
        "\n`title` = The title of the embed.",
        "\n`titleUrl` = The url that the title should link to.",

        "\n`description` = The description of the embed.",
        "\n`color` = The color of the embed. (Hex code like #FFFFFF)",

        "\n`thumbnailUrl` = The url of the thumbnail.",
        "\n`imageUrl` = The url of the image.",

        "\n`footer` = The footer of the embed.",
        "\n`footerIconUrl` = The url of the footer icon.",

        "\n`author` = The author of the embed.",
        "\n`authorUrl` = The url that the author should link to.",
        "\n`authorIconUrl` = The url of the author icon.",

        "\n`timestamp` = If the embed should have a timestamp or not. (true/false)"
})
@Examples({"on load:",
        "\tcreate a new embed:",
        "\t\twebhook: \"https://discord.com/api/webhooks/?????/??????\"",
        "\t\ttitle: \"Title\"",
        "\t\ttitleUrl: \"https://google.com\"",
        "\t\tdescription: \"Description\"",
        "\t\tcolor: \"##3437eb\"",
        "\t\tthumbnailUrl: \"https://skripthub.net/static/img/ogLogo.png\"",
        "\t\timageUrl: \"https://skripthub.net/static/img/ogLogo.png\"",
        "\t\tfooter: \"Hello im a footer\"",
        "\t\tfooterIconUrl: \"https://skripthub.net/static/img/ogLogo.png\"",
        "\t\tauthor: \"Im a author\"",
        "\t\tauthorUrl: \"https://google.com\"",
        "\t\tauthorIconUrl: \"https://skripthub.net/static/img/ogLogo.png\"",
        "\t\ttimestamp: true"})
@Since("2.0-RELEASE")

public class SecSendEmbed extends EffectSection {

    private static final EntryValidator.EntryValidatorBuilder ENTRY_VALIDATOR = EntryValidator.builder();

    private static void addEntryData(ExpressionEntryData<?> data) {
        ENTRY_VALIDATOR.addEntryData(data);
    }

    static {
        Skript.registerSection(SecSendEmbed.class, "create [a[n]] [new] embed");
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("webhook", null, false, String.class));

        addEntryData(new ExpressionEntryData<>("title", null, true, String.class));
        addEntryData(new ExpressionEntryData<>("titleUrl", null, true, String.class));

        addEntryData(new ExpressionEntryData<>("description", null, true, String.class));
        addEntryData(new ExpressionEntryData<>("color", null, true, String.class));

        addEntryData(new ExpressionEntryData<>("thumbnailUrl", null, true, String.class));
        addEntryData(new ExpressionEntryData<>("imageUrl", null, true, String.class));

        addEntryData(new ExpressionEntryData<>("footer", null, true, String.class));
        addEntryData(new ExpressionEntryData<>("footerIconUrl", null, true, String.class));

        addEntryData(new ExpressionEntryData<>("author", null, true, String.class));
        addEntryData(new ExpressionEntryData<>("authorUrl", null, true, String.class));
        addEntryData(new ExpressionEntryData<>("authorIconUrl", null, true, String.class));

        addEntryData(new ExpressionEntryData<>("timestamp", null, true, Boolean.class));
    }


    private Expression<String> webhook;

    private Expression<String> title;
    private Expression<String> titleUrl;

    private Expression<String> description;
    private Expression<String> color;
    private Expression<String> thumbnailUrl;
    private Expression<String> imageUrl;
    private Expression<String> footer;
    private Expression<String> footerIconUrl;
    private Expression<String> author;
    private Expression<String> authorUrl;
    private Expression<String> authorIconUrl;
    private Expression<Boolean> timestamp;

    @SuppressWarnings({"NullableProblems", "unchecked"})
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult, SectionNode sectionNode, List<TriggerItem> list) {
        if(getParser().isCurrentSection(SecSendEmbed.class)) {
            Skript.error("The send embed creation section is not meant to be put inside of another embed creation section..");
            return false;
        }
        EntryContainer container = ENTRY_VALIDATOR.build().validate(sectionNode);
        if (container == null) return false;

        this.webhook = (Expression<String>) container.getOptional("webhook", false);
        if (this.webhook == null) return false;


        this.title = (Expression<String>) container.getOptional("title", false);
        if (this.title != null) this.titleUrl = (Expression<String>) container.getOptional("titleUrl", false);

        this.description = (Expression<String>) container.getOptional("description", false);
        this.color = (Expression<String>) container.getOptional("color", false);
        this.thumbnailUrl = (Expression<String>) container.getOptional("thumbnailUrl", false);
        this.imageUrl = (Expression<String>) container.getOptional("imageUrl", false);
        this.footer = (Expression<String>) container.getOptional("footer", false);
        this.footerIconUrl = (Expression<String>) container.getOptional("footerIconUrl", false);
        this.author = (Expression<String>) container.getOptional("author", false);
        if(this.author != null) this.authorUrl = (Expression<String>) container.getOptional("authorUrl", false);
        if(this.author != null) this.authorIconUrl = (Expression<String>) container.getOptional("authorIconUrl", false);
        this.timestamp = (Expression<Boolean>) container.getOptional("timestamp", false);

        return true;
    }

    @Override
    protected @Nullable TriggerItem walk(Event event) {
        execute(event);
        return super.walk(event, false);
    }

    private void execute(Event event) {
        String webhook = this.webhook.getSingle(event);

        String title;
        String titleUrl;
        String description;
        String color;
        String thumbnailUrl;
        String imageUrl;
        String footer;
        String footerIconUrl;
        String author;
        String authorUrl;
        String authorIconUrl;
        Boolean timestamp;

        if (this.title != null)
            title = this.title.getSingle(event);
        else
            title = null;

        if (this.titleUrl != null)
            titleUrl = this.titleUrl.getSingle(event);
        else
            titleUrl = null;

        if (this.description != null)
            description = this.description.getSingle(event);
        else
            description = null;

        if (this.color != null)
            color = this.color.getSingle(event);
        else
            color = null;

        if (this.thumbnailUrl != null)
            thumbnailUrl = this.thumbnailUrl.getSingle(event);
        else
            thumbnailUrl = null;

        if (this.imageUrl != null)
            imageUrl = this.imageUrl.getSingle(event);
        else
            imageUrl = null;

        if (this.footer != null)
            footer = this.footer.getSingle(event);
        else
            footer = null;

        if (this.footerIconUrl != null)
            footerIconUrl = this.footerIconUrl.getSingle(event);
        else
            footerIconUrl = null;

        if (this.author != null)
            author = this.author.getSingle(event);
        else
            author = null;

        if (this.authorUrl != null)
            authorUrl = this.authorUrl.getSingle(event);
        else
            authorUrl = null;

        if (this.authorIconUrl != null)
            authorIconUrl = this.authorIconUrl.getSingle(event);
        else
            authorIconUrl = null;

        if (this.timestamp != null)
            timestamp = this.timestamp.getSingle(event);
        else
            timestamp = null;

        WebhookManager.sendEmbed(webhook, title, titleUrl, description, color, thumbnailUrl, imageUrl, footer, footerIconUrl, author, authorUrl, authorIconUrl, timestamp);
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean b) {
        return "create a new embed";
    }
}
