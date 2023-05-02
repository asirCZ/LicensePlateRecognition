#include <Servo.h>

Servo gateServo;
const int GATE_OPEN_POSITION = 98;
const int GATE_CLOSED_POSITION = 9;
const int GATE_TIMEOUT_MS = 5000; // 5 seconds
unsigned long lastGateOpenTime = 0;

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(9600);
  while (!Serial) {
    ;  // wait for serial port to connect.
  }
  Serial.println("Initialization complete");
  gateServo.attach(8);
  gateServo.write(GATE_OPEN_POSITION);
}

void loop() {
  if (Serial.available() > 0) {
    openGateForInput();
    lastGateOpenTime = millis();
  }
  if (millis() - lastGateOpenTime > GATE_TIMEOUT_MS) {
    gateServo.write(GATE_CLOSED_POSITION);
  }
}

void openGateForInput() {
  gateServo.write(GATE_CLOSED_POSITION);
  while (Serial.available() > 0) {
    Serial.read();
  }
  gateServo.write(GATE_OPEN_POSITION);
}
