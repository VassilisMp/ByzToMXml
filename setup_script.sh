#!/bin/bash
# instalation of MK fonts
# download fonts
wget https://papline.gr/index.php/vivliothiki-downloads/category/27-2015-12-12-21-46-05?download=1922:ekdosi-6-5-portable /tmp
# install unzip
sudo apt-get install unzip -y
# unzip zip file
unzip /tmp/27-2015-12-12-21-46-05?download=1922:ekdosi-6-5-portable -d /tmp
# move font files in local folder
mv /tmp/MK/fonts/* ~/.local/share/fonts
# cache fonts
fc-cache -f -v

# export PATH_TO_FX=/opt/javafx-sdk-11.0.2/lib
# javac --module-path $PATH_TO_FX --add-modules javafx.controls HelloFX.java
# javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml HelloFX.java
# java --module-path $PATH_TO_FX --add-modules javafx.controls HelloFX

# add in intellij Run configuration vm options
# --module-path lib/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml