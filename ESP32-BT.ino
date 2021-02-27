#define CAMERA_MODEL_AI_THINKER
#define FILE_PHOTO "/pic.jpg"

#include "esp_camera.h"
#include "pins.h"
#include "BluetoothSerial.h"

BluetoothSerial SerialBT;

void initializeBluetooth()
{
  if(!SerialBT.begin("ESP32CAM"))
  {
    Serial.println("Bluetooth unable to start");
    ESP.restart();
  }

  else
  {
    Serial.println("Bluetooth discoverable");
  }

  SerialBT.register_callback(BlueToothCallBack);   //callback can be changed?
  Serial.println("Device Discoverable via Bluetooth");
}

//this function appears to call back from bluetooth app to change the parameters of the camera
void BlueToothCallBack(esp_spp_cb_event_t event, esp_spp_cb_param_t *param) //what does this do??????
{
  if(event == ESP_SPP_SRV_OPEN_EVT) //triggers when new client connects //server open event
  {
    Serial.println("Client Connected"); //client????
  }
  else if(event == ESP_SPP_DATA_IND_EVT)//data index event
  {
    Serial.printf("ESP_SPP_DATA_IND_EVT len = %d, handle = %d\n\n", param -> data_ind.len, param -> data_ind.handle);
    String stringRead = String(*param -> data_ind.data);//is it receiving a string and converting it to *param?
    int paramInt = stringRead.toInt() - 48; // -48 ???? //what number is sent here to take 48 from?
    Serial.printf("paramInt: %d\n", paramInt);
    setCameraParam(0); //changed paramInt                           //set camera param with what?

  }
}

void setCameraParam(int paramInt) //this comes from an outside source from flutter app from video
{
  sensor_t  *s = esp_camera_sensor_get();
  switch(paramInt)
  {
    case 4:
      s -> set_framesize(s, FRAMESIZE_UXGA); //what are these frame sizes?????
      break;

    case 3:
      s -> set_framesize(s, FRAMESIZE_SXGA);
      break;

    case 2:
      s -> set_framesize(s, FRAMESIZE_XGA);
      break;

    case 1:
      s -> set_framesize(s, FRAMESIZE_SVGA);
      break;

    case 0:
    default:
      s -> set_framesize(s, FRAMESIZE_VGA);   //most likely, stick to this
      break;
  }
  Serial.println("\n\n\nPicture to be taken and sent. CameraParam set\n\n\n");///////////////////////////////////////

  TakePicAndSend();    //that is it??? take the picture?
}
     
void initializeCamera()
{
  camera_config_t config;
  config.ledc_channel = LEDC_CHANNEL_0;
  config.ledc_timer = LEDC_TIMER_0;
  config.pin_d0 = Y2_GPIO_NUM;
  config.pin_d1 = Y3_GPIO_NUM;
  config.pin_d2 = Y4_GPIO_NUM;
  config.pin_d3 = Y5_GPIO_NUM;
  config.pin_d4 = Y6_GPIO_NUM;
  config.pin_d5 = Y7_GPIO_NUM;
  config.pin_d6 = Y8_GPIO_NUM;
  config.pin_d7 = Y9_GPIO_NUM;
  config.pin_xclk = XCLK_GPIO_NUM;
  config.pin_pclk = PCLK_GPIO_NUM;
  config.pin_vsync = VSYNC_GPIO_NUM;
  config.pin_href = HREF_GPIO_NUM;
  config.pin_sscb_sda = SIOD_GPIO_NUM;
  config.pin_sscb_scl = SIOC_GPIO_NUM;
  config.pin_pwdn = PWDN_GPIO_NUM;
  config.pin_reset = RESET_GPIO_NUM;
  config.xclk_freq_hz = 20000000;
  config.pixel_format = PIXFORMAT_JPEG;
  //init with high specs to pre-allocate larger buffers
  if(psramFound()){
    config.frame_size = FRAMESIZE_UXGA;
    config.jpeg_quality = 10;
    config.fb_count = 2;
  } else {
    config.frame_size = FRAMESIZE_SVGA;
    config.jpeg_quality = 12;
    config.fb_count = 1;
  }
  esp_err_t err = esp_camera_init(&config);
  if(err != ESP_OK)
  {
    Serial.printf("Camera init failed with error 0x%x", err);
    //send an error code to device to reset connection to bluetooth aswell///////////////////////
    return;
  }
}

void TakePicAndSend()
{
  camera_fb_t *fb = NULL;
  esp_err_t res = ESP_OK; //???????????????
  fb = esp_camera_fb_get(); //take a picture
  if(fb -> format != PIXFORMAT_JPEG)
  {
    Serial.println("Error with fb -> format != PIXFORMAT_JPEG");
    return;
  }
                  //writing to Serial, then sent to bluetooth??????
                  //from writeSerialBT(camera_fb_t *fb)
  SerialBT.write(fb -> buf, fb -> len);   //spits gibberish on the screen
  SerialBT.flush();    //Waits for the transmission of outgoing seriel data to finish
  
  Serial.printf("\nbuf = %d,\n len = %d\n\n", fb -> buf, fb -> len);  /////////////////debug///////
  
  Serial.printf("\npicture name: %s\n", FILE_PHOTO);  /////////////////debug///////
  Serial.println("\nWidth: ");  /////////////////debug///////
  Serial.print(fb -> width);  /////////////////debug///////
  Serial.println("\nHeight: ");  /////////////////debug///////
  Serial.print(fb -> height);  /////////////////debug///////
  Serial.println("\nLength: ");  /////////////////debug///////
  Serial.print(fb -> len);  /////////////////debug///////
  
  Serial.printf("\nfb = %d,\n\n", fb);  /////////////////debug///////
                   
  Serial.println("\n\n\nbuf and len serialBT write completed....\n\n\n");/////////////////////

  esp_camera_fb_return(fb);

}

void setup() 
{
  // put your setup code here, to run once:
  Serial.begin(115200);
  initializeBluetooth();
  initializeCamera();

}


void loop() 
{
  //take a picture and send via bluetooth. wait a couple of miliseconds
  //Set up camera //necessary?
  //Take a pic
  //Chop up the pic into an array
  //send each array via bluetooth
  // confirm everything is sent, clean where picture was stored
  //wait to repeat the process

  String request = "NULL";
  
  while(request == "send")
  {
    //take the picture and send
    Serial.println("Bluetooth connection established");
    Serial.println("Picture Taken");
    Serial.println("Picture Sent");
    Serial.println("Finished sending");

    request = "NULL";
  }
}
