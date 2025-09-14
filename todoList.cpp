#include <iostream>
#include <vector>
#include <limits>
#include <cctype>  
#include <fstream>
#include <sstream>
#include <map>
#include <ctime>
using namespace std;
#define RESET   "\033[0m"
#define RED     "\033[31m"
#define GREEN   "\033[32m"
#define YELLOW  "\033[33m"
#define CYAN    "\033[36m"

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

// TWO DIGITS FUNCTION //

string twoDigits(int n) {
    if (n < 10) {
        return "0" + to_string(n);
    }
    return to_string(n);
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
    cout << CYAN << "\n --- Your To-Do List ---\n" << RESET;
    
    time_t timestamp = time(0);
    struct tm datetime = *localtime(&timestamp);
    string today = to_string(datetime.tm_year + 1900) + "-" +
                   twoDigits(datetime.tm_mon + 1) + "-" +
                   twoDigits(datetime.tm_mday);

    for (int i = 0; i < tasks.size(); i++) {
        string color = RESET;
        string statusIcon = "[ ]";

        if (completed[i]) {
            color = GREEN;
            statusIcon = "[✓]";
        } else if (dueDates[i] < today) {
            color = RED;
            statusIcon = "[!]";
        } else if (dueDates[i] == today) {
            color = YELLOW;
            statusIcon = "[*]";
        }

        cout << i + 1 << ". " << color << statusIcon << " "
             << tasks[i] << "  (Due: " << dueDates[i] << ")" << RESET << "\n";
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


// SAVE TASKS TO FILE //

void saveTasks(vector<string>& tasks, vector<bool>& completed, vector<string>& dueDates){
    ofstream file("tasks.txt");
    
    if (!file) {
        cout << "Error saving tasks!\n";
        return;
    }
    
    for(int i = 0; i < tasks.size(); i++){
        file << tasks[i] << "|" << dueDates[i] << "|" << completed[i] << "\n";
    }
    
    file.close();
}

// LOAD TASKS FROM A FILE //

void loadTasks(vector<string>& tasks, vector<bool>& completed, vector<string>& dueDates){
    
    ifstream file("tasks.txt");
    if (!file.is_open()){
        return; 
    }
    
    string line = "";
    
    while (getline(file, line)){
        stringstream ss(line); 
        string task, date, done;

        getline(ss, task, '|');   // task description
        getline(ss, date, '|');   // due date
        getline(ss, done, '|');   // completion status
        
        tasks.push_back(task);
        dueDates.push_back(date);
        completed.push_back(done == "1" || done == "true");
    }
    
    file.close();
    
}

// CALENDAR VIEW //

void calendarView(vector<string>& tasks, vector<bool>& completed, vector<string>& dueDates) {
    
    cout << CYAN << "\n === Calendar View ===\n" << RESET;
    
    if (tasks.empty()) {
        cout << "No tasks yet!\n";
        return;
    }
    
    map<string, vector<int>> dateMap;
    string date;
    
    for (int i = 0; i < tasks.size(); i++) {
        dateMap[dueDates[i]].push_back(i);
    }

    for (auto& entry : dateMap) {
        cout << CYAN << "── " << entry.first << " ──" << RESET << "\n";
        for (int idx : entry.second) {
            string icon;
            if (completed[idx]) {
                icon = "[✓] ";
            }
            else {
                icon = "[ ] ";
            }
            cout << "   " << icon << tasks[idx] << "\n";
        }
        cout << "\n";
    }
}


// OVERDUE FUNCTION //

void overdue(vector<string>& tasks, vector<bool>& completed, vector<string>& dueDates){
    
    time_t timestamp = time(0);
    struct tm datetime = *localtime(&timestamp);
    
    string today = to_string(datetime.tm_year + 1900) + "-" +
                   twoDigits(datetime.tm_mon + 1) + "-" +
                   twoDigits(datetime.tm_mday);
    
    cout << "\n=== Overdue Tasks (Before " << today << ") ===\n";

    bool found = false;
    for (int i = 0; i < tasks.size(); i++) {
        if (!completed[i] && dueDates[i] < today) {
            cout << " - " << tasks[i] << " (Due: " << dueDates[i] << ")\n";
            found = true;
        }
    }
    if (!found) {
        cout << "No overdue tasks!\n";
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
    
    loadTasks(tasks, completed, dueDates);
    
 do {   
    validInput = true;
     
    // Menu
    cout << CYAN << "\n╔══════════════════════════════╗\n";
    cout <<   "║        📌   TO-DO MENU        ║\n";
    cout <<   "╚══════════════════════════════╝" << RESET << "\n";

    cout << YELLOW << " 1 " << RESET << " → Show Tasks\n";
    cout << YELLOW << " 2 " << RESET << " → Add Task\n";
    cout << YELLOW << " 3 " << RESET << " → Complete Task\n";
    cout << YELLOW << " 4 " << RESET << " → Delete Task\n";
    cout << YELLOW << " 5 " << RESET << " → Calendar View\n";
    cout << YELLOW << " 6 " << RESET << " → Overdue Tasks\n";
    cout << YELLOW << " 7 " << RESET << " → Exit\n\n";

    
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
            calendarView(tasks, completed, dueDates);
        }
        else if (option == 6) {
            overdue(tasks, completed, dueDates);
        } 
        else if (option == 7) {
            saveTasks(tasks, completed, dueDates);
            cout << "Goodbye!\n";
        }
        else {
            cout << "Invalid choice!\n";
        }
    }
 } while(option != 7);   
}
