package com.reactive.sec11Sinks.usecase;

import com.reactive.utils.Utils;

public class SlackApp {
    public static void main(String[] args) {

        SlackRoom slackRoom = new SlackRoom("reactor");

        SlackMember sam = new SlackMember("sam");
        SlackMember jake = new SlackMember("jake");
        SlackMember mike = new SlackMember("mike");

        slackRoom.joinRoom(sam);
        slackRoom.joinRoom(jake);

        sam.says("Hi all..");
        Utils.sleepSeconds(4);

        jake.says("Hey!");
        sam.says("I simply wanted to say hi..");
        Utils.sleepSeconds(4);

        slackRoom.joinRoom(mike);
        mike.says("Hey guys..glad to be here...");
    }
}
