keytool -genkeypair -alias DTMJ -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore dtmj.p12 -validity 3650
mv dtmj.p12 src/main/resources/