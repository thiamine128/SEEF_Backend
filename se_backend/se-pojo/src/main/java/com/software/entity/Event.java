package com.software.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Long subscriber;
    private String id;
    private String type;
    private Long subject;
    private Long object;
    private Long where;

    public static Event subscribe(Long subscriber, Long user) {
        return new Event(user, UUID.randomUUID().toString(), "subscribe", subscriber, user, null);
    }

    public static Event like(Long user, Long blog, Long blogPoster) {
        return new Event(blogPoster, UUID.randomUUID().toString(), "thumb", user, blog, null);
    }

    public static Event favour(Long user, Long blog, Long blogPoster) {
        return new Event(blogPoster, UUID.randomUUID().toString(), "favour", user, blog, null);
    }

    public static Event comment(Long user, Long blog, Long blogPoster, Long comment) {
        return new Event(blogPoster, UUID.randomUUID().toString(), "comment", user, comment, blog);
    }

    public static Event replyComment(Long user, Long comment, Long replyTo, Long reply) {
        return new Event(replyTo, UUID.randomUUID().toString(), "replyComment", user, reply, comment);
    }

    public static Event reply(Long user, Long comment, Long replyTo, Long reply) {
        return new Event(replyTo, UUID.randomUUID().toString(), "reply", user, reply, comment);
    }
}
