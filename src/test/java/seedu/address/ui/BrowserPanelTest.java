package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalBooks.ALI;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.book.Book;
<<<<<<< HEAD

public class BrowserPanelTest extends GuiUnitTest {
=======
import seedu.address.model.person.Person;

public class BrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
    private SimpleObjectProperty<Book> selectedBook = new SimpleObjectProperty<>();
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
<<<<<<< HEAD
        guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedBook));
=======
        //guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedBook));
        guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedPerson));
>>>>>>> 922c72f86ad2b5420953d4580f0969fbec323143
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(BrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        guiRobot.interact(() -> selectedBook.set(ALI));
        URL expectedBookUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + ALI.getBookName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedBookUrl, browserPanelHandle.getLoadedUrl());
    }
}
