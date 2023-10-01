package com.users.yehor_ostroverkhov.service;

import com.users.yehor_ostroverkhov.dto.ContactsDto;
import com.users.yehor_ostroverkhov.dto.DateRangeDto;
import com.users.yehor_ostroverkhov.dto.UserDto;
import com.users.yehor_ostroverkhov.model.UserEntity;
import com.users.yehor_ostroverkhov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Value("${minAge}")
    private int minAge;

    public UserEntity save(UserDto userDto) {
        UserEntity userEntity = new UserEntity();

        setValidatedDtoParametersInUser(userEntity, userDto);

        return userRepository.save(userEntity);
    }

    private void setValidatedDtoParametersInUser(UserEntity userEntity, UserDto userDto) {
        LocalDate userBirthDate = userDto.birthDate();
        LocalDate minAllowBirthDate = LocalDate.now().minusYears(minAge);

        if (userBirthDate.isAfter(minAllowBirthDate)) {
            throw new DateTimeException("Age must be at least " + minAge);
        }

        userEntity.setEmail(userDto.email());
        userEntity.setFirstName(userDto.firstName());
        userEntity.setLastName(userDto.lastName());
        userEntity.setBirthDate(userBirthDate);
        userEntity.setAddress(userDto.address());
        userEntity.setPhoneNumber(userDto.phoneNumber());
    }

    public UserEntity changeUserContacts(Long id, ContactsDto contactsDto) {
        UserEntity foundUser = userRepository.getReferenceById(id);

        foundUser.setAddress(contactsDto.address());
        foundUser.setPhoneNumber(contactsDto.phoneNumber());

        return userRepository.save(foundUser);
    }

    public UserEntity save(Long id, UserDto userDto) {
        UserEntity foundUser = userRepository.getReferenceById(id);
        
        setValidatedDtoParametersInUser(foundUser, userDto);

        return userRepository.save(foundUser);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserEntity> findAllUsersInBirthDateRange(DateRangeDto dateRangeDto) {
        List<UserEntity> allUsersInRange = userRepository.findAllByBirthDateAfterAndBirthDateBefore(
                dateRangeDto.fromDate(), dateRangeDto.toDate());
        allUsersInRange.addAll(userRepository.findAllByBirthDate(dateRangeDto.fromDate()));
        allUsersInRange.addAll(userRepository.findAllByBirthDate(dateRangeDto.toDate()));
        return allUsersInRange;
    }
}
