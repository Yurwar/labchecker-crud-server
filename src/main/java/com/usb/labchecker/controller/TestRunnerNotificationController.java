package com.usb.labchecker.controller;

import com.usb.labchecker.model.dto.TestRunnerBuildStartNotificationDto;
import com.usb.labchecker.model.service.BotNotificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class TestRunnerNotificationController {
    private BotNotificationService botNotificationService;

    public TestRunnerNotificationController(BotNotificationService botNotificationService) {
        this.botNotificationService = botNotificationService;
    }

    @PostMapping("/start")
    public void notifyAboutStartOfBuild(@RequestBody TestRunnerBuildStartNotificationDto testRunnerBuildStartNotificationDto) {
        botNotificationService.notifyAboutStartOfBuild(testRunnerBuildStartNotificationDto);
    }
}
