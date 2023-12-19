package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

@WebMvcTest(FilmController.class)
public class FilmControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmDbStorage filmStorage;
    @MockBean
    private FilmService filmService;

    @Test
    public void addFilm_ValidData_Returns200() throws Exception {
        String filmJson = "{\"name\": \"Interstellar\", \"description\": \"A fantastic film with amazing visuals.\", \"releaseDate\": \"1895-12-29\", \"duration\": 1}";

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addFilm_EmptyData_Returns400() throws Exception {
        String filmJson = "";

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void addFilm_EmptyName_Returns400() throws Exception {
        String filmJson = "{\"name\": \"\", \"description\": \"A fantastic film with amazing visuals.\", \"releaseDate\": \"2022-01-01\", \"duration\": 1}";

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void addFilm_ShortDescription_Returns200() throws Exception {
        String shortDescription = "е".repeat(199);

        String filmJson = "{\"name\": \"Inception\", \"description\": \"" + shortDescription + "\", \"releaseDate\": \"2022-01-01\", \"duration\": 150}";

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addFilm_MaxLengthDescription_Returns200() throws Exception {
        String longDescription = "е".repeat(200);

        String filmJson = "{\"name\": \"Interstellar\", \"description\": \"" + longDescription + "\", \"releaseDate\": \"2022-01-01\", \"duration\": 180}";

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addFilm_LongDescription_Returns400() throws Exception {
        String longDescription = "е".repeat(201);

        String filmJson = "{\"name\": \"Interstellar\", \"description\": \"" + longDescription + "\", \"releaseDate\": \"2022-01-01\", \"duration\": 180}";

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void addFilm_InvalidReleaseDate_Returns400() throws Exception {
        String filmJson = "{\"name\": \"Interstellar\", \"description\": \"A fantastic film with amazing visuals.\", \"releaseDate\": \"1895-12-27\", \"duration\": 180}";

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void addFilm_MinReleaseDate_Returns200() throws Exception {
        String filmJson = "{\"name\": \"The Matrix\", \"description\": \"An iconic science fiction film.\", \"releaseDate\": \"1895-12-28\", \"duration\": 136}";

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addFilm_FutureReleaseDate_Returns200() throws Exception {
        String filmJson = "{\"name\": \"The Matrix\", \"description\": \"An iconic science fiction film.\", \"releaseDate\": \"2024-01-01\", \"duration\": 136}";

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addFilm_NegativeDuration_Returns400() throws Exception {
        String filmJson = "{\"name\": \"Interstellar\", \"description\": \"A fantastic film with amazing visuals.\", \"releaseDate\": \"2022-01-01\", \"duration\": -180}";

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void addFilm_ZeroDuration_Returns400() throws Exception {
        String filmJson = "{\"name\": \"The Dark Knight\", \"description\": \"A gripping movie with a brilliant performance by Heath Ledger.\", \"releaseDate\": \"2022-01-01\", \"duration\": 0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}