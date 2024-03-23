package dataaccess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import core.Accounts;
import core.Note;
import core.NoteOverview;
import core.User;
import json.AccountsPersistence;

@TestInstance(Lifecycle.PER_CLASS)
public class RemoteNotesAccessTest {

    private Accounts testAccounts;

    private WireMockConfiguration wireMockConfig;

    private WireMockServer wireMockServer;

    private RemoteNotesAccess dataAccess;

    private ObjectMapper objectmapper = AccountsPersistence.getObjectMapper();

    @BeforeEach
    private void setup() throws URISyntaxException {
        wireMockConfig = WireMockConfiguration.wireMockConfig().port(8089);
        wireMockServer = new WireMockServer(wireMockConfig.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockConfig.portNumber());
        dataAccess = new RemoteNotesAccess(new URI("http://localhost:8089/notes"));
        addTestUsers();
    }

    private void addTestUsers() {
        final Note note = new Note("title1", "text1");
        final Note note2 = new Note("title2", "text2");
        final Note note3 = new Note("title3", "text3");
        final Note note4 = new Note("title4", "text4");
        final NoteOverview noteoverview = new NoteOverview();
        final NoteOverview noteoverview2 = new NoteOverview();

        noteoverview.addNote(note);
        noteoverview.addNote(note2);
        noteoverview2.addNote(note3);
        noteoverview2.addNote(note4);

        final User employee1 = new User("testuserone", "Password1", noteoverview);
        final User employee2 = new User("testusertwo", "Password2", noteoverview2);

        testAccounts = new Accounts();
        testAccounts.addUser(employee1);
        testAccounts.addUser(employee2);
    }

    @Test
    public void testSelectedIndex() {
        assertEquals(0, dataAccess.getSelectedIndex());
        dataAccess.setSelectedIndex(2);
        assertEquals(2, dataAccess.getSelectedIndex());
        dataAccess.setSelectedIndex(3);
        assertEquals(3, dataAccess.getSelectedIndex());
    }

    @Test
    public void testGetAccounts() throws Exception {
        WireMock.stubFor(WireMock.get(urlEqualTo("/accounts"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)
        .withHeader("Content-Type", "application/json")
        .withBody(objectmapper.writeValueAsString(testAccounts))));
        assertEquals(testAccounts.getAccounts().get(0).getUsername(), dataAccess.readAccounts().getAccounts().get(0).getUsername());
        assertEquals(testAccounts.getAccounts().get(1).getUsername(), dataAccess.readAccounts().getAccounts().get(1).getUsername());
    }
    
    @Test
    public void testLogin() throws Exception {
        WireMock.stubFor(WireMock.post(urlEqualTo("/authenticate-user?username=testuserone&password=Password1"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)
        // .withHeader("Content-Type", "application/json")
        .withBody(objectmapper.writeValueAsString(testAccounts.getUser("testuserone")))));
        
        assertEquals(testAccounts.getUser("testuserone").getPassword(), dataAccess.userLogin("testuserone", "Password1").getPassword());
        
        WireMock.stubFor(WireMock.post(urlEqualTo("/authenticate-user?username=testuserone&password=Password1"))
        .willReturn(WireMock.aResponse()
        .withStatus(404)
        .withBody(objectmapper.writeValueAsString(testAccounts.getUser("testuserone")))));
    }
    
    @Test
    public void testGetLoggedInUser() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> dataAccess.getLoggedInUser());
        
        WireMock.stubFor(WireMock.post(urlEqualTo("/authenticate-user?username=testuserone&password=Password1"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)
        .withBody(objectmapper.writeValueAsString(testAccounts.getUser("testuserone")))));
        dataAccess.userLogin("testuserone", "Password1");
        
        assertEquals("testuserone", dataAccess.getLoggedInUser().getUsername());
    }
    
    @Test
    public void testCreateUser() throws Exception {
        WireMock.stubFor(WireMock.post(urlEqualTo("/create-user"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)
        .withBody(objectmapper.writeValueAsString(testAccounts.getUser("testuserone")))));
        dataAccess.createUser(testAccounts.getUser("testuserone"));
        
    }

    @Test
    public void testAddNote() throws Exception {
        WireMock.stubFor(WireMock.post(urlEqualTo("/authenticate-user?username=testuserone&password=Password1"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)
        .withBody(objectmapper.writeValueAsString(testAccounts.getUser("testuserone")))));
        dataAccess.userLogin("testuserone", "Password1");
        
        WireMock.stubFor(WireMock.put(urlEqualTo("/create-note?username=testuserone"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)));
        dataAccess.addNote(new Note("test", "test"));
    }

    @Test
    public void testGetNote() throws Exception {
        WireMock.stubFor(WireMock.get(urlEqualTo("/user/note?username=testuserone&index=0"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)
        .withBody(objectmapper.writeValueAsString(testAccounts.getUser("testuserone").getNoteByIndex(0)))));

        assertEquals("title1", dataAccess.getNote("testuserone", 0).getTitle());
    }
    
    @Test
    public void testSort() throws Exception {
        WireMock.stubFor(WireMock.post(urlEqualTo("/authenticate-user?username=testuserone&password=Password1"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)
        .withBody(objectmapper.writeValueAsString(testAccounts.getUser("testuserone")))));
        dataAccess.userLogin("testuserone", "Password1");

        WireMock.stubFor(WireMock.get(urlEqualTo("/user/sort-created?username=testuserone"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)));

        dataAccess.sortNotesByCreatedDate();

        WireMock.stubFor(WireMock.get(urlEqualTo("/user/sort-title?username=testuserone"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)));

        dataAccess.sortNotesByTitle();

        WireMock.stubFor(WireMock.get(urlEqualTo("/user/sort-edited?username=testuserone"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)));

        dataAccess.sortNotesByLastEditedDate();
    }

    @Test
    public void testDeleteNote() throws Exception {
        WireMock.stubFor(WireMock.post(urlEqualTo("/authenticate-user?username=testuserone&password=Password1"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)
        .withBody(objectmapper.writeValueAsString(testAccounts.getUser("testuserone")))));
        dataAccess.userLogin("testuserone", "Password1");

        WireMock.stubFor(WireMock.delete(urlEqualTo("/delete-note?username=testuserone&index=0"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)));
        dataAccess.deleteNote(0);
    }
    
    @Test
    public void testGetNoteOverview() throws Exception {
        WireMock.stubFor(WireMock.post(urlEqualTo("/authenticate-user?username=testuserone&password=Password1"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)
        .withBody(objectmapper.writeValueAsString(testAccounts.getUser("testuserone")))));
        dataAccess.userLogin("testuserone", "Password1");

        WireMock.stubFor(WireMock.get(urlEqualTo("/user/noteOverview?username=testuserone"))
        .willReturn(WireMock.aResponse()
        .withStatus(200)
        .withBody(objectmapper.writeValueAsString(testAccounts.getUser("testuserone").getNoteOverview()))));
        assertEquals("title1", dataAccess.getUserNoteOverview().getNotes().get(0).getTitle());
    }
    
    @AfterEach
    public void stopWireMockServer() {
        wireMockServer.stop();
    }
}