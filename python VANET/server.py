import socket

myIP = input("Insert my IP:")
UDP_IP = ""
UDP_PORT = 5005

sock = socket.socket(socket.AF_INET, # Internet
                     socket.SOCK_DGRAM) # UDP
sock.bind((UDP_IP, UDP_PORT))

while True:
    data, addr = sock.recvfrom(1024) # buffer size is 1024 bytes
    if addr[0] != myIP:
	print "Receive Beacon From: ",addr[0],":",addr[1]," Message: ", data
