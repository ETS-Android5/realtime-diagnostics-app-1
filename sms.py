#!/usr/bin/env python3
from twilio.rest import Client
import json 

import serial 


def send_text_not_secure(sid,token,message,sendn,recvn):
	client = Client(sid, token)
	message = client.messages.create(body=message,from_=sendn,to=recvn)
	print("message sent (bad method) - message id = {}".format(message.sid))

def send_text(cred_file, message, sendn, recvn):
	tempf = open(cred_file, 'r')
	cred = json.loads(tempf.read())
	tempf.close()

	account_sid = cred["account_sid"]
	auth_token = cred["auth_token"]
	client = Client(account_sid, auth_token)

	message = client.messages.create(body=message,from_=sendn,to=recvn)
	print("message sent - message id = {}".format(message.sid))

def sms_listener(port_name="/dev/ttyACM0", baud_rate=115200, ign=6, filter_key=["MESG:::","FROM::"]):
	print("starting the SMS listener")
	running = True
	arduino_port = serial.Serial(port_name, baud_rate)
	while (running):
		while (arduino_port.inWaiting() == 0):
			pass
		raw_data = arduino_port.readline()
		processed_data = arduinoString.decode('utf-8').strip()
		data_tag = processed_data[:ign]
		if(data_tag in filter_key):
			print(processed_data[ign:])			


if __name__ == "__main__":
	# send_text('cred.json',"hello this is a test",'+12244791518','+18478686626')
	tsid = "ACff003bbe467e6f77330f29e8baf8b6fc"
	ttoken = "3da9fa49b56789fc259b81e32a6d1efe"
	mod_num = "+18478686626"
	send_text_not_secure(tsid,ttoken,"more tests",'+12244791518',mod_num)

