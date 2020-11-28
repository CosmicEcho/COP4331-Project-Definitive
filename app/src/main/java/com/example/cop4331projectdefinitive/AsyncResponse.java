package com.example.cop4331projectdefinitive;

// Imports
import java.util.List;

/**
 * Interface that is used to populate the RecyclerView for EmployeeActivity by forcing implementation
 * of the callback function onTaskCompleted
 */

public interface AsyncResponse {
    void onTaskCompleted(List<User> userList);
}
