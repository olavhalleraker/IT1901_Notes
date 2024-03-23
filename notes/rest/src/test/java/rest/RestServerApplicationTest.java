package rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.User;
import json.AccountsPersistence;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(
    classes = {NotesController.class, NotesService.class, RestServerApplication.class})
@WebMvcTest
public class RestServerApplicationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private NotesService notesService;

  private ObjectMapper objectMapper = AccountsPersistence.getObjectMapper();

  private String getUrl(String... segments) {
    String url = "/" + NotesController.NOTES_SERVICE_PATH;
    for (String seg : segments) {
      url = url + "/" + seg;
    }
    return url;
  }

  @BeforeAll
  public void setup() throws IllegalStateException, IOException {
    // objectMapper = AccountsPersistence.getObjectMapper();
    notesService.setTestMode();
  }

  @Test
  public void contextLoads() throws Exception {
    assertNotNull(objectMapper);
  }

  @Test
  public void testGetUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(getUrl("user?username=12345"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    mockMvc.perform(MockMvcRequestBuilders.get(getUrl("user?username=testuserone"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testAuthenticateUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post(getUrl("authenticate-user?username=testuserone&password=Password1"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    mockMvc.perform(MockMvcRequestBuilders.post(getUrl("authenticate-user?username=testuserone&password=p"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException().getMessage().equals("Invalid login")));
  }

  @Test
  public void testCreateUser() throws Exception {
    User user = new User("testuserthree", "Password3", null);

    mockMvc.perform(MockMvcRequestBuilders.put(getUrl("create-user"))
        .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(objectMapper.writeValueAsString(user)).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mockMvc.perform(MockMvcRequestBuilders.get(getUrl("user?username=testuserthree"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    mockMvc.perform(
        MockMvcRequestBuilders.post(getUrl("authenticate-user?username=testuserthree&password=Password3"))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mockMvc.perform(MockMvcRequestBuilders.put(getUrl("create-user"))
        .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .content(objectMapper.writeValueAsString(user)).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isConflict());
  }

  @Test
  public void testGetNote() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(getUrl("user/note?username=testuserone&index=0"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mockMvc.perform(MockMvcRequestBuilders.get(getUrl("user/note?username=testuserone&index=3"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testDeleteNote() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete(getUrl("delete-note?username=testuserone&index=3"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());

    mockMvc.perform(MockMvcRequestBuilders.delete(getUrl("delete-note?username=testuserone&index=0"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testSort() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post(getUrl("user/sort-edited?username=testuserone"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    mockMvc.perform(MockMvcRequestBuilders.post(getUrl("user/sort-edited?username=testuse"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    mockMvc.perform(MockMvcRequestBuilders.post(getUrl("user/sort-title?username=testuserone"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    mockMvc.perform(MockMvcRequestBuilders.post(getUrl("user/sort-title?username=testuse"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    mockMvc.perform(MockMvcRequestBuilders.post(getUrl("user/sort-created?username=testuserone"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    mockMvc.perform(MockMvcRequestBuilders.post(getUrl("user/sort-created?username=testuse"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());

  }

  @AfterAll
  public void tearDown() {
    Path.of(System.getProperty("user.home")
        + "/springbootserver-test.json").toFile().delete();
  }
}
