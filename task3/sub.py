import paho.mqtt.client as mqtt_client

def connect_mqtt() -> mqtt_client:
    def on_connect(client, userdata, flags, rc):
        if rc == 0:
            print ('connected')
        else:
            print ('failed to connect')
    client = mqtt_client.Client('sub')
    client.on_connect = on_connect
    client.tls_set(tls_version=mqtt_client.ssl.PROTOCOL_TLS)
    client.username_pw_set("ITIS_team_5", "pass")
    client.connect("09629404c2c24cccad58723dff2ed544.s1.eu.hivemq.cloud", 8883)
    return client
    
def subscribe(client: mqtt_client):
    def on_message(client, userdata, msg):
        print(f"Received '{msg.payload}' from '{msg.topic}' topic")
    client.subscribe('itis/team_5')
    client.on_message = on_message
    
try:
    client = connect_mqtt()
    subscribe(client)
    client.loop_forever()
except KeyboardInterrupt:
    print('2')
    client.loop_stop()

