package com.api.furshet.service;

import com.api.furshet.domain.entity.CallbackRequest;
import com.api.furshet.dto.CallbackRequestDto;
import com.api.furshet.dto.CategoryDTO;
import com.api.furshet.repository.CallbackRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CallbackRequestService {
    private final CallbackRequestRepository callbackRequestRepository;

    public CallbackRequest create(CallbackRequestDto dto) {

        CallbackRequest callbackRequest = CallbackRequest.builder()
                .FIO(dto.getFIO())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .amountPeople(dto.getAmountPeople())
                .data(dto.getData())
                .typeCallbackRequest(dto.getTypeCallbackRequest())
                .build();

        return callbackRequestRepository.save(callbackRequest);
    }

    public List<CallbackRequest> findAll() {
        return callbackRequestRepository.findAll();
    }

    public CallbackRequest findById(Long id) {
        return callbackRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("CallbackRequest not found " + id));
    }

    public CallbackRequest update(CallbackRequestDto dto) {
        if (dto.getId() != null) {
            CallbackRequest callbackRequest = callbackRequestRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("CallbackRequest not found " + dto.getId()));

            callbackRequest.setFIO(dto.getFIO());
            callbackRequest.setEmail(dto.getEmail());
            callbackRequest.setPhone(dto.getPhone());
            callbackRequest.setAmountPeople(dto.getAmountPeople());
            callbackRequest.setData(dto.getData());
            callbackRequest.setTypeCallbackRequest(dto.getTypeCallbackRequest());
            return callbackRequestRepository.save(callbackRequest);
        } else {
            return create(dto);
        }
    }

    public void delete(Long id) {
        callbackRequestRepository.deleteById(id);
    }
}
