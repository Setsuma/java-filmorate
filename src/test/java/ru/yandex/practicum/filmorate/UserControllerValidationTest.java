package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.time.LocalDate;

@WebMvcTest(UserController.class)
public class UserControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDbStorage userStorage;
    @MockBean
    private UserService userService;

    @Test
    public void addUser_ValidData_Returns200() throws Exception {
        String userJson = "{\"login\": \"johndoe\", \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"birthday\": \"2000-01-01\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addUser_EmptyData_Returns400() throws Exception {
        String userJson = "";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void addUser_EmptyEmail_Returns400() throws Exception {
        String userJson = "{\"login\": \"johndoe\", \"name\": \"John Doe\", \"email\": \"\", \"birthday\": \"2000-01-01\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void addUser_InvalidEmail_Returns400() throws Exception {
        String userJson = "{\"login\": \"johndoe\", \"name\": \"John Doe\", \"email\": \"example.com\", \"birthday\": \"2000-01-01\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void addUser_EmptyLogin_Returns400() throws Exception {
        String userJson = "{\"login\": \"\", \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"birthday\": \"2000-01-01\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void addUser_LoginWithSpaces_Returns400() throws Exception {
        String userJson = "{\"login\": \"john doe\", \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"birthday\": \"2000-01-01\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void addUser_EmptyName_Returns200() throws Exception {
        String userJson = "{\"login\": \"johndoe\", \"name\": \"\", \"email\": \"john.doe@example.com\", \"birthday\": \"2000-01-01\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addUser_FutureBirthday_Returns400() throws Exception {
        String userJson = "{\"login\": \"johndoe\", \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"birthday\": \"2024-01-01\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void addUser_PresentBirthday_Returns200() throws Exception {
        String userJson = "{\"login\": \"johndoe\", \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"birthday\": \"" + LocalDate.now() + "\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}