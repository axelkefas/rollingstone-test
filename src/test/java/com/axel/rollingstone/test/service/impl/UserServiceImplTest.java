package com.axel.rollingstone.test.service.impl;

import com.axel.rollingstone.test.entity.User;
import com.axel.rollingstone.test.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;



@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("tester");
        user.setPassword("testing");

        userService.saveUser(user);

        verify(userRepository).save(userArgumentCaptor.capture());

        User userCapture = userArgumentCaptor.getValue();
        Assert.assertNotNull(userCapture);
        Assert.assertEquals(userCapture.getUsername(),user.getUsername());
    }

    @Test
    public void testGetUsers() {
        User user = new User();
        user.setUsername("tester");
        user.setPassword("testing");

        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        User data = userService.findUserById(anyInt());

        Assert.assertNotNull(data);
        Assert.assertEquals(user.getUsername(),data.getUsername());
    }

    @Test
    public void testUpdateUser() {

        User user = new User();
        user.setUsername("tester");
        user.setPassword("testing");

        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        userService.updateUser(user);

        verify(userRepository).save(userArgumentCaptor.capture());

        User userCapture = userArgumentCaptor.getValue();
        Assert.assertNotNull(userCapture);
        Assert.assertEquals(userCapture.getUsername(),user.getUsername());
    }

    @Test
    public void testDeleteUser() {

        User user = new User();
        user.setUsername("tester");
        user.setPassword("testing");

        userService.deleteUser(anyInt());

        verify(userRepository).deleteById(anyInt());

    }

    @Test
    public void testLogin() {
        User user = new User();
        user.setUsername("tester");
        user.setPassword("testing");

        Mockito.when(userRepository.findUserByUsernameAndPassword(any(),any())).thenReturn(Optional.of(user));

        String token = userService.login(any(),any());

        Assert.assertNotNull(token);
    }

    @Test
    public void testFindUserById() {

        User user = new User();
        user.setUsername("tester");
        user.setPassword("testing");

        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        User data = userService.findUserById(anyInt());

        Assert.assertNotNull(data);
        Assert.assertEquals(user.getUsername(),data.getUsername());
    }
}