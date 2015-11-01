#!/bin/bash
sudo service ifplugd stop
sudo ifconfig wlan0 down
sudo iwconfig wlan0 mode ad-hoc
sudo iwconfig wlan0 channel 11
sudo iwconfig wlan0 essid "RPi ad-hoc"
sudo ifconfig wlan0 192.168.2.1 netmask 255.255.255.0  up
sudo service ifplugd start
sudo route add -net 192.168.2.0 netmask 255.255.255.0 dev wlan0

