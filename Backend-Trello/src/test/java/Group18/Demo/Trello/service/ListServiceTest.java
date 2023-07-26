package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.repository.ListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ListServiceTest {
    @Mock
    private ListRepository listRepository;

    @InjectMocks
    private ListService listService;

    @Test
    public void createListsForBoard() {
        Board board = new Board(1, "Sample Board");
        when(listRepository.save(any(Group18.Demo.Trello.model.List.class))).thenAnswer(invocation -> {
            Group18.Demo.Trello.model.List list = invocation.getArgument(0);
            list.setId(1); // Simulate saving the list and setting its ID
            return list;
        });
        List<Group18.Demo.Trello.model.List> lists = listService.createListsForBoard(board);
        assertNotNull(lists);
        assertEquals(3, lists.size());
        for (Group18.Demo.Trello.model.List list : lists) {
            assertSame(board, list.getBoard());
        }
    }

    @Test
    void getList_ValidListId() {
        int listId = 1;
        Board board = new Board(1, "Sample Board");
        Group18.Demo.Trello.model.List list = new Group18.Demo.Trello.model.List(board, "sample");

        when(listRepository.findById(listId)).thenReturn(Optional.of(list));

        Group18.Demo.Trello.model.List returnList = listService.getList(listId);
        assertNotNull(returnList);
        assertEquals(list, returnList);
        verify(listRepository).findById(listId);
    }

    @Test
    void getList_InternalServerError() {
        int listId = 1;
        Board board = new Board(1, "Sample Board");
        Group18.Demo.Trello.model.List list = new Group18.Demo.Trello.model.List(board, "sample");

        when(listRepository.findById(listId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> listService.getList(listId));
        verify(listRepository).findById(listId);
    }

    @Test
    void saveList_ValidList() {
        Board board = new Board(1, "Sample board");
        Group18.Demo.Trello.model.List list = new Group18.Demo.Trello.model.List(board, "sample");

        String result = listService.saveList(list);
        assertEquals("List successfully saved", result);
        verify(listRepository).save(list);
    }

    @Test
    void saveList_InternalServerError() {
        Board board = new Board(1, "Sample board");
        Group18.Demo.Trello.model.List list = new Group18.Demo.Trello.model.List(board, "sample");

        doThrow(new RuntimeException("Some database error")).when(listRepository).save(list);

        String result = listService.saveList(list);
        assertEquals("Error: Unable to save list", result);
        verify(listRepository).save(list);
    }
}