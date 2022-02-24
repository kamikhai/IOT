import os
from RPi import GPIO
 
step = 1 

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM) 

clk = 17
dt = 18
sw = 27
redPin = 12
greenPin = 19
bluePin = 13
GPIO.setup(redPin,GPIO.OUT)
GPIO.setup(greenPin,GPIO.OUT)
GPIO.setup(bluePin,GPIO.OUT)
 
GPIO.setup(clk, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
GPIO.setup(dt, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
GPIO.setup(sw, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
 
counter = 0
clkLastState = GPIO.input(clk)
dtLastState = GPIO.input(dt)
swLastState = GPIO.input(sw)

def red():
    GPIO.output(redPin,GPIO.HIGH)
    GPIO.output(greenPin,GPIO.LOW)
    GPIO.output(bluePin,GPIO.LOW)


def green():
    GPIO.output(redPin,GPIO.LOW)
    GPIO.output(greenPin,GPIO.HIGH)
    GPIO.output(bluePin,GPIO.LOW)


def blue():
    GPIO.output(redPin,GPIO.LOW)
    GPIO.output(greenPin,GPIO.LOW)
    GPIO.output(bluePin,GPIO.HIGH)


def changeColor():
    global counter
    if counter % 3 == 0:
        red()
    elif counter % 3 == 1:
        green()
    else:
        blue()


def clkClicked(channel):
    global counter
    global step
 
    clkState = GPIO.input(clk)
    dtState = GPIO.input(dt)
 
    if clkState == 0 and dtState == 1:
        counter = counter + step
        print ("Counter ", counter)
    changeColor()
    
    
def dtClicked(channel):
    global clkLastState
    global counter
    clkState = GPIO.input(clk)
    dtState = GPIO.input(dt)
    
    if clkState == 1 and dtState == 0:
        counter = counter - step
        print ("Counter ", counter)
    changeColor()     
 
 
def swClicked(channel):
    GPIO.output(redPin,GPIO.HIGH)
    GPIO.output(greenPin,GPIO.LOW)
    GPIO.output(bluePin,GPIO.HIGH)          
 
GPIO.add_event_detect(clk, GPIO.FALLING, callback=clkClicked, bouncetime=300)
GPIO.add_event_detect(dt, GPIO.FALLING, callback=dtClicked, bouncetime=300)
GPIO.add_event_detect(sw, GPIO.FALLING, callback=swClicked, bouncetime=300)
 
print("Start monitoring input")
 
while True:
    try:
        x = 1
    except KeyboardInterrupt:
        print('2')
        break
GPIO.cleanup()