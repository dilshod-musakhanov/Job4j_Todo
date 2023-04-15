The project job4j_todo

Technology stack to be used: 
Java 17
Maven 4.00
Spring boot, 
Thymeleaf, 
Bootstrap, 
Hibernate 5.6.11, 
PostgreSql 42.2.9
Lombok 1.18.22
Liquibase 4.15.04
log4j 1.2.17
h2database 2.1.214


Description of the project:
1. Schema of the Task table with the fields id, title, description, created, done. 
   Location /db/
   Upload script via liquibase.

2. Views.
   Application provides the following options:
   - View all tasks:  a list of all tasks displaying the title and creation date of each task.
   - Pending tasks: a list of all pending tasks displaying the title and creation date of each task.
   - Completed tasks: a list of all completed tasks displaying the title and creation date of each task.
   - Add new tasks: a form to add new task by adding title and description.
   If we click on the task title on any list it goes to edit form where we can find edit, delete, and back buttons
   - Edit button will take to update form where we can edit title or description and change the status of done then it
     will redirect to the page with a list of all tasks.
   - Delete button will delete the task and redirect to the page with a list of all tasks.
   - Back button will take to the previous page.
   In case of error in any CRUD operations it will take to the page with a list of all tasks and displays applicable error message. 

3. The application has layers: Controllers, Services, and Persistence.

More details to be added by the completion of the project...

Author: Dilshod Musakhanov
Contact: musakhanov@yahoo.com