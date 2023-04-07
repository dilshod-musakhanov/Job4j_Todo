Application TODO list

Technology stack to be used: 
Spring boot, 
Thymeleaf, 
Bootstrap, 
Hibernate, 
PostgreSql

Description of the project:
1. Schema of the Task table with the fields id, description, created, done. Location /db/
   Upload script via liquibase.
2. Views.
   A page with a list of all tasks. The table displays the name, creation date, and status (completed or not).
   Add a "Add task" button on the page with the list.
   On the page with the list, add three links: All, Completed, and New. When clicking on the links, 
   the table should display all tasks, only completed tasks, or only new tasks, respectively.
   When clicking on a task, navigate to a page with a detailed description of the task.
   On the page with the detailed description, add buttons for: Mark as completed, Edit, and Delete.
   If the user clicks the "Mark as completed" button, the task's status should change to a completed.
   The "Edit" button should navigate the user to a separate page for editing the task.
   The "Delete" button should delete the task and redirect to the list of all tasks.
3. The application should have three layers: Controllers, Services, and Persistence.