package com.company.invitation.web;

import com.company.invitation.InvitationBusinessProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/invitation")
public class InvitationController {
    private final InvitationBusinessProcess businessProcess;

    @Autowired
    public InvitationController(InvitationBusinessProcess businessProcess) {
        this.businessProcess = businessProcess;
    }

    @RequestMapping(path = "/process", method = GET)
    @ResponseStatus(OK)
    public Map processInvitation() {
        ResponseEntity<Map> responseEntity = businessProcess.processInvitation();
        return responseEntity.getBody();
    }
}