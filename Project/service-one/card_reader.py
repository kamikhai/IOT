#!/usr/bin/env python

import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522
from time import sleep
from kafka import KafkaProducer
from json import dumps

GPIO.setwarnings(False)

producer = KafkaProducer(bootstrap_servers=['192.168.0.102:9092', '192.168.0.102:9093', '192.168.0.102:9094'], 
        value_serializer=lambda x: dumps(x).encode('utf-8'))

reader = SimpleMFRC522()

try:
        while True:
                id, text = reader.read()
                print(id)
                print(text)
                data = {'name' : text.strip(), 'id' : id}
                producer.send('request', value = data)
                sleep(2)

finally:
        GPIO.cleanup()