package com.avocado.community.api.service;

import com.avocado.MemberEvent;
import com.avocado.community.common.utils.UUIDUtil;
import com.avocado.community.db.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final UUIDUtil uuidUtil;

    @Transactional
    public void consumerEventListener(String consumerId, MemberEvent memberEvent) {
        UUID id = uuidUtil.joinByHyphen(consumerId);
        switch(memberEvent.getEvent()) {
            case SIGN_UP:
                consumerRepository.save(id, memberEvent.getSignupInfo());
                break;
            case UPDATE:
                consumerRepository.updateInfo(id, memberEvent.getUpdateInfo());
                break;
            case SIGN_OUT:
                consumerRepository.delete(id);
                break;
        }

    }
}
