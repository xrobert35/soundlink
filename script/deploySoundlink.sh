echo "#Move to /SoundLink/Code_Source/soundlink"
cd /SoundLink/Code_Source/soundlink
echo "# PULL "
git pull
echo "#Move to SoundLink_Server"
cd SoundLink_Server
echo "#STOP TOMCAT"
/bin/su pi /SoundLink/apache-tomcat-8.5.9/bin/shutdown.sh
echo "#mvn package"
mvn package
echo "#Move to /SoundLink/apache-tomcat-8.5.9/webapps"
cd /SoundLink/apache-tomcat-8.5.9/webapps
echo "#CLEAN"
rm -rf soundlink_server
rm soundlink_server.war
echo "#CP /SoundLink/Code_Source/soundlink/SoundLink_Server/soundlink_server/target/soundlink_server.war ."
cp /SoundLink/Code_Source/soundlink/SoundLink_Server/soundlink_server/target/soundlink_server.war .
echo "#START TOMCAT"
/bin/su pi /SoundLink/apache-tomcat-8.5.9/bin/startup.sh