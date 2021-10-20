// https://github.com/FeistyKit/javaDiscordTest
package com.feisty.lfmp;

import java.util.Objects;
import java.util.ArrayList;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.Embed;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import java.io.FileNotFoundException;
import reactor.core.publisher.Mono;

public class CustomBot {
  public DiscordClient client;
  public Mono login;
  public Embed em;

  public CustomBot(String authFileName, int tokenLine) throws FileNotFoundException {

    String auth = (String) FileReader.readToArrayList(authFileName).toArray()[tokenLine];

    this.client = DiscordClient.create(auth);
    login = client.withGateway((GatewayDiscordClient gateway) -> {
        Mono<Void> printOnLogin =
            gateway
                .on(ReadyEvent.class, event -> Mono.fromRunnable(() -> {
                  final User self = event.getSelf();
                  System.out.printf("Logged in as %s#%s%n", self.getUsername(),
                                    self.getDiscriminator());
                }))
                .then();
        // MessageCreateEvent example
        Mono<Void> handlePingCommand =
            gateway
                .on(MessageCreateEvent.class,
                    event -> {
                      Message message = event.getMessage();
                      if (message.getContent().equalsIgnoreCase("!lfmpleaderboard")) {
                        return message.getChannel().flatMap(
                            channel -> {
                              String msg = "Leaderboard of players in the LFMP: \n";
                              ArrayList<String> l = toMaxLen(msg, (String[]) App.fmtScores().toArray()); //I *love* java
                              Mono r = null;
                              for (String i : l) {
                                // we know that this will go at least once because message cannot be empty.
                                r = channel.createMessage(i);
                              }
                              return Objects.requireNonNull(r);
                            });
                      }
                      return Mono.empty();
                    })
                .then();
        // combine them!
        return printOnLogin.and(handlePingCommand);
    });
  }

  private static ArrayList<String> toMaxLen(String msg, String[] source) {
      ArrayList<String> toReturn = new ArrayList<>();
      if (source.length >= 1) {
            if (msg.length() + source[0].length() < 2000) {
                toReturn.add(msg + source[0]);
            } else {
                toReturn.add(msg);
                toReturn.add(source[0]);
            }
            for (int i = 1; i < source.length; i++) {
                assert source[i].length() < 2000 : "String `" + source[i] + "` is too long! Maximum is 2000, it has a length of " + Integer.toString(source[i].length());
                toReturn.add(source[i]);
            }
      } else {
            toReturn.add(msg);
      }
      return toReturn;
  }

  public void run() { login.block(); }
}
