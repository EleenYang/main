package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.EditBookCommand;
import seedu.address.logic.commands.EditBookCommand.EditBookDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.BookName;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditBookCommand object
 */
public class EditBookCommandParser implements Parser<EditBookCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditBookCommand
     * and returns an EditBookCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBookCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AUTHOR, PREFIX_RATING, PREFIX_TAG);

        EditBookDescriptor editBookDescriptor = new EditBookDescriptor();

        if (!argMultimap.getValue(PREFIX_NAME).isPresent() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBookCommand.MESSAGE_USAGE));
        }

        BookName bookName = ParserUtil.parseBookName(argMultimap.getValue(PREFIX_NAME).get());
        editBookDescriptor.setName(bookName);

        if (argMultimap.getValue(PREFIX_AUTHOR).isPresent()) {
            editBookDescriptor.setAuthor(ParserUtil.parseAuthor(argMultimap.getValue(PREFIX_AUTHOR).get()));
        }
        if (argMultimap.getValue(PREFIX_RATING).isPresent()) {
            editBookDescriptor.setRating(ParserUtil.parseRating(argMultimap.getValue(PREFIX_RATING).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editBookDescriptor::setTags);

        if (!editBookDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditBookCommand.MESSAGE_NOT_EDITED);
        }

        return new EditBookCommand(new BookNameContainsExactKeywordsPredicate(bookName), editBookDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}