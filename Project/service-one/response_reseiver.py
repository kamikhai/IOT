#!/usr/bin/env python

import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522
from time import sleep
from json import dumps
from kafka import KafkaConsumer
import json

clk = 15
dt = 14
redPin = 12
greenPin = 19
bluePin = 13

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(clk, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
GPIO.setup(dt, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
GPIO.setup(redPin,GPIO.OUT)
GPIO.setup(greenPin,GPIO.OUT)
GPIO.setup(bluePin,GPIO.OUT)

def green():
    GPIO.output(redPin,GPIO.LOW)
    GPIO.output(greenPin,GPIO.HIGH)
    GPIO.output(bluePin,GPIO.LOW)
def red():
    GPIO.output(redPin,GPIO.HIGH)
    GPIO.output(greenPin,GPIO.LOW)
    GPIO.output(bluePin,GPIO.LOW)
def light():
    GPIO.output(redPin,GPIO.HIGH)
    GPIO.output(greenPin,GPIO.HIGH)
    GPIO.output(bluePin,GPIO.HIGH)

light()

consumer = KafkaConsumer(
 bootstrap_servers=['192.168.0.102:9092', '192.168.0.102:9093', '192.168.0.102:9094'],
 value_deserializer = lambda v: json.loads(v.decode('utf-8')),
 auto_offset_reset='earliest'
)

try:
        consumer.subscribe(topics='response')
        for message in consumer:
                if message.value["allowed"]:
                        green()
                        sleep(1)
                        light()
                else:
                        red()
                        sleep(1)
                        light()
finally:
        GPIO.cleanup()