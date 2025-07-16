# Python Ports
This repository shows how far Python ports can go <br>
The Tizen, OpenHarmony and Firefox OS port is just the files zipped <br>
The webOS port required me to make 2 Gzip compressed tarballs (.tar.gz) and a .ar archive (renamed to .ipk) <br>
This bypasses using commands like ares-package <br>
<br>
# INSTALLATION: <br>
Firefox OS: <br>
- Enable developer mode (Settings > Device Information > More info > Developer Mode) <br>
- Connect the device via USB <br>
- Use Firefox WebIDE (firefox desktop old versions) or Remote Debugging tools. <br>
   Alternatively, push the app via ADB commands: <br>
   ``adb push foxpy.zip /sdcard/foxpy.zip`` <br>
   <br>
TV webOS: <br>
- Enable Developer Mode app on your webOS TV <br>
- On PC, install webOS TV CLI tools (ares-install, ares-setup-device, etc.) <br>
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
   - Download DevEco IDE for "hdc" <br>
   - Unzip the harmonypy.app file from the harmonypy.zip <br>
   - Connect your device to your PC <br>
   - Open a Terminal/Command prompt <br>
   - Verify that its connected: <br>
     ```hdc list targets``` <br>
   - Install it via: <br>
     ```hdc shell am start -n org.bubbudert.harmonypy/.harmonypy``` <br>
