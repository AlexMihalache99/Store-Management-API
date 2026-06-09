package com.alexandru.store_management_api.controller;

import com.alexandru.store_management_api.entity.User;
import com.alexandru.store_management_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController controller;

    @Test
    void createUser_delegatesToService() {
        User u = new User("name","pw","r");
        when(userService.createUser(u)).thenReturn(u);

        User res = controller.createUser(u);
        assertThat(res).isSameAs(u);
        verify(userService).createUser(u);
    }

    @Test
    void getAllUsers_delegates() {
        when(userService.getAllUsers()).thenReturn(List.of());
        var res = controller.getAllUsers();
        assertThat(res).isEmpty();
        verify(userService).getAllUsers();
    }

    @Test
    void getUserById_delegates() {
        User u = new User("n","p","r");
        when(userService.getUserById(1L)).thenReturn(u);
        assertThat(controller.getUserById(1L)).isSameAs(u);
    }

    @Test
    void updateUser_delegates() {
        User u = new User("n","p","r");
        when(userService.updateUser(2L, u)).thenReturn(u);
        assertThat(controller.updateUser(2L, u)).isSameAs(u);
    }

    @Test
    void deleteUser_delegates() {
        doNothing().when(userService).deleteUser(3L);
        controller.deleteUser(3L);
        verify(userService).deleteUser(3L);
    }
}
