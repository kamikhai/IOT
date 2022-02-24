from RPi import GPIO
from time import sleep

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

counter = 0
clkLastState = GPIO.input(clk)
    
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

try:

        while True:
                clkState = GPIO.input(clk)
                dtState = GPIO.input(dt)
                if clkState != clkLastState:
                        if dtState != clkState:
                                counter += 1
                        else:
                                counter -= 1
                        print(counter)
                if counter % 3 == 0:
                    red()
                elif counter % 3 == 1:
                    green()
                else:
                    blue()
                clkLastState = clkState
                sleep(0.01)
finally:
        GPIO.cleanup()

