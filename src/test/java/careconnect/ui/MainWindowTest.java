package careconnect.ui;

import static careconnect.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;

import careconnect.logic.LogicManager;
import careconnect.model.Model;
import careconnect.model.ModelManager;
import careconnect.model.UserPrefs;
import careconnect.storage.JsonAddressBookStorage;
import careconnect.storage.JsonUserPrefsStorage;
import careconnect.storage.StorageManager;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class MainWindowTest extends ApplicationTest {
    @TempDir
    public Path temporaryFolder;

    @Override
    public void start(Stage primaryStage) {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        LogicManager logic = new LogicManager(model, storage);

        MainWindow mainWindow = new MainWindow(new Stage(), logic);
        mainWindow.show();
    }

    @Test
    public void testMenuBar_hasTwoMenus() {
        // Find the MenuBar using its fx:id
        MenuBar menuBar = lookup("#menuBar").query();

        // Assert that the MenuBar is not null
        assertNotNull(menuBar);

        // Assert that the MenuBar contains exactly 2 menus
        assertEquals(2, menuBar.getMenus().size());

        // Get the first and second menus
        Menu firstMenu = menuBar.getMenus().get(0);
        Menu secondMenu = menuBar.getMenus().get(1);

        // Verify the text of the first and second menu
        assertEquals("File", firstMenu.getText(), "The first menu should be 'File'");
        assertEquals("Help", secondMenu.getText(), "The second menu should be 'Help'");
    }
}
