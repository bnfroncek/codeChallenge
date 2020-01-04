1. The purpose of this Github repo was to store my source code that I created for the MS3 Coding Challenge.
2. All the needed files are contained in this repository to get the code running. You must install the SQLite JDBC driver. You must also add opencsv and all of its requirements to your buildpath when building the project.
3. My approach was pretty straightforward as I've had to deal with CSV Parsing before. I downloaded opencsv which is an open source library
   This allowed me to open the original CSV and parse it easily as well as creating and writing to a new CSV easily.
   I started with making multiple helper methods which would facilitate how I planned on writing the code as well as make the code easier to read.
   My first method was createNewDatabase this was straightforward and would create a new database and the table in the database if one does not exist.
   Next was my ParseCSV method. This is where the bulk of the heavy lifting is done. It checks line by line in the CSV and checks if its a good record or not.
   This is based on white spaces in the CSV and by double checking and making sure the length of the line is also correct.
   If the record was good I would then call my Insert method. It just parses the line and adds it to the database.
   If the record was bad that line would be sent to the bad csv.
   After all the parsing is done this same method prints off the log file containing the stats of the CSV.
   I also currently have the program only looking for files in the "C:/Users/user/eclipse-workspace/challenge/" directory. This could easily be changed to look in a users current directory or default it to somewhere like documents. 
   The only downside to my program that I have discovered so far is that it is not the fastest. Because it inserts record by record it is slowed by how many records there are. A better solution I think would be to create a list of good records and add every good line to it. And then perform only one insert on the entire batch. However I am not sure how to do this.