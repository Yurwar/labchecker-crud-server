package com.usb.labchecker.controller;

import com.usb.labchecker.model.dto.LabResultByStudentIdDto;
import com.usb.labchecker.model.dto.LabResultTestingServerDto;
import com.usb.labchecker.model.entity.LabResult;
import com.usb.labchecker.model.service.BotNotificationService;
import com.usb.labchecker.model.service.LabResultService;
import com.usb.labchecker.model.service.SpreadSheetService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/labresults")
public class LabResultController {
    private final LabResultService labResultService;
    private final BotNotificationService botNotificationService;
    private final SpreadSheetService spreadSheetService;

    public LabResultController(LabResultService labResultService,
                               BotNotificationService botNotificationService,
                               SpreadSheetService spreadSheetService) {
        this.labResultService = labResultService;
        this.botNotificationService = botNotificationService;
        this.spreadSheetService = spreadSheetService;
    }

    @PostMapping
    public LabResult addLabResult(@RequestBody LabResultTestingServerDto labResultDto){
        botNotificationService.notifyAboutFinishOfBuild(labResultDto);
        LabResult labResult = labResultService.addLabResult(labResultDto);
        spreadSheetService.postLabResult(labResult);
        return labResult;
    }

//    @PutMapping
//    public LabResult updateLabResult(@RequestBody LabResultTestingServerDto labResultDto){
//        spreadSheetService.putLabResult(labResult);
//    }

    @GetMapping
    public Iterable<LabResultByStudentIdDto> getLabResultsByStudentId(@RequestParam(name = "studentId") Integer studentId) {
        return labResultService.findLabResultsForStudent(studentId);
    }

    @GetMapping("/by_student_and_subject")
    public Set<LabResultByStudentIdDto> getLabResultsByStudentIdAndSubjectId(@RequestParam(name = "studentId") Integer studentId,
                                                                             @RequestParam(name = "subjectId") Integer subjectId){
        return labResultService.getLabResultsByStudentIdAndSubjectId(studentId, subjectId);
    }
}
