# Distributed Systems Project 1

The goal of this project is to implement Raymond's Algorithm in the a distributed environment, such as Amazon Web Services EC2 Instances.


To run the code complete the following steps.
## Initial Set up
### when reviewing your EC2 instance, 
###  add a security rule to allow all port access.
###   make sure you can reach the distributed networks
ping <\server_address\>  
### install git
sudo yum install git
### update java
sudo yum install java-1.8.0
### for compiling java
sudo yum install java-1.8.0-openjdk-devel
### remove older java
sudo yum remove java-1.7.0-openjdk
### if you wish to keep the old java version,
###  use the following to determine the default versions to use
sudo /usr/sbin/alternatives --config java
sudo /usr/sbin/alternatives --config javac
### to pull the git repository
git clone https://github.com/rashidsabbir/DistributedSysProject1.git

## JAR Files
### in the DistributedSysProject1 directory,
###  to run a Single Threaded Server instance,
###   (where other processes are blocked) 
java -jar Server.jar \<port_number\>
### to run a Multi-threaded Server instance
###  where multiple clients can access the system at a time
java -jar MultiThread.jar \<port_number\>
### to run a Client instance
java -jar Client.jar \<host_address\> \<port_number\>  
