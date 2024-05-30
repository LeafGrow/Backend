package de.leafgrow.leafgrow_project.service;

import de.leafgrow.leafgrow_project.domain.entity.ConfirmationCode;
import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.repository.ConfirmationCodeRepository;
import de.leafgrow.leafgrow_project.repository.UserRepository;
import de.leafgrow.leafgrow_project.service.interfaces.ConfirmationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private ConfirmationCodeRepository confirmationCodeRepository;
    private UserRepository userRepository;

    public ConfirmationServiceImpl(ConfirmationCodeRepository confirmationCodeRepository, UserRepository userRepository) {
        this.confirmationCodeRepository = confirmationCodeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User confirmUser(String code) {
        ConfirmationCode confirmationCode = confirmationCodeRepository.findByCode(code);
        if (confirmationCode == null || confirmationCode.getExpired().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Недействительный код подтверждения или истек срок его действия.");
        }

        User user = confirmationCode.getUser();

        if (user == null) {
            throw new IllegalArgumentException("Ни один пользователь не связан с этим кодом подтверждения.");
        }

        user.setActive(true);
        userRepository.save(user);
        confirmationCodeRepository.delete(confirmationCode);

        return user;
    }


    @Override
    public String generateConfirmationCode(User user) {
        LocalDateTime expired = LocalDateTime.now().plusMinutes(10);
        String code = UUID.randomUUID().toString();
        ConfirmationCode entity = new ConfirmationCode(code, user, expired);
        confirmationCodeRepository.save(entity);
        return code;
    }

}
