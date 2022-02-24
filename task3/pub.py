import os
from RPi import GPIO
import paho.mqtt.client as mqtt
from gpiozero import CPUTemperature
 
step = 1 

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM) 

clk = 17
dt = 18
sw = 27

GPIO.setup(sw, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
 
swLastState = GPIO.input(sw)

def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print ('connected')
    else:
        print ('failed to connect')

def on_publish(client, userdata, result):
    print('temp published \n')
    pass

client = mqtt.Client('publisher')
client.on_connect = on_connect
client.on_publish = on_publish
client.tls_set(tls_version=mqtt.ssl.PROTOCOL_TLS)
client.username_pw_set("ITIS_team_5", "pass")
client.connect("09629404c2c24cccad58723dff2ed544.s1.eu.hivemq.cloud", 8883)
 
def swClicked(channel):
    cpu = CPUTemperature()
    print("temp = ", cpu.temperature)
    client.publish('itis/team_5', str(cpu))
    
 
GPIO.add_event_detect(sw, GPIO.FALLING, callback=swClicked, bouncetime=300)
 
print("Start monitoring input")
 
while True:
    try:
        x = 1
    except KeyboardInterrupt:
        print('2')
        break
GPIO.cleanup()
