package com.api.furshet.service;

import com.api.furshet.domain.entity.Feedback;
import com.api.furshet.domain.entity.TelegramUsers;
import com.api.furshet.domain.enums.TypeFeedback;
import com.api.furshet.dto.FeedbackDTO;
import com.api.furshet.mail.FeedbackMail;
import com.api.furshet.mail.OrderMail;
import com.api.furshet.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    private final TelegramUsersService telegramUsersService;

    @Autowired
    private FeedbackMail feedbackMail;


    public Feedback create(FeedbackDTO feedbackDTO) {
        Feedback feedback = Feedback.builder()
                .id(feedbackDTO.getId())
                .FI(feedbackDTO.getFI())
                .rating(feedbackDTO.getRating())
                .date(feedbackDTO.getDate())
                .comment(feedbackDTO.getComment())
                .active(feedbackDTO.getActive() != null ? feedbackDTO.getActive() : true)
                .typeFeedback(feedbackDTO.getTypeFeedback() != null ? feedbackDTO.getTypeFeedback() : TypeFeedback.IN_PROGRESS)
                .build();

        Feedback savedFeedback = feedbackRepository.save(feedback);

        List<TelegramUsers> users = telegramUsersService.findAll();

        List<String> emails = users.stream()
                .map(TelegramUsers::getName)
                .toList();

        feedbackMail.sendOrderEmail(emails, feedback);

        return savedFeedback;
    }

    public Feedback update(FeedbackDTO feedbackDTO) {
        if (feedbackDTO.getId() != null) {
            Feedback feedback = feedbackRepository.findById(feedbackDTO.getId()).orElseThrow(() -> new RuntimeException("Feedback id not found"));

            feedback.setFI(feedbackDTO.getFI());
            feedback.setRating(feedbackDTO.getRating());
            feedback.setDate(feedbackDTO.getDate());
            feedback.setComment(feedbackDTO.getComment());

            feedback.setActive(
                    feedbackDTO.getActive() != null ? feedbackDTO.getActive() : feedback.getActive()
            );

            feedback.setTypeFeedback(
                    feedbackDTO.getTypeFeedback() != null
                            ? feedbackDTO.getTypeFeedback()
                            : feedback.getTypeFeedback()
            );

            return feedbackRepository.save(feedback);
        } else {
            return create(feedbackDTO);
        }
    }

    public void updateFeedbackStatus(Long id, TypeFeedback typeFeedback) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new RuntimeException("Feedback not found"));

        feedback.setTypeFeedback(typeFeedback);
        feedbackRepository.save(feedback);
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAllWithInProgressFirst();
    }

    public List<Feedback> findAllWithActiveTrue() {
        return feedbackRepository.findAllByActiveTrue();
    }

    public List<Feedback> findAllByTypeFeedbackAndActiveTrue() {
        return feedbackRepository.findAllByTypeFeedbackAndActiveTrue(TypeFeedback.ACCEPTED);
    }

    public Feedback findById(Long id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new RuntimeException("Feedback id not found"));
    }

    public void delete(Long id) {
        feedbackRepository.deleteById(id);
    }
}
