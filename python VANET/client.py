import sys
import socket
import time
import datetime

print "VANETs Broadcast [Simple Flooding]" 
UDP_IP = input("Broadcast IP:")
UDP_PORT = 5005
MESSAGE = datetime.datetime.fromtimestamp(time.time()).strftime('%Y-%m-%d %H:%M:%S')

print "UDP target IP:", UDP_IP
print "UDP target port:", UDP_PORT
print "message:", MESSAGE

sock = socket.socket(socket.AF_INET, # Internet
                     socket.SOCK_DGRAM) # UDP
#sock.bind((UDP_IP, 0))
sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

while(1):
	MESSAGE = datetime.datetime.fromtimestamp(time.time()).strftime('%Y-%m-%d %H:%M:%S')
	print "Send Beacon ",len(MESSAGE), " bytes Message: ", MESSAGE
	sock.sendto(MESSAGE, (UDP_IP, UDP_PORT))
	time.sleep(1)
