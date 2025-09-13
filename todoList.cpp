#include <iostream>
#include <vector>
#include <limits>
#include <cctype>  
using namespace std;

/* Anisha Penikalapati
   Aug 11, 2025
   Program Description: Allows user to add tasks with due dates to their to-do list 
   and check off completed tasks, delete tasks, and see remaining tasks.
*/


// VALIDATING DATE FORMAT //

bool isValidDateFormat(string& date) {
    
    if (date.length() != 10) {
        return false;
    }
    
    if (date[4] != '-' || date[7] != '-') {
        return false;
    }

    // Check YYYY, MM, DD parts are digits
    for (int i = 0; i < 10; i++) {
        if (i == 4 || i == 7){
            continue;
        }
        if (!isdigit(date[i])) {
            return false;
        }
    }

    // check month range 01-12
    int month = stoi(date.substr(5, 2));
    if (month < 1 || month > 12) {
        return false;
    }

    // check day range 01-31
    int day = stoi(date.substr(8, 2));
    if (day < 1 || day > 31) {
        return false;
    }

    return true;
}

// ADD TASK FUNCTION //

void addTask(vector<string>& tasks, vector<bool>& completed, vector<string>& dueDates){
    // Variables
    string task_description, due_date;
    
    cin.ignore(numeric_limits<streamsize>::max(), '\n');
    
    // Asking for task description
    cout << "Enter task description: ";
    getline(cin, task_description);
    
    do {
        cout << "Enter due date (YYYY-MM-DD): ";
        getline(cin, due_date);
        if (!isValidDateFormat(due_date)) {
            cout << "Invalid date format! Please enter date as YYYY-MM-DD.\n";
        }
    } while (!isValidDateFormat(due_date));
    
    tasks.push_back(task_description);
    completed.push_back(false);
    dueDates.push_back(due_date);
    cout << "Task Added! \n\n";
}

// SHOW TASKS FUNCTION //

void showTasks(vector<string>& tasks, vector<bool>& completed, vector<string>& dueDates){
    cout << "\n --- To-Do List --- \n";
    
    // if no tasks
    if (tasks.empty()) {
        cout << "No tasks yet!\n";
        return;
    }
    
    for(int i = 0; i < tasks.size(); i++){
        if(completed[i]){
            cout << i + 1 << ". " << "[✓] " << tasks[i] << " (Due: " << dueDates[i] << ")\n";
        }
        else{
            cout << i + 1 << ". " << "[ ] " << tasks[i] << " (Due: " << dueDates[i] << ")\n";
        }
    }
    cout << "\n";
}

// COMPLETE TASK FUNCTION //

void completeTask(vector<bool>& completed, int total_tasks) {
    
    // Variable
    int task_number;
    bool validInput = true;
    
    // getting task number to mark complete
    
    try{
        cout << "Enter task number to mark complete: ";
        cin >> task_number;
        
         if (cin.fail()) { // if user entered letters
            throw runtime_error("Invalid input! Please enter a number.");
        }
        
    }
    
    catch(runtime_error &e){
        cout << e.what() << endl;
        cin.clear();  // clears the error
        cin.ignore(numeric_limits<streamsize>::max(), '\n'); // discards the wrong input
        validInput = false;
    }
    
    if (task_number > 0 && task_number <= total_tasks && validInput) {
        completed[task_number - 1] = true;
        cout << "Task marked as completed!\n";
    } 
    else if (task_number < 0 || task_number > total_tasks) {
        cout << "Invalid task number!\n";
    }
}

// DELETE TASK FUNCTION //

void deleteTask(vector<string>& tasks, vector<bool>& completed, vector<string>& dueDates){
    
    // Variable
    int task_number;
    bool validInput = true;
    // getting task number to delete
    try{
        cout << "Enter task number to delete: ";
        cin >> task_number;
        
        if (cin.fail()) { // if user entered letters
            throw runtime_error("Invalid input! Please enter a number.");
        }
        
    }
    
    catch(runtime_error &e){
        cout << e.what() << endl;
        cin.clear();  // clears the error
        cin.ignore(numeric_limits<streamsize>::max(), '\n'); // discards the wrong input
        validInput = false;
    }
    
    if (task_number > 0 && task_number <= tasks.size() && validInput) {
        tasks.erase(tasks.begin() + (task_number - 1));
        completed.erase(completed.begin() + (task_number - 1));
        dueDates.erase(dueDates.begin() + (task_number - 1));
        cout << "Task deleted!\n";
    } 
    else if (task_number < 0 || task_number > tasks.size()) {
        cout << "Invalid task number!\n";
    }
}

// MAIN FUNCTION //

int main(){
    // Variables
    int option;
    vector<string> tasks;
    vector<bool> completed;
    vector<string> dueDates;
    bool validInput = true;
    
 do {   
    validInput = true;
     
    // Menu
    cout << "\n ===== 𝐓𝐎𝐃𝐎 𝐋𝐈𝐒𝐓 𝐌𝐄𝐍𝐔 ===== \n";
    cout << "1. Show Tasks \n";
    cout << "2. Add Task \n";
    cout << "3. Complete Task \n";
    cout << "4. Delete Task \n";
    cout << "5. Exit \n";
    
    // Getting option from user
   
    try { 
        cout << "Choose an option: ";
        cin >> option;

        if (cin.fail()) { // if user entered letters
            throw runtime_error("Invalid input! Please enter a number.");
        }
    }
    catch (const runtime_error& e) {
        cout << e.what() << endl;
        cin.clear();  // clears the error
        cin.ignore(numeric_limits<streamsize>::max(), '\n'); // discards the wrong input
        validInput = false;
    }
     
    if(validInput) {

        if (option == 1) {
             showTasks(tasks, completed, dueDates);
        } 
        else if (option == 2) {
            addTask(tasks, completed, dueDates);
        } 
        else if (option == 3) {
            completeTask(completed, tasks.size());
        } 
        else if (option == 4) {
            deleteTask(tasks, completed, dueDates);
        } 
        else if (option == 5) {
            cout << "Goodbye!\n";
        } 
        else {
            cout << "Invalid choice!\n";
        }
    }
 } while(option != 5);   
}