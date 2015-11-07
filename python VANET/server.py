import socket



#myIP = input("Insert my IP:")
myID = '1'
UDP_IP = ""
UDP_PORT = 5005
UDP_BROADCAST_IP = '192.168.2.255'

sock = socket.socket(socket.AF_INET, # Internet
                     socket.SOCK_DGRAM) # UDP
sock.bind((UDP_IP, UDP_PORT))
sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

neighborDict = {}

while True:
	message, addr = sock.recvfrom(1024) # buffer size is 1024 bytes
	data = message.split(" ")	
	messageID = data[0] 
	seqNumber = data[2]
	carName = data[1]
	if messageID != myID:
		print "Receive Beacon From: ",addr[0],":",addr[1]," Message: ", message
		if neighborDict.has_key(messageID):
			if neighborDict[messageID] >= seqNumber:
				print "Drop older message from ",messageID," seqNumber : ",seqNumber
			else:
				neighborDict[messageID] = int(seqNumber)
				#Rebroadcast Message
				print "Rebroadcast ", message
				sock.sendto(message,(UDP_BROADCAST_IP,UDP_PORT))
		else:
			#Found new neighbor!
			print "New neighbor ID: ",messageID," Name: ",carName
			neighborDict[messageID] = int(seqNumber)
			
