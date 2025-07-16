# Python Ports
This repository shows how far Python ports can go <br>
The Blackberry, Tizen and Firefox OS port is just the files zipped <br>
The webOS port required me to make 2 Gzip compressed tarballs (.tar.gz) and a .ar archive (renamed to .ipk) <br>
This bypasses using commands like ares-package <br>
The Firefox OS port is a hybrid app for KaiOS 3.0+ or older and Firefox OS, by combining manifest.json (KaiOS 3.0+) and manifest.webapp (KaiOS 3.0 or older and Firefox OS) <br>
NOTE: The BlackBerry port only works for BlackBerry 8, 9 and 10 <br>
The thing that makes the BlackBerry port from BB8, is because BlackBerry 7 has too old Webkit to run the Python interpreter <br>
All of these ports use Brython, and in development Pyodide, but Pyodide was too powerful for these devices. <br>
<br>
# INSTALLATION: <br>
Firefox OS: <br>
1. Enable developer mode (Settings > Device Information > More info > Developer Mode) <br>
2. Connect the device via USB <br>
3. Use Firefox WebIDE (firefox desktop old versions) or Remote Debugging tools. <br>
   Alternatively, push the app via ADB commands
   ```adb push foxpy.zip /sdcard/foxpy.zip``` <br>
   <br>
KaiOS: <br>
1. Enable Developer Options on KaiOS device (usually via multiple presses on version in settings) <br>
2. Use KaiOS Developer Tools (available on KaiOS site) on your PC <br>
3. Connect device via USB <br>
4. Use the tools to sideload foxpy.zip <br>
   Alternatively, use webide or Firefox's remote debugging to install apps <br>
   <br>
TV webOS: <br>
1. Enable Developer Mode app on your webOS TV <br>
2. On PC, install webOS TV CLI tools (ares-install, ares-setup-device, etc.) <br>
3. Connect TV and PC on the same network <br>
4. Use webOS IDE to install the app <br>
<br>
Tizen: <br>
1. Enable Developer Mode on your Tizen device <br>
2. Install sdb on your PC <br>
3. Connect device via USB or Wi-Fi <br>
4. Install app with: <br>
  ```sdb install tizenpy.wgt``` <br>
  <br>
BlackBerry 8 or newer: <br>
1. Enable Developer Mode on the device <br>
2. Connect device over USB or Wi-Fi <br>
3. Use Sachesi or DDPB tools on PC to sideload .bar files <br>
