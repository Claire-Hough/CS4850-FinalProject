# CS 4850: Final Project Version 1
Claire Hough\
cnhfg6

# How to Run:
This program is a maven Java project.\
First, unzip the Server and Client files. Go into both files’ directories until you can see the “src” folders. The point is you need to be in the root folder of the project. In there, launch a command prompt like PowerShell for both the Server and Client. Run the command:\
	mvn clean install\
or \
	mvn clean package
Both should work the same. Then run the following command in the Server PowerShell window first:\
	Java -jar .\target\NetworksServer-1.0-SNAPSHOT.jar\
After the Server is running, run the following command in the Client PowerShell window:\
	Java -jar .\target\NetworksClient-1.0-SNAPSHOT.jar\

# How to Stop:
You can use Crtl+C to end the socket connection, or type a period (.) in the Client to end the connection as well.\
