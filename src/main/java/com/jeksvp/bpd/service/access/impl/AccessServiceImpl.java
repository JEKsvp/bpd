package com.jeksvp.bpd.service.access.impl;

import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.kafka.producer.AccessRequestProducer;
import com.jeksvp.bpd.service.access.AccessMessageProcessor;
import com.jeksvp.bpd.service.access.AccessRequestValidator;
import com.jeksvp.bpd.service.access.AccessService;
import com.jeksvp.bpd.web.dto.creator.AccessRequestMsgCreator;
import com.jeksvp.bpd.web.dto.request.access.AccessRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessServiceImpl implements AccessService {

    private final AccessRequestValidator accessRequestValidator;
    private final AccessRequestProducer accessRequestProducer;
    private final AccessRequestMsgCreator accessRequestMsgCreator;
    private final List<AccessMessageProcessor> accessMessageProcessors;

    public AccessServiceImpl(AccessRequestValidator accessRequestValidator, AccessRequestProducer accessRequestProducer,
                             AccessRequestMsgCreator accessRequestMsgCreator,
                             List<AccessMessageProcessor> accessMessageProcessors) {
        this.accessRequestValidator = accessRequestValidator;
        this.accessRequestProducer = accessRequestProducer;
        this.accessRequestMsgCreator = accessRequestMsgCreator;
        this.accessMessageProcessors = accessMessageProcessors;
    }

    @Override
//    todo do in transaction
    public void sendAccessRequest(AccessRequest accessRequest) {
        accessRequestValidator.validate(accessRequest);
        AccessRequestMsg accessRequestMsg = accessRequestMsgCreator.create(accessRequest);
        accessRequestProducer.sendAccessRequestMessage(accessRequestMsg);
    }

    @Override
//    todo do in transaction
    public void updateAccessStatus(AccessRequestMsg accessRequestMsg) {
        accessMessageProcessors.forEach(processor -> processor.process(accessRequestMsg));
    }
}
