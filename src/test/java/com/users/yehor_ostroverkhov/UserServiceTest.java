package com.users.yehor_ostroverkhov;

import com.users.yehor_ostroverkhov.dto.ContactsDto;
import com.users.yehor_ostroverkhov.dto.DateRangeDto;
import com.users.yehor_ostroverkhov.dto.UserDto;
import com.users.yehor_ostroverkhov.model.UserEntity;
import com.users.yehor_ostroverkhov.repository.UserRepository;
import com.users.yehor_ostroverkhov.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        UserDto userDto = new UserDto("test@email", "firstName", "lastName",
                LocalDate.of(2000, 1, 1), "Kyiv", "+380661234567");
        UserEntity savedUser = new UserEntity("test@email", "firstName", "lastName",
                LocalDate.of(2000, 1, 1), "Kyiv", "+380661234567");

        when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(savedUser);

        UserEntity result = userService.save(userDto);

        assertNotNull(result);
        assertEquals(savedUser, result);
        verify(userRepository).save(Mockito.any(UserEntity.class));
    }

    @Test
    void testChangeUserContacts() {
        Long userId = 1L;
        ContactsDto contactsDto = new ContactsDto("Kyiv", "+380661234567");
        UserEntity foundUser = new UserEntity("test@email", "firstName", "lastName",
                LocalDate.of(2000, 1, 1), "Kyiv", "+380661234567");

        when(userRepository.getReferenceById(userId)).thenReturn(foundUser);
        when(userRepository.save(foundUser)).thenReturn(foundUser);

        UserEntity result = userService.changeUserContacts(userId, contactsDto);

        assertNotNull(result);
        assertEquals(contactsDto.address(), result.getAddress());
        assertEquals(contactsDto.phoneNumber(), result.getPhoneNumber());
        verify(userRepository).save(foundUser);
    }

    @Test
    void testSaveWithId() {
        Long userId = 1L;
        UserDto userDto = new UserDto("test@email", "firstName", "lastName",
                LocalDate.of(2000, 1, 1), "Kyiv", "+380661234567");
        UserEntity foundUser = new UserEntity("test@email", "firstName", "lastName",
                LocalDate.of(2000, 1, 1), "Kyiv", "+380661234567");

        when(userRepository.getReferenceById(userId)).thenReturn(foundUser);
        when(userRepository.save(foundUser)).thenReturn(foundUser);

        UserEntity result = userService.save(userId, userDto);

        assertNotNull(result);
        assertEquals(userDto.email(), result.getEmail());
        assertEquals(userDto.firstName(), result.getFirstName());
        assertEquals(userDto.lastName(), result.getLastName());
        assertEquals(userDto.birthDate(), result.getBirthDate());
        assertEquals(userDto.address(), result.getAddress());
        assertEquals(userDto.phoneNumber(), result.getPhoneNumber());
        verify(userRepository).save(foundUser);
    }

    @Test
    void testDeleteUserById() {
        Long userId = 1L;
        userService.deleteUserById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void testFindAllUsersInBirthDateRange() {
        LocalDate fromDate = LocalDate.now().minusYears(30);
        LocalDate toDate = LocalDate.now().minusYears(20);
        DateRangeDto dateRangeDto = new DateRangeDto(fromDate, toDate);

        List<UserEntity> usersInRange = new ArrayList<>(List.of(new UserEntity(), new UserEntity()));
        List<UserEntity> usersFromDate = new ArrayList<>(List.of(new UserEntity()));
        List<UserEntity> usersToDate = new ArrayList<>(List.of(new UserEntity()));

        when(userRepository.findAllByBirthDateAfterAndBirthDateBefore(fromDate, toDate)).thenReturn(usersInRange);
        when(userRepository.findAllByBirthDate(fromDate)).thenReturn(usersFromDate);
        when(userRepository.findAllByBirthDate(toDate)).thenReturn(usersToDate);

        List<UserEntity> result = userService.findAllUsersInBirthDateRange(dateRangeDto);

        assertEquals(4, result.size());
        verify(userRepository).findAllByBirthDateAfterAndBirthDateBefore(fromDate, toDate);
        verify(userRepository).findAllByBirthDate(fromDate);
        verify(userRepository).findAllByBirthDate(toDate);
    }
}