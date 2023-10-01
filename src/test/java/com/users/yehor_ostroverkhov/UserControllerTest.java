package com.users.yehor_ostroverkhov;

import com.users.yehor_ostroverkhov.controller.UserController;
import com.users.yehor_ostroverkhov.dto.ContactsDto;
import com.users.yehor_ostroverkhov.dto.DateRangeDto;
import com.users.yehor_ostroverkhov.dto.UserDto;
import com.users.yehor_ostroverkhov.model.UserEntity;
import com.users.yehor_ostroverkhov.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllByBirthBetween() {
        DateRangeDto dateRangeDto = new DateRangeDto(
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 2));
        UserEntity userEntity = new UserEntity();
        userEntity.setBirthDate(LocalDate.of(2000, 1, 1));
        List<UserEntity> userList = List.of(userEntity);
        
        Mockito.when(userService.findAllUsersInBirthDateRange(dateRangeDto)).thenReturn(userList);
        
        ResponseEntity<List<UserEntity>> response = userController.findAllByBirthBetween(dateRangeDto);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    void testRegisterUser() {
        UserDto userDto = new UserDto("test@email", "firstName", "lastName",
                LocalDate.of(2000, 1, 1), "Kyiv", "+380661234567");
        UserEntity savedUser = new UserEntity("test@email", "firstName", "lastName",
                LocalDate.of(2000, 1, 1), "Kyiv", "+380661234567");

        Mockito.when(userService.save(userDto)).thenReturn(savedUser);

        ResponseEntity<UserEntity> response = userController.registerUser(userDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedUser, response.getBody());
    }

    @Test
    void testChangeUserContacts() {
        Long userId = 1L;
        ContactsDto contactsDto = new ContactsDto("Kyiv", "+380661234567");
        UserEntity savedUser = new UserEntity();
        savedUser.setAddress("Kyiv");
        savedUser.setPhoneNumber("+380661234567");

        Mockito.when(userService.changeUserContacts(userId, contactsDto)).thenReturn(savedUser);

        ResponseEntity<UserEntity> response = userController.changeUserContacts(userId, contactsDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedUser, response.getBody());
    }

    @Test
    void testUpdateAllUserFields() {
        Long userId = 1L;
        UserDto userDto = new UserDto("test@email", "firstName", "lastName",
                LocalDate.of(2000, 1, 1), "Kyiv", "+380661234567");
        UserEntity savedUser = new UserEntity("test@email", "firstName", "lastName",
                LocalDate.of(2000, 1, 1), "Kyiv", "+380661234567");

        Mockito.when(userService.save(userId, userDto)).thenReturn(savedUser);

        ResponseEntity<UserEntity> response = userController.updateAllUserFields(userId, userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedUser, response.getBody());
    }

    @Test
    void testDeleteUser() {
        ResponseEntity<UserEntity> response = userController.deleteUser(1L);

        Mockito.verify(userService).deleteUserById(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}