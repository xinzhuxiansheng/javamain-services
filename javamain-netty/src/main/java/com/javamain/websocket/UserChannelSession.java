package com.javamain.websocket;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会话管理
 */
public class UserChannelSession {

    // 用于多端同时接受消息，允许同一个账号在多个设备同时在线，比如iPad、iPhone、Mac等设备同时收到消息
    // key: userId, value: 多个用户的channel
    private static Map<String, List<Channel>> multiSession = new HashMap<>();

    // 用于记录用户id和客户端channel长id的关联关系
    private static Map<String, String> userChannelIdRelation = new HashMap<>();

    public static void putUserChannelIdRelation(String channelId, String userId) {
        userChannelIdRelation.put(channelId, userId);
    }
    public static String getUserIdByChannelId(String channelId) {
        return userChannelIdRelation.get(channelId);
    }

    public static void putMultiChannels(String userId, Channel channel) {

        List<Channel> channels = getMultiChannels(userId);
        if (channels == null || channels.size() == 0) {
            channels = new ArrayList<>();
        }
        channels.add(channel);

        multiSession.put(userId, channels);
    }
    public static List<Channel> getMultiChannels(String userId) {
        return multiSession.get(userId);
    }

    public static void removeUselessChannels(String userId, String channelId) {

        List<Channel> channels = getMultiChannels(userId);
        if (channels == null || channels.size() == 0) {
            return;
        }

        for (int i = 0 ; i < channels.size() ; i ++) {
            Channel tempChannel = channels.get(i);
            if (tempChannel.id().asLongText().equals(channelId)) {
                channels.remove(i);
            }
        }

        multiSession.put(userId, channels);
    }

    public static List<Channel> getMyOtherChannels(String userId, String channelId) {
        List<Channel> channels = getMultiChannels(userId);
        if (channels == null || channels.size() == 0) {
            return null;
        }

        List<Channel> myOtherChannels = new ArrayList<>();
        for (int i = 0 ; i < channels.size() ; i ++) {
            Channel tempChannel = channels.get(i);
            if (!tempChannel.id().asLongText().equals(channelId)) {
                myOtherChannels.add(tempChannel);
            }
        }
        return myOtherChannels;
    }

    public static void outputMulti() {

        System.out.println("++++++++++++++++++");

        for (Map.Entry<String, List<Channel>> entry : multiSession.entrySet()) {
            System.out.println("----------");

            System.out.println("UserId: " + entry.getKey());
            List<Channel> temp = entry.getValue();
            for (Channel c : temp) {
                System.out.println("\t\t ChannelId: " + c.id().asLongText());
            }

            System.out.println("----------");
        }


        System.out.println("++++++++++++++++++");

    }

}

