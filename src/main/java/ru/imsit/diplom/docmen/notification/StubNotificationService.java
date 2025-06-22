package ru.imsit.diplom.docmen.notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StubNotificationService implements INotificationService {

    @Override
    public void notify(String subject, String text) {
        log.info("Отправка оповещения {} по адресу {}", text, subject);
    }
}
