# Python Ports
The webOS port required me to make 2 Gzip compressed tarballs (.tar.gz) and a .ar archive (renamed to .ipk) <br>
This bypasses using commands like ares-package <br>
<br>
# INSTALLATION: <br>
Firefox OS: <br>
- Enable Developer mode <br>
- Connect the device via USB <br>
- Push the app to the device:<br>
   ```adb push foxpy.zip /sdcard/foxpy.zip``` <br>
   <br>
KaiOS: <br>
 - On your KaiOS device, enable Developer Mode (in settings or dialing a code) <br>
 - Connect your KaiOS phone to your PC via USB <br>
 - Download KaiOS Developer Tools <br>
 - Install the app: <br>
    ```kadeploy --install foxpy.zip``` <br>
   <br>
TV webOS: <br>
- Enable Developer Mode app on your webOS TV <br>
- On PC, download the webOS IDE <br>
- Connect TV and PC on the same network <br>
- Use webOS IDE to install the app <br>
   <br>
Tizen: <br>
- Enable Developer Mode on your Tizen device <br>
- Install sdb on your PC <br>
- Connect device via USB or Wi-Fi <br>
- Install app with: <br>
  ```sdb install tizenpy.wgt``` <br>
   <br>
OpenHarmony: <br>
- Enable developer mode <br>
- Download DevEco IDE for "hdc" <br>
- Unzip the harmonypy.app file from the harmonypy.zip <br>
- Connect your device to your PC <br>
- Open a Terminal/Command prompt <br>
- Verify that its connected: <br>
   ```hdc list targets``` <br>
- Install it via: <br>
  ```hdc shell am start -n org.bubbudert.harmonypy/.harmonypy``` <br>
     <br>
BlackBerry: <br>
 - Enable Developer Mode <br>
 - Connect the device to your PC <br>
 - Download BlackBerry SDK Tools <br>
 - Replace <ip> with the device serial or ID and <pass> with the device password: <br>
   ```blackberry-deploy -installApp -device <ip> -password <pass> pyberry.bar``` <br>
