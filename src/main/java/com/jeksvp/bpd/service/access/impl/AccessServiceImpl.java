package com.jeksvp.bpd.service.access.impl;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.kafka.producer.AccessRequestProducer;
import com.jeksvp.bpd.repository.UserRepository;
import com.jeksvp.bpd.service.access.AccessRequestValidator;
import com.jeksvp.bpd.service.access.AccessService;
import com.jeksvp.bpd.web.dto.creator.AccessRequestMsgCreator;
import com.jeksvp.bpd.web.dto.request.access.AccessRequest;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl implements AccessService {

    private final AccessRequestValidator accessRequestValidator;
    private final AccessRequestProducer accessRequestProducer;
    private final AccessRequestMsgCreator accessRequestMsgCreator;
    private final AccessMessageProcessorImpl accessMessageProcessorImpl;
    private final UserRepository userRepository;

    public AccessServiceImpl(AccessRequestValidator accessRequestValidator,
                             AccessRequestProducer accessRequestProducer,
                             AccessRequestMsgCreator accessRequestMsgCreator,
                             AccessMessageProcessorImpl accessMessageProcessorImpl,
                             UserRepository userRepository) {
        this.accessRequestValidator = accessRequestValidator;
        this.accessRequestProducer = accessRequestProducer;
        this.accessRequestMsgCreator = accessRequestMsgCreator;
        this.accessMessageProcessorImpl = accessMessageProcessorImpl;
        this.userRepository = userRepository;
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
        User fromUser = userRepository.findById(accessRequestMsg.getFromUsername())
                .orElseThrow(() -> new ApiException(ApiErrorContainer.USER_NOT_FOUND));

        User toUser = userRepository.findById(accessRequestMsg.getToUsername())
                .orElseThrow(() -> new ApiException(ApiErrorContainer.USER_NOT_FOUND));

        accessMessageProcessorImpl.process(accessRequestMsg, fromUser.getUsername(), toUser.getUsername());
        accessMessageProcessorImpl.process(accessRequestMsg, toUser.getUsername(), fromUser.getUsername());
    }
}
