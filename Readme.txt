List of files submitted:
createdb.sql
dropdb.sql
Populate.java
DB3UI.java
Readme.txt
ojdbc7.jar

The Range query will work on the first instance. Once it woorks it will not refresh the page so the previous selection remains.

The text box displays the current queries being executed.

Populate.java runs with ojdbc7.jar and DB3UI.java runs with ojdbc6.jar and sdoapi.jar
below are the compile and run commands for both the files

For Populate.java
Compile - javac -cp .:ojdbc7.jar Populate.java
Run - java -cp .:ojdbc7.jar Populate building.xy hydrant.xy firebuilding.txt

For DB3UI
Compile - javac -cp .:ojdbc6.jar:sdoapi.jar DB3UI.java
Run - java -cp .:ojdbc6.jar:sdoapi.jar DB3UI map.jpg