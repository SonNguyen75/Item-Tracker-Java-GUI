# Item-Tracker
A GUI Java app that can be used to track food and drink items through a Springboot server

# Description
- A GUI Java app created with Java Swing used to track items like food and drink. Basic fields of food and drink items like names, measurements, expiry date and price can be stored 
using a server created with Springboot.
- Data stored by the app will be automatically saved and loaded by the app.
- User can track create a new item with a dialog, delete an item, or see which item is currently expired, not expired or almost expired. 

# Building
1) Clone the repo
2) Open 2 of the folder containing the project in your preferred IDE(2 folders are: FoodTrackerAppClient and FoodTrackerAppServer, Intellij was used to build this project)
3) Make sure to build and run the FoodTrackerAppServer first and then the FoodTrackerAppClient (Reversed order will cause the Client side not working as intended)
4) Configure the SDK of the project to Java SDK 17
5) Run and use the app

# Issues
- When notify the user the error when they left a field beside "notes" emptied. The "Add Item" dialog box will ocassionally quit on itself

# Contributing
Nam Son Nguyen (UI and Server)
