package ge.itlab.loanservice.service;

import ge.itlab.loanservice.model.domain.UserInfo;
import ge.itlab.loanservice.model.dto.UserInfoDto;
import ge.itlab.loanservice.model.exception.PasswordMatchingException;
import ge.itlab.loanservice.model.exception.UsernameAlreadyExistsException;
import ge.itlab.loanservice.repo.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public void addUser(UserInfoDto userInfoDto) {
        if (!userInfoDto.getPassword().equals(userInfoDto.getPasswordAgain())) {
            throw new PasswordMatchingException("Password mismatch");
        }
        Optional<UserInfo> userFromDB = userInfoRepository.findById(userInfoDto.getUsername());
        if (userFromDB.isPresent()) {
            throw new UsernameAlreadyExistsException("Username: " + userFromDB.get().getUsername() + " already exists in DB");
        }
        UserInfo userInfo = objectMapper.userUnfoDtoToDomain(userInfoDto);
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
    }

    public UserInfo findUserById(String username) {
        return userInfoRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll();
    }

}
