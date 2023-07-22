package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.repository.BoardRepository;
import Group18.Demo.Trello.repository.ListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.mock;


class ListServiceTest {
    @InjectMocks
    private ListService listService = mock(ListService.class);

    @Mock
    private ListRepository listRepository;

    @Mock
    private BoardService boardService;

    @Test
    public void testCreateListsForBoard() {
        Board board = new Board(1, "MockBoard");

        List<Group18.Demo.Trello.model.List> lists = listService.createListsForBoard(board);

        Assertions.assertNotNull(lists, "List should be created");
    }
}