<h1>C195 Final Project</h1>
Author: Trevor Evans, Email: teva188@wgu.edu Version: 1.2, Date: 12/20/22 Updated 12/26/22<br>
IDE: IntelliJ IDEA Community Edition 2021.3<br>
JDK: Oracle OpenJDk Version 17.0.5<br>
JavaFX Version: javafx-SDK Version 17.0.2<br>
DataBase Driver: mysql connector version j8.0.31<br>
<hr>
<h1>Use Case</h1>
Upon launch user is directed to the login screen<br>
<p><h3>Login Screen</h3>
<ol>
<li>Cancel Button: will close out the window and terminate the program upon click</li>
<li>UserID text bar: space where user should key in their identification</li>
<li>Password text bar: place where user should key in their password</li>
<li>Log in button: upon clicking program will check entered information.<br>
<ul><li>if information is missing or incorrect the user will be informed</li>
<li>if information is valid the user is redirected to the Home screen</li></ul></li></ol></p>
<p><h2 id="Home Screen">Home Screen</h2>
<ol>
<li>Customers button: directs user to the customer's information screen.</li>
<li>Appointments button: Directs the user to the Appointment's information screen.</li>
<li>Reports button: directs the user to the reports display screen.</li>
<li>Cancel button: displays conformation window, upon confirmation logs out user and directs to the login screen.</li></ol></p>
<p><h2>Customers screen cases</h2>
<ol><li>Create Customer button: directs the user to create customer screen.</li>
<li>Customers table: displays list of customers and their information, customer will be selected if clicked.</li>
<li>Search bar: place the user should type in the name of the customer they intend to search.</li>
<li>Search button: will search initiate a search for the customer who was entered in the search bar.<ul>
<li>if no information is entered in the search bar user will be informed</li>
<li>if no customer matches the information entered, in the search bar, the user will be informed</li>
<li>if a customer matches the entered information the customers' table will display only matching customer(s)</li></ul></li>
<li>Update Customer button: directs the user to the update customer screen with the selected customer's info<ul>
<li>if a customer is not selected. The user is informed instead.</li></ul></li>
<li>Appointments button: displays a message containing selected customer's scheduled appointment.(s)<ul>
<li>if no customer is selected user will be informed</li></ul></li>
<li>Delete customer button: deletes selected customer from the database and updates the table.<ul>
<li>if selected customer has a scheduled appointment the user will be informed that such a customer can not be deleted</li>
<li>if no customer is selected user will be informed</li></ul></li>
<li>Cancel button: displays confirmation message, upon confirmation directs to home screen</li></ol></p>
<p><h3>Create New Customer</h3>
<ol><li>Home button: displays a confirmation message, upon confirmation redirects to the Home screen.</li>
<li>Name text field: place where the user should type in the name of the customer.</li>
<li>Address text field: place where user should type in the customer's address.</li>
<li>Phone text field: place where user should enter customer's phone number.</li>
<li>Postal Code text field: place the user should type in the customer's postal code.</li>
<li>Country menu: displays a list of selectable countries for which the customer resides.</li>
<li>Division menu: displays a list of selectable divisions for which the customer is a part of.</li>
<li>Save button: saves the new customer to the database.<ul>
<li>if a text field is left empty the user is informed</li>
<li>if a dropdown menu item is not selected the user is informed</li>
<li>upon a successful save program directs to the Customer Info screen</li></ul></li>
<li>Cancel button: displays a confirmation message, upon confirmation directs to the Customer Info screen</li>
</ol></p>
<p><h3>Update Customer Screen</h3><ol>
<li>Home button: displays a confirmation message, upon confirmation directs to the Home screen.</li>
<li>Name text field: place where can update the name of the customer.</li>
<li>Address text field: place where user can update customer's address.</li>
<li>Phone text field: place where user can update customer's phone number.</li>
<li>Postal Code text field: place the user can update the customer's postal code.</li>
<li>Country menu: displays a list of selectable countries for which the customer resides.</li>
<li>Division menu: displays a list of selectable divisions for which the customer is a part of.</li>
<li>Save button: saves the updated customer to the database.<ul>
<li>if a text field is left empty the user is informed.</li>
<li>if a dropdown menu item is not selected the user is informed.</li>
<li>upon a successful save program directs to the Customer Info screen.</li></ul></li>
<li>Cancel button: displays a confirmation message, upon confirmation directs to the Customer Info screen.</li></ol></p>
<p><h2>Appointments screen</h2>
<ol><li>Radio buttons change which appointments display in the appointments table</li>
<ul><li>All button: the default selection on Appointments initialization. It displays all appointments made</li>
<li>Week button: when selected, it displays, only, appointments made within weeks time</li>
<li>Month button: when selected, it displays, only, appointments made within a month time</li></ul>
<li>Search text input bar: the area that the user should key in the name of the appointment they are searching for.</li>
<li>Search button: upon user clicking the search button, it will display the appointment(s) matching the entry in the search bar.</li>
<li>Appointments table: displays all selectable appointments within the restrictions mentioned in <b>Appointments Screen cases</b> 1-4</li>
<li>Create Appointment button: directs the user to the Create New Appointment screen</li>
<li>Update Appointment button: directs the user to the Update Appointment screen populated with the selected appointment information.</li>
<ul><li>If an appointment is not selected the user is informed instead</li></ul>
<li>Delete Appointment button: deletes the selected appointment from the database and updates Appointments Table.</li>
<ul><li>If an appointment is not selected the user is informed instead</li></ul>
<li>Home button: displays confirmation message, upon confirmation directs the user to the Home Screen.</li></ol></p>
<p><h2>Create New Appointment</h2>
<ol><li>Home button: displays a confirmation message, upon confirmation directs the user to the Navigation screen.</li>
<li>Title text field: The area where the user should key in the appointment's title.</li>
<li>Location text field: The area where the user should enter the area of the appointment.</li>
<li>Type menu: a menu displaying the appointment types that the user can select from.</li>
<li>User ID menu: menu that displays all user numbers, The user can select their number from the list.</li>
<li>Contact menu: displays a list of contacts that the user can select from.</li>
<li>Start Date selection box: the area where the user can type in or select the date that the appointment will start.</li>
<li>End Date selection box: the area where the user can type in or select the date that the appointment will end.</li>
<ul><li>If Start Date box is set the End Date box will aut-populates to match the start date as an appointment wouldn't fall and end on separate days.</li></ul>
<li>Customer ID menu: displays a list of all customer IDs that the user can select from.</li>
<li>Start Time menu: displays a list of times that the user can select from for when the appointment is set to start.</li>
<li>End Time menu: displays a list of times that the user can select from for when the appointment is set to end.</li>
<li>Description text field: area where the can write a brief description of the appointment.</li>
<li>Save button: saves the new appointment to the database.</li>
<ul><li>If any information is missing, or times and/dates aren't permissible, it will inform the user of the error instead.</li></ul>
<li>Cancel button: displays a confirmation message. Upon confirmation directs the user to the Appointments screen.</li></ol></p>
<p><h3>Update Appointment</h3></h2>
<ol><li>Home button: displays a confirmation message, upon confirmation directs the user to the Navigation screen.</li>
<li>Title text field: The area where the user should key in the appointment's title.</li>
<li>Location text field: The area where the user should enter the area of the appointment.</li>
<li>Type menu: a menu displaying the appointment types that the user can select from.</li>
<li>User ID menu: menu that displays all user numbers.</li>
<li>Contact menu: displays a list of contacts that the user can select from.</li>
<li>Start Date selection box: the area where the user can type in or select the date that the appointment will start.</li>
<li>End Date selection box: the area where the user can type in or select the date that the appointment will end.</li>
<ul><li>If Start Date box is set the End Date box will auto populates to match the start date as an appointment wouldn't fall and end on separate days.</li></ul>
<li>Customer ID menu: displays a list of all customer IDs that the user can select from.</li>
<li>Start Time menu: displays a list of times that the user can select from for when the appointment is set to start.</li>
<li>End Time menu: displays a list of times that the user can select from for when the appointment is set to end.</li>
<li>Description text field: area where the user can change description of the appointment.</li>
<li>Save button: saves the updated appointment to the database.</li>
<ul><li>If any information is missing, or times and/dates aren't permissible, it will inform the user of the error instead.</li></ul>
<li>Cancel button: displays a confirmation message. Upon confirmation directs the user to the Appointments screen.</li></ol></p>
<p><h2>Reports</h2>
<ol><li>Home button: displays a confirmation message. Upon confirmation directs the user to the Navigation screen.</li>
<li>Generate by Contact ID:</li> <ul><li>The user needs to select a contact ID for whom's appointment(s) they want to generate.</li>
<li>The user can then click the generate button.</li>
<li>A report will generate displaying all appointments associated with the contact whose ID was selected.</li></ul>
<li>Generate by Customer ID:</li> <ul><li>The user needs to select a customer ID for whom appointment(s) they want to generate.</li>
<li>The user can then click the generate button.</li>
<li>A report will generate displaying all appointments associated with the customer whose ID was selected.</li></ul>
<li>Total number of appointments:</li>
<ul><li>combo box: Type is the default selection.</li>
<ul><li>If type is selected when the generate button is clicked a report is generated displaying the number of appointments of each type.</li></ul>
<li>Alternatively, the Month radio button can be selected. Automatically de-selecting the Type button.</li>
<ul><li>If the generate button is clicked while month button is selected, a report will be generated displaying each month and the number of appointments made for that month.</li></ul></ul></ol></p>

