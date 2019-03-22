package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.BookCardHandle;
import guitests.guihandles.BookListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.book.Book;
import seedu.address.ui.BookCard;


/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class BookGuiTestAssert {

    private static final String LABEL_DEFAULT_STYLE = "label";

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(BookCardHandle expectedCard, BookCardHandle actualCard) {
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getAuthor(), actualCard.getAuthor());
        assertEquals(expectedCard.getRating(), actualCard.getRating());

        expectedCard.getTags().forEach(tag ->
            assertEquals(expectedCard.getTagStyleClasses(tag), actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedBook}.
     */
    public static void assertCardDisplaysBook(Book expectedBook, BookCardHandle actualCard) {

        assertEquals(expectedBook.getBookName().fullName, actualCard.getName());
        assertEquals(expectedBook.getAuthor().fullName, actualCard.getAuthor());
        assertEquals(expectedBook.getRating().value, actualCard.getRating());
        assertTagsEqual(expectedBook, actualCard);
    }

    /**
     * Returns the color style for {@code tagName}'s label. The tag's color is determined by looking up the color
     * in {@code BookCard#TAG_COLOR_STYLES}, using an index generated by the hash code of the tag's content.
     *
     * @see BookCard #getTagColorStyleFor(String)
     */
    private static String getTagColorStyleFor(String tagName) {
        switch (tagName) {
        case "popular":
            return "blue";
        case "fantasy":
            return "orange";
        case "classic":
            return "yellow";
        case "novel":
            return "black";
        case "romantic":
            return "blue";
        case "children":
            return "brown";
        case "thriller":
            return "teal";
        case "adventure":
            return "orange";
        case "textbook":
            return "teal";
        case "boring":
            return "read";
        default:
            throw new AssertionError(tagName + " does not have a color assigned.");
        }
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedPerson} with the correct
     * color.
     */
    private static void assertTagsEqual(Book expectedBook, BookCardHandle actualCard) {
        List<String> expectedTags = expectedBook.getTags().stream()
            .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag ->
            assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getTagColorStyleFor(tag)),
                actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that the list in {@code bookListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(BookListPanelHandle bookListPanelHandle, Book... books) {
        for (int i = 0; i < books.length; i++) {
            bookListPanelHandle.navigateToCard(i);
            assertCardDisplaysBook(books[i], bookListPanelHandle.getBookCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code bookListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(BookListPanelHandle bookListPanelHandle, List<Book> books) {
        assertListMatching(bookListPanelHandle, books.toArray(new Book[0]));
    }

    /**
     * Asserts the size of the list in {@code bookListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(BookListPanelHandle bookListPanelHandle, int size) {
        int numberOfPeople = bookListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
