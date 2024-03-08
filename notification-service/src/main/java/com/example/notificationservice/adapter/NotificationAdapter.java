package com.example.notificationservice.adapter;

import com.example.notificationservice.dto.NotificationSendDto;

public interface NotificationAdapter {

    void send(NotificationSendDto notificationSendDto) throws Exception;
}
