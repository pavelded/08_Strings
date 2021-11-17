import java.util.ArrayList;
import java.util.List;

public class TodoList {

    ArrayList<String> todoList= new ArrayList<>();

    public void add(String todo) {
        todoList.add(todo);
    }

    public void add(int index, String todo) {
        if (index < 0) {
        } else if (index > todoList.size()) {
            todoList.add(todo);
        } else {
            todoList.add(index, todo);
        }

    }

    public void edit(String todo, int index) {
        if (todoList.lastIndexOf(todo) == index) {
            todoList.remove(index);
            todoList.add(index, todo);
        } else if (index > todoList.size()) {

        } else {
            todoList.remove(index);
            todoList.add(index, todo);
        }
    }

    public void delete(int index) {
        if (index >= 0 && index < todoList.size()) {
            todoList.remove(index);
        }
    }

    public ArrayList<String> getTodos() {
        return todoList;
    }

}