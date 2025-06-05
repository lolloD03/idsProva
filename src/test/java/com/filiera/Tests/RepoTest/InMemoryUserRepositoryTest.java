package com.filiera.Tests.RepoTest;


import com.filiera.model.users.Acquirente;
import com.filiera.model.users.User;
import com.filiera.repository.InMemoryUserRepository;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private InMemoryUserRepository repository;
    private User user;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
        user = new Acquirente(
                "testUser",
                "password123",
                "test@example.com",
                "Test"
        );
    }

    @Test
    void save_shouldStoreUser() {
        repository.save(user);
        Optional<User> found = repository.findById(user.getId());
        assertTrue(found.isPresent());
        assertEquals(user, found.get());
    }

    @Test
    void findById_shouldReturnCorrectUser() {
        repository.save(user);
        Optional<User> found = repository.findById(user.getId());
        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getName());
    }

    @Test
    void findById_shouldReturnEmptyWhenUserNotExists() {
        Optional<User> result = repository.findById(UUID.randomUUID());
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        User user2 = new Acquirente( "secondUser", "abc", "second@example.com","SecondTest");
        repository.save(user);
        repository.save(user2);

        List<User> all = repository.findAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(user));
        assertTrue(all.contains(user2));
    }

    @Test
    void deleteById_shouldRemoveUser() {
        repository.save(user);
        repository.deleteById(user.getId());

        Optional<User> result = repository.findById(user.getId());
        assertTrue(result.isEmpty());
    }

}