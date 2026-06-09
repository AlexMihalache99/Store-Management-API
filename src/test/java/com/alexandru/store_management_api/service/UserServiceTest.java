package com.alexandru.store_management_api.service;

import com.alexandru.store_management_api.entity.User;
import com.alexandru.store_management_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_savesAndReturns() {
        User user = new User("alice", "pass", "ROLE_USER");
        when(userRepository.save(user)).thenReturn(user);

        User saved = userService.createUser(user);

        assertThat(saved).isSameAs(user);
        verify(userRepository).save(user);
    }

    @Test
    void getAllUsers_returnsList() {
        User u1 = new User("a","p","r");
        when(userRepository.findAll()).thenReturn(List.of(u1));

        var list = userService.getAllUsers();
        assertThat(list).hasSize(1);
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_found() {
        User u = new User("bob","p","r");
        when(userRepository.findById(1L)).thenReturn(Optional.of(u));

        User res = userService.getUserById(1L);
        assertThat(res).isSameAs(u);
    }

    @Test
    void getUserById_notFound_throws() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserById(2L));
    }

    @Test
    void updateUser_updatesWhenFound() {
        User existing = new User("old","p","ROLE_USER");
        when(userRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);

        User updated = new User();
        updated.setUsername("new");
        updated.setRole("ROLE_ADMIN");

        User res = userService.updateUser(3L, updated);
        assertThat(res.getUsername()).isEqualTo("new");
        assertThat(res.getRole()).isEqualTo("ROLE_ADMIN");
        verify(userRepository).save(existing);
    }

    @Test
    void updateUser_returnsNullWhenNotFound() {
        when(userRepository.findById(4L)).thenReturn(Optional.empty());
        User res = userService.updateUser(4L, new User());
        assertThat(res).isNull();
    }

    @Test
    void deleteUser_invokesRepository() {
        doNothing().when(userRepository).deleteById(5L);
        userService.deleteUser(5L);
        verify(userRepository).deleteById(5L);
    }
}
