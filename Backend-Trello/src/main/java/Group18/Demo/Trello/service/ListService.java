package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.model.Task;
import Group18.Demo.Trello.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ListService {
    @Autowired
    ListRepository listRepository;

    public List<Group18.Demo.Trello.model.List> createListsForBoard(Board board) {
        List<Group18.Demo.Trello.model.List> lists = new ArrayList<>();

        //Create 3 lists, to do, doing and done
        Group18.Demo.Trello.model.List toDoList = new Group18.Demo.Trello.model.List(board,"To_Do");
        Group18.Demo.Trello.model.List doingList = new Group18.Demo.Trello.model.List(board,"Doing");
        Group18.Demo.Trello.model.List doneList = new Group18.Demo.Trello.model.List(board, "Done");

        //Save lists to the database
        saveList(toDoList);
        saveList(doingList);
        saveList(doneList);

        //Add and return lists
        lists.add(toDoList);
        lists.add(doingList);
        lists.add(doneList);
        return lists;
    }

    public Group18.Demo.Trello.model.List getList(int listId) {
        Group18.Demo.Trello.model.List list = listRepository.findById(listId).get();
        return list;
    }

    public String saveList(Group18.Demo.Trello.model.List list) {
        try {
            listRepository.save(list);
            return "List successfully saved";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to save list";
        }
    }
}
