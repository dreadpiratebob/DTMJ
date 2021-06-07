apt update && apt upgrade -y
apt install snapd
snap install core

keytool -genkeypair -alias DTMJ -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore src/main/resources/keystore/dtmj.p12 -validity 3650