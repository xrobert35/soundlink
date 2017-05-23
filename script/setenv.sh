export JAVA_HOME=/usr/lib/jvm/jdk1.8.0_102
CATALINA_OPTS="$CATALINA_OPTS -server "
CATALINA_OPTS="$CATALINA_OPTS -Xms256m -Xmx738m "
CATALINA_OPTS="$CATALINA_OPTS -Djava.security.egd=file:/dev/./urandom -Djava.awt.headless=true -XX:+UseConcMarkSweepGC "
CATALINA_OPTS="$CATALINA_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,address=8001,server=y,suspend=n "
export CATALINA_OPTS