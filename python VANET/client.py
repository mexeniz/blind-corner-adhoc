import sys
import socket
import time
import datetime
import random

UDP_PORT = 5005
#print "VANETs Broadcast [Simple Flooding] on port ",UDP_PORT 
#UDP_IP = input("Broadcast IP X.X.X.255 :")
UDP_IP = '192.168.2.255'

#print "UDP target IP:", UDP_IP
#print "UDP target port:", UDP_PORT
#print "message:", MESSAGE

sock = socket.socket(socket.AF_INET, # Internet
                     socket.SOCK_DGRAM) # UDP
#sock.bind((UDP_IP, 0))
# set up broadcast
sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

carID = "2"
name = "RPi-2"
seqNumber = 1
velocityX = random.randrange(2,20,4)
positionX = 0
positionY = 290
boundX = 550
boundY = 500

while(1):
        velocityX = random.randrange(2,20,1)
        positionX += velocityX
        if positionX > boundX :
                positionX = positionX-boundX
                #seqNumber = 1
                #ran = random.randrange(10,1000,1)
                #carID = ""+str(ran)
                #name = "RPi-"+str(ran)
        MESSAGE = "{0} {1} {2} {3} {4}".format(carID,name,seqNumber,positionX,positionY)
        print "Send Beacon ",len(MESSAGE), " bytes Message: ", MESSAGE
        sock.sendto(MESSAGE, (UDP_IP, UDP_PORT))
        seqNumber += 1
        time.sleep(0.1)

