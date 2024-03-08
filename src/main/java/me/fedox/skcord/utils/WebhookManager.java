package me.fedox.skcord.utils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;


import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class WebhookManager {

    public static void sendMessage(String url, String message) {

        if (url.contains("?thread_id=")) {
            String[] split = url.split("\\?thread_id=");
            String threadId = split[1];

            WebhookClient client = new WebhookClientBuilder(url).setThreadId(Long.parseLong(threadId)).build();
            return;
        }

        WebhookClient client = new WebhookClientBuilder(url).build();

        client.send(message);
    }

    public static void sendEmbed(String webhook, String title, String titleUrl, String description, String color, String thumbnailUrl, String imageUrl, String footer, String footerUrl, String author, String authorUrl, String authorIconUrl, Boolean timestamp) {
        WebhookEmbedBuilder builder = new WebhookEmbedBuilder();
        if (title != null) {
            if (titleUrl != null)
                builder.setTitle(new WebhookEmbed.EmbedTitle(title, titleUrl));
            else
                builder.setTitle(new WebhookEmbed.EmbedTitle(title, ""));
        }
        if (description != null)
            builder.setDescription(description);
        if (color != null)
            builder.setColor(ColorManager.convertHexToDecimal(color));
        if (thumbnailUrl != null)
            builder.setThumbnailUrl(thumbnailUrl);
        if (imageUrl != null)
            builder.setImageUrl(imageUrl);
        if (footer != null) {
            if (footerUrl != null)
                builder.setFooter(new WebhookEmbed.EmbedFooter(footer, footerUrl));
            else
                builder.setFooter(new WebhookEmbed.EmbedFooter(footer, null));
        }
        if (author != null) {
            if (authorUrl != null) {
                if (authorIconUrl != null)
                    builder.setAuthor(new WebhookEmbed.EmbedAuthor(author, authorIconUrl, authorUrl));
                else
                    builder.setAuthor(new WebhookEmbed.EmbedAuthor(author, null, authorUrl));
            } else {
                if (authorIconUrl != null)
                    builder.setAuthor(new WebhookEmbed.EmbedAuthor(author, null, authorIconUrl));
                else
                    builder.setAuthor(new WebhookEmbed.EmbedAuthor(author, null, null));
            }
        }
        if (timestamp != null) {
            LocalDateTime localDateTime = LocalDateTime.now();

            ZoneId systemZoneId = ZoneId.systemDefault();

            ZonedDateTime zonedDateTime = localDateTime.atZone(systemZoneId);

            OffsetDateTime offsetDateTime = zonedDateTime.toOffsetDateTime();

            builder.setTimestamp(offsetDateTime);
        }

        if (webhook.contains("?thread_id=")) {
            String[] split = webhook.split("\\?thread_id=");
            String threadId = split[1];

            WebhookClient client = new WebhookClientBuilder(webhook).setThreadId(Long.parseLong(threadId)).build();
            client.send(builder.build());
            return;
        }

        WebhookClient client = new WebhookClientBuilder(webhook).build();
        client.send(builder.build());
    }

}
