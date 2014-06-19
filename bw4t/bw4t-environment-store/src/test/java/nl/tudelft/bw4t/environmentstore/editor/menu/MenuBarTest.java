package nl.tudelft.bw4t.environmentstore.editor.menu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Frame;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.controller.ZoneController;
import nl.tudelft.bw4t.environmentstore.editor.menu.controller.AbstractMenuOption;
import nl.tudelft.bw4t.environmentstore.editor.menu.controller.FileFilters;
import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeBlockFrame;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeSequenceFrame;
import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.environmentstore.util.NoMockOptionPrompt;
import nl.tudelft.bw4t.environmentstore.util.OptionPrompt;
import nl.tudelft.bw4t.environmentstore.util.YesMockOptionPrompt;
import nl.tudelft.bw4t.map.BlockColor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MenuBarTest {
	
    /**
     * The base directory of all files used in the test.
     */
    private static final String BASE = System.getProperty("user.dir") + "/src/test/resources/";

    /**
     * The path of the xml file used to test the open button.
     */
    private static final String FILE_OPEN_PATH = BASE + "open.xml";

    /**
     * The path of the xml file used to test the open button.
     */
    private static final String FILE_EXPORT_PATH = BASE + "export/";

    /**
     * The path of the xml file used to save dummy data
     */
    private static final String FILE_SAVE_PATH = BASE + "dummy.xml";

    /**
     * The Scenario editor that is spied upon for this test.
     */
    private EnvironmentStore envStore;
    
    /**
     * The Map Panel Controller to create the environmentstore from.
     */
    private MapPanelController mapController;

    /**
     * File choose used to mock the behaviour of the user.
     */
    private JFileChooser filechooser;

    /**
     * Setup the testing environment by creating the scenario editor and
     * assigning the editor attribute to a spy object of the ScenarioEditor.
     */
    @Before
    public void setUp() throws IOException {
    	mapController = new MapPanelController(5, 5);
    	envStore = spy(new EnvironmentStore(mapController));

        filechooser = mock(JFileChooser.class);

        /*
        Retrieve the controllers, should be one for each item.
         */
        ActionListener[] listeners = envStore.getTopMenuBar().getMenuItemFileOpen().getActionListeners();
        AbstractMenuOption menuOption = (AbstractMenuOption) listeners[0];
        menuOption.setCurrentFileChooser(filechooser);
    }
    
    @After
    public final void closeEditor() throws IOException {
    	envStore.dispose();
    }
    
    
    /**
     * Test if the menu exit works
     * Case: New window, press exit without any changes.
     */
    @Test
    public void menuOptionsListenersTest() {
    	/* Reset the controller to the spied objects controller */
        assertEquals(envStore.getTopMenuBar().getMenuItemFileNew().getActionListeners().length, 1);
        assertEquals(envStore.getTopMenuBar().getMenuItemFileOpen().getActionListeners().length, 1);
        assertEquals(envStore.getTopMenuBar().getMenuItemFileSave().getActionListeners().length, 1);
        assertEquals(envStore.getTopMenuBar().getMenuItemFileSaveAs().getActionListeners().length, 1);
        assertEquals(envStore.getTopMenuBar().getMenuItemPreview().getActionListeners().length, 1);
        assertEquals(envStore.getTopMenuBar().getMenuItemFileExit().getActionListeners().length, 1);
        
        assertEquals(envStore.getTopMenuBar().getMenuItemRandomizeRooms().getActionListeners().length, 1);
        assertEquals(envStore.getTopMenuBar().getMenuItemRandomizeBlocks().getActionListeners().length, 1);
        assertEquals(envStore.getTopMenuBar().getMenuItemRandomizeSequence().getActionListeners().length, 1);
    }
    
    @Test
    public void menuOptionRandomizeRoomsTest() {
    	ZoneController[][] zcArray = mapController.getZoneControllers();
    	envStore.getTopMenuBar().getMenuItemRandomizeRooms().doClick();
    	assertFalse(zcArray.equals(mapController.getZoneControllers()));
    }
    
    @Test
    public void menuOptionRandomizeBlocksTest() {
    	boolean found = false;
    	ZoneController[][] bc = mapController.getZoneControllers();
    	envStore.getTopMenuBar().getMenuItemRandomizeRooms().doClick();
    	envStore.getTopMenuBar().getMenuItemRandomizeBlocks().doClick();
    	for (Frame f : Frame.getFrames()) {
    		if (f instanceof RandomizeBlockFrame) {
    			found = true;
    			((RandomizeBlockFrame) f).getApplyButton().doClick();
    		}
    	}
    	assertTrue(found);
    	assertFalse(bc.equals(mapController.getZoneControllers()));
    }
    
    @Test
    public void menuOptionRandomizeSequenceTest() {
    	boolean found = false;
    	List<BlockColor> bc = mapController.getSequence();
    	envStore.getTopMenuBar().getMenuItemRandomizeSequence().doClick();
    	for (Frame f : Frame.getFrames()) {
    		if (f instanceof RandomizeSequenceFrame) {
    			found = true;
    			((RandomizeSequenceFrame) f).getRandomizeButton().doClick();
    			((RandomizeSequenceFrame) f).getApplyButton().doClick();
    		}
    	}
    	assertTrue(found);
    	assertFalse(bc.equals(mapController.getSequence()));
    }   
    @Test
    public void menuOptionPreviewTest() {
    	boolean found = false;
    	envStore.getTopMenuBar().getMenuItemPreview().doClick();
    	for (Frame f : Frame.getFrames()) {
    		if (f instanceof JFrame) {
    			JFrame frame = (JFrame) f;
    			if (frame.getTitle().equals("Map Preview")) {
    				found = true;
    			}
    			f.dispose();
    		}
    	}
    	assertTrue(found);
    }
    @Test
    public void fileFiltersTest() {
    	assertTrue(Arrays.equals(FileFilters.goalFilter().getExtensions(), new String[] {
    		"goal"
    	}));
    	assertTrue(Arrays.equals(FileFilters.xmlFilter().getExtensions(), new String[] {
    		"xml"
    	}));
    	assertTrue(Arrays.equals(FileFilters.mapFilter().getExtensions(), new String[] {
    		"map"
    	}));
    	assertTrue(Arrays.equals(FileFilters.masFilter().getExtensions(), new String[] {
    		"mas2g"
    	}));
    }
    @Test
    public void menuBarGettersSettersTest() {
    	MenuBar mb = new MenuBar();
    	mb.setLastFileLocation("Test");
    	assertTrue(mb.hasLastFileLocation());
    	assertTrue(mb.getLastFileLocation().equals("Test"));
    	mb.setLastFileLocation(null);
    	assertFalse(mb.hasLastFileLocation());
    }
}
